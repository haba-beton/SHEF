package net.atlanticbb.tantlinger.ui.text.actions;

import net.atlanticbb.tantlinger.ui.UIUtils;
import org.bushe.swing.action.ActionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class CutAction extends BasicEditAction {

  public CutAction() {
    super("");
    putValue(Action.NAME, i18n.str("cut"));
    putValue(Action.SMALL_ICON, UIUtils.getIcon(UIUtils.X16, "cut.png"));
    putValue(ActionManager.LARGE_ICON, UIUtils.getIcon(UIUtils.X24, "cut.png"));
    putValue(Action.ACCELERATOR_KEY,
      KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    putValue(MNEMONIC_KEY, (int) i18n.mnem("cut"));
    addShouldBeEnabledDelegate(a -> {
      JEditorPane ed = getCurrentEditor();
      return ed != null && ed.getSelectionStart() != ed.getSelectionEnd();
    });
    putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME));
  }

  protected void doEdit(ActionEvent e, JEditorPane editor) {
    editor.cut();
  }

  protected void contextChanged() {
    super.contextChanged();
    this.updateEnabledState();
  }
}