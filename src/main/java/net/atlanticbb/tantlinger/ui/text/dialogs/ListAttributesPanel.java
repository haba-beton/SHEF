package net.atlanticbb.tantlinger.ui.text.dialogs;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class ListAttributesPanel extends HTMLAttributeEditorPanel {
  public  static final int  UL_LIST = 0;
  public  static final int  OL_LIST = 1;

  private static final String   UL = i18n.str("unordered_list");
  private static final String   OL = i18n.str("ordered_list");
  private static final String[] LIST_TYPES = {UL, OL};

  private static final String[] OL_TYPES = {"1", "a", "A", "i", "I"};
  private static final String[] OL_TYPE_LABELS = {
    "1, 2, 3, ...",
    "a, b, c, ...",
    "A, B, C, ...",
    "i, ii, iii, ...",
    "I, II, III, ..."
  };

  private static final String[] UL_TYPES = {"disc", "square", "circle"};
  private static final String[] UL_TYPE_LABELS = {
    i18n.str("solid_circle"), i18n.str("solid_square"), i18n.str("open_circle")
  };

  private JLabel typeLabel = null;
  private JComboBox<String> typeCombo = null;
  private JComboBox<String> styleCombo = null;
  private JSpinner startAtField = null;
  private JCheckBox styleCB = null;
  private JCheckBox startAtCB = null;

  public ListAttributesPanel() {
    this(new Hashtable<>());
  }

  public ListAttributesPanel(Hashtable<String,String> ht) {
    super();
    initialize();
    setAttributes(ht);
    updateComponentsFromAttribs();
  }

  public void setListType(int t) {
    typeCombo.setSelectedIndex(t);
    updateForType();
  }

  public int getListType() {
    return typeCombo.getSelectedIndex();
  }

  private void updateForType() {
    styleCombo.removeAllItems();
    if (typeCombo.getSelectedItem().equals(UL)) {
      for (String ulTypeLabel : UL_TYPE_LABELS) {
        styleCombo.addItem(ulTypeLabel);
      }
      startAtCB.setEnabled(false);
      startAtField.setEnabled(false);
    }
    else {
      for (String olTypeLabel : OL_TYPE_LABELS) {
        styleCombo.addItem(olTypeLabel);
      }
      startAtCB.setEnabled(true);
      startAtField.setEnabled(startAtCB.isSelected());
    }
  }

  private int getIndexForStyle(String s) {
    if (typeCombo.getSelectedIndex() == UL_LIST) {
      for (int i = 0; i < UL_TYPES.length; i++) {
        if (UL_TYPES[i].equals(s)) {
          return i;
        }
      }
    }
    else {
      for (int i = 0; i < OL_TYPES.length; i++) {
        if (OL_TYPES[i].equals(s)) {
          return i;
        }
      }
    }
    return 0;
  }

  private void initialize() {
    GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
    gridBagConstraints2.gridx = 0;
    gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints2.gridy = 2;
    GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
    gridBagConstraints1.gridx = 0;
    gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints1.insets = new java.awt.Insets(0, 0, 5, 5);
    gridBagConstraints1.gridy = 1;
    GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
    gridBagConstraints5.fill = java.awt.GridBagConstraints.NONE;
    gridBagConstraints5.gridy = 2;
    gridBagConstraints5.weightx = 1.0;
    gridBagConstraints5.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints5.gridx = 1;
    GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
    gridBagConstraints4.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints4.gridy = 1;
    gridBagConstraints4.weightx = 1.0;
    gridBagConstraints4.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints4.insets = new java.awt.Insets(0, 0, 5, 0);
    gridBagConstraints4.gridx = 1;
    GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
    gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints3.gridy = 0;
    gridBagConstraints3.weightx = 1.0;
    gridBagConstraints3.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints3.insets = new java.awt.Insets(0, 0, 5, 0);
    gridBagConstraints3.gridx = 1;
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
    gridBagConstraints.gridy = 0;
    typeLabel = new JLabel();
    typeLabel.setText(i18n.str("list_type"));
    this.setLayout(new GridBagLayout());
    this.setSize(new java.awt.Dimension(234, 159));
    this.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
    this.add(typeLabel, gridBagConstraints);
    this.add(getTypeCombo(), gridBagConstraints3);
    this.add(getStyleCombo(), gridBagConstraints4);
    this.add(getStartAtField(), gridBagConstraints5);
    this.add(getStyleCB(), gridBagConstraints1);
    this.add(getStartAtCB(), gridBagConstraints2);
  }

  public void updateComponentsFromAttribs() {
    if (attribs.containsKey("type")) {
      styleCB.setSelected(true);
      styleCombo.setEnabled(true);
      int i = getIndexForStyle(attribs.get("type"));
      styleCombo.setSelectedIndex(i);
    }
    else {
      styleCB.setSelected(false);
      styleCombo.setEnabled(false);
    }

    if (attribs.containsKey("start")) {
      startAtCB.setSelected(true);
      startAtField.setEnabled(true);
      try {
        startAtField.getModel().setValue(Integer.parseInt(attribs.get("start")));
      }
      catch (Exception ignored) {
      }
    }
    else {
      startAtCB.setSelected(false);
      startAtField.setEnabled(false);
    }
  }

  public void updateAttribsFromComponents() {
    if (styleCB.isSelected()) {
      if (typeCombo.getSelectedIndex() == UL_LIST) {
        attribs.put("type", UL_TYPES[styleCombo.getSelectedIndex()]);
      }
      else {
        attribs.put("type", OL_TYPES[styleCombo.getSelectedIndex()]);
      }
    }
    else {
      attribs.remove("type");
    }

    if (startAtCB.isSelected()) {
      attribs.put("start", startAtField.getModel().getValue().toString());
    }
    else {
      attribs.remove("start");
    }
  }

  private JComboBox<String> getTypeCombo() {
    if (typeCombo == null) {
      typeCombo = new JComboBox<>(LIST_TYPES);
      typeCombo.addItemListener(e -> updateForType());
    }
    return typeCombo;
  }

  private JComboBox<String> getStyleCombo() {
    if (styleCombo == null) {
      styleCombo = new JComboBox<>(UL_TYPE_LABELS);
    }
    return styleCombo;
  }

  private JSpinner getStartAtField() {
    if (startAtField == null) {
      startAtField = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
    }
    return startAtField;
  }

  private JCheckBox getStyleCB() {
    if (styleCB == null) {
      styleCB = new JCheckBox();
      styleCB.setText(i18n.str("style"));
      styleCB.addItemListener(e -> styleCombo.setEnabled(styleCB.isSelected()));
    }
    return styleCB;
  }

  private JCheckBox getStartAtCB() {
    if (startAtCB == null) {
      startAtCB = new JCheckBox();
      startAtCB.setText(i18n.str("start_at"));
      startAtCB.addItemListener(e -> startAtField.setEnabled(startAtCB.isSelected()));
    }
    return startAtCB;
  }
}