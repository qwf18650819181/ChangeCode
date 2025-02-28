package com.wanzi.changecode.autoinsertaction;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.CheckboxTree;
import com.intellij.ui.CheckedTreeNode;
import com.intellij.ui.ScrollPaneFactory;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 功能描述:
 *
 * @author: qiu wanzi
 * @date: 2025年2月28日 星期五
 * @version: 1.0
 */
public class FieldSelectionDialog extends DialogWrapper {
    private CheckboxTree checkboxTree;
    private CheckedTreeNode rootNode;

    public FieldSelectionDialog(Project project, List<String> fieldNames) {
        super(project);
        setTitle("Select Fields to Generate Getters and Setters");

        rootNode = new CheckedTreeNode("All Fields");
        fieldNames.forEach(fieldName -> {
            CheckedTreeNode node = new CheckedTreeNode(fieldName);
            node.setChecked(false);
            rootNode.add(node);
        });

        CheckboxTree.CheckboxTreeCellRenderer checkboxTreeCellRendererBase = new CheckboxTree.CheckboxTreeCellRenderer() {
            @Override
            public void customizeRenderer(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                if (value instanceof CheckedTreeNode) {
                    CheckedTreeNode node = (CheckedTreeNode) value;
                    getTextRenderer().append(node.getUserObject().toString());
                }
            }
        };
        checkboxTree = new CheckboxTree(checkboxTreeCellRendererBase, rootNode);

        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = ScrollPaneFactory.createScrollPane(checkboxTree);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    public List<String> getSelectedFields() {
        return getSelectedFields(rootNode);
    }

    private List<String> getSelectedFields(CheckedTreeNode node) {
        java.util.List<String> selectedFields = new ArrayList<>();
        Enumeration children = node.children();
        while (children.hasMoreElements()) {
            CheckedTreeNode child = (CheckedTreeNode) children.nextElement();
            if (child.isChecked()) {
                selectedFields.add(child.getUserObject().toString());
            }
            selectedFields.addAll(getSelectedFields(child));
        }
        return selectedFields;
    }
}