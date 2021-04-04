package net.atlanticbb.tantlinger.ui.text.actions;

import net.atlanticbb.tantlinger.ui.UIUtils;
import net.atlanticbb.tantlinger.ui.text.HTMLUtils;

import javax.swing.*;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HTMLFontColorAction extends HTMLTextEditAction {

  private static final long serialVersionUID = 1L;

  public HTMLFontColorAction() {
    super(i18n.str("color_"));
    putValue(MNEMONIC_KEY,(int)i18n.mnem("color_"));
    this.putValue(SMALL_ICON, UIUtils.getIcon(UIUtils.X16, "color.png"));
  }

  protected void sourceEditPerformed(ActionEvent e, JEditorPane editor) {
    Color c = getColorFromUser(editor);
    if (c == null)
      return;

    String prefix = "<font color=" + HTMLUtils.colorToHex(c) + ">";
    String postfix = "</font>";
    String sel = editor.getSelectedText();
    if (sel == null) {
      editor.replaceSelection(prefix + postfix);

      int pos = editor.getCaretPosition() - postfix.length();
      if (pos >= 0)
        editor.setCaretPosition(pos);
    } else {
      sel = prefix + sel + postfix;
      editor.replaceSelection(sel);
    }
  }

  protected void wysiwygEditPerformed(ActionEvent e, JEditorPane editor) {
    Color color = getColorFromUser(editor);
    if (color != null) {
      Action a = new StyledEditorKit.ForegroundAction("Color", color);
      a.actionPerformed(e);
    }
  }

  private Color getColorFromUser(Component c) {
    Window win = SwingUtilities.getWindowAncestor(c);
    if (win != null)
      c = win;
    return JColorChooser.showDialog(c, "Color", Color.black);
  }

}
