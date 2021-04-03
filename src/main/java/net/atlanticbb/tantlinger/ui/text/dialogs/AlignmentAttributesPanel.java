package net.atlanticbb.tantlinger.ui.text.dialogs;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class AlignmentAttributesPanel extends HTMLAttributeEditorPanel {
  private static final long serialVersionUID = 1L;
  private static final String[] VERT_ALIGNMENTS = {"top", "middle", "bottom"};
  private static final String[] HORIZ_ALIGNMENTS = {"left", "center", "right", "justify"};


  private JCheckBox vAlignCB = null;
  private JCheckBox hAlignCB = null;
  private JComboBox<String> vLocCombo = null;
  private JComboBox<String> hLocCombo = null;


  public AlignmentAttributesPanel() {
    this(new Hashtable<>());
  }

  public AlignmentAttributesPanel(Hashtable<String,String> attr) {
    super(attr);
    initialize();
    updateComponentsFromAttribs();
  }

  public void updateComponentsFromAttribs() {
    if (attribs.containsKey("align"))
    {
      hAlignCB.setSelected(true);
      hLocCombo.setEnabled(true);
      hLocCombo.setSelectedItem(attribs.get("align"));
    } else {
      hAlignCB.setSelected(false);
      hLocCombo.setEnabled(false);
    }

    if (attribs.containsKey("valign"))
    {
      vAlignCB.setSelected(true);
      vLocCombo.setEnabled(true);
      vLocCombo.setSelectedItem(attribs.get("valign"));
    } else {
      vAlignCB.setSelected(false);
      vLocCombo.setEnabled(false);
    }
  }


  public void updateAttribsFromComponents() {
    if (vAlignCB.isSelected())
      attribs.put("valign", vLocCombo.getSelectedItem().toString());
    else
      attribs.remove("valign");

    if (hAlignCB.isSelected())
      attribs.put("align", hLocCombo.getSelectedItem().toString());
    else
      attribs.remove("align");
  }

  public void setComponentStates(Hashtable<String,String> attribs) {
    if (attribs.containsKey("align"))
    {
      hAlignCB.setSelected(true);
      hLocCombo.setEnabled(true);
      hLocCombo.setSelectedItem(attribs.get("align"));
    } else {
      hAlignCB.setSelected(false);
      hLocCombo.setEnabled(false);
    }

    if (attribs.containsKey("valign"))
    {
      vAlignCB.setSelected(true);
      vLocCombo.setEnabled(true);
      vLocCombo.setSelectedItem(attribs.get("valign"));
    } else {
      vAlignCB.setSelected(false);
      vLocCombo.setEnabled(false);
    }
  }

  private void initialize() {
    GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
    gridBagConstraints3.fill = java.awt.GridBagConstraints.NONE;
    gridBagConstraints3.gridy = 1;
    gridBagConstraints3.weightx = 1.0;
    gridBagConstraints3.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints3.gridx = 1;
    GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
    gridBagConstraints2.fill = java.awt.GridBagConstraints.NONE;
    gridBagConstraints2.gridy = 0;
    gridBagConstraints2.weightx = 1.0;
    gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints2.insets = new java.awt.Insets(0, 0, 5, 0);
    gridBagConstraints2.gridx = 1;
    GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
    gridBagConstraints1.gridx = 0;
    gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints1.insets = new java.awt.Insets(0, 0, 0, 5);
    gridBagConstraints1.gridy = 1;
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
    gridBagConstraints.gridy = 0;
    this.setLayout(new GridBagLayout());
    this.setSize(185, 95);
    this.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createTitledBorder(i18n.str("content_alignment")), javax.swing.BorderFactory.createEmptyBorder(2, 5, 2, 5)));

    this.setPreferredSize(new java.awt.Dimension(185, 95));
    this.setMaximumSize(this.getPreferredSize());
    this.setMinimumSize(this.getPreferredSize());
    this.add(getVAlignCB(), gridBagConstraints);
    this.add(getHAlignCB(), gridBagConstraints1);
    this.add(getVLocCombo(), gridBagConstraints2);
    this.add(getHLocCombo(), gridBagConstraints3);
  }

  private JCheckBox getVAlignCB() {
    if (vAlignCB == null) {
      vAlignCB = new JCheckBox();
      vAlignCB.setText(i18n.str("vertical"));

      vAlignCB.addActionListener(e -> vLocCombo.setEnabled(vAlignCB.isSelected()));
    }
    return vAlignCB;
  }

  private JCheckBox getHAlignCB() {
    if (hAlignCB == null) {
      hAlignCB = new JCheckBox();
      hAlignCB.setText(i18n.str("horizontal"));

      hAlignCB.addActionListener(e -> hLocCombo.setEnabled(hAlignCB.isSelected()));
    }
    return hAlignCB;
  }

  private JComboBox<String> getVLocCombo() {
    if (vLocCombo == null) {
      vLocCombo = new JComboBox<>(VERT_ALIGNMENTS);
    }
    return vLocCombo;
  }

  private JComboBox<String> getHLocCombo() {
    if (hLocCombo == null) {
      hLocCombo = new JComboBox<>(HORIZ_ALIGNMENTS);

    }
    return hLocCombo;
  }

}