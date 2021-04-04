package net.atlanticbb.tantlinger.ui.text.dialogs;

import net.atlanticbb.tantlinger.ui.text.TextEditPopupManager;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;
import java.util.Map;


public class LinkPanel extends HTMLAttributeEditorPanel {
  private JPanel hlinkPanel = null;
  private JLabel urlLabel = null;
  private JLabel textLabel = null;
  private JTextField urlField = null;
  private JTextField textField = null;
  private HTMLAttributeEditorPanel linkAttrPanel = null;

  private boolean urlFieldEnabled;

  /**
   * This is the default constructor
   */
  public LinkPanel() {
    this(true);
  }

  public LinkPanel(boolean urlFieldEnabled) {
    this(new Hashtable<>(), urlFieldEnabled);
  }

  public LinkPanel(Hashtable<String,String> attr, boolean urlFieldEnabled) {
    super();
    this.urlFieldEnabled = urlFieldEnabled;
    initialize();
    setAttributes(attr);
    updateComponentsFromAttribs();
  }


  public void updateComponentsFromAttribs() {
    linkAttrPanel.updateComponentsFromAttribs();
    urlField.setText(attribs.getOrDefault("href", ""));
  }

  public void updateAttribsFromComponents() {
    linkAttrPanel.updateAttribsFromComponents();
    attribs.put("href", urlField.getText());
  }

  public void setAttributes(Map<String,String> at) {
    super.setAttributes(at);
    linkAttrPanel.setAttributes(attribs);
  }

  public void setLinkText(String text) {
    textField.setText(text);
  }

  public String getLinkText() {
    return textField.getText();
  }

  private void initialize() {
    this.setLayout(new BorderLayout(5, 5));
    this.setSize(328, 218);
    this.add(getHlinkPanel(), java.awt.BorderLayout.NORTH);
    this.add(getLinkAttrPanel(), BorderLayout.CENTER);

    TextEditPopupManager popupMan = TextEditPopupManager.getInstance();
    popupMan.registerJTextComponent(urlField);
    popupMan.registerJTextComponent(textField);
  }

  private JPanel getHlinkPanel() {
    if (hlinkPanel == null) {
      GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
      gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
      gridBagConstraints3.gridy = 1;
      gridBagConstraints3.weightx = 1.0;
      gridBagConstraints3.gridx = 1;
      GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
      gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
      gridBagConstraints2.gridy = 0;
      gridBagConstraints2.weightx = 1.0;
      gridBagConstraints2.insets = new java.awt.Insets(0, 0, 5, 0);
      gridBagConstraints2.gridx = 1;
      GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
      gridBagConstraints1.gridx = 0;
      gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
      gridBagConstraints1.insets = new java.awt.Insets(0, 0, 0, 5);
      gridBagConstraints1.gridy = 1;
      textLabel = new JLabel();
      textLabel.setText(i18n.str("text"));
      GridBagConstraints gridBagConstraints = new GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
      gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
      gridBagConstraints.gridy = 0;
      urlLabel = new JLabel();
      urlLabel.setText(i18n.str("url"));
      hlinkPanel = new JPanel();
      hlinkPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createTitledBorder(null, i18n.str("link"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null), javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)));
      hlinkPanel.setLayout(new GridBagLayout());
      hlinkPanel.add(urlLabel, gridBagConstraints);
      hlinkPanel.add(textLabel, gridBagConstraints1);
      hlinkPanel.add(getUrlField(), gridBagConstraints2);
      hlinkPanel.add(getTextField(), gridBagConstraints3);
    }
    return hlinkPanel;
  }

  private JTextField getUrlField() {
    if (urlField == null) {
      urlField = new JTextField();
      urlField.setEditable(urlFieldEnabled);
    }
    return urlField;
  }

  private JTextField getTextField() {
    if (textField == null) {
      textField = new JTextField();
    }
    return textField;
  }

  private JPanel getLinkAttrPanel() {
    if (linkAttrPanel == null) {
      linkAttrPanel = new LinkAttributesPanel();
    }

    return linkAttrPanel;
  }
}