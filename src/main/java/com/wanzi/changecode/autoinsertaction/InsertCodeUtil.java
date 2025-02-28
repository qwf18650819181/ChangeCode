package com.wanzi.changecode.autoinsertaction;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.intellij.util.IncorrectOperationException;

/**
 * 功能描述:
 *
 * @author: qiu wanzi
 * @date: 2025年2月26日 星期三
 * @version: 1.0
 */
public class InsertCodeUtil {

    public static void insertMethod(PsiJavaFile psiClass, String methodCode, Editor editor) throws IncorrectOperationException {
        System.out.println("Inserting method into file: " + psiClass.getName());
        PsiElementFactory factory = JavaPsiFacade.getElementFactory(psiClass.getProject());
        PsiMethod method = factory.createMethodFromText(methodCode, psiClass);
        if (method == null) {
            System.out.println("Failed to create method from text.");
            return;
        }
        PsiElement element = psiClass.add(method);
        if (element == null) {
            System.out.println("Failed to add method to PsiClass.");
            return;
        }
        Project project = psiClass.getProject();
        PsiDocumentManager.getInstance(project).doPostponedOperationsAndUnblockDocument(editor.getDocument());
        PsiDocumentManager.getInstance(project).commitDocument(editor.getDocument());
        int offset = element.getTextOffset();
        editor.getCaretModel().moveToOffset(offset);
        editor.getSelectionModel().selectLineAtCaret();
        System.out.println("Method inserted and editor updated.");
    }
}
