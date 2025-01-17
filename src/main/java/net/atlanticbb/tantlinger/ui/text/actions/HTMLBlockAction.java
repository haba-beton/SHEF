package net.atlanticbb.tantlinger.ui.text.actions;

import net.atlanticbb.tantlinger.ui.UIUtils;
import net.atlanticbb.tantlinger.ui.text.CompoundUndoManager;
import net.atlanticbb.tantlinger.ui.text.ElementWriter;
import net.atlanticbb.tantlinger.ui.text.HTMLUtils;
import org.bushe.swing.action.ActionManager;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class HTMLBlockAction extends HTMLTextEditAction {

  public static final int DIV = 0;
  public static final int P = 1;
  public static final int H1 = 2;
  public static final int H2 = 3;
  public static final int H3 = 4;
  public static final int H4 = 5;
  public static final int H5 = 6;
  public static final int H6 = 7;
  public static final int PRE = 8;
  public static final int BLOCKQUOTE = 9;
  public static final int OL = 10;
  public static final int UL = 11;

  private static final int[] KEYS = {
    KeyEvent.VK_D, KeyEvent.VK_ENTER, KeyEvent.VK_1, KeyEvent.VK_2,
    KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5, KeyEvent.VK_6,
    KeyEvent.VK_R, KeyEvent.VK_Q, KeyEvent.VK_N, KeyEvent.VK_U
  };

  public static final String[] ELEMENT_TYPES = {
    i18n.str("body_text"),
    i18n.str("paragraph"),
    i18n.str("heading") + " 1",
    i18n.str("heading") + " 2",
    i18n.str("heading") + " 3",
    i18n.str("heading") + " 4",
    i18n.str("heading") + " 5",
    i18n.str("heading") + " 6",
    i18n.str("preformatted"),
    i18n.str("blockquote"),
    i18n.str("ordered_list"),
    i18n.str("unordered_list")
  };

  private int type;

  public HTMLBlockAction(int type) throws IllegalArgumentException {
    super("");
    if (type < 0 || type >= ELEMENT_TYPES.length)
      throw new IllegalArgumentException("Illegal argument");

    this.type = type;
    putValue(NAME, ELEMENT_TYPES[type]);
    putValue(Action.ACCELERATOR_KEY,
      KeyStroke.getKeyStroke(KEYS[type], InputEvent.ALT_MASK));
    if (type == P)
      putValue(MNEMONIC_KEY, (int) i18n.mnem("paragraph"));
    else if (type == PRE)
      putValue(MNEMONIC_KEY, (int) i18n.mnem("preformatted"));
    else if (type == BLOCKQUOTE)
      putValue(MNEMONIC_KEY, (int) i18n.mnem("blockquote"));
    else if (type == OL) {
      putValue(SMALL_ICON, UIUtils.getIcon(UIUtils.X16, "listordered.png"));
      putValue(MNEMONIC_KEY, (int) i18n.mnem("ordered_list"));
    }
    else if (type == UL) {
      putValue(SMALL_ICON, UIUtils.getIcon(UIUtils.X16, "listunordered.png"));
      putValue(MNEMONIC_KEY, (int) i18n.mnem("unordered_list"));
    }
    else {
      String s = type + "";
      putValue(Action.MNEMONIC_KEY, (int) s.charAt(0));
    }
    putValue(ActionManager.BUTTON_TYPE, ActionManager.BUTTON_TYPE_VALUE_RADIO);
    putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME));
  }

  protected void updateWysiwygContextState(JEditorPane ed) {
    HTMLDocument document = (HTMLDocument) ed.getDocument();
    Element elem = document.getParagraphElement(ed.getCaretPosition());

    String elemName = elem.getName();
    if (elemName.equals("p-implied"))
      elemName = elem.getParentElement().getName();

    if (type == DIV && (elemName.equals("div") || elemName.equals("body") || elemName.equals("td"))) {
      setSelected(true);
    }
    else if (type == UL) {
      Element listElem = HTMLUtils.getListParent(elem);
      setSelected(listElem != null && (listElem.getName().equals("ul")));
    }
    else if (type == OL) {
      Element listElem = HTMLUtils.getListParent(elem);
      setSelected(listElem != null && (listElem.getName().equals("ol")));
    } else setSelected(elemName.equals(getTag().toString().toLowerCase()));
  }

  protected void updateSourceContextState(JEditorPane ed) {
    setSelected(false);
  }

  protected void sourceEditPerformed(ActionEvent e, JEditorPane editor) {
    String tag = getTag().toString();
    String prefix = "\n<" + tag + ">\n\t";
    String postfix = "\n</" + tag + ">\n";
    if (type == OL || type == UL) {
      prefix += "<li>";
      postfix = "</li>" + postfix;
    }

    String sel = editor.getSelectedText();
    if (sel == null) {
      editor.replaceSelection(prefix + postfix);

      int pos = editor.getCaretPosition() - postfix.length();
      if (pos >= 0)
        editor.setCaretPosition(pos);
    } else {
      sel = prefix + sel + postfix;
      editor.replaceSelection(sel);
    }
  }


  protected void wysiwygEditPerformed(ActionEvent e, JEditorPane editor) {
    HTMLDocument document = (HTMLDocument) editor.getDocument();
    int caret = editor.getCaretPosition();
    CompoundUndoManager.beginCompoundEdit(document);
    try {
      if (type == OL || type == UL) {
        insertList(editor, e);
      } else {
        changeBlockType(editor, e);
      }
      editor.setCaretPosition(caret);
    } catch (Exception awwCrap) {
      awwCrap.printStackTrace();
    }

    CompoundUndoManager.endCompoundEdit(document);
  }


  private HTML.Tag getRootTag(Element elem) {
    HTML.Tag root = HTML.Tag.BODY;
    if (HTMLUtils.getParent(elem, HTML.Tag.TD) != null)
      root = HTML.Tag.TD;
    return root;
  }
     
    /*private String cutOutElement(Element el) throws BadLocationException
    {
        String txt = HTMLUtils.getElementHTML(el, false);       
        HTMLUtils.removeElement(el);        
        return txt;
    }*/

  private void insertHTML(String html, HTML.Tag tag, HTML.Tag root, ActionEvent e) {
    HTMLEditorKit.InsertHTMLTextAction a =
      new HTMLEditorKit.InsertHTMLTextAction("insertHTML", html, root, tag);
    a.actionPerformed(e);
  }

  private void changeListType(Element listParent, HTML.Tag replaceTag, HTMLDocument document) {
    StringWriter out = new StringWriter();
    ElementWriter w = new ElementWriter(out, listParent);
    try {
      w.write();
      String html = out.toString();
      html = html.substring(html.indexOf('>') + 1);
      html = html.substring(0, html.lastIndexOf('<'));
      html = '<' + replaceTag.toString() + '>' + html + "</" + replaceTag.toString() + '>';
      document.setOuterHTML(listParent, html);
    }
    catch (Exception ignored) {
    }
  }

  private void insertList(JEditorPane editor, ActionEvent e)
    throws BadLocationException {
    HTMLDocument document = (HTMLDocument) editor.getDocument();
    int caretPos = editor.getCaretPosition();
    Element elem = document.getParagraphElement(caretPos);
    HTML.Tag parentTag = HTML.getTag(elem.getParentElement().getName());

    //check if we need to change the list from one type to another
    Element listParent = elem.getParentElement().getParentElement();
    HTML.Tag listTag = HTML.getTag(listParent.getName());
    if (listTag.equals(HTML.Tag.UL) || listTag.equals(HTML.Tag.OL)) {
      HTML.Tag t = HTML.getTag(listParent.getName());
      if (type == OL && t.equals(HTML.Tag.UL)) {
        changeListType(listParent, HTML.Tag.OL, document);
        return;
      } else if (type == UL && listTag.equals(HTML.Tag.OL)) {
        changeListType(listParent, HTML.Tag.UL, document);
        return;
      }
    }

    if (!parentTag.equals(HTML.Tag.LI))//don't allow nested lists
    {
      //System.err.println("INSERT LIST");
      changeBlockType(editor, e);
    } else//is already a list, so turn off list
    {
      HTML.Tag root = getRootTag(elem);
      String txt = HTMLUtils.getElementHTML(elem, false);
      editor.setCaretPosition(elem.getEndOffset());
      insertHTML("<p>" + txt + "</p>", HTML.Tag.P, root, e);
      HTMLUtils.removeElement(elem);
    }

  }


  private void changeBlockType(JEditorPane editor, ActionEvent e)
    throws BadLocationException {
    HTMLDocument doc = (HTMLDocument) editor.getDocument();
    Element curE = doc.getParagraphElement(editor.getSelectionStart());
    Element endE = doc.getParagraphElement(editor.getSelectionEnd());

    Element curTD = HTMLUtils.getParent(curE, HTML.Tag.TD);
    HTML.Tag tag = getTag();
    HTML.Tag rootTag = getRootTag(curE);
    String html = "";

    if (isListType()) {
      html = "<" + getTag() + ">";
      tag = HTML.Tag.LI;
    }

    //a list to hold the elements we want to change
    List<Element> elToRemove = new ArrayList<>();
    elToRemove.add(curE);

    while (true) {
      html += HTMLUtils.createTag(tag,
        curE.getAttributes(), HTMLUtils.getElementHTML(curE, false));
      if (curE.getEndOffset() >= endE.getEndOffset()
        || curE.getEndOffset() >= doc.getLength())
        break;
      curE = doc.getParagraphElement(curE.getEndOffset() + 1);
      elToRemove.add(curE);

      //did we enter a (different) table cell?
      Element ckTD = HTMLUtils.getParent(curE, HTML.Tag.TD);
      if (ckTD != null && !ckTD.equals(curTD))
        break;//stop here so we don't mess up the table
    }

    if (isListType())
      html += "</" + getTag() + ">";

    //set the caret to the start of the last selected block element
    editor.setCaretPosition(curE.getStartOffset());

    //insert our changed block
    //we insert first and then remove, because of a bug in jdk 6.0
    insertHTML(html, getTag(), rootTag, e);

    //now, remove the elements that were changed.
    for (Element c : elToRemove) {
      HTMLUtils.removeElement(c);
    }
  }

  private boolean isListType() {
    return type == OL || type == UL;
  }

  public HTML.Tag getTag() {
    HTML.Tag tag = HTML.Tag.DIV;

    switch (type) {
      case P:
        tag = HTML.Tag.P;
        break;
      case H1:
        tag = HTML.Tag.H1;
        break;
      case H2:
        tag = HTML.Tag.H2;
        break;
      case H3:
        tag = HTML.Tag.H3;
        break;
      case H4:
        tag = HTML.Tag.H4;
        break;
      case H5:
        tag = HTML.Tag.H5;
        break;
      case H6:
        tag = HTML.Tag.H6;
        break;
      case PRE:
        tag = HTML.Tag.PRE;
        break;
      case UL:
        tag = HTML.Tag.UL;
        break;
      case OL:
        tag = HTML.Tag.OL;
        break;
      case BLOCKQUOTE:
        tag = HTML.Tag.BLOCKQUOTE;
        break;
      case DIV:
        tag = HTML.Tag.DIV;
        break;
    }

    return tag;
  }
}
