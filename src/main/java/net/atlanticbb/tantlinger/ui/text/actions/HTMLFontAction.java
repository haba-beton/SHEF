package net.atlanticbb.tantlinger.ui.text.actions;

import net.atlanticbb.tantlinger.ui.text.CompoundUndoManager;
import net.atlanticbb.tantlinger.ui.text.HTMLUtils;
import net.atlanticbb.tantlinger.ui.text.dialogs.HTMLFontDialog;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HTMLFontAction extends HTMLTextEditAction {

  public HTMLFontAction() {
    super(i18n.str("font_"));
  }

  protected void sourceEditPerformed(ActionEvent e, JEditorPane editor) {
    HTMLFontDialog d = createDialog(editor);
    d.setLocationRelativeTo(d.getParent());
    d.setVisible(true);
    if (!d.hasUserCancelled()) {
      editor.requestFocusInWindow();
      editor.replaceSelection(d.getHTML());
    }
  }

  protected void wysiwygEditPerformed(ActionEvent e, JEditorPane editor) {
    HTMLDocument doc = (HTMLDocument) editor.getDocument();
    Element chElem = doc.getCharacterElement(editor.getCaretPosition());
    AttributeSet sas = chElem.getAttributes();

    HTMLFontDialog d = createDialog(editor);
    d.setBold(sas.containsAttribute(StyleConstants.Bold, Boolean.TRUE));
    d.setItalic(sas.containsAttribute(StyleConstants.Italic, Boolean.TRUE));
    d.setUnderline(sas.containsAttribute(StyleConstants.Underline, Boolean.TRUE));

    Object o = sas.getAttribute(StyleConstants.FontFamily);
    if (o != null)
      d.setFontName(o.toString());
    o = sas.getAttribute(StyleConstants.FontSize);
    if (o != null) {
      try {
        d.setFontSize(Integer.parseInt(o.toString()));
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    d.setLocationRelativeTo(d.getParent());
    d.setVisible(true);
    if (!d.hasUserCancelled()) {
      MutableAttributeSet tagAttrs = new SimpleAttributeSet();
      tagAttrs.addAttribute(StyleConstants.FontFamily, d.getFontName());
      tagAttrs.addAttribute(StyleConstants.FontSize, d.getFontSize());
      tagAttrs.addAttribute(StyleConstants.Bold, d.isBold());
      tagAttrs.addAttribute(StyleConstants.Italic, d.isItalic());
      tagAttrs.addAttribute(StyleConstants.Underline, d.isUnderline());

      CompoundUndoManager.beginCompoundEdit(editor.getDocument());
      HTMLUtils.setCharacterAttributes(editor, tagAttrs);
      CompoundUndoManager.endCompoundEdit(editor.getDocument());
    }
  }

  private HTMLFontDialog createDialog(JTextComponent ed) {
    Window w = SwingUtilities.getWindowAncestor(ed);
    String t = "";
    if (ed.getSelectedText() != null)
      t = ed.getSelectedText();
    HTMLFontDialog d = null;
    if (w instanceof Frame)
      d = new HTMLFontDialog((Frame) w, t);
    else if (w instanceof Dialog)
      d = new HTMLFontDialog((Dialog) w, t);

    return d;
  }
}