package net.atlanticbb.tantlinger.ui.text;

import javax.swing.text.*;

public class IndentationFilter extends DocumentFilter {
  private boolean isSoftTabs;
  private boolean isAutoIndent;

  public IndentationFilter() {
    this(false, true);
  }

  public IndentationFilter(boolean isSoftTabs, boolean isAutoIndent) {
    this.isSoftTabs = isSoftTabs;
    this.isAutoIndent = isAutoIndent;
  }

  public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
    throws BadLocationException {
    Document doc = fb.getDocument();

    if (text != null && length == 0) {
      if (isSoftTabs)
        text = convertTabsToSpaces(text, doc);

      if (isAutoIndent && text.startsWith("\n"))
        text += getLineIndentation(offset, doc);
    }

    super.replace(fb, offset, length, text, attrs);
  }

  private String convertTabsToSpaces(String text, Document doc) {
    try {
      AbstractDocument adoc = (AbstractDocument) doc;
      int tabSize = (Integer) adoc.getProperty("tabSize");

      String softTab = "";
      for (int i = 1; i <= tabSize; i++)
        softTab += ' ';

      StringBuilder sb = new StringBuilder(text);
      int pos = 0;
      while ((pos = sb.indexOf("\t", pos)) != -1) {
        sb.replace(pos, pos + 1, softTab);
        pos += softTab.length();
      }
      return sb.toString();
    }
    catch (Exception ignored) {
    }

    return text;
  }

  private String getLineIndentation(int offs, Document doc)
    throws BadLocationException {
    StringBuilder ws = new StringBuilder();

    if (doc instanceof AbstractDocument) {
      AbstractDocument adoc = (AbstractDocument) doc;
      int s = adoc.getParagraphElement(offs).getStartOffset();
      String line = adoc.getText(s, offs - s);

      for (int i = 0; i < line.length(); i++) {
        char c = line.charAt(i);
        if (c == '\n' || !Character.isWhitespace(c))
          break;
        ws.append(c);
      }
    }

    return ws.toString();
  }

  public boolean isAutoIndent() {
    return isAutoIndent;
  }

  public void setAutoIndent(boolean isAutoIndent) {
    this.isAutoIndent = isAutoIndent;
  }

  public boolean isSoftTabs() {
    return isSoftTabs;
  }

  public void setSoftTabs(boolean isSoftTabs) {
    this.isSoftTabs = isSoftTabs;
  }
}