package com.wanzi.changecode.autoinsertaction;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.PopupStep;
import com.intellij.openapi.ui.popup.util.BaseListPopupStep;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自动插入功能处理器
 *
 * @author ly
 */
public class AutoInsertPopupStep extends BaseListPopupStep<String> {

    private Project project;
    private Editor editor;
    private PsiJavaFile psiJavaFile;

    public AutoInsertPopupStep(@NotNull String title, Project project, PsiJavaFile psiJavaFile, Editor editor) {
        super(title, AutoInsertType.getTypeNameList());
        this.project = project;
        this.editor = editor;
        this.psiJavaFile = psiJavaFile;
    }

    private static List<String> getFieldNames(PsiJavaFile psiJavaFile) {
        Set<String> hasMethodNames = Arrays.stream(psiJavaFile.getClasses())
                .flatMap(psiClass -> Arrays.stream(psiClass.getAllMethods()))
                .filter(method -> method.getName().startsWith("safeGet") && method.getName().length() > 7)
                .map(method -> method.getName().substring(7, 8).toLowerCase(Locale.ENGLISH) + method.getName().substring(8))
                .collect(Collectors.toSet());
        return Arrays.stream(psiJavaFile.getClasses())
                .flatMap(psiClass -> Arrays.stream(psiClass.getFields()))
                .map(PsiField::getName)
                .filter(s -> !hasMethodNames.contains(s))
                .collect(Collectors.toList());
    }


    @Override
    @Nullable
    public PopupStep onChosen(@Nullable String selectedValue, boolean finalChoice) {

        FieldSelectionDialog dialog = new FieldSelectionDialog(this.project, getFieldNames(this.psiJavaFile));
        dialog.show();

        if (dialog.isOK()) {
            List<String> selectedFields = dialog.getSelectedFields();
            List<PsiField> fields = Arrays.stream(psiJavaFile.getClasses()).flatMap(psiClass -> Arrays.stream(psiClass.getFields())).toList();
            for (PsiField field : fields) {
                if (selectedFields.contains(field.getName())) {
                    generateGetter(field);
                }
            }
        }

        return finalChoice ? PopupStep.FINAL_CHOICE : super.onChosen(selectedValue, finalChoice);
    }

    public void generateGetter(PsiField field) {
        Project project = field.getProject();
        String fieldName = field.getName();
        String fieldType = field.getType().getPresentableText();
        String getterName = "safeGet" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);

        String getterCode;
        if (fieldType.equals("Integer")) {
            getterCode = "\n\tpublic " + fieldType + " " + getterName + "() {\n" +
                    "\t\treturn " + fieldName + " != null ? " + fieldName + " : " + 0 + ";\n" +
                    "\t}\n";
        } else if (fieldType.equals("String")) {
            getterCode = "\n\tpublic " + fieldType + " " + getterName + "() {\n" +
                    "\t\treturn " + fieldName + " != null ? " + fieldName + " : \"\";\n" +
                    "\t}\n";
        } else if (fieldType.startsWith("List")) {
            getterCode = "\n\tpublic " + fieldType + " " + getterName + "() {\n" +
                    "\t\tif (" + fieldName + " == null) " + fieldName + " = new ArrayList<>(); \n\t\treturn " + fieldName + ";\n" +
                    "\t}\n";
        } else {
            getterCode = "\n\tpublic " + fieldType + " " + getterName + "() {\n" +
                    "\t\treturn " + fieldName + ";\n" +
                    "\t}\n";
        }
        Runnable runnable = () -> {
            ApplicationManager.getApplication().runWriteAction(() -> {
                Document document = editor.getDocument();
                document.replaceString(editor.getSelectionModel().getSelectionStart(), editor.getSelectionModel().getSelectionEnd(), getterCode);
            });
        };
        WriteCommandAction.runWriteCommandAction(project, runnable);
    }


    @Override
    public boolean hasSubstep(@Nullable String selectedValue) {
        return false;
    }

    @Override
    public String getTextFor(String value) {
        return value;
    }

    @Override
    public Icon getIconFor(String value) {
        return null;  // Optionally, return an icon here if needed
    }
}