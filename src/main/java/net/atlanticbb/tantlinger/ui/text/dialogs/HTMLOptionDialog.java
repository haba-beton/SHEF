package net.atlanticbb.tantlinger.ui.text.dialogs;

import net.atlanticbb.tantlinger.ui.OptionDialog;

import javax.swing.*;
import java.awt.*;

public abstract class HTMLOptionDialog extends OptionDialog {
  public HTMLOptionDialog(Frame parent, String title, String desc, Icon ico) {
    super(parent, title, desc, ico);
  }

  public HTMLOptionDialog(Dialog parent, String title, String desc, Icon ico) {
    super(parent, title, desc, ico);
  }

  public abstract String getHTML();
}