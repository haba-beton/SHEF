package net.atlanticbb.tantlinger.ui.text.dialogs;

import net.atlanticbb.tantlinger.i18n.I18n;
import net.atlanticbb.tantlinger.ui.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.Vector;


public class HTMLFontDialog extends HTMLOptionDialog {
  private static final long serialVersionUID = 1L;

  private static final I18n i18n = I18n.getInstance("net.atlanticbb.tantlinger.ui.text.dialogs");

  private static Icon icon = UIUtils.getIcon(UIUtils.X48, "fontsize.png");
  private static String title = i18n.str("font");
  private static String desc = i18n.str("font_desc");

  private static final Integer[] SIZES = {
    8,
    10,
    12,
    14,
    18,
    24,
    36
  };

  private JPanel jContentPane = null;
  private JLabel fontLabel = null;
  private JComboBox<String>  fontCombo = null;
  private JComboBox<Integer> sizeCombo = null;
  private JPanel stylePanel = null;
  private JCheckBox boldCB = null;
  private JCheckBox italicCB = null;
  private JCheckBox ulCB = null;
  private JPanel previewPanel = null;
  private JLabel previewLabel = null;
  private JPanel spacerPanel = null;

  private String text = "";

  public HTMLFontDialog(Frame parent, String text) {
    super(parent, title, desc, icon);
    initialize(text);
  }

  public HTMLFontDialog(Dialog parent, String text) {
    super(parent, title, desc, icon);
    initialize(text);
  }

  public boolean isBold() {
    return boldCB.isSelected();
  }

  public boolean isItalic() {
    return italicCB.isSelected();
  }

  public boolean isUnderline() {
    return ulCB.isSelected();
  }

  public void setBold(boolean b) {
    boldCB.setSelected(b);
    updatePreview();
  }

  public void setItalic(boolean b) {
    italicCB.setSelected(b);
    updatePreview();
  }

  public void setUnderline(boolean b) {
    ulCB.setSelected(b);
    updatePreview();
  }

  public void setFontName(String fn) {
    fontCombo.setSelectedItem(fn);
    updatePreview();
  }

  public String getFontName() {
    return fontCombo.getSelectedItem().toString();
  }

  public int getFontSize() {
    return (Integer) sizeCombo.getSelectedItem();
  }

  public void setFontSize(int size) {
    sizeCombo.setSelectedItem(size);
    updatePreview();
  }

  public String getHTML() {
    String html = "<font ";
    html += "name=\"" + fontCombo.getSelectedItem() + "\" ";
    html += "size=\"" + (sizeCombo.getSelectedIndex() + 1) + "\">";

    if (boldCB.isSelected())
      html += "<b>";
    if (italicCB.isSelected())
      html += "<i>";
    if (ulCB.isSelected())
      html += "<u>";

    html += text;

    if (boldCB.isSelected())
      html += "</b>";
    if (italicCB.isSelected())
      html += "</i>";
    if (ulCB.isSelected())
      html += "</u>";

    html += "</font>";
    return html;
  }

  private void initialize(String text) {
    setContentPane(getJContentPane());
    pack();
    setSize(350, getHeight());
    setResizable(false);
    this.text = text;
  }

  private void updatePreview() {
    int style = Font.PLAIN;
    if (boldCB.isSelected())
      style += Font.BOLD;
    if (italicCB.isSelected())
      style += Font.ITALIC;

    if (ulCB.isSelected())
      previewLabel.setBorder(
        BorderFactory.createMatteBorder(
          0, 0, 1, 0, previewLabel.getForeground()));
    else
      previewLabel.setBorder(null);

    String font = fontCombo.getSelectedItem().toString();
    Integer size = SIZES[sizeCombo.getSelectedIndex()];
    Font f = new Font(font, style, size);
    previewLabel.setFont(f);

  }

  /**
   * This method initializes jContentPane
   *
   * @return javax.swing.JPanel
   */
  private JPanel getJContentPane() {
    if (jContentPane == null) {
      GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
      gridBagConstraints21.gridx = 0;
      gridBagConstraints21.gridwidth = 3;
      gridBagConstraints21.anchor = java.awt.GridBagConstraints.WEST;
      gridBagConstraints21.fill = java.awt.GridBagConstraints.HORIZONTAL;
      gridBagConstraints21.insets = new java.awt.Insets(5, 0, 0, 0);
      gridBagConstraints21.gridy = 1;
      GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
      gridBagConstraints2.fill = java.awt.GridBagConstraints.NONE;
      gridBagConstraints2.gridy = 0;
      gridBagConstraints2.weightx = 1.0;
      gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
      gridBagConstraints2.gridx = 2;
      GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
      gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
      gridBagConstraints1.gridy = 0;
      gridBagConstraints1.weightx = 1.0;
      gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
      gridBagConstraints1.insets = new java.awt.Insets(0, 0, 0, 5);
      gridBagConstraints1.gridx = 1;
      GridBagConstraints gridBagConstraints = new GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
      gridBagConstraints.gridy = 0;
      fontLabel = new JLabel();
      fontLabel.setText(i18n.str("font")); //$NON-NLS-1$
      jContentPane = new JPanel();
      jContentPane.setLayout(new GridBagLayout());
      jContentPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
      jContentPane.add(fontLabel, gridBagConstraints);
      jContentPane.add(getFontCombo(), gridBagConstraints1);
      jContentPane.add(getSizeCombo(), gridBagConstraints2);
      jContentPane.add(getStylePanel(), gridBagConstraints21);

      sizeCombo.setSelectedItem(previewLabel.getFont().getSize());
    }
    return jContentPane;
  }

