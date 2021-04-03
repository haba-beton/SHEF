package net.atlanticbb.tantlinger.ui.text.actions;

import javax.swing.*;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.event.ActionEvent;

/**
 * Action which clears inline text styles
 *
 * @author Bob Tantlinger
 */
public class ClearStylesAction extends HTMLTextEditAction {

  public ClearStylesAction() {
    super(i18n.str("clear_styles"));
    putValue(MNEMONIC_KEY,(int)i18n.mnem("clear_styles"));
    putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke("shift ctrl Y"));

    addShouldBeEnabledDelegate(a -> getEditMode() != SOURCE);
  }

  protected void wysiwygEditPerformed(ActionEvent e, JEditorPane editor) {
    HTMLDocument document = (HTMLDocument) editor.getDocument();
    HTMLEditorKit kit = (HTMLEditorKit) editor.getEditorKit();

    MutableAttributeSet attrs = new SimpleAttributeSet();
    attrs.addAttribute(StyleConstants.NameAttribute, HTML.Tag.CONTENT);

    int selStart = editor.getSelectionStart();
    int selEnd   = editor.getSelectionEnd();

    if (selEnd > selStart) {
      document.setCharacterAttributes(selStart, selEnd - selStart, attrs, true);
    }

    kit.getInputAttributes().removeAttributes(kit.getInputAttributes());
    kit.getInputAttributes().addAttributes(attrs);
        
        /*//boolean shouldClearSel = false;
        if(editor.getSelectedText() == null)
        {
            editor.replaceSelection("  ");
            editor.setSelectionStart(editor.getCaretPosition() - 1);
            editor.setSelectionEnd(editor.getSelectionStart() + 1); 
            document.setCharacterAttributes(editor.getSelectionStart(), 
                editor.getSelectionEnd() - editor.getSelectionStart(), attrs, true);
            editor.setSelectionStart(editor.getCaretPosition());
            editor.setSelectionEnd(editor.getCaretPosition());
        }
        else
        {
            document.setCharacterAttributes(editor.getSelectionStart(), 
                editor.getSelectionEnd() - editor.getSelectionStart(), attrs, true);
        }*/
  }

  protected void sourceEditPerformed(ActionEvent e, JEditorPane editor) {
  }
}