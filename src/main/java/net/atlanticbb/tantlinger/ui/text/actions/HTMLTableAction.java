package net.atlanticbb.tantlinger.ui.text.actions;

import net.atlanticbb.tantlinger.ui.UIUtils;
import net.atlanticbb.tantlinger.ui.text.CompoundUndoManager;
import net.atlanticbb.tantlinger.ui.text.HTMLUtils;
import net.atlanticbb.tantlinger.ui.text.dialogs.NewTableDialog;

import javax.swing.*;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HTMLTableAction extends HTMLTextEditAction {
  private static final long serialVersionUID = 1L;

  public HTMLTableAction() {
    super(i18n.str("table_"));
    putValue(MNEMONIC_KEY, (int) i18n.mnem("table_"));

    putValue(SMALL_ICON, UIUtils.getIcon(UIUtils.X16, "table.png"));
    putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME));
  }

  protected void sourceEditPerformed(ActionEvent e, JEditorPane editor) {
    NewTableDialog dlg = createNewTableDialog(editor);
    if (dlg == null)
      return;
    dlg.setLocationRelativeTo(dlg.getParent());
    dlg.setVisible(true);
    if (dlg.hasUserCancelled())
      return;

    editor.replaceSelection(dlg.getHTML());
  }

  protected void wysiwygEditPerformed(ActionEvent e, JEditorPane editor) {
    NewTableDialog dlg = createNewTableDialog(editor);
    if (dlg == null)
      return;
    dlg.setLocationRelativeTo(dlg.getParent());
    dlg.setVisible(true);
    if (dlg.hasUserCancelled())
      return;

    HTMLDocument document = (HTMLDocument) editor.getDocument();
    String html = dlg.getHTML();

    Element elem = document.getParagraphElement(editor.getCaretPosition());
    CompoundUndoManager.beginCompoundEdit(document);
    try {
      if (HTMLUtils.isElementEmpty(elem))
        document.setOuterHTML(elem, html);
      else if (elem.getName().equals("p-implied"))
        document.insertAfterEnd(elem, html);
      else
        HTMLUtils.insertHTML(html, HTML.Tag.TABLE, editor);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    CompoundUndoManager.endCompoundEdit(document);
  }

  private NewTableDialog createNewTableDialog(JTextComponent ed) {
    Window w = SwingUtilities.getWindowAncestor(ed);
    NewTableDialog d = null;
    if (w instanceof Frame)
      d = new NewTableDialog((Frame) w);
    else if (w instanceof Dialog)
      d = new NewTableDialog((Dialog) w);

    return d;
  }
}
