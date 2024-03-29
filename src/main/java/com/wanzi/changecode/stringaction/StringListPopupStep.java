package com.wanzi.changecode.stringaction;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.PopupStep;
import com.intellij.openapi.ui.popup.util.BaseListPopupStep;
import com.wanzi.changecode.stringaction.strategy.StringConverter;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

/**
 * 字符串功能处理器
 *
 * @author ly
 */
public class StringListPopupStep extends BaseListPopupStep<String> {

    private Editor editor;
    private Project project;

    public StringListPopupStep(@NotNull String title, @NotNull List<String> values, Editor editor, Project project) {
        super(title, values);
        this.editor = editor;
        this.project = project;
    }

    @Override
    @Nullable
    public PopupStep onChosen(@Nullable String selectedValue, boolean finalChoice) {
        // 处理选中的值
        if (StringUtils.isNotBlank(selectedValue)) {
            String selectedText = editor.getSelectionModel().getSelectedText();
            StringConverter strategyInstance = StringConverterType.getStrategyInstance(selectedValue);
            ReplaceProcess.replaceText(strategyInstance.execute(selectedText), editor, project);
        }
        // 如果是最终选择，则关闭弹出窗口
        return finalChoice ? PopupStep.FINAL_CHOICE : super.onChosen(selectedValue, finalChoice);
    }

    @Override
    public boolean hasSubstep(@Nullable String selectedValue) {
        // 在这里可以定义是否有子步骤
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