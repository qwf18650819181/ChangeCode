package com.wanzi.changecode;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.wanzi.changecode.common.StringConstant;
import com.wanzi.changecode.stringaction.StringConverterType;
import com.wanzi.changecode.stringaction.StringListPopupStep;

import java.util.List;
import java.util.Objects;

/**
 * @auth: qwf
 * @date: 2024年2月2日 0002
 * @description:
 */
public class StringAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        Editor editor = (Editor) event.getDataContext().getData(StringConstant.EDITOR);
        Project project = event.getProject();

        // 创建要展示的列表数据
        List<String> typeNameList = StringConverterType.getTypeNameList();

        // 创建列表弹出窗口
        ListPopup listPopup = JBPopupFactory.getInstance()
                .createListPopup(new StringListPopupStep("转换", typeNameList, editor, project));

        // 在屏幕中间显示列表弹出窗口
        listPopup.showInBestPositionFor(Objects.requireNonNull(editor));
    }
}
