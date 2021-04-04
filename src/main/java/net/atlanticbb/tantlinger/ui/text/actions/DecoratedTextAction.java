package net.atlanticbb.tantlinger.ui.text.actions;

import javax.swing.*;
import javax.swing.text.TextAction;

public abstract class DecoratedTextAction extends TextAction {
  Action delegate;

  public DecoratedTextAction(String name, Action delegate) {
    super(name);
    this.delegate = delegate;
  }

  public Action getDelegate() {
    return delegate;
  }
}