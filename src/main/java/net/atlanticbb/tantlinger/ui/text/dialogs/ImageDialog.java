package net.atlanticbb.tantlinger.ui.text.dialogs;

import net.atlanticbb.tantlinger.i18n.I18n;
import net.atlanticbb.tantlinger.ui.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ImageDialog extends HTMLOptionDialog {

  private static final I18n i18n = I18n.getInstance("net.atlanticbb.tantlinger.ui.text.dialogs");

  private static Icon icon = UIUtils.getIcon(UIUtils.X48, "image.png");
  private static String title = i18n.str("image");
  private static String desc = i18n.str("image_desc");

  private ImagePanel imagePanel;

  public ImageDialog(Frame parent) {
    super(parent, title, desc, icon);
    init();
  }

  public ImageDialog(Dialog parent) {
    super(parent, title, desc, icon);
    init();
  }

  private void init() {
    imagePanel = new ImagePanel();
    setContentPane(imagePanel);
    setSize(600, 400);
    setResizable(false);
  }

  public void setImageAttributes(Map<String,String> attr) {
    imagePanel.setAttributes(attr);
  }

  public Map<String,String> getImageAttributes() {
    return imagePanel.getAttributes();
  }

  private String createImgAttributes(Map<String,String> ht) {
    String html = "";
    for (String k : ht.keySet()) {
      if (k.equals("a") || k.equals("name"))
        continue;
      html += " " + k + "=" + "\"" + ht.get(k) + "\"";
    }

    return html;
  }

  public String getHTML() {
    Map<String,String> imgAttr = imagePanel.getAttributes();
    boolean hasLink = imgAttr.containsKey("a");
    String html = "";
    if (hasLink) {
      html = "<a " + imgAttr.get("a") + ">";
    }
    html += "<img" + createImgAttributes(imgAttr) + ">";

    if (hasLink)
      html += "</a>";

    return html;
  }

}
