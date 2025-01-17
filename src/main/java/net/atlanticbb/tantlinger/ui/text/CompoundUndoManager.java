package net.atlanticbb.tantlinger.ui.text;

import net.atlanticbb.tantlinger.i18n.I18n;
import net.atlanticbb.tantlinger.ui.UIUtils;
import org.bushe.swing.action.ActionManager;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.text.TextAction;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.CompoundEdit;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;


//TODO add static method to unregister documents

/**
 * Manages compound undoable edits.
 * <p>
 * Before an undoable edit happens on a particular document, you should call
 * the static method CompoundUndoManager.beginCompoundEdit(doc)
 * <p>
 * Conversely after an undoable edit happens on a particular document,
 * you shoulc call the static method CompoundUndoManager.beginCompoundEdit(doc)
 * <p>
 * For either of these methods to work, you must add an instance of
 * CompoundUndoManager as a document listener... e.g
 * <p>
 * doc.addUndoableEditListener(new CompoundUndoManager(doc, new UndoManager());
 * <p>
 * Note that each CompoundUndoManager should have its own UndoManager.
 *
 * @author Bob Tantlinger
 */
public class CompoundUndoManager implements UndoableEditListener {
  private static final I18n i18n = I18n.getInstance("net.atlanticbb.tantlinger.ui.text");

  /**
   * Static undo action that works across all documents
   * with a CompoundUndoManager registered as an UndoableEditListener
   */
  public static Action UNDO = new UndoAction();

  /**
   * Static undo action that works across all documents
   * with a CompoundUndoManager registered as an UndoableEditListener
   */
  public static Action REDO = new RedoAction();

  private UndoManager undoer;
  private CompoundEdit compoundEdit = null;
  private Document    document;

  private static Vector<Document> docs = new Vector<>();
  private static Vector<CompoundUndoManager> lsts = new Vector<>();
  private static Vector<UndoManager> undoers = new Vector<>();

  protected static void registerDocument(Document doc, CompoundUndoManager lst, UndoManager um) {
    docs.add(doc);
    lsts.add(lst);
    undoers.add(um);
  }

  /**
   * Gets the undo manager for a document that has a CompoundUndoManager
   * as an UndoableEditListener
   *
   * @return The registed undomanger for the document
   */
  public static UndoManager getUndoManagerForDocument(Document doc) {
    for (int i = 0; i < docs.size(); i++) {
      if (docs.elementAt(i) == doc)
        return undoers.elementAt(i);
    }

    return null;
  }

  /**
   * Notifies the CompoundUndoManager for the specified Document that
   * a compound edit is about to begin.
   *
   */
  public static void beginCompoundEdit(Document doc) {
    for (int i = 0; i < docs.size(); i++) {
      if (docs.elementAt(i) == doc) {
        CompoundUndoManager l = lsts.elementAt(i);
        l.beginCompoundEdit();
        return;
      }
    }
  }

  /**
   * Notifies the CompoundUndoManager for the specified Document that
   * a compound edit is complete.
   *
   */
  public static void endCompoundEdit(Document doc) {
    for (int i = 0; i < docs.size(); i++) {
      if (docs.elementAt(i) == doc) {
        CompoundUndoManager l = lsts.elementAt(i);
        l.endCompoundEdit();
        return;
      }
    }
  }

  /**
   * Updates the enabled states of the UNDO and REDO actions
   * for the specified document
   */
  public static void updateUndo(Document doc) {
    UndoManager um = getUndoManagerForDocument(doc);
    if (um != null) {
      UNDO.setEnabled(um.canUndo());
      REDO.setEnabled(um.canRedo());
    }
  }

  /**
   * Discards all edits for the specified Document
   */
  public static void discardAllEdits(Document doc) {
    UndoManager um = getUndoManagerForDocument(doc);
    if (um != null) {
      um.discardAllEdits();
      UNDO.setEnabled(um.canUndo());
      REDO.setEnabled(um.canRedo());
    }
  }

  /**
   * Creates a new CompoundUndoManager
   * @param um  The UndoManager to use for this document
   */
  public CompoundUndoManager(Document doc, UndoManager um) {
    undoer = um;
    document = doc;
    registerDocument(document, this, undoer);
  }

  /**
   * Creates a new CompoundUndoManager
   */
  public CompoundUndoManager(Document doc) {
    this(doc, new UndoManager());
  }

  public void undoableEditHappened(UndoableEditEvent evt) {
    UndoableEdit edit = evt.getEdit();
    if (compoundEdit != null) {
      //System.out.println("adding to compound");
      compoundEdit.addEdit(edit);
    } else {
      undoer.addEdit(edit);
      updateUndo(document);
    }
  }

  protected void beginCompoundEdit() {
    //System.out.println("starting compound");
    compoundEdit = new CompoundEdit();
  }

  protected void endCompoundEdit() {
    //System.out.println("ending compound");
    if (compoundEdit != null) {
      compoundEdit.end();
      undoer.addEdit(compoundEdit);
      updateUndo(document);
    }
    compoundEdit = null;
  }

  static class UndoAction extends TextAction {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public UndoAction() {
      super(i18n.str("undo"));
      putValue(Action.SMALL_ICON, UIUtils.getIcon(UIUtils.X16, "undo.png"));
      putValue(ActionManager.LARGE_ICON, UIUtils.getIcon(UIUtils.X24, "undo.png"));
      putValue(MNEMONIC_KEY, (int) i18n.mnem("undo"));

      setEnabled(false);
      putValue(
        Action.ACCELERATOR_KEY,
        KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
      putValue(SHORT_DESCRIPTION, getValue(NAME));
    }

    public void actionPerformed(ActionEvent e) {
      Document doc = getTextComponent(e).getDocument();
      UndoManager um = getUndoManagerForDocument(doc);
      if (um != null) {
        try {
          um.undo();
        } catch (CannotUndoException ex) {
          System.out.println("Unable to undo: " + ex);
          ex.printStackTrace();
        }

        updateUndo(doc);
      }
    }
  }

  static class RedoAction extends TextAction {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public RedoAction() {
      super(i18n.str("redo"));
      putValue(Action.SMALL_ICON, UIUtils.getIcon(UIUtils.X16, "redo.png"));
      putValue(ActionManager.LARGE_ICON, UIUtils.getIcon(UIUtils.X24, "redo.png"));
      putValue(MNEMONIC_KEY, (int) i18n.mnem("redo"));

      setEnabled(false);
      putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
        KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
      putValue(SHORT_DESCRIPTION, getValue(NAME));
    }

    public void actionPerformed(ActionEvent e) {
      Document doc = getTextComponent(e).getDocument();
      UndoManager um = getUndoManagerForDocument(doc);
      if (um != null) {
        try {
          um.redo();
        } catch (CannotUndoException ex) {
          System.out.println("Unable to redo: " + ex);
          ex.printStackTrace();
        }

        updateUndo(doc);
      }
    }
  }
}