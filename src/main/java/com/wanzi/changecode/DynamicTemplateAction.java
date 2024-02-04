package com.wanzi.changecode;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.wanzi.changecode.common.StringConstant;
import com.wanzi.changecode.dynamictemplateaction.DynamicTemplateListPopupStep;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @auth: qwf
 * @date: 2024年2月4日 0004
 * @description:
 */
public class DynamicTemplateAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        // 创建列表弹出窗口
        ListPopup listPopup = JBPopupFactory.getInstance().createListPopup(new DynamicTemplateListPopupStep(event));
        // 在屏幕中间显示列表弹出窗口
        listPopup.showInBestPositionFor(Objects.requireNonNull((Editor) event.getDataContext().getData(StringConstant.EDITOR)));
    }
}
