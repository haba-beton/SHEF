package net.atlanticbb.tantlinger.ui.text.dialogs;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class TableAttributesPanel extends HTMLAttributeEditorPanel {

  private static final String[] ALIGNMENTS = {"left", "center", "right"};
  private static final String[] MEASUREMENTS = {"percent", "pixels"};

  private JCheckBox widthCB = null;
  private JSpinner widthField = null;
  private JComboBox<String> widthCombo = null;
  private JCheckBox alignCB = null;
  private JCheckBox cellSpacingCB = null;
  private JSpinner cellSpacingField = null;
  private JCheckBox borderCB = null;
  private JSpinner borderField = null;
  private JCheckBox cellPaddingCB = null;
  private JSpinner cellPaddingField = null;
  private JComboBox<String> alignCombo = null;
  private BGColorPanel bgPanel = null;
  private JPanel expansionPanel = null;

  public TableAttributesPanel() {
    this(new Hashtable<>());
  }

  public TableAttributesPanel(Hashtable<String,String> attribs) {
    super(attribs);
    initialize();
    updateComponentsFromAttribs();
  }

  public void updateComponentsFromAttribs() {
    if (attribs.containsKey("width"))
    {
      widthCB.setSelected(true);
      String w = attribs.get("width");
      if (w.endsWith("%"))
        w = w.substring(0, w.length() - 1);
      else
        widthCombo.setSelectedIndex(1);
      widthField.setEnabled(true);

      try {
        widthField.getModel().setValue(new Integer(w));
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    } else {
      widthCB.setSelected(false);
      widthField.setEnabled(false);
      widthCombo.setEnabled(false);
    }

    if (attribs.containsKey("align"))
    {
      alignCB.setSelected(true);
      alignCombo.setEnabled(true);
      alignCombo.setSelectedItem(attribs.get("align"));
    } else {
      alignCB.setSelected(false);
      alignCombo.setEnabled(false);
    }

    if (attribs.containsKey("border"))
    {
      borderCB.setSelected(true);
      borderField.setEnabled(true);
      try {
        borderField.getModel().setValue(
          new Integer(attribs.get("border")));
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    } else {
      borderCB.setSelected(false);
      borderField.setEnabled(false);
    }

    if (attribs.containsKey("cellpadding"))
    {
      cellPaddingCB.setSelected(true);
      cellPaddingField.setEnabled(true);
      try {
        cellPaddingField.getModel().setValue(
          new Integer(attribs.get("cellpadding")));
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    } else {
      cellPaddingCB.setSelected(false);
      cellPaddingField.setEnabled(false);
    }

    if (attribs.containsKey("cellspacing"))
    {
      cellSpacingCB.setSelected(true);
      cellSpacingField.setEnabled(true);
      try {
        cellSpacingField.getModel().setValue(
          new Integer(attribs.get("cellspacing")));
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    } else {
      cellSpacingCB.setSelected(false);
      cellSpacingField.setEnabled(false);
    }

    if (attribs.containsKey("bgcolor"))
    {
      bgPanel.setSelected(true);
      bgPanel.setColor(attribs.get("bgcolor"));
    } else {
      bgPanel.setSelected(false);
    }
  }

  public void updateAttribsFromComponents() {
    if (widthCB.isSelected()) {
      String w = widthField.getModel().getValue().toString();
      if (widthCombo.getSelectedIndex() == 0)
        w += "%";
      attribs.put("width", w);
    } else
      attribs.remove("width");

    if (alignCB.isSelected())
      attribs.put("align", alignCombo.getSelectedItem().toString());
    else
      attribs.remove("align");

    if (borderCB.isSelected())
      attribs.put("border",
        borderField.getModel().getValue().toString());
    else
      attribs.remove("border");

    if (cellSpacingCB.isSelected())
      attribs.put("cellspacing",
        cellSpacingField.getModel().getValue().toString());
    else
      attribs.remove("cellspacing");

    if (cellPaddingCB.isSelected())
      attribs.put("cellpadding",
        cellPaddingField.getModel().getValue().toString());
    else
      attribs.remove("cellpadding");

    if (bgPanel.isSelected())
      attribs.put("bgcolor", bgPanel.getColor());
    else
      attribs.remove("bgcolor");
  }

  public void setComponentStates(Hashtable<String,String> attribs) {
    if (attribs.containsKey("width"))
    {
      widthCB.setSelected(true);
      String w = attribs.get("width");
      if (w.endsWith("%"))
        w = w.substring(0, w.length() - 1);
      else
        widthCombo.setSelectedIndex(1);
      try {
        widthField.getModel().setValue(new Integer(w));
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    } else {
      widthCB.setSelected(false);
      widthField.setEnabled(false);
      widthCombo.setEnabled(false);
    }


    if (attribs.containsKey("align"))
    {
      alignCB.setSelected(true);
      alignCombo.setSelectedItem(attribs.get("align"));
    } else {
      alignCB.setSelected(false);
      alignCombo.setEnabled(false);
    }

    if (attribs.containsKey("border"))
    {
      borderCB.setSelected(true);
      try {
        borderField.getModel().setValue(
          new Integer(attribs.get("border")));
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    } else {
      borderCB.setSelected(false);
      borderField.setEnabled(false);
    }

    if (attribs.containsKey("cellpadding"))
    {
      cellPaddingCB.setSelected(true);
      try {
        cellPaddingField.getModel().setValue(
          new Integer(attribs.get("cellpadding")));
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    } else {
      cellPaddingCB.setSelected(false);
      cellPaddingField.setEnabled(false);
    }

    if (attribs.containsKey("cellspacing"))
    {
      cellSpacingCB.setSelected(true);
      try {
        cellSpacingField.getModel().setValue(
          new Integer(attribs.get("cellspacing")));
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    } else {
      cellSpacingCB.setSelected(false);
      cellSpacingField.setEnabled(false);
    }

    if (attribs.containsKey("bgcolor"))
    {
      bgPanel.setSelected(true);
      bgPanel.setColor(attribs.get("bgcolor"));
    } else {
      bgPanel.setSelected(false);
    }

  }

  private void initialize() {
    GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
    gridBagConstraints12.gridx = 0;
    gridBagConstraints12.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints12.gridwidth = 4;
    gridBagConstraints12.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints12.weightx = 0.0;
    gridBagConstraints12.weighty = 1.0;
    gridBagConstraints12.gridy = 4;
    GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
    gridBagConstraints11.gridx = 0;
    gridBagConstraints11.gridwidth = 4;
    gridBagConstraints11.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints11.weighty = 0.0;
    gridBagConstraints11.gridy = 3;
    GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
    gridBagConstraints4.fill = java.awt.GridBagConstraints.NONE;
    gridBagConstraints4.gridy = 1;
    gridBagConstraints4.weightx = 0.0;
    gridBagConstraints4.gridwidth = 2;
    gridBagConstraints4.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints4.insets = new java.awt.Insets(0, 0, 5, 15);
    gridBagConstraints4.gridx = 1;
    GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
    gridBagConstraints10.fill = java.awt.GridBagConstraints.NONE;
    gridBagConstraints10.gridy = 2;
    gridBagConstraints10.weightx = 1.0;
    gridBagConstraints10.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints10.insets = new java.awt.Insets(0, 0, 10, 0);
    gridBagConstraints10.gridx = 4;
    GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
    gridBagConstraints9.gridx = 3;
    gridBagConstraints9.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints9.insets = new java.awt.Insets(0, 0, 10, 3);
    gridBagConstraints9.gridy = 2;
    GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
    gridBagConstraints8.fill = java.awt.GridBagConstraints.NONE;
    gridBagConstraints8.gridy = 2;
    gridBagConstraints8.weightx = 0.0;
    gridBagConstraints8.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints8.insets = new java.awt.Insets(0, 0, 10, 15);
    gridBagConstraints8.gridx = 1;
    GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
    gridBagConstraints7.gridx = 0;
    gridBagConstraints7.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints7.insets = new java.awt.Insets(0, 0, 10, 3);
    gridBagConstraints7.gridy = 2;
    GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
    gridBagConstraints6.fill = java.awt.GridBagConstraints.NONE;
    gridBagConstraints6.gridy = 1;
    gridBagConstraints6.weightx = 1.0;
    gridBagConstraints6.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints6.insets = new java.awt.Insets(0, 0, 2, 0);
    gridBagConstraints6.gridx = 4;
    GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
    gridBagConstraints5.gridx = 3;
    gridBagConstraints5.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints5.insets = new java.awt.Insets(0, 0, 2, 3);
    gridBagConstraints5.gridy = 1;
    GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
    gridBagConstraints3.gridx = 0;
    gridBagConstraints3.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints3.insets = new java.awt.Insets(0, 0, 2, 3);
    gridBagConstraints3.gridy = 1;
    GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
    gridBagConstraints2.fill = java.awt.GridBagConstraints.NONE;
    gridBagConstraints2.gridy = 0;
    gridBagConstraints2.weightx = 0.0;
    gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints2.gridwidth = 2;
    gridBagConstraints2.insets = new java.awt.Insets(0, 0, 10, 0);
    gridBagConstraints2.gridx = 2;
    GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
    gridBagConstraints1.fill = java.awt.GridBagConstraints.NONE;
    gridBagConstraints1.gridy = 0;
    gridBagConstraints1.weightx = 0.0;
    gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints1.insets = new java.awt.Insets(0, 0, 10, 0);
    gridBagConstraints1.gridx = 1;
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 3);
    gridBagConstraints.gridy = 0;
    this.setLayout(new GridBagLayout());
    this.setSize(320, 140);
    this.setPreferredSize(new java.awt.Dimension(320, 140));

    this.add(getWidthCB(), gridBagConstraints);
    this.add(getWidthField(), gridBagConstraints1);
    this.add(getWidthCombo(), gridBagConstraints2);
    this.add(getAlignCB(), gridBagConstraints3);
    this.add(getCellSpacingCB(), gridBagConstraints5);
    this.add(getCellSpacingField(), gridBagConstraints6);
    this.add(getBorderCB(), gridBagConstraints7);
    this.add(getBorderField(), gridBagConstraints8);
    this.add(getCellPaddingCB(), gridBagConstraints9);
    this.add(getCellPaddingField(), gridBagConstraints10);
    this.add(getAlignCombo(), gridBagConstraints4);
    this.add(getBGPanel(), gridBagConstraints11);
    this.add(getExpansionPanel(), gridBagConstraints12);
  }

  private JCheckBox getWidthCB() {
    if (widthCB == null) {
      widthCB = new JCheckBox();
      widthCB.setText(i18n.str("width"));
      widthCB.addItemListener(e -> {
        widthField.setEnabled(widthCB.isSelected());
        widthCombo.setEnabled(widthCB.isSelected());
      });
    }
    return widthCB;
  }

  private JSpinner getWidthField() {
    if (widthField == null) {
      widthField = new JSpinner(new SpinnerNumberModel(100, 1, 999, 1));

    }
    return widthField;
  }

  private JComboBox<String> getWidthCombo() {
    if (widthCombo == null) {
      widthCombo = new JComboBox<>(MEASUREMENTS);
    }
    return widthCombo;
  }

  private JCheckBox getAlignCB() {
    if (alignCB == null) {
      alignCB = new JCheckBox();
      alignCB.setText(i18n.str("align"));
      alignCB.addItemListener(new java.awt.event.ItemListener() {
        public void itemStateChanged(java.awt.event.ItemEvent e) {
          alignCombo.setEnabled(alignCB.isSelected());
        }
      });
    }
    return alignCB;
  }

  private JCheckBox getCellSpacingCB() {
    if (cellSpacingCB == null) {
      cellSpacingCB = new JCheckBox();
      cellSpacingCB.setText(i18n.str("cellspacing"));
      cellSpacingCB.addItemListener(e -> cellSpacingField.setEnabled(cellSpacingCB.isSelected()));
    }
    return cellSpacingCB;
  }

  private JSpinner getCellSpacingField() {
    if (cellSpacingField == null) {
      cellSpacingField = new JSpinner(new SpinnerNumberModel(1, 0, 999, 1));

    }
    return cellSpacingField;
  }

  private JCheckBox getBorderCB() {
    if (borderCB == null) {
      borderCB = new JCheckBox();
      borderCB.setText(i18n.str("border"));
      borderCB.addItemListener(e -> borderField.setEnabled(borderCB.isSelected()));
    }
    return borderCB;
  }

  private JSpinner getBorderField() {
    if (borderField == null) {
      borderField = new JSpinner(new SpinnerNumberModel(1, 0, 999, 1));

    }
    return borderField;
  }

  private JCheckBox getCellPaddingCB() {
    if (cellPaddingCB == null) {
      cellPaddingCB = new JCheckBox();
      cellPaddingCB.setText(i18n.str("cellpadding"));
      cellPaddingCB.addItemListener(e -> cellPaddingField.setEnabled(cellPaddingCB.isSelected()));
    }
    return cellPaddingCB;
  }

  private JSpinner getCellPaddingField() {
    if (cellPaddingField == null) {
      cellPaddingField = new JSpinner(new SpinnerNumberModel(1, 0, 999, 1));

    }
    return cellPaddingField;
  }

  private JComboBox<String> getAlignCombo() {
    if (alignCombo == null) {
      alignCombo = new JComboBox<>(ALIGNMENTS);
    }
    return alignCombo;
  }

  private JPanel getBGPanel() {
    if (bgPanel == null) {
      bgPanel = new BGColorPanel();

    }
    return bgPanel;
  }

  private JPanel getExpansionPanel() {
    if (expansionPanel == null) {
      expansionPanel = new JPanel();
    }
    return expansionPanel;
  }
}
