package net.atlanticbb.tantlinger.ui.text.actions;

import net.atlanticbb.tantlinger.ui.text.HTMLUtils;

import javax.swing.*;
import javax.swing.text.Element;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import java.awt.event.ActionEvent;

public class TabAction extends DecoratedTextAction {
  private static final long serialVersionUID = 1L;
  public static final int FORWARD = 0;
  public static final int BACKWARD = 1;

  private int type;

  public TabAction(int type, Action defaultTabAction) {
    super("tabAction", defaultTabAction);
    this.type = type;
  }

  public void actionPerformed(ActionEvent e) {
    JEditorPane editor;
    HTMLDocument document;

    editor = (JEditorPane) getTextComponent(e);
    document = (HTMLDocument) editor.getDocument();
    Element elem = document.getParagraphElement(editor.getCaretPosition());
    Element tdElem = HTMLUtils.getParent(elem, HTML.Tag.TD);
    if (tdElem != null) {
      try {
        if (type == FORWARD)
          editor.setCaretPosition(tdElem.getEndOffset());
        else
          editor.setCaretPosition(tdElem.getStartOffset() - 1);
      } catch (IllegalArgumentException ex) {
        ex.printStackTrace();
      }
    } else
      delegate.actionPerformed(e);
  }
}