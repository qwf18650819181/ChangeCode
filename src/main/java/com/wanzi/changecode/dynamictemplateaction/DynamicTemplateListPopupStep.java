package com.wanzi.changecode.dynamictemplateaction;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.popup.PopupStep;
import com.intellij.openapi.ui.popup.util.BaseListPopupStep;
import com.wanzi.changecode.common.StringConstant;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * 模板功能处理器
 *
 * @author ly
 */
public class DynamicTemplateListPopupStep extends BaseListPopupStep<String> {

    private final AnActionEvent event;

    private static final String TITLE = "自定义";

    public DynamicTemplateListPopupStep(AnActionEvent event) {
        super(TITLE, DynamicTemplate.getTypes());
        this.event = event;
    }

    @Override
    @Nullable
    public PopupStep onChosen(@Nullable String selectedValue, boolean finalChoice) {
        Editor editor = (Editor) this.event.getDataContext().getData(StringConstant.EDITOR);
        // 处理选中的值
        if (StringUtils.isNotBlank(selectedValue)) {
            if (DynamicTemplate.DYNAMIC.equals(selectedValue)) {
                return super.onChosen(selectedValue, finalChoice);
            }
            WriteCommandAction.runWriteCommandAction(this.event.getProject(),
                    () -> ApplicationManager.getApplication().runWriteAction(
                            () -> editor.getDocument().insertString(editor.getSelectionModel().getSelectionStart(), DynamicTemplate.read(selectedValue))));
        }
        // 如果是最终选择，则关闭弹出窗口
        return finalChoice ? PopupStep.FINAL_CHOICE : super.onChosen(selectedValue, false);
    }

    @Override
    public boolean hasSubstep(@Nullable String selectedValue) {
        return false;
    }

    @Nullable
    @Override
    public String getTextFor(String value) {
        // 返回列表项的显示文本
        return value;
    }

    @Nullable
    @Override
    public Icon getIconFor(String value) {
        // 返回列表项的图标，如果不需要图标，则返回 null
        return null;
    }
}