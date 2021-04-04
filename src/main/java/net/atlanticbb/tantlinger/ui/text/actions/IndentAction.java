package net.atlanticbb.tantlinger.ui.text.actions;

import net.atlanticbb.tantlinger.ui.text.CompoundUndoManager;
import net.atlanticbb.tantlinger.ui.text.HTMLUtils;

import javax.swing.*;
import javax.swing.text.Element;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.event.ActionEvent;
import java.util.*;

public class IndentAction extends HTMLTextEditAction {
  private static final long serialVersionUID = 1L;

  public static final int INDENT = 0;
  public static final int OUTDENT = 1;

  protected int direction;

  public IndentAction(int direction) throws IllegalArgumentException {
    super("");
    if (direction == INDENT)
      putValue(NAME, "Indent");
    else if (direction == OUTDENT)
      putValue(NAME, "Outdent");
    else
      throw new IllegalArgumentException("Invalid indentation direction");
    this.direction = direction;
  }

  protected void sourceEditPerformed(ActionEvent e, JEditorPane editor) {
  }

  private void insertHTML(String html, HTML.Tag tag, HTML.Tag root, ActionEvent e) {
    HTMLEditorKit.InsertHTMLTextAction a = new HTMLEditorKit.InsertHTMLTextAction("insertHTML", html, root, tag);
    a.actionPerformed(e);
  }

  private HTML.Tag getRootTag(Element elem) {
    HTML.Tag root = HTML.Tag.BODY;
    if (HTMLUtils.getListParent(elem) != null) {
      root = HTML.Tag.UL;
    } else if (HTMLUtils.getParent(elem, HTML.Tag.TD) != null)
      root = HTML.Tag.TD;
    else if (HTMLUtils.getParent(elem, HTML.Tag.BLOCKQUOTE) != null)
      root = HTML.Tag.BLOCKQUOTE;
    return root;
  }


  private int getIndentationLevel(Element el) {
    int level = 0;
    while ((!el.getName().equals("body")) && (!el.getName().equals("td"))) {
      if (el.getName().equals("blockquote"))
        level++;
      el = el.getParentElement();
    }

    return level;
  }

  private Map<Element,List<Element>> getListElems(List<Element> elems) {
    Map<Element,List<Element>> lis = new HashMap<>();
    for (Element elem : elems) {
      Element li = HTMLUtils.getParent(elem, HTML.Tag.LI);
      if (li != null) {
        Element listEl = HTMLUtils.getListParent(li);
        if (!lis.containsKey(listEl)) {
          lis.put(listEl, new ArrayList<>());
        }

        lis.get(listEl).add(li);
      }
    }
    return lis;
  }

  private void unindent(ActionEvent e,JEditorPane editor) {
    List<Element> elems = getParagraphElements(editor);
    if (elems.size() == 0)
      return;

    List<Element> listElems = getLeadingTralingListElems(elems);
    elems.removeAll(listElems);

    Set<Element> elsToIndent = new HashSet<>();
    Set<Element> elsToOutdent = new HashSet<>();
    Element lastBqParent = null;
    for (int i = 0; i < elems.size(); i++) {
      Element el = elems.get(i);
      Element bqParent = HTMLUtils.getParent(el, HTML.Tag.BLOCKQUOTE);
      if (bqParent == null)
        continue;

      if (lastBqParent == null || bqParent.getStartOffset() >= lastBqParent.getEndOffset()) {
        elsToOutdent.add(bqParent);
        lastBqParent = bqParent;
      }

      if (i == 0 || i == elems.size() - 1) {
        int c = bqParent.getElementCount();
        for (int j = 0; j < c; j++) {
          Element bqChild = bqParent.getElement(j);
          int start = bqChild.getStartOffset();
          int end = bqChild.getEndOffset();
          if (end < editor.getSelectionStart() || start > editor.getSelectionEnd())
            elsToIndent.add(bqChild);
        }
      }
    }

    HTMLDocument doc = (HTMLDocument) editor.getDocument();
    adjustListElemsIndent(listElems, doc);
    blockquoteElements(new ArrayList<>(elsToIndent), doc);
    unblockquoteElements(new ArrayList<>(elsToOutdent), doc);


  }

