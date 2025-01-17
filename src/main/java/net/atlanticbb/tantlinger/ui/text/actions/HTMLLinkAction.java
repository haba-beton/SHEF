package net.atlanticbb.tantlinger.ui.text.actions;

import net.atlanticbb.tantlinger.ui.UIUtils;
import net.atlanticbb.tantlinger.ui.text.HTMLUtils;
import net.atlanticbb.tantlinger.ui.text.dialogs.HyperlinkDialog;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import javax.swing.text.html.HTML;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HTMLLinkAction extends HTMLTextEditAction {
  private static final long serialVersionUID = 1L;

  public HTMLLinkAction() {
    super(i18n.str("hyperlink_"));
    putValue(MNEMONIC_KEY, (int) i18n.mnem("hyperlink_"));

    putValue(SMALL_ICON, UIUtils.getIcon(UIUtils.X16, "link.png"));
    putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME));
  }

  protected void sourceEditPerformed(ActionEvent e, JEditorPane editor) {
    HyperlinkDialog dlg = createDialog(editor);
    if (dlg == null)
      return;

    dlg.setLocationRelativeTo(dlg.getParent());
    dlg.setLinkText(editor.getSelectedText());
    dlg.setVisible(true);
    if (dlg.hasUserCancelled())
      return;

    editor.requestFocusInWindow();
    editor.replaceSelection(dlg.getHTML());
  }

  protected void wysiwygEditPerformed(ActionEvent e, JEditorPane editor) {
    HyperlinkDialog dlg = createDialog(editor);
    if (dlg == null)
      return;

    if (editor.getSelectedText() != null)
      dlg.setLinkText(editor.getSelectedText());
    dlg.setLocationRelativeTo(dlg.getParent());
    dlg.setVisible(true);
    if (dlg.hasUserCancelled())
      return;

    String tagText = dlg.getHTML();
    //if(editor.getCaretPosition() == document.getLength())
    if (editor.getSelectedText() == null)
      tagText += "&nbsp;";

    editor.replaceSelection("");
    HTMLUtils.insertHTML(tagText, HTML.Tag.A, editor);
    dlg = null;
  }

  protected HyperlinkDialog createDialog(JTextComponent ed) {
    Window w = SwingUtilities.getWindowAncestor(ed);
    HyperlinkDialog d = null;
    if (w instanceof Frame)
      d = new HyperlinkDialog((Frame) w);
    else if (w instanceof Dialog)
      d = new HyperlinkDialog((Dialog) w);


    return d;
  }
}