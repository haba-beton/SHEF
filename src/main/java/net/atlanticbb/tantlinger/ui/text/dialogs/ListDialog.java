package net.atlanticbb.tantlinger.ui.text.dialogs;

import net.atlanticbb.tantlinger.i18n.I18n;
import net.atlanticbb.tantlinger.ui.OptionDialog;
import net.atlanticbb.tantlinger.ui.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ListDialog extends OptionDialog {
  private static final I18n i18n = I18n.getInstance("net.atlanticbb.tantlinger.ui.text.dialogs");

  public static final int UNORDERED = ListAttributesPanel.UL_LIST;
  public static final int ORDERED = ListAttributesPanel.OL_LIST;

  private static Icon icon = UIUtils.getIcon(UIUtils.X48, "categories.png");
  private static String title = i18n.str("list_properties");
  private static String desc = i18n.str("list_properties_desc");

  private ListAttributesPanel listAttrPanel;

  public ListDialog(Frame parent) {
    super(parent, title, desc, icon);
    init();
  }

  public ListDialog(Dialog parent) {
    super(parent, title, desc, icon);
    init();
  }

  private void init() {
    listAttrPanel = new ListAttributesPanel();
    setContentPane(listAttrPanel);
    pack();
    setSize(220, getHeight());
    setResizable(false);
  }

  public void setListType(int t) {
    listAttrPanel.setListType(t);
  }

  public int getListType() {
    return listAttrPanel.getListType();
  }

  public void setListAttributes(Map<String,String> attr) {
    listAttrPanel.setAttributes(attr);
  }

  public Map<String,String> getListAttributes() {
    return listAttrPanel.getAttributes();
  }
}
