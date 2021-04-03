package net.atlanticbb.tantlinger.ui.text.actions;

import net.atlanticbb.tantlinger.ui.text.dialogs.TextFinderDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class FindReplaceAction extends BasicEditAction {
  private final boolean isReplaceTab;
  private TextFinderDialog dialog;
  private final boolean showHTML;

  public FindReplaceAction(boolean isReplace, boolean showHTML) {
    super(null);
    this.showHTML = showHTML;
    if (isReplace) {
      putValue(NAME, i18n.str("replace_"));
      putValue(MNEMONIC_KEY, (int) i18n.mnem("replace_"));
    }
    else {
      putValue(NAME, i18n.str("find_"));
      putValue(MNEMONIC_KEY, (int) i18n.mnem("find_"));
      putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_F, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }

    isReplaceTab = isReplace;
  }

  protected void doEdit(ActionEvent e, JEditorPane textComponent) {
    Component c = SwingUtilities.getWindowAncestor(textComponent);
    if (dialog == null) {
      if (c instanceof Frame) {
        if (isReplaceTab)
          dialog = new TextFinderDialog((Frame) c, textComponent, TextFinderDialog.REPLACE);
        else
          dialog = new TextFinderDialog((Frame) c, textComponent, TextFinderDialog.FIND);
      }
      else if (c instanceof Dialog) {
        if (isReplaceTab)
          dialog = new TextFinderDialog((Dialog) c, textComponent, TextFinderDialog.REPLACE);
        else
          dialog = new TextFinderDialog((Dialog) c, textComponent, TextFinderDialog.FIND);
      }
      else
        return;
    }

    if (!dialog.isVisible()) {
      dialog.show((isReplaceTab) ? TextFinderDialog.REPLACE : TextFinderDialog.FIND);
    }
  }

  @Override
  protected void updateContextState(JEditorPane editor) {
    if (dialog != null) {
      dialog.setJTextComponent(editor);
    }
  }
}