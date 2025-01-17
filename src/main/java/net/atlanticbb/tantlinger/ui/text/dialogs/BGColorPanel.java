package net.atlanticbb.tantlinger.ui.text.dialogs;

import net.atlanticbb.tantlinger.i18n.I18n;
import net.atlanticbb.tantlinger.ui.UIUtils;
import net.atlanticbb.tantlinger.ui.text.HTMLUtils;

import javax.swing.*;
import java.awt.*;


public class BGColorPanel extends JPanel {
  private static final I18n i18n = I18n.getInstance("net.atlanticbb.tantlinger.ui.text.dialogs");

  private JCheckBox bgColorCB = null;
  private JPanel colorPanel = null;
  private JButton colorButton = null;
  private Color selColor = Color.WHITE;

  public BGColorPanel() {
    super();
    initialize();
  }

  public void setSelected(boolean sel) {
    bgColorCB.setSelected(sel);
    colorButton.setEnabled(sel);
  }

  public boolean isSelected() {
    return bgColorCB.isSelected();
  }

  public String getColor() {
    return HTMLUtils.colorToHex(selColor);
  }

  public void setColor(String hexColor) {
    selColor = HTMLUtils.stringToColor(hexColor);
    colorPanel.setBackground(selColor);
  }

  private void initialize() {
    GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
    gridBagConstraints2.gridx = 2;
    gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints2.weightx = 1.0;
    gridBagConstraints2.gridy = 0;
    GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
    gridBagConstraints1.gridx = 1;
    gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints1.insets = new java.awt.Insets(0, 0, 0, 5);
    gridBagConstraints1.gridy = 0;
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
    gridBagConstraints.gridy = 0;
    this.setLayout(new GridBagLayout());
    this.setSize(175, 30);
    this.setPreferredSize(new java.awt.Dimension(175, 30));
    this.setMinimumSize(getPreferredSize());
    this.setMaximumSize(getPreferredSize());
    this.add(getBgColorCB(), gridBagConstraints);
    this.add(getColorPanel(), gridBagConstraints1);
    this.add(getColorButton(), gridBagConstraints2);

    colorButton.setEnabled(bgColorCB.isSelected());
  }

  /**
   * This method initializes bgColorCB
   *
   * @return javax.swing.JCheckBox
   */
  private JCheckBox getBgColorCB() {
    if (bgColorCB == null) {
      bgColorCB = new JCheckBox();
      bgColorCB.setText(i18n.str("background")); //$NON-NLS-1$

      bgColorCB.addItemListener(e -> {
        colorButton.setEnabled(bgColorCB.isSelected());
        if (bgColorCB.isSelected())
          colorPanel.setBackground(selColor);
        else
          colorPanel.setBackground(getBackground());
      });
    }
    return bgColorCB;
  }

  /**
   * This method initializes colorPanel
   *
   * @return javax.swing.JPanel
   */
  private JPanel getColorPanel() {
    if (colorPanel == null) {
      colorPanel = new JPanel();
      colorPanel.setPreferredSize(new java.awt.Dimension(50, 20));
      colorPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

    }
    return colorPanel;
  }

  /**
   * This method initializes colorButton
   *
   * @return javax.swing.JButton
   */
  private JButton getColorButton() {
    if (colorButton == null) {
      colorButton = new JButton();
      colorButton.setIcon(UIUtils.getIcon(UIUtils.X16, "color.png"));
      colorButton.setPreferredSize(new java.awt.Dimension(20, 20));
      colorButton.addActionListener(e -> {
        Color c = JColorChooser.showDialog(BGColorPanel.this, i18n.str("color"), selColor);
        if (c != null) {
          selColor = c;
          colorPanel.setBackground(c);
          colorPanel.setToolTipText(HTMLUtils.colorToHex(c));
        }
      });
    }
    return colorButton;
  }
}