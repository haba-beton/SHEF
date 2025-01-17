package net.atlanticbb.tantlinger.ui.text.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;

public abstract class BasicEditAction extends HTMLTextEditAction {

  public BasicEditAction(String name) {
    super(name);
  }

  protected final void sourceEditPerformed(ActionEvent e, JEditorPane editor) {
    doEdit(e, editor);
  }

  protected final void wysiwygEditPerformed(ActionEvent e, JEditorPane editor) {
    doEdit(e, editor);
  }

  protected abstract void doEdit(ActionEvent e, JEditorPane editor);

  protected void updateContextState(JEditorPane editor) {
  }

  protected final void updateWysiwygContextState(JEditorPane wysEditor) {
    updateContextState(wysEditor);
  }

  protected final void updateSourceContextState(JEditorPane srcEditor) {
    updateContextState(srcEditor);
  }
}