  /**
   * This method initializes fontCombo
   *
   * @return javax.swing.JComboBox
   */
  private JComboBox<String> getFontCombo() {
    if (fontCombo == null) {
      GraphicsEnvironment gEnv =
        GraphicsEnvironment.getLocalGraphicsEnvironment();
      String[] envfonts = gEnv.getAvailableFontFamilyNames();
      Vector<String> fonts = new Vector<>();
      fonts.add("Default");
      fonts.add("serif");
      fonts.add("sans-serif");
      fonts.add("monospaced");
      Collections.addAll(fonts, envfonts);

      fontCombo = new JComboBox<>(fonts);
      fontCombo.addItemListener(e -> updatePreview());
    }
    return fontCombo;
  }

  /**
   * This method initializes sizeCombo
   *
   * @return javax.swing.JComboBox
   */
  private JComboBox<Integer> getSizeCombo() {
    if (sizeCombo == null) {
      sizeCombo = new JComboBox<>(SIZES);
      sizeCombo.setSelectedItem(12);
      sizeCombo.addItemListener(e -> updatePreview());
    }
    return sizeCombo;
  }

  /**
   * This method initializes stylePanel
   *
   * @return javax.swing.JPanel
   */
  private JPanel getStylePanel() {
    if (stylePanel == null) {
      GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
      gridBagConstraints7.gridx = 0;
      gridBagConstraints7.anchor = java.awt.GridBagConstraints.NORTHWEST;
      gridBagConstraints7.weighty = 1.0;
      gridBagConstraints7.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints7.gridy = 3;
      GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
      gridBagConstraints6.gridx = 1;
      gridBagConstraints6.gridwidth = 1;
      gridBagConstraints6.gridheight = 4;
      gridBagConstraints6.fill = java.awt.GridBagConstraints.BOTH;
      gridBagConstraints6.weightx = 1.0;
      gridBagConstraints6.weighty = 1.0;
      gridBagConstraints6.anchor = java.awt.GridBagConstraints.NORTHWEST;
      gridBagConstraints6.gridy = 0;
      GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
      gridBagConstraints5.gridx = 0;
      gridBagConstraints5.anchor = java.awt.GridBagConstraints.WEST;
      gridBagConstraints5.insets = new java.awt.Insets(0, 0, 0, 5);
      gridBagConstraints5.weighty = 0.0;
      gridBagConstraints5.gridy = 2;
      GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
      gridBagConstraints4.gridx = 0;
      gridBagConstraints4.anchor = java.awt.GridBagConstraints.WEST;
      gridBagConstraints4.insets = new java.awt.Insets(0, 0, 0, 5);
      gridBagConstraints4.gridy = 1;
      GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
      gridBagConstraints3.gridx = 0;
      gridBagConstraints3.anchor = java.awt.GridBagConstraints.WEST;
      gridBagConstraints3.insets = new java.awt.Insets(5, 0, 0, 5);
      gridBagConstraints3.gridy = 0;
      stylePanel = new JPanel();
      stylePanel.setLayout(new GridBagLayout());
      stylePanel.add(getBoldCB(), gridBagConstraints3);
      stylePanel.add(getItalicCB(), gridBagConstraints4);
      stylePanel.add(getUlCB(), gridBagConstraints5);
      stylePanel.add(getPreviewPanel(), gridBagConstraints6);
      stylePanel.add(getSpacerPanel(), gridBagConstraints7);
    }
    return stylePanel;
  }

  /**
   * This method initializes boldCB
   *
   * @return javax.swing.JCheckBox
   */
  private JCheckBox getBoldCB() {
    if (boldCB == null) {
      boldCB = new JCheckBox();
      boldCB.setText(i18n.str("bold"));
      boldCB.addItemListener(e -> updatePreview());
    }
    return boldCB;
  }

  /**
   * This method initializes italicCB
   *
   * @return javax.swing.JCheckBox
   */
  private JCheckBox getItalicCB() {
    if (italicCB == null) {
      italicCB = new JCheckBox();
      italicCB.setText(i18n.str("italic"));
      italicCB.addItemListener(e -> updatePreview());
    }
    return italicCB;
  }

  private JCheckBox getUlCB() {
    if (ulCB == null) {
      ulCB = new JCheckBox();
      ulCB.setText(i18n.str("underline"));
      ulCB.addItemListener(e -> updatePreview());
    }
    return ulCB;
  }

  private JPanel getPreviewPanel() {
    if (previewPanel == null) {
      previewLabel = new JLabel();
      previewLabel.setText("AaBbYyZz");
      JPanel spacer = new JPanel(new FlowLayout(FlowLayout.LEFT));
      spacer.setBackground(Color.WHITE);
      spacer.add(previewLabel);
      previewPanel = new JPanel();
      previewPanel.setLayout(new BorderLayout());
      previewPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createTitledBorder(null, i18n.str("preview"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null), javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED))))); //$NON-NLS-1$
      previewPanel.setPreferredSize(new java.awt.Dimension(90, 100));
      previewPanel.setMaximumSize(previewPanel.getPreferredSize());
      previewPanel.setMinimumSize(previewPanel.getPreferredSize());
      previewPanel.add(spacer, null);
    }
    return previewPanel;
  }

  /**
   * This method initializes spacerPanel
   */
  private JPanel getSpacerPanel() {
    if (spacerPanel == null) {
      spacerPanel = new JPanel();
    }
    return spacerPanel;
  }
}