  private void adjustListElemsIndent(List<Element> elems, HTMLDocument doc) {
    Set<Element> rootLists = new HashSet<>();
    Set<Element> liElems   = new HashSet<>();
    for (Element elem : elems) {
      Element liEl = HTMLUtils.getParent(elem, HTML.Tag.LI);
      if (liEl == null)
        continue;
      liElems.add(liEl);
      Element rootList = HTMLUtils.getListParent(liEl);
      if (rootList != null) {
        while (HTMLUtils.getListParent(rootList.getParentElement()) != null) {
          rootList = HTMLUtils.getListParent(rootList.getParentElement());
        }
        rootLists.add(rootList);
      }
    }

    for (Element rl : rootLists) {
      String newHtml = buildListHTML(rl, new ArrayList<>(liElems));
      System.err.println(newHtml);
      try {
        doc.setInnerHTML(rl, newHtml);
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  private List<ListItem> getItems(Element list, List<Element> selLiElems, int level) {
    int c = list.getElementCount();
    List<ListItem> items = new ArrayList<>();
    for (int i = 0; i < c; i++) {
      Element e = list.getElement(i);
      if (e.getName().equals("li")) {
        ListItem item = new ListItem();
        item.listTag = HTML.getTag(list.getName());
        item.level = level;
        if (selLiElems.contains(e)) {
          if (direction == INDENT) {
            item.level++;
          }
          else {
            if (item.level > 0) {
              item.level--;
            }
          }
        }
        item.html = HTMLUtils.getElementHTML(e, true);
        items.add(item);
      }
      else if (HTMLUtils.getListParent(e) == e) {
        items.addAll(getItems(e, selLiElems, level + 1));
      }
    }
    return items;
  }

  private String buildListHTML(Element list, List<Element> liItems) {
    List<ListItem> items = getItems(list,liItems,0);
    ListItem lastItem = null;
    StringBuilder html = new StringBuilder();
    for (ListItem listItem : items) {
      if (lastItem != null && (lastItem.level != listItem.level || !lastItem.listTag.equals(listItem.listTag))) {
        if (lastItem.level > listItem.level) {
          html.append(openOrCloseList(lastItem.listTag, -1 * (lastItem.level - listItem.level)));
          html.append(listItem.html);
        } else if (listItem.level > lastItem.level) {
          html.append(openOrCloseList(listItem.listTag, (listItem.level - lastItem.level)));
          html.append(listItem.html);
        } else {
          //html.append("</" + lastItem.listTag + ">");
          //html.append("<" + item.listTag + ">");
          html.append(listItem.html);
        }
      } else {
        if (lastItem == null)
          html.append(openOrCloseList(listItem.listTag, listItem.level));
        html.append(listItem.html);
      }

      lastItem = listItem;
    }

    if (lastItem != null)
      html.append(openOrCloseList(lastItem.listTag, -1 * lastItem.level));

    return html.toString();
  }

  private String openOrCloseList(HTML.Tag ltag, int level) {
    String tag;
    if (level < 0)
      tag = "</" + ltag + ">\n";
    else
      tag = "<" + ltag + ">\n";
    int c = Math.abs(level);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < c; i++)
      sb.append(tag);
    return sb.toString();
  }

  private static class ListItem {
    String html;
    int level;
    HTML.Tag listTag;
  }

  private List<Element> getLeadingTralingListElems(List<Element> elems) {
    Set<Element> listElems = new HashSet<>();
    for (Element elem : elems) {
      if (HTMLUtils.getListParent(elem) != null)
        listElems.add(elem);
      else
        break;
    }

    for (int i = elems.size() - 1; i >= 0; i--) {
      Element el = elems.get(i);
      if (HTMLUtils.getListParent(el) != null)
        listElems.add(el);
      else
        break;
    }

    return new ArrayList<>(listElems);
  }

  private void indent1(ActionEvent e, JEditorPane editor) {
    List<Element> elems = getParagraphElements(editor);
    if (elems.size() == 0)
      return;

    List<Element> listElems = this.getLeadingTralingListElems(elems);
    elems.removeAll(listElems);
    HTMLDocument doc = (HTMLDocument) editor.getDocument();
    blockquoteElements(elems, doc);
    adjustListElemsIndent(listElems, doc);
  }

  private void indent(ActionEvent e, JEditorPane editor) {
    List<Element> elems = getParagraphElements(editor);
    if (elems.size() == 0)
      return;

    HTMLDocument doc = (HTMLDocument) editor.getDocument();
    List<Element> nonListElems = new LinkedList<>();
    for (Iterator<?> it = elems.iterator(); it.hasNext(); ) {
      Element el = (Element) it.next();
      if (HTMLUtils.getListParent(el) == null) {
        nonListElems.add(el);
        it.remove();
      }
    }
    blockquoteElements(nonListElems, doc);

    Map<Element,List<Element>> listEls = getListElems(elems);
    for (Element listParent : listEls.keySet()) {
      List<Element> liElems = listEls.get(listParent);
      StringBuffer sb = new StringBuffer();
      sb.append("<").append(listParent.getName()).append(">\n");
      for (Element elem : liElems) {
        sb.append(HTMLUtils.getElementHTML(elem, true));
      }
      sb.append("</").append(listParent.getName()).append(">\n");
      System.err.println(sb);

      for (int i = liElems.size() - 1; i >= 0; i--) {
        Element liElem = liElems.get(i);
        try {
          if (i == 0) {
            doc.setOuterHTML(liElem, sb.toString());
          } else {
            HTMLUtils.removeElement(liElem);
          }
        } catch (Exception ble) {
          ble.printStackTrace();
        }
      }
    }
  }

  private void unblockquoteElements(List<Element> elems, HTMLDocument doc) {
    for (Element curE : elems) {
      if (!curE.getName().equals("blockquote"))
        continue;

      String eleHtml = HTMLUtils.getElementHTML(curE, false);
      HTML.Tag t = HTMLUtils.getStartTag(eleHtml);
      if (t == null || !t.breaksFlow())
        eleHtml = "<p>\n" + eleHtml + "</p>\n";

      try {
        doc.setOuterHTML(curE, eleHtml);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  private void blockquoteElements(List<Element> elems, HTMLDocument doc) {
    for (Element curE : elems) {
      String eleHtml = HTMLUtils.getElementHTML(curE, true);
      StringBuilder sb = new StringBuilder();
      sb.append("<blockquote>\n");
      sb.append(eleHtml);
      sb.append("</blockquote>\n");
      try {
        doc.setOuterHTML(curE, sb.toString());
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }


  public List<Element> getParagraphElements(JEditorPane editor) {
    List<Element> elems = new ArrayList<>();
    try {
      HTMLDocument doc = (HTMLDocument) editor.getDocument();
      Element curE = getParaElement(doc, editor.getSelectionStart());
      Element endE = getParaElement(doc, editor.getSelectionEnd());

      while (curE.getEndOffset() <= endE.getEndOffset()) {
        elems.add(curE);
        curE = getParaElement(doc, curE.getEndOffset() + 1);
        if (curE.getEndOffset() >= doc.getLength())
          break;
      }
    }
    catch (ClassCastException ignored) {
    }

    return elems;
  }

  private Element getParaElement(HTMLDocument doc, int pos) {
    return doc.getParagraphElement(pos);
  }

  protected void wysiwygEditPerformed(ActionEvent e, JEditorPane editor) {
    int cp = editor.getCaretPosition();
    CompoundUndoManager.beginCompoundEdit(editor.getDocument());
    if (direction == INDENT)
      indent1(e, editor);
    else
      unindent(e, editor);
    CompoundUndoManager.endCompoundEdit(editor.getDocument());
    editor.setCaretPosition(cp);
  }

}
