package net.atlanticbb.tantlinger.ui.text.actions;

import net.atlanticbb.tantlinger.i18n.I18n;
import net.atlanticbb.tantlinger.ui.DefaultAction;
import org.bushe.swing.action.ShouldBeEnabledDelegate;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.event.ActionEvent;

public abstract class HTMLTextEditAction extends DefaultAction {
  static final I18n i18n = I18n.getInstance("net.atlanticbb.tantlinger.ui.text.actions");

  public static final String EDITOR = "editor";

  public static final int DISABLED = -1;
  public static final int WYSIWYG = 0;
  public static final int SOURCE = 1;

  public HTMLTextEditAction(String name) {
    super(name);
    addShouldBeEnabledDelegate(new ShouldBeEnabledDelegate() {
      public boolean shouldBeEnabled(Action a) {
        return getEditMode() != DISABLED;
      }
    });
    updateEnabledState();
  }

  public void execute(ActionEvent e) throws Exception {
    if (getEditMode() == WYSIWYG)
      wysiwygEditPerformed(e, getCurrentEditor());
    else if (getEditMode() == SOURCE)
      sourceEditPerformed(e, getCurrentEditor());
  }

  public int getEditMode() {
    JEditorPane ep = getCurrentEditor();
    if (ep == null)
      return DISABLED;
    if (ep.getDocument() instanceof HTMLDocument && ep.getEditorKit() instanceof HTMLEditorKit)
      return WYSIWYG;
    return SOURCE;
  }

  protected JEditorPane getCurrentEditor() {
    try {
      return (JEditorPane) getContextValue(EDITOR);
    } catch (ClassCastException ignored) {
    }

    return null;
  }

  protected void actionPerformedCatch(Throwable t) {
    t.printStackTrace();
  }

  protected void contextChanged() {
    if (getEditMode() == WYSIWYG)
      updateWysiwygContextState(getCurrentEditor());
    else if (getEditMode() == SOURCE)
      updateSourceContextState(getCurrentEditor());
  }

  protected void updateWysiwygContextState(JEditorPane wysEditor) {

  }

  protected void updateSourceContextState(JEditorPane srcEditor) {

  }

  protected abstract void wysiwygEditPerformed(ActionEvent e, JEditorPane editor);

  protected abstract void sourceEditPerformed(ActionEvent e, JEditorPane editor);
}