package net.atlanticbb.tantlinger.ui.text.dialogs;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class SizeAttributesPanel extends HTMLAttributeEditorPanel {
  private static final String[] MEASUREMENTS = {"percent", "pixels"};

  private JCheckBox widthCB = null;
  private JCheckBox heightCB = null;
  private JSpinner widthField = null;
  private JSpinner heightField = null;
  private JComboBox<String> wMeasurementCombo = null;
  private JComboBox<String> hMeasurementCombo = null;

  public SizeAttributesPanel() {
    this(new Hashtable<>());
  }

  public SizeAttributesPanel(Hashtable<String,String> attr) {
    super(attr);
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
        wMeasurementCombo.setSelectedIndex(1);
      try {
        widthField.getModel().setValue(new Integer(w));
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      wMeasurementCombo.setEnabled(true);
      widthField.setEnabled(true);
    } else {
      widthCB.setSelected(false);
      widthField.setEnabled(false);
      wMeasurementCombo.setEnabled(false);
    }

    if (attribs.containsKey("height"))
    {
      heightCB.setSelected(true);
      String h = attribs.get("height");
      if (h.endsWith("%"))                            
        h = h.substring(0, h.length() - 1);
      else
        hMeasurementCombo.setSelectedIndex(1);
      try {
        heightField.getModel().setValue(new Integer(h));
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      hMeasurementCombo.setEnabled(true);
      heightField.setEnabled(true);
    } else {
      heightCB.setSelected(false);
      heightField.setEnabled(false);
      hMeasurementCombo.setEnabled(false);
    }
  }

  public void updateAttribsFromComponents() {
    if (widthCB.isSelected()) {
      String w = widthField.getModel().getValue().toString();
      if (wMeasurementCombo.getSelectedIndex() == 0)
        w += "%";
      attribs.put("width", w);
    } else
      attribs.remove("width");

    if (heightCB.isSelected()) {
      String h = heightField.getModel().getValue().toString();
      if (hMeasurementCombo.getSelectedIndex() == 0)
        h += "%";
      attribs.put("height", h);
    } else
      attribs.remove("height");
  }

  public void setComponentStates(Hashtable<String,String> attribs) {
    if (attribs.containsKey("width")) {
      widthCB.setSelected(true);
      String w = attribs.get("width");
      if (w.endsWith("%"))                            
        w = w.substring(0, w.length() - 1);
      else
        wMeasurementCombo.setSelectedIndex(1);
      try {
        widthField.getModel().setValue(new Integer(w));
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
      wMeasurementCombo.setEnabled(true);
      widthField.setEnabled(true);
    }
    else {
      widthCB.setSelected(false);
      widthField.setEnabled(false);
      wMeasurementCombo.setEnabled(false);
    }

    if (attribs.containsKey("height")) {
      heightCB.setSelected(true);
      String h = attribs.get("height");
      if (h.endsWith("%"))                            
        h = h.substring(0, h.length() - 1);
      else
        hMeasurementCombo.setSelectedIndex(1);
      try {
        heightField.getModel().setValue(new Integer(h));
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
      hMeasurementCombo.setEnabled(true);
      heightField.setEnabled(true);
    }
    else {
      heightCB.setSelected(false);
      heightField.setEnabled(false);
      hMeasurementCombo.setEnabled(false);
    }
  }

  private void initialize() {
    GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
    gridBagConstraints5.fill = java.awt.GridBagConstraints.NONE;
    gridBagConstraints5.gridy = 1;
    gridBagConstraints5.weightx = 0.0;
    gridBagConstraints5.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints5.ipadx = 0;
    gridBagConstraints5.gridx = 2;
    GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
    gridBagConstraints4.fill = java.awt.GridBagConstraints.NONE;
    gridBagConstraints4.gridy = 0;
    gridBagConstraints4.weightx = 1.0;
    gridBagConstraints4.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints4.insets = new java.awt.Insets(0, 0, 5, 0);
    gridBagConstraints4.gridx = 2;
    GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
    gridBagConstraints3.fill = java.awt.GridBagConstraints.NONE;
    gridBagConstraints3.gridy = 1;
    gridBagConstraints3.weightx = 0.0;
    gridBagConstraints3.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints3.insets = new java.awt.Insets(0, 0, 0, 5);
    gridBagConstraints3.ipadx = 0;
    gridBagConstraints3.gridx = 1;
    GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
    gridBagConstraints2.fill = java.awt.GridBagConstraints.NONE;
    gridBagConstraints2.gridy = 0;
    gridBagConstraints2.weightx = 0.0;
    gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints2.insets = new java.awt.Insets(0, 0, 0, 5);
    gridBagConstraints2.ipadx = 0;
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
    this.setSize(215, 95);
    this.setPreferredSize(new java.awt.Dimension(215, 95));
    this.setMaximumSize(getPreferredSize());
    this.setMinimumSize(getPreferredSize());
    this.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createTitledBorder(null, i18n.str("size"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null), javax.swing.BorderFactory.createEmptyBorder(2, 5, 2, 5)));
    this.add(getWidthCB(), gridBagConstraints);
    this.add(getHeightCB(), gridBagConstraints1);
    this.add(getWidthField(), gridBagConstraints2);
    this.add(getHeightField(), gridBagConstraints3);
    this.add(getWMeasurementCombo(), gridBagConstraints4);
    this.add(getHMeasurementCombo(), gridBagConstraints5);
  }

  private JCheckBox getWidthCB() {
    if (widthCB == null) {
      widthCB = new JCheckBox();
      widthCB.setText(i18n.str("width"));

      widthCB.addItemListener(e -> {
        widthField.setEnabled(widthCB.isSelected());
        wMeasurementCombo.setEnabled(widthCB.isSelected());
      });
    }
    return widthCB;
  }

  private JCheckBox getHeightCB() {
    if (heightCB == null) {
      heightCB = new JCheckBox();
      heightCB.setText(i18n.str("height"));

      heightCB.addItemListener(e -> {
        heightField.setEnabled(heightCB.isSelected());
        hMeasurementCombo.setEnabled(heightCB.isSelected());
      });
    }
    return heightCB;
  }

  private JSpinner getWidthField() {
    if (widthField == null) {

      widthField = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));

    }

    return widthField;
  }

  private JSpinner getHeightField() {
    if (heightField == null) {

      heightField = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));

    }

    return heightField;
  }

  private JComboBox<String> getWMeasurementCombo() {
    if (wMeasurementCombo == null) {
      wMeasurementCombo = new JComboBox<>(MEASUREMENTS);

    }
    return wMeasurementCombo;
  }

  private JComboBox<String> getHMeasurementCombo() {
    if (hMeasurementCombo == null) {
      hMeasurementCombo = new JComboBox<>(MEASUREMENTS);

    }
    return hMeasurementCombo;
  }

}