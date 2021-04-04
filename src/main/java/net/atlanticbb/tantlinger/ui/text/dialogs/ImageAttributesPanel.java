package net.atlanticbb.tantlinger.ui.text.dialogs;

import net.atlanticbb.tantlinger.ui.text.TextEditPopupManager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;


public class ImageAttributesPanel extends HTMLAttributeEditorPanel {
  private static final long serialVersionUID = 1L;

  private static final String[] ALIGNMENTS = {
      "top", "middle", "bottom", "left", "right"
    };

  private JLabel imgUrlLabel = null;
  private JButton selectFileButton = null;
  private JCheckBox altTextCB = null;
  private JCheckBox widthCB = null;
  private JCheckBox heightCB = null;
  private JCheckBox borderCB = null;
  private JSpinner widthField = null;
  private JSpinner heightField = null;
  private JSpinner borderField = null;
  private JCheckBox vSpaceCB = null;
  private JCheckBox hSpaceCB = null;
  private JCheckBox alignCB = null;
  private JSpinner vSpaceField = null;
  private JSpinner hSpaceField = null;
  private JComboBox<String> alignCombo = null;
  private JTextField imgUrlField = null;
  private JTextField altTextField = null;
  private JPanel attribPanel = null;

  private JPanel spacerPanel = null;

  public ImageAttributesPanel() {
    super();
    initialize();
    updateComponentsFromAttribs();
  }

  public void updateComponentsFromAttribs() {
    if (attribs.containsKey("src"))
      imgUrlField.setText(attribs.get("src"));

    if (attribs.containsKey("alt"))
    {
      altTextCB.setSelected(true);
      altTextField.setEditable(true);
      altTextField.setText(attribs.get("alt"));
    } else {
      altTextCB.setSelected(false);
      altTextField.setEditable(false);
    }

    if (attribs.containsKey("width"))
    {
      widthCB.setSelected(true);
      widthField.setEnabled(true);
      try {
        widthField.getModel().setValue(
          new Integer(attribs.get("width")));
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    } else {
      widthCB.setSelected(false);
      widthField.setEnabled(false);
    }

    if (attribs.containsKey("height"))
    {
      heightCB.setSelected(true);
      heightField.setEnabled(true);
      try {
        heightField.getModel().setValue(
          new Integer(attribs.get("height")));
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    } else {
      heightCB.setSelected(false);
      heightField.setEnabled(false);
    }

    if (attribs.containsKey("hspace"))
    {
      hSpaceCB.setSelected(true);
      hSpaceField.setEnabled(true);
      try {
        hSpaceField.getModel().setValue(
          new Integer(attribs.get("hspace")));
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    } else {
      hSpaceCB.setSelected(false);
      hSpaceField.setEnabled(false);
    }

    if (attribs.containsKey("vspace"))
    {
      vSpaceCB.setSelected(true);
      vSpaceField.setEnabled(true);
      try {
        vSpaceField.getModel().setValue(
          new Integer(attribs.get("vspace")));
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    } else {
      vSpaceCB.setSelected(false);
      vSpaceField.setEnabled(false);
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

    if (attribs.containsKey("align"))
    {
      alignCB.setSelected(true);
      alignCombo.setEnabled(true);
      alignCombo.setSelectedItem(attribs.get("align"));
    } else {
      alignCB.setSelected(false);
      alignCombo.setEnabled(false);
    }
  }

  public void updateAttribsFromComponents() {
    attribs.put("src", imgUrlField.getText());

    if (altTextCB.isSelected())
      attribs.put("alt", altTextField.getText());
    else
      attribs.remove("alt");

    if (widthCB.isSelected())
      attribs.put("width", widthField.getModel().getValue().toString());
    else
      attribs.remove("width");

    if (heightCB.isSelected())
      attribs.put("height", heightField.getModel().getValue().toString());
    else
      attribs.remove("height");

    if (vSpaceCB.isSelected())
      attribs.put("vspace", vSpaceField.getModel().getValue().toString());
    else
      attribs.remove("vspace");

    if (hSpaceCB.isSelected())
      attribs.put("hspace", hSpaceField.getModel().getValue().toString());
    else
      attribs.remove("hspace");

    if (borderCB.isSelected())
      attribs.put("border", borderField.getModel().getValue().toString());
    else
      attribs.remove("border");

    if (alignCB.isSelected())
      attribs.put("align", alignCombo.getSelectedItem().toString());
    else
      attribs.remove("align");
  }

  private void initialize() {
    GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
    gridBagConstraints22.gridx = 2;
    gridBagConstraints22.gridwidth = 2;
    gridBagConstraints22.anchor = java.awt.GridBagConstraints.CENTER;
    gridBagConstraints22.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints22.weighty = 1.0;
    gridBagConstraints22.gridy = 0;
    GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
    gridBagConstraints21.gridx = 0;
    gridBagConstraints21.gridwidth = 2;
    gridBagConstraints21.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints21.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints21.weighty = 1.0;
    gridBagConstraints21.gridy = 3;
    GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
    gridBagConstraints16.gridx = 0;
    gridBagConstraints16.gridwidth = 2;
    gridBagConstraints16.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints16.gridy = 2;
    GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
    gridBagConstraints15.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints15.gridy = 1;
    gridBagConstraints15.weightx = 1.0;
    gridBagConstraints15.insets = new java.awt.Insets(0, 0, 10, 0);
    gridBagConstraints15.gridwidth = 1;
    gridBagConstraints15.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints15.gridx = 1;
    GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
    gridBagConstraints14.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints14.gridy = 0;
    gridBagConstraints14.weightx = 1.0;
    gridBagConstraints14.insets = new java.awt.Insets(0, 0, 5, 0);
    gridBagConstraints14.gridwidth = 1;
    gridBagConstraints14.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints14.gridx = 1;
    GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
    gridBagConstraints1.gridx = 0;
    gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints1.insets = new java.awt.Insets(0, 0, 10, 5);
    gridBagConstraints1.gridy = 1;
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
    gridBagConstraints.gridy = 0;
    imgUrlLabel = new JLabel();
    imgUrlLabel.setText(i18n.str("image_url"));

    selectFileButton = new JButton("Select Image File...");
    selectFileButton.addActionListener(evt -> {
      JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));
      File selected;
      chooser.setDialogTitle("Select Image File");
      chooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "png", "gif"));
      chooser.setAcceptAllFileFilterUsed(false);
      int rc = chooser.showDialog(ImageAttributesPanel.this, "Select");
      if (rc == JFileChooser.APPROVE_OPTION) {
        selected = chooser.getSelectedFile();
        if (selected == null)
          return;

        imgUrlField.setText("file:" + selected.getAbsolutePath());
      }
    });

    this.setLayout(new GridBagLayout());
    this.add(imgUrlLabel, gridBagConstraints);
    this.add(selectFileButton, gridBagConstraints22);
    this.add(getAltTextCB(), gridBagConstraints1);
    this.add(getImgUrlField(), gridBagConstraints14);
    this.add(getAltTextField(), gridBagConstraints15);
    this.add(getAttribPanel(), gridBagConstraints16);
    this.add(getSpacerPanel(), gridBagConstraints21);
    this.setSize(700, 300);

    TextEditPopupManager popupMan = TextEditPopupManager.getInstance();
    popupMan.registerJTextComponent(imgUrlField);
    popupMan.registerJTextComponent(altTextField);
    imgUrlField.setToolTipText("Filename (prefaced with \"file:\" or Link (prefaced with \"http://\")");

  }

  private JCheckBox getAltTextCB() {
    if (altTextCB == null) {
      altTextCB = new JCheckBox();
      altTextCB.setText(i18n.str("alt_text"));
      altTextCB.addItemListener(e -> altTextField.setEditable(altTextCB.isSelected()));
    }
    return altTextCB;
  }

  private JCheckBox getWidthCB() {
    if (widthCB == null) {
      widthCB = new JCheckBox();
      widthCB.setText(i18n.str("width"));
      widthCB.addItemListener(e -> widthField.setEnabled(widthCB.isSelected()));
    }
    return widthCB;
  }

  private JCheckBox getHeightCB() {
    if (heightCB == null) {
      heightCB = new JCheckBox();
      heightCB.setText(i18n.str("height"));
      heightCB.addItemListener(e -> heightField.setEnabled(heightCB.isSelected()));
    }
    return heightCB;
  }

  private JCheckBox getBorderCB() {
    if (borderCB == null) {
      borderCB = new JCheckBox();
      borderCB.setText(i18n.str("border"));
      borderCB.addItemListener(e -> borderField.setEnabled(borderCB.isSelected()));
    }
    return borderCB;
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

  private JSpinner getBorderField() {
    if (borderField == null) {
      borderField = new JSpinner(new SpinnerNumberModel(1, 0, 999, 1));
    }
    return borderField;
  }

  private JCheckBox getVSpaceCB() {
    if (vSpaceCB == null) {
      vSpaceCB = new JCheckBox();
      vSpaceCB.setText(i18n.str("vspace"));
      vSpaceCB.addItemListener(e -> vSpaceField.setEnabled(vSpaceCB.isSelected()));
    }
    return vSpaceCB;
  }

  private JCheckBox getHSpaceCB() {
    if (hSpaceCB == null) {
      hSpaceCB = new JCheckBox();
      hSpaceCB.setText(i18n.str("hspace"));
      hSpaceCB.addItemListener(e -> hSpaceField.setEnabled(hSpaceCB.isSelected()));
    }
    return hSpaceCB;
  }

  private JCheckBox getAlignCB() {
    if (alignCB == null) {
      alignCB = new JCheckBox();
      alignCB.setText(i18n.str("align"));
      alignCB.addItemListener(e -> alignCombo.setEnabled(alignCB.isSelected()));
    }
    return alignCB;
  }

  private JSpinner getVSpaceField() {
    if (vSpaceField == null) {
      vSpaceField = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
    }
    return vSpaceField;
  }

  /**
   * This method initializes hSpaceField
   *
   * @return javax.swing.JSpinner
   */
  private JSpinner getHSpaceField() {
    if (hSpaceField == null) {
      hSpaceField = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
      //hSpaceField.setColumns(4);
    }
    return hSpaceField;
  }

  private JComboBox<String> getAlignCombo() {
    if (alignCombo == null) {
      alignCombo = new JComboBox<>(ALIGNMENTS);
    }
    return alignCombo;
  }

  private JTextField getImgUrlField() {
    if (imgUrlField == null) {
      imgUrlField = new JTextField();
    }
    return imgUrlField;
  }

  private JTextField getAltTextField() {
    if (altTextField == null) {
      altTextField = new JTextField();
    }
    return altTextField;
  }

  private JPanel getAttribPanel() {
    if (attribPanel == null) {
      GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
      gridBagConstraints13.anchor = java.awt.GridBagConstraints.WEST;
      gridBagConstraints13.insets = new Insets(0, 0, 10, 0);
      gridBagConstraints13.gridx = 3;
      gridBagConstraints13.gridy = 2;
      gridBagConstraints13.weightx = 1.0;
      gridBagConstraints13.fill = java.awt.GridBagConstraints.NONE;
      GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
      gridBagConstraints12.anchor = java.awt.GridBagConstraints.WEST;
      gridBagConstraints12.insets = new java.awt.Insets(0, 0, 10, 0);
      gridBagConstraints12.gridx = 3;
      gridBagConstraints12.gridy = 1;
      gridBagConstraints12.weightx = 0.0;
      gridBagConstraints12.fill = java.awt.GridBagConstraints.NONE;
      GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
      gridBagConstraints11.anchor = java.awt.GridBagConstraints.WEST;
      gridBagConstraints11.insets = new java.awt.Insets(0, 0, 5, 0);
      gridBagConstraints11.gridx = 3;
      gridBagConstraints11.gridy = 0;
      gridBagConstraints11.weightx = 0.0;
      gridBagConstraints11.fill = java.awt.GridBagConstraints.NONE;
      GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
      gridBagConstraints10.anchor = java.awt.GridBagConstraints.WEST;
      gridBagConstraints10.gridx = 2;
      gridBagConstraints10.gridy = 2;
      gridBagConstraints10.insets = new java.awt.Insets(0, 0, 10, 5);
      GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
      gridBagConstraints9.anchor = java.awt.GridBagConstraints.WEST;
      gridBagConstraints9.gridx = 2;
      gridBagConstraints9.gridy = 1;
      gridBagConstraints9.insets = new java.awt.Insets(0, 0, 10, 5);
      GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
      gridBagConstraints8.anchor = java.awt.GridBagConstraints.WEST;
      gridBagConstraints8.gridx = 2;
      gridBagConstraints8.gridy = 0;
      gridBagConstraints8.insets = new java.awt.Insets(0, 0, 5, 5);
      GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
      gridBagConstraints7.anchor = java.awt.GridBagConstraints.WEST;
      gridBagConstraints7.insets = new java.awt.Insets(0, 0, 10, 10);
      gridBagConstraints7.gridx = 1;
      gridBagConstraints7.gridy = 2;
      gridBagConstraints7.weightx = 0.0;
      gridBagConstraints7.fill = java.awt.GridBagConstraints.NONE;
      GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
      gridBagConstraints6.anchor = java.awt.GridBagConstraints.WEST;
      gridBagConstraints6.insets = new java.awt.Insets(0, 0, 10, 10);
      gridBagConstraints6.gridx = 1;
      gridBagConstraints6.gridy = 1;
      gridBagConstraints6.weightx = 0.0;
      gridBagConstraints6.fill = java.awt.GridBagConstraints.NONE;
      GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
      gridBagConstraints5.anchor = GridBagConstraints.WEST;
      gridBagConstraints5.insets = new Insets(0, 0, 5, 10);
      gridBagConstraints5.gridx = 1;
      gridBagConstraints5.gridy = 0;
      gridBagConstraints5.weightx = 0.0;
      gridBagConstraints5.fill = GridBagConstraints.NONE;
      GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
      gridBagConstraints4.anchor = java.awt.GridBagConstraints.WEST;
      gridBagConstraints4.gridx = 0;
      gridBagConstraints4.gridy = 2;
      gridBagConstraints4.insets = new java.awt.Insets(0, 0, 10, 5);
      GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
      gridBagConstraints3.anchor = java.awt.GridBagConstraints.WEST;
      gridBagConstraints3.gridx = 0;
      gridBagConstraints3.gridy = 1;
      gridBagConstraints3.insets = new java.awt.Insets(0, 0, 10, 5);
      GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
      gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
      gridBagConstraints2.gridx = 0;
      gridBagConstraints2.gridy = 0;
      gridBagConstraints2.insets = new java.awt.Insets(0, 0, 5, 5);
      attribPanel = new JPanel();
      attribPanel.setLayout(new GridBagLayout());
      attribPanel.add(getWidthCB(), gridBagConstraints2);
      attribPanel.add(getHeightCB(), gridBagConstraints3);
      attribPanel.add(getBorderCB(), gridBagConstraints4);
      attribPanel.add(getWidthField(), gridBagConstraints5);
      attribPanel.add(getHeightField(), gridBagConstraints6);
      attribPanel.add(getBorderField(), gridBagConstraints7);
      attribPanel.add(getVSpaceCB(), gridBagConstraints8);
      attribPanel.add(getHSpaceCB(), gridBagConstraints9);
      attribPanel.add(getAlignCB(), gridBagConstraints10);
      attribPanel.add(getVSpaceField(), gridBagConstraints11);
      attribPanel.add(getHSpaceField(), gridBagConstraints12);
      attribPanel.add(getAlignCombo(), gridBagConstraints13);
    }
    return attribPanel;
  }

  private JPanel getSpacerPanel() {
    if (spacerPanel == null) {
      spacerPanel = new JPanel();
    }
    return spacerPanel;
  }
}