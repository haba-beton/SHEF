package net.atlanticbb.tantlinger.ui.text.dialogs;

import net.atlanticbb.tantlinger.ui.text.TextEditPopupManager;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class StyleAttributesPanel extends HTMLAttributeEditorPanel {
  private static final long serialVersionUID = 1L;
  private JLabel classLabel = null;
  private JLabel idLabel = null;
  private JTextField classField = null;
  private JTextField idField = null;

  public StyleAttributesPanel() {
    this(new Hashtable<>());
  }

  public StyleAttributesPanel(Hashtable<String,String> attr) {
    super();
    initialize();
    setAttributes(attr);
    this.updateComponentsFromAttribs();
  }

  private void initialize() {
    GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
    gridBagConstraints4.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints4.gridy = 1;
    gridBagConstraints4.weightx = 1.0;
    gridBagConstraints4.insets = new java.awt.Insets(0, 0, 5, 0);
    gridBagConstraints4.gridx = 1;
    GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
    gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints3.gridy = 0;
    gridBagConstraints3.weightx = 1.0;
    gridBagConstraints3.insets = new java.awt.Insets(0, 0, 5, 0);
    gridBagConstraints3.weighty = 0.0;
    gridBagConstraints3.gridx = 1;
    GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
    gridBagConstraints1.gridx = 0;
    gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints1.insets = new java.awt.Insets(0, 0, 5, 5);
    gridBagConstraints1.gridy = 1;
    idLabel = new JLabel();
    idLabel.setText(i18n.str("id"));
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
    gridBagConstraints.gridy = 0;
    classLabel = new JLabel();
    classLabel.setText(i18n.str("class"));
    this.setLayout(new GridBagLayout());
    this.setSize(new java.awt.Dimension(210, 60));
    this.setPreferredSize(new java.awt.Dimension(210, 60));
    this.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
    this.add(classLabel, gridBagConstraints);
    this.add(idLabel, gridBagConstraints1);
    this.add(getClassField(), gridBagConstraints3);
    this.add(getIdField(), gridBagConstraints4);

    TextEditPopupManager popupMan = TextEditPopupManager.getInstance();
    popupMan.registerJTextComponent(classField);
    popupMan.registerJTextComponent(idField);

  }

  public void updateComponentsFromAttribs() {
    classField.setText(attribs.getOrDefault("class", ""));
    idField.setText(attribs.getOrDefault("id", ""));
  }

  public void updateAttribsFromComponents() {
    if (!classField.getText().equals(""))
      attribs.put("class", classField.getText());
    else
      attribs.remove("class");

    if (!idField.getText().equals(""))
      attribs.put("id", idField.getText());
    else
      attribs.remove("id");

  }

  private JTextField getClassField() {
    if (classField == null) {
      classField = new JTextField();
    }
    return classField;
  }

  private JTextField getIdField() {
    if (idField == null) {
      idField = new JTextField();
    }
    return idField;
  }
}