package net.atlanticbb.tantlinger.ui.text.dialogs;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;
import java.util.Map;

public class RowAttributesPanel extends HTMLAttributeEditorPanel {
  private AlignmentAttributesPanel alignPanel = null;
  private BGColorPanel bgColorPanel = null;
  private JPanel expansionPanel = null;

  public RowAttributesPanel() {
    this(new Hashtable<>());
  }

  public RowAttributesPanel(Hashtable<String,String> attr) {
    super(attr);
    initialize();
    alignPanel.setAttributes(getAttributes());
    updateComponentsFromAttribs();
  }

  public void updateComponentsFromAttribs() {
    if (attribs.containsKey("bgcolor")) {
      bgColorPanel.setSelected(true);
      bgColorPanel.setColor(attribs.get("bgcolor"));
    }
    alignPanel.updateComponentsFromAttribs();
  }

  public void updateAttribsFromComponents() {
    if (bgColorPanel.isSelected())
      attribs.put("bgcolor", bgColorPanel.getColor());
    else
      attribs.remove("bgcolor");
    alignPanel.updateAttribsFromComponents();
  }

  public void setComponentStates(Hashtable<String,String> attribs) {
    if (attribs.containsKey("bgcolor")) {
      bgColorPanel.setSelected(true);
      bgColorPanel.setColor(attribs.get("bgcolor"));
    }
    alignPanel.setComponentStates(attribs);
  }

  public void setAttributes(Map<String,String> attr) {
    alignPanel.setAttributes(attr);
    super.setAttributes(attr);
  }

  private void initialize() {
    GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
    gridBagConstraints2.gridx = 0;
    gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints2.weighty = 1.0;
    gridBagConstraints2.gridy = 2;
    GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
    gridBagConstraints1.gridx = 0;
    gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints1.weightx = 1.0;
    gridBagConstraints1.gridy = 1;
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
    gridBagConstraints.weightx = 0.0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.gridy = 0;
    this.setLayout(new GridBagLayout());
    this.setSize(279, 140);
    this.setPreferredSize(new java.awt.Dimension(215, 140));
    this.add(getAlignPanel(), gridBagConstraints);
    this.add(getBgColorPanel(), gridBagConstraints1);
    this.add(getExpansionPanel(), gridBagConstraints2);
  }

  private AlignmentAttributesPanel getAlignPanel() {
    if (alignPanel == null) {
      alignPanel = new AlignmentAttributesPanel();
    }
    return alignPanel;
  }

  private BGColorPanel getBgColorPanel() {
    if (bgColorPanel == null) {
      bgColorPanel = new BGColorPanel();
    }
    return bgColorPanel;
  }

  private JPanel getExpansionPanel() {
    if (expansionPanel == null) {
      expansionPanel = new JPanel();
    }
    return expansionPanel;
  }
}