package net.atlanticbb.tantlinger.ui.text;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.CSS;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.*;

public class HTMLUtils {

  private static final Tidy tidy = new Tidy();

  static {
    tidy.setQuiet(true);
    tidy.setShowWarnings(false);
    tidy.setForceOutput(true);
    tidy.setFixComments(true);
    tidy.setFixUri(true);
    tidy.setDropEmptyParas(true);
    tidy.setLiteralAttribs(true);
    tidy.setTrimEmptyElements(true);
    tidy.setXHTML(true);
  }

  public static boolean isImplied(Element el) {
    return el.getName().equals("p-implied");
  }

  public static String createTag(HTML.Tag enclTag, String innerHTML) {
    return createTag(enclTag, new SimpleAttributeSet(), innerHTML);
  }

  public static String createTag(HTML.Tag enclTag, AttributeSet set, String innerHTML) {
    return tagOpen(enclTag, set) + innerHTML + tagClose(enclTag);
  }

  private static String tagOpen(HTML.Tag enclTag, AttributeSet set) {
    String t = "<" + enclTag;
    for (Enumeration<?> e = set.getAttributeNames(); e.hasMoreElements(); ) {
      Object name = e.nextElement();
      if (!name.toString().equals("name")) {
        Object val = set.getAttribute(name);
        t += " " + name + "=\"" + val + "\"";
      }
    }

    return t + ">";
  }

  private static String tagClose(HTML.Tag t) {
    return "</" + t + ">";
  }

  public static List<Element> getParagraphElements(JEditorPane editor) {
    List<Element> elems = new LinkedList<>();
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

  private static Element getParaElement(HTMLDocument doc, int pos) {
    Element curE = doc.getParagraphElement(pos);
    while (isImplied(curE)) {
      curE = curE.getParentElement();
    }

    Element lp = getListParent(curE);
    if (lp != null)
      curE = lp;

    return curE;
  }

  public static Element getParent(Element curElem, HTML.Tag parentTag) {
    Element parent = curElem;
    while (parent != null) {
      if (parent.getName().equals(parentTag.toString()))
        return parent;
      parent = parent.getParentElement();
    }

    return null;
  }

  public static boolean isElementEmpty(Element el) {
    String s = getElementHTML(el, false).trim();
    return s.length() == 0;
  }

  public static Element getListParent(Element elem) {
    Element parent = elem;
    while (parent != null) {
      if (parent.getName().equalsIgnoreCase("UL") ||
        parent.getName().equalsIgnoreCase("OL") ||
        parent.getName().equals("dl") || parent.getName().equals("menu") ||
        parent.getName().equals("dir"))
        return parent;
      parent = parent.getParentElement();
    }
    return null;
  }

  public static Element getPreviousElement(HTMLDocument doc, Element el) {
    if (el.getStartOffset() > 0)
      return doc.getParagraphElement(el.getStartOffset() - 1);
    return el;
  }

  public static Element getNextElement(HTMLDocument doc, Element el) {
    if (el.getEndOffset() < doc.getLength())
      return doc.getParagraphElement(el.getEndOffset() + 1);
    return el;
  }

  public static String removeEnclosingTags(Element elem, String txt) {
    HTML.Tag t = HTML.getTag(elem.getName());
    return removeEnclosingTags(t, txt);
  }

  public static String removeEnclosingTags(HTML.Tag t, String txt) {
    String openStart = "<" + t;
    String closeTag = "</" + t + ">";

    txt = txt.trim();

    if (txt.startsWith(openStart)) {
      int n = txt.indexOf(">");
      if (n != -1) {
        txt = txt.substring(n + 1);
      }
    }

    if (txt.endsWith(closeTag)) {
      txt = txt.substring(0, txt.length() - closeTag.length());
    }

    return txt;
  }

  public static String getElementHTML(Element el, boolean includeEnclosingTags) {
    String txt = "";

    try {
      StringWriter out = new StringWriter();
      ElementWriter w = new ElementWriter(out, el);
      w.write();
      txt = out.toString();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    if (includeEnclosingTags)
      return txt;
    return removeEnclosingTags(el, txt);
  }

  public static void removeElement(Element el) throws BadLocationException {
    HTMLDocument document = (HTMLDocument) el.getDocument();
    int start = el.getStartOffset();
    int len = el.getEndOffset() - start;

    Element tdEle = HTMLUtils.getParent(el, HTML.Tag.TD);
    if (tdEle != null && el.getEndOffset() == tdEle.getEndOffset()) {
      document.remove(start, len - 1);
    } else {
      if (el.getEndOffset() > document.getLength())
        len = document.getLength() - start;

      document.remove(start, len);
    }
  }

  public static HTML.Tag getStartTag(String text) {
    String html = text.trim();
    int s = html.indexOf('<');
    if (s != 0)
      return null;
    int e = html.indexOf('>');
    if (e == -1)
      return null;

    String tagName = html.substring(1, e).trim();
    if (tagName.indexOf(' ') != -1)
      tagName = tagName.split("\\s")[0];

    return HTML.getTag(tagName);
  }

  private static int depthFromRoot(Element curElem) {
    Element parent = curElem;
    int depth = 0;
    while (parent != null) {
      if (parent.getName().equals("body") || /*parent.getName().equals("blockquote") ||*/ parent.getName().equals("td"))
        break;
      parent = parent.getParentElement();
      depth++;
    }

    return depth;
  }

  public static void insertArbitraryHTML(String rawHtml, JEditorPane editor) {
    tidy.setOutputEncoding("UTF-8");
    tidy.setInputEncoding("UTF-8");

    ByteArrayInputStream bin = new ByteArrayInputStream(rawHtml.getBytes(StandardCharsets.UTF_8));
    Document doc = tidy.parseDOM(bin, null);
    NodeList nodelist = doc.getElementsByTagName("body");

    if (nodelist != null) {
      Node body = nodelist.item(0);
      NodeList bodyChildren = body.getChildNodes();

      int len = bodyChildren.getLength();
      for (int i = 0; i < len; i++) {
        String ml = xmlToString(bodyChildren.item(i));
        if (ml != null) {
          HTML.Tag tag = getStartTag(ml);
          if (tag == null) {
            tag = HTML.Tag.SPAN;
            ml = "<span>" + ml + "</span>";
          }
          insertHTML(ml, tag, editor);
        }
      }
    }
  }

  private static String xmlToString(Node node) {
    try {
      Source source = new DOMSource(node);
      StringWriter stringWriter = new StringWriter();
      Result result = new StreamResult(stringWriter);
      TransformerFactory factory = TransformerFactory.newInstance();
      Transformer transformer = factory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
      transformer.transform(source, result);

      return stringWriter.getBuffer().toString();
    }
    catch (TransformerException e) {
      e.printStackTrace();
    }

    return null;
  }

  public static void insertHTML(String html, HTML.Tag tag, JEditorPane editor) {
    HTMLEditorKit editorKit;
    HTMLDocument document;
    try {
      editorKit = (HTMLEditorKit) editor.getEditorKit();
      document = (HTMLDocument) editor.getDocument();
    } catch (ClassCastException ex) {
      return;
    }

    int caret = editor.getCaretPosition();
    Element pElem = document.getParagraphElement(caret);

    boolean breakParagraph = tag.breaksFlow() || tag.isBlock();
    boolean beginParagraph = caret == pElem.getStartOffset();
    html = jEditorPaneizeHTML(html);

    try {
      if (breakParagraph && beginParagraph) {
        document.insertBeforeStart(pElem, "<p></p>");
        Element nextEl = document.getParagraphElement(caret + 1);
        editorKit.insertHTML(document, caret + 1, html, depthFromRoot(nextEl)/*1*/, 0, tag);
        document.remove(caret, 1);
      }
      else if (breakParagraph && !beginParagraph) {
        editorKit.insertHTML(document, caret, html, depthFromRoot(pElem)/*1*/, 0, tag);
      }
      else if (!breakParagraph && beginParagraph) {
        /* Trick: insert a non-breaking space after start, so that we're inserting into the middle of a line.
         * Then, remove the space. This works around a bug when using insertHTML near the beginning of a
         * paragraph.*/
        document.insertAfterStart(pElem, "&nbsp;");
        editorKit.insertHTML(document, caret + 1, html, 0, 0, tag);
        document.remove(caret, 1);
      }
      else if (!breakParagraph && !beginParagraph) {
        editorKit.insertHTML(document, caret, html, 0, 0, tag);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static AttributeSet getCharacterAttributes(JEditorPane editor) {
    int pos;

    if (editor.getSelectedText() != null) {
      pos = editor.getSelectionEnd() - 1;
    }
    else {
      pos = (editor.getCaretPosition() > 0) ? (editor.getCaretPosition() - 1) : 0;
    }

    try {
      return ((StyledDocument)editor.getDocument()).getCharacterElement(pos).getAttributes();
    }
    catch (Exception ignored) {
    }

    return null;
  }

  public static String getFontFamily(JEditorPane editor) {
    AttributeSet attr = getCharacterAttributes(editor);
    if (attr != null) {
      Object val = attr.getAttribute(StyleConstants.FontFamily);
      if (val != null)
        return val.toString();
      val = attr.getAttribute(CSS.Attribute.FONT_FAMILY);
      if (val != null)
        return val.toString();
      val = attr.getAttribute(HTML.Tag.FONT);
      if (val instanceof AttributeSet) {
        AttributeSet set = (AttributeSet) val;
        val = set.getAttribute(HTML.Attribute.FACE);
        if (val != null)
          return val.toString();
      }
    }

    return null;
  }

  public static void setFontFamily(JEditorPane editor, String fontName) {
    AttributeSet attr = getCharacterAttributes(editor);

    if (attr == null) {
      return;
    }

    printAttribs(attr);

    if (fontName == null) {
      Object attribute = attr.getAttribute(HTML.Tag.FONT);
      if (attribute instanceof AttributeSet) {
        MutableAttributeSet set = new SimpleAttributeSet((AttributeSet) attribute);
        attribute = set.getAttribute(HTML.Attribute.FACE); //does it have a FACE attrib?
        if (attribute != null) {
          set.removeAttribute(HTML.Attribute.FACE);
          removeCharacterAttribute(editor, HTML.Tag.FONT); //remove the current font tag
          if (set.getAttributeCount() > 0) {
            //it's not empty so replace the other font attribs
            SimpleAttributeSet fontSet = new SimpleAttributeSet();
            fontSet.addAttribute(HTML.Tag.FONT, set);
            setCharacterAttributes(editor, set);
          }
        }
      }
      //also remove these for good measure
      removeCharacterAttribute(editor, StyleConstants.FontFamily);
      removeCharacterAttribute(editor, CSS.Attribute.FONT_FAMILY);
    }
    else {
      MutableAttributeSet tagAttrs = new SimpleAttributeSet();
      tagAttrs.addAttribute(StyleConstants.FontFamily, fontName);
      setCharacterAttributes(editor, tagAttrs);
    }

    printAttribs(attr);
  }

  public static void removeCharacterAttribute(JEditorPane editor, CSS.Attribute atr, String val) {
    HTMLDocument doc;
    MutableAttributeSet attr;
    try {
      doc = (HTMLDocument) editor.getDocument();
      attr = ((HTMLEditorKit) editor.getEditorKit()).getInputAttributes();
    } catch (ClassCastException cce) {
      return;
    }

    List<CharStyleToken> tokens = tokenizeCharAttribs(doc, editor.getSelectionStart(), editor.getSelectionEnd());
    for (CharStyleToken t : tokens) {
      if (t.attrs.isDefined(atr) && t.attrs.getAttribute(atr).toString().equals(val)) {
        SimpleAttributeSet sas = new SimpleAttributeSet();
        sas.addAttributes(t.attrs);
        sas.addAttribute(StyleConstants.NameAttribute, HTML.Tag.CONTENT);
        sas.removeAttribute(atr);
        doc.setCharacterAttributes(t.offs, t.len, sas, true);
      }
    }
    int pos = editor.getCaretPosition();
    attr.addAttributes(doc.getCharacterElement(pos).getAttributes());
    attr.removeAttribute(atr);
  }

  public static void removeCharacterAttribute(JEditorPane editor, Object atr) {
    HTMLDocument doc;
    MutableAttributeSet attr;
    try {
      doc = (HTMLDocument) editor.getDocument();
      attr = ((HTMLEditorKit) editor.getEditorKit()).getInputAttributes();
    } catch (ClassCastException cce) {
      return;
    }

    List<CharStyleToken> tokens = tokenizeCharAttribs(doc, editor.getSelectionStart(), editor.getSelectionEnd());
    for (CharStyleToken t : tokens) {
      if (t.attrs.isDefined(atr)) {
        SimpleAttributeSet sas = new SimpleAttributeSet();
        sas.addAttributes(t.attrs);
        sas.addAttribute(StyleConstants.NameAttribute, HTML.Tag.CONTENT);
        sas.removeAttribute(atr);
        doc.setCharacterAttributes(t.offs, t.len, sas, true);
      }
    }
    int pos = editor.getCaretPosition();
    attr.addAttributes(doc.getCharacterElement(pos).getAttributes());
    attr.removeAttribute(atr);
  }

  private static List<CharStyleToken> tokenizeCharAttribs(HTMLDocument doc, int s, int e) {
    LinkedList<CharStyleToken> tokens = new LinkedList<>();
    CharStyleToken tok = new CharStyleToken();
    for (; s <= e; s++) {
      AttributeSet as = doc.getCharacterElement(s).getAttributes();
      if (tok.attrs == null || (s + 1 <= e && !as.isEqual(tok.attrs))) {
        tok = new CharStyleToken();
        tok.offs = s;
        tokens.add(tok);
        tok.attrs = as;
      }

      if (s + 1 <= e)
        tok.len++;
    }

    return tokens;
  }

  public static void setCharacterAttributes(JEditorPane editor, AttributeSet attr, boolean replace) {
    HTMLDocument doc;
    StyledEditorKit k;
    try {
      doc = (HTMLDocument) editor.getDocument();
      k = (StyledEditorKit) editor.getEditorKit();
    } catch (ClassCastException ex) {
      return;
    }

    //TODO figure out what the "CR" attribute is.
    //Somewhere along the line the attribute  CR (String key) with a value of Boolean.TRUE
    //gets inserted. If it is in the attributes, something gets screwed up
    //and the text gets all jumbled up and doesn't render correctly.
    //Is it yet another JEditorPane bug?
    MutableAttributeSet inputAttributes = k.getInputAttributes();
    SimpleAttributeSet sas = new SimpleAttributeSet(attr);
    sas.removeAttribute("CR");
    attr = sas;

    int p0 = editor.getSelectionStart();
    int p1 = editor.getSelectionEnd();
    if (p0 != p1) {
      doc.setCharacterAttributes(p0, p1 - p0, attr, replace);
    }
    else {
      //No selection, so we have to update the input attributes
      //otherwise they apparently get reread from the document...
      //not sure if this is a bug or what, but the following works
      //so just go with it.
      if (replace) {
        attr = attr.copyAttributes();
        inputAttributes.removeAttributes(inputAttributes);
        inputAttributes.addAttribute(StyleConstants.NameAttribute, HTML.Tag.CONTENT);
      }
      inputAttributes.addAttributes(attr);
      //System.err.println("inputAttr: " + inputAttributes);
    }
  }

  public static void setCharacterAttributes(JEditorPane editor, AttributeSet attrs) {
    setCharacterAttributes(editor, attrs, false);
  }

  public static Map<String,String> tagAttribsToMap(String atts) {
    Map<String,String> attribs = new HashMap<>();

    StringTokenizer st = new StringTokenizer(atts.trim(), " ");
    String lastAtt = null;
    while (st.hasMoreTokens()) {
      String atVal = st.nextToken().trim();
      int equalPos = atVal.indexOf('=');
      if (equalPos == -1) {
        if (lastAtt == null)
          break;
        String lastVal = attribs.get(lastAtt);
        attribs.put(lastAtt, lastVal + " " + atVal);
        continue;
      }

      String at = atVal.substring(0, equalPos);
      String val = atVal.substring(atVal.indexOf('=') + 1);
      if (val.startsWith("\""))
        val = val.substring(1);
      if (val.endsWith("\""))
        val = val.substring(0, val.length() - 1);

      attribs.put(at, val);
      lastAtt = at;
    }

    return attribs;
  }

  public static String colorToHex(Color color) {
    String colorstr = "#";

    // Red
    String str = Integer.toHexString(color.getRed());
    if (str.length() > 2) {
    }
    else if (str.length() < 2)
      colorstr += "0" + str;
    else
      colorstr += str;

    // Green
    str = Integer.toHexString(color.getGreen());
    if (str.length() > 2) {
    }
    else if (str.length() < 2)
      colorstr += "0" + str;
    else
      colorstr += str;

    // Blue
    str = Integer.toHexString(color.getBlue());
    if (str.length() > 2) {
    }
    else if (str.length() < 2)
      colorstr += "0" + str;
    else
      colorstr += str;

    return colorstr;
  }


  /**
   * Convert a "#FFFFFF" hex string to a Color.
   * If the color specification is bad, an attempt
   * will be made to fix it up.
   */
  public static Color hexToColor(String value) {
    String digits;

    if (value.startsWith("#"))
      digits = value.substring(1, Math.min(value.length(), 7));
    else
      digits = value;

    String hstr = "0x" + digits;
    Color c;

    try {
      c = Color.decode(hstr);
    } catch (NumberFormatException nfe) {
      c = Color.BLACK; // just return black
    }
    return c;
  }

  /**
   * Convert a color string such as "RED" or "#NNNNNN" or "rgb(r, g, b)"
   * to a Color.
   */
  public static Color stringToColor(String str) {
    Color color;

    if (str.length() == 0)
      color = Color.black;
    else if (str.charAt(0) == '#')
      color = hexToColor(str);
    else if (str.equalsIgnoreCase("Black"))
      color = hexToColor("#000000");
    else if (str.equalsIgnoreCase("Silver"))
      color = hexToColor("#C0C0C0");
    else if (str.equalsIgnoreCase("Gray"))
      color = hexToColor("#808080");
    else if (str.equalsIgnoreCase("White"))
      color = hexToColor("#FFFFFF");
    else if (str.equalsIgnoreCase("Maroon"))
      color = hexToColor("#800000");
    else if (str.equalsIgnoreCase("Red"))
      color = hexToColor("#FF0000");
    else if (str.equalsIgnoreCase("Purple"))
      color = hexToColor("#800080");
    else if (str.equalsIgnoreCase("Fuchsia"))
      color = hexToColor("#FF00FF");
    else if (str.equalsIgnoreCase("Green"))
      color = hexToColor("#008000");
    else if (str.equalsIgnoreCase("Lime"))
      color = hexToColor("#00FF00");
    else if (str.equalsIgnoreCase("Olive"))
      color = hexToColor("#808000");
    else if (str.equalsIgnoreCase("Yellow"))
      color = hexToColor("#FFFF00");
    else if (str.equalsIgnoreCase("Navy"))
      color = hexToColor("#000080");
    else if (str.equalsIgnoreCase("Blue"))
      color = hexToColor("#0000FF");
    else if (str.equalsIgnoreCase("Teal"))
      color = hexToColor("#008080");
    else if (str.equalsIgnoreCase("Aqua"))
      color = hexToColor("#00FFFF");
    else
      color = hexToColor(str); // sometimes get specified without leading #
    return color;
  }

  public static String jEditorPaneizeHTML(String html) {
    return html.replaceAll("(<\\s*\\w+\\b[^>]*)/(\\s*>)", "$1$2");
  }

  public static void printAttribs(AttributeSet attr) {
    System.err.println("----------------------------------------------------------------");
    System.err.println(attr);
    Enumeration<?> ee = attr.getAttributeNames();
    while (ee.hasMoreElements()) {
      Object name = ee.nextElement();
      Object atr = attr.getAttribute(name);
      System.err.println(name + " " + name.getClass().getName() + " | " + atr + " " + atr.getClass().getName());
    }
    System.err.println("----------------------------------------------------------------");
  }

  private static class CharStyleToken {
    int offs;
    int len;
    AttributeSet attrs;
  }
}
