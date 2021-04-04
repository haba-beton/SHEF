package net.atlanticbb.tantlinger.ui.text.dialogs;

import net.atlanticbb.tantlinger.ui.text.TextEditPopupManager;

import javax.swing.*;
import java.awt.*;

public class LinkAttributesPanel extends HTMLAttributeEditorPanel {
  private static final long serialVersionUID = 1L;
  private static final String NEW_WIN = "New Window";
  private static final String SAME_WIN = "Same Window";
  private static final String SAME_FRAME = "Same Frame";
  private static final String[] TARGET_LABELS = {NEW_WIN, SAME_WIN, SAME_FRAME};
  private static final String[] TARGETS = {"_blank", "_top", "_self"};
  private JCheckBox nameCB = null;
  private JCheckBox titleCB = null;
  private JCheckBox openInCB = null;
  private JTextField nameField = null;
  private JTextField titleField = null;
  private JComboBox<String> openInCombo = null;
  private JPanel spacerPanel = null;

  public LinkAttributesPanel() {
    super();
    initialize();
    updateComponentsFromAttribs();
  }

  public void setEnabled(boolean b) {
    super.setEnabled(b);
    nameCB.setEnabled(b);
    titleCB.setEnabled(b);
    openInCB.setEnabled(b);
    nameField.setEditable(nameCB.isSelected() && b);
    titleField.setEditable(titleCB.isSelected() && b);
    openInCombo.setEnabled(openInCB.isSelected() && b);
  }

  private void initialize() {
    GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
    gridBagConstraints6.gridx = 0;
    gridBagConstraints6.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints6.weighty = 1.0;
    gridBagConstraints6.weightx = 0.0;
    gridBagConstraints6.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints6.gridwidth = 2;
    gridBagConstraints6.gridy = 3;
    GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
    gridBagConstraints5.fill = java.awt.GridBagConstraints.NONE;
    gridBagConstraints5.gridy = 2;
    gridBagConstraints5.weightx = 1.0;
    gridBagConstraints5.insets = new java.awt.Insets(0, 0, 5, 0);
    gridBagConstraints5.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints5.gridx = 1;
    GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
    gridBagConstraints4.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints4.gridy = 1;
    gridBagConstraints4.weightx = 1.0;
    gridBagConstraints4.insets = new java.awt.Insets(0, 0, 5, 0);
    gridBagConstraints4.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints4.gridx = 1;
    GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
    gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints3.gridy = 0;
    gridBagConstraints3.weightx = 1.0;
    gridBagConstraints3.insets = new java.awt.Insets(0, 0, 5, 0);
    gridBagConstraints3.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints3.gridx = 1;
    GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
    gridBagConstraints2.gridx = 0;
    gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints2.insets = new java.awt.Insets(0, 0, 5, 5);
    gridBagConstraints2.gridy = 2;
    GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
    gridBagConstraints1.gridx = 0;
    gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints1.insets = new java.awt.Insets(0, 0, 5, 5);
    gridBagConstraints1.gridy = 1;
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
    gridBagConstraints.gridy = 0;
    this.setLayout(new GridBagLayout());
    this.setSize(new java.awt.Dimension(320, 118));
    this.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createTitledBorder(null, i18n.str("attributes"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null), javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    this.add(getNameCB(), gridBagConstraints);
    this.add(getTitleCB(), gridBagConstraints1);
    this.add(getOpenInCB(), gridBagConstraints2);
    this.add(getNameField(), gridBagConstraints3);
    this.add(getTitleField(), gridBagConstraints4);
    this.add(getOpenInCombo(), gridBagConstraints5);
    this.add(getSpacerPanel(), gridBagConstraints6);


    TextEditPopupManager.getInstance().registerJTextComponent(nameField);
    TextEditPopupManager.getInstance().registerJTextComponent(titleField);

  }

  public void updateComponentsFromAttribs() {
    if (attribs.containsKey("name")) {
      nameCB.setSelected(true);
      nameField.setEditable(true);
      nameField.setText(attribs.get("name"));
    }
    else {
      nameCB.setSelected(false);
      nameField.setEditable(false);
    }


    if (attribs.containsKey("title")) {
      titleCB.setSelected(true);
      titleField.setEditable(true);
      titleField.setText(attribs.get("title"));
    }
    else {
      titleCB.setSelected(false);
      titleField.setEditable(false);
    }

    if (attribs.containsKey("target")) {
      openInCB.setSelected(true);
      String val = attribs.get("target");
      openInCombo.setEnabled(true);
      for (int i = 0; i < TARGETS.length; i++) {
        if (val.equals(TARGETS[i])) {
          openInCombo.setSelectedIndex(i);
          break;
        }
      }
    }
    else {
      openInCB.setSelected(false);
      openInCombo.setEnabled(false);
    }
  }

  public void updateAttribsFromComponents() {
    if (openInCB.isSelected())
      attribs.put("target", TARGETS[openInCombo.getSelectedIndex()]);
    else
      attribs.remove("target");

    if (titleCB.isSelected())
      attribs.put("title", titleField.getText());
    else
      attribs.remove("title");

    if (nameCB.isSelected())
      attribs.put("name", nameField.getText());
    else
      attribs.remove("name");
  }

  private JCheckBox getNameCB() {
    if (nameCB == null) {
      nameCB = new JCheckBox();
      nameCB.setText(i18n.str("name"));
      nameCB.addItemListener(e -> nameField.setEditable(nameCB.isSelected()));
    }
    return nameCB;
  }

  private JCheckBox getTitleCB() {
    if (titleCB == null) {
      titleCB = new JCheckBox();
      titleCB.setText(i18n.str("title"));
      titleCB.addItemListener(e -> titleField.setEditable(titleCB.isSelected()));
    }
    return titleCB;
  }

  private JCheckBox getOpenInCB() {
    if (openInCB == null) {
      openInCB = new JCheckBox();
      openInCB.setText(i18n.str("open_in"));
      openInCB.addItemListener(e -> openInCombo.setEnabled(openInCB.isSelected()));
    }
    return openInCB;
  }

  private JTextField getNameField() {
    if (nameField == null) {
      nameField = new JTextField();
    }
    return nameField;
  }

  private JTextField getTitleField() {
    if (titleField == null) {
      titleField = new JTextField();
    }
    return titleField;
  }

  private JComboBox<String> getOpenInCombo() {
    if (openInCombo == null) {
      openInCombo = new JComboBox<>(TARGET_LABELS);
    }
    return openInCombo;
  }

  private JPanel getSpacerPanel() {
    if (spacerPanel == null) {
      spacerPanel = new JPanel();
    }
    return spacerPanel;
  }
}