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

  protected void sourceEditPerformed(ActionEvent e,JEditorPane editor) {
    HTMLFontDialog htmlFontDialog = createDialog(editor);
    htmlFontDialog.setLocationRelativeTo(htmlFontDialog.getParent());
    htmlFontDialog.setVisible(true);
    if (!htmlFontDialog.hasUserCancelled()) {
      editor.requestFocusInWindow();
      editor.replaceSelection(htmlFontDialog.getHTML());
    }
  }

  protected void wysiwygEditPerformed(ActionEvent e,JEditorPane editor) {
    HTMLDocument doc                        = (HTMLDocument)editor.getDocument();
    Element      characterElement           = doc.getCharacterElement(editor.getCaretPosition());
    AttributeSet characterElementAttributes = characterElement.getAttributes();

    HTMLFontDialog htmlFontDialog = createDialog(editor);
    htmlFontDialog.setBold(     characterElementAttributes.containsAttribute(StyleConstants.Bold,     Boolean.TRUE));
    htmlFontDialog.setItalic(   characterElementAttributes.containsAttribute(StyleConstants.Italic,   Boolean.TRUE));
    htmlFontDialog.setUnderline(characterElementAttributes.containsAttribute(StyleConstants.Underline,Boolean.TRUE));

    Object attribute = characterElementAttributes.getAttribute(StyleConstants.FontFamily);

    if (attribute != null) {
      htmlFontDialog.setFontName(attribute.toString());
    }

    attribute = characterElementAttributes.getAttribute(StyleConstants.FontSize);

    if (attribute != null) {
      try {
        htmlFontDialog.setFontSize(Integer.parseInt(attribute.toString()));
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    htmlFontDialog.setLocationRelativeTo(htmlFontDialog.getParent());
    htmlFontDialog.setVisible(true);

    if (!htmlFontDialog.hasUserCancelled()) {
      MutableAttributeSet tagAttrs = new SimpleAttributeSet();
      tagAttrs.addAttribute(StyleConstants.FontFamily, htmlFontDialog.getFontName());
      tagAttrs.addAttribute(StyleConstants.FontSize, htmlFontDialog.getFontSize());
      tagAttrs.addAttribute(StyleConstants.Bold, htmlFontDialog.isBold());
      tagAttrs.addAttribute(StyleConstants.Italic, htmlFontDialog.isItalic());
      tagAttrs.addAttribute(StyleConstants.Underline, htmlFontDialog.isUnderline());

      CompoundUndoManager.beginCompoundEdit(editor.getDocument());
      HTMLUtils.setCharacterAttributes(editor, tagAttrs);
      CompoundUndoManager.endCompoundEdit(editor.getDocument());
    }
  }

  private HTMLFontDialog createDialog(JTextComponent textComponent) {
    Window window = SwingUtilities.getWindowAncestor(textComponent);

    String text = "";

    if (textComponent.getSelectedText() != null) {
      text = textComponent.getSelectedText();
    }

    HTMLFontDialog htmlFontDialog = null;

    if (window instanceof Frame) {
      htmlFontDialog = new HTMLFontDialog((Frame) window, text);
    }
    else if (window instanceof Dialog) {
      htmlFontDialog = new HTMLFontDialog((Dialog) window, text);
    }

    return htmlFontDialog;
  }
}