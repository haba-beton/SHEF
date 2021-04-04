package net.atlanticbb.tantlinger.ui.text.dialogs;

import net.atlanticbb.tantlinger.i18n.I18n;
import net.atlanticbb.tantlinger.ui.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class HyperlinkDialog extends HTMLOptionDialog {
  private static final I18n i18n = I18n.getInstance("net.atlanticbb.tantlinger.ui.text.dialogs");

  private static Icon icon = UIUtils.getIcon(UIUtils.X48, "link.png");
  private static String title = i18n.str("hyperlink");
  private static String desc = i18n.str("hyperlink_desc");

  private LinkPanel linkPanel;

  public HyperlinkDialog(Frame parent) {
    this(parent, title, desc, icon, true);
  }

  public HyperlinkDialog(Dialog parent) {
    this(parent, title, desc, icon, true);
  }

  public HyperlinkDialog(Dialog parent, String title, String desc, Icon ico, boolean urlFieldEnabled) {
    super(parent, title, desc, ico);
    init(urlFieldEnabled);
  }

  public HyperlinkDialog(Frame parent, String title, String desc, Icon ico, boolean urlFieldEnabled) {
    super(parent, title, desc, ico);
    init(urlFieldEnabled);
  }

  private void init(boolean urlFieldEnabled) {
    linkPanel = new LinkPanel(urlFieldEnabled);
    linkPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    setContentPane(linkPanel);
    setSize(500, 370);
    setResizable(false);
  }

  public Map<String,String> getAttributes() {
    return linkPanel.getAttributes();
  }

  public void setAttributes(Map<String,String> attribs) {
    linkPanel.setAttributes(attribs);
  }

  public void setLinkText(String text) {
    linkPanel.setLinkText(text);
  }

  public String getLinkText() {
    return linkPanel.getLinkText();
  }

  public String getHTML() {
    String html = "<a";
    Map<String,String> ht = getAttributes();
    for (String k : ht.keySet()) {
      html += " " + k + "=" + "\"" + ht.get(k) + "\"";
    }

    html += ">" + getLinkText() + "</a>";

    return html;
  }
}