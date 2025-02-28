package com.wanzi.changecode;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.wanzi.changecode.autoinsertaction.AutoInsertPopupStep;
import com.wanzi.changecode.autoinsertaction.AutoInsertType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * @auth: qwf
 * @date: 2024年2月4日 0004
 * @description:
 */
public class AutoInsertAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {

        Editor editor = event.getData(CommonDataKeys.EDITOR);
        if (editor == null) {
            System.out.println("Editor is null");
            return;
        }

        Project project = event.getProject();
        if (project == null) {
            System.out.println("Project is null");
            return;
        }

        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(editor.getDocument());
        if (!(psiFile instanceof PsiJavaFile)) {
            System.out.println("PsiFile is not a PsiJavaFile");
            return;
        }

        PsiJavaFile psiJavaFile = (PsiJavaFile) psiFile;

        // 创建要展示的列表数据
        List<String> typeNameList = AutoInsertType.getTypeNameList();
        System.out.println(typeNameList);

        // 创建列表弹出窗口
        ListPopup listPopup = JBPopupFactory.getInstance()
                .createListPopup(new AutoInsertPopupStep("插入", project, psiJavaFile, editor));

        // 在屏幕中间显示列表弹出窗口
        listPopup.showInBestPositionFor(Objects.requireNonNull(editor));
    }
}
