package net.atlanticbb.tantlinger.ui.text.actions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class SelectAllAction extends BasicEditAction {
  private static final long serialVersionUID = 1L;

  public SelectAllAction() {
    super(i18n.str("select_all"));
    putValue(MNEMONIC_KEY, (int) i18n.mnem("select_all"));
    putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME));
  }

  protected void doEdit(ActionEvent e, JEditorPane editor) {
    editor.selectAll();
  }
}