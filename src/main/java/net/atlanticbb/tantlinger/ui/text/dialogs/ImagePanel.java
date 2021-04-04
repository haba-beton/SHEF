package net.atlanticbb.tantlinger.ui.text.dialogs;

import net.atlanticbb.tantlinger.ui.text.HTMLUtils;
import net.atlanticbb.tantlinger.ui.text.TextEditPopupManager;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class ImagePanel extends HTMLAttributeEditorPanel {
  private static final long serialVersionUID = 1L;
  private ImageAttributesPanel imageAttrPanel;
  private LinkAttributesPanel linkAttrPanel;
  private JTextField linkUrlField;
  private JCheckBox linkCB;

  public ImagePanel() {
    this(new Hashtable<>());
  }

  public ImagePanel(Hashtable<String,String> at) {
    super();
    initialize();
    setAttributes(at);
    updateComponentsFromAttribs();
  }

  private String createAttribs(Map<String,String> ht) {
    String html = "";
    for (String k : ht.keySet()) {
      html += " " + k + "=" + "\"" + ht.get(k) + "\"";
    }

    return html;
  }

  public void updateComponentsFromAttribs() {
    imageAttrPanel.setAttributes(attribs);
    if (attribs.containsKey("a")) {
      linkCB.setSelected(true);
      linkAttrPanel.setEnabled(true);
      linkUrlField.setEditable(true);
      Map<String,String> ht = HTMLUtils.tagAttribsToMap(attribs.get("a"));
      linkUrlField.setText(ht.getOrDefault("href", ""));
      linkAttrPanel.setAttributes(ht);
    }
    else {
      linkCB.setSelected(false);
      linkAttrPanel.setEnabled(false);
      linkUrlField.setEditable(false);
      linkAttrPanel.setAttributes(new HashMap<>());
    }
  }

  public void updateAttribsFromComponents() {
    imageAttrPanel.updateAttribsFromComponents();
    linkAttrPanel.updateAttribsFromComponents();
    if (linkCB.isSelected()) {
      Map<String,String> ht = linkAttrPanel.getAttributes();
      ht.put("href", linkUrlField.getText());
      attribs.put("a", createAttribs(ht));
    } else {
      attribs.remove("a");
    }
  }

  private void initialize() {
    JTabbedPane tabs = new JTabbedPane();
    linkAttrPanel = new LinkAttributesPanel();
    linkCB = new JCheckBox(i18n.str("link"));
    linkUrlField = new JTextField();
    JPanel urlPanel = new JPanel(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.anchor = java.awt.GridBagConstraints.WEST;
    gbc.insets = new java.awt.Insets(0, 0, 5, 5);
    gbc.gridy = 0;
    urlPanel.add(linkCB, gbc);

    gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.insets = new java.awt.Insets(0, 0, 5, 0);
    gbc.gridx = 1;
    urlPanel.add(linkUrlField, gbc);

    JPanel linkPanel = new JPanel(new BorderLayout(5, 5));
    linkPanel.add(urlPanel, BorderLayout.NORTH);
    linkPanel.add(linkAttrPanel, BorderLayout.CENTER);
    linkPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    imageAttrPanel = new ImageAttributesPanel();
    imageAttrPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    tabs.addTab(i18n.str("image"), imageAttrPanel);
    tabs.addTab(i18n.str("link"), linkPanel);        

    setLayout(new BorderLayout());
    add(tabs);

    linkAttrPanel.setEnabled(linkCB.isSelected());
    linkUrlField.setEditable(linkCB.isSelected());
    linkCB.addItemListener(e -> {
      linkAttrPanel.setEnabled(linkCB.isSelected());
      linkUrlField.setEditable(linkCB.isSelected());
    });

    TextEditPopupManager.getInstance().registerJTextComponent(linkUrlField);
  }
}