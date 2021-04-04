package net.atlanticbb.tantlinger.ui.text.dialogs;

import net.atlanticbb.tantlinger.i18n.I18n;

import javax.swing.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public abstract class HTMLAttributeEditorPanel extends JPanel {
  static final I18n i18n = I18n.getInstance("net.atlanticbb.tantlinger.ui.text.dialogs");

  protected Map<String,String> attribs = new HashMap<>();

  public HTMLAttributeEditorPanel() {
    super();
  }

  public HTMLAttributeEditorPanel(Hashtable<String,String> attribs) {
    super();
    this.attribs = attribs;
  }

  public void setAttributes(Map<String,String> attribs) {
    this.attribs = attribs;
    updateComponentsFromAttribs();
  }

  public Map<String,String> getAttributes() {
    updateAttribsFromComponents();
    return attribs;
  }

  public abstract void updateComponentsFromAttribs();

  public abstract void updateAttribsFromComponents();
}
