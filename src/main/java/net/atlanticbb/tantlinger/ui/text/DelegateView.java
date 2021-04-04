package net.atlanticbb.tantlinger.ui.text;

import javax.swing.event.DocumentEvent;
import javax.swing.text.*;
import javax.swing.text.Position.Bias;
import java.awt.*;

public abstract class DelegateView extends View {
  protected View delegate;

  public DelegateView(View delegate) {
    super(delegate.getElement());
    this.delegate = delegate;
  }

  public void append(View v) {
    delegate.append(v);
  }

  public View breakView(int axis, int offset, float pos, float len) {
    return delegate.breakView(axis, offset, pos, len);
  }

  public void changedUpdate(DocumentEvent e, Shape a, ViewFactory f) {
    delegate.changedUpdate(e, a, f);
  }

  public View createFragment(int p0, int p1) {
    return delegate.createFragment(p0, p1);
  }

  @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
  public boolean equals(Object obj) {
    return delegate.equals(obj);
  }

  public float getAlignment(int axis) {
    return delegate.getAlignment(axis);
  }

  public AttributeSet getAttributes() {
    return delegate.getAttributes();
  }

  public int getBreakWeight(int axis, float pos, float len) {
    return delegate.getBreakWeight(axis, pos, len);
  }

  public Shape getChildAllocation(int index, Shape a) {
    return delegate.getChildAllocation(index, a);
  }

  public Container getContainer() {
    return delegate.getContainer();
  }

  public Document getDocument() {
    return delegate.getDocument();
  }

  public Element getElement() {
    return delegate.getElement();
  }

  public int getEndOffset() {
    return delegate.getEndOffset();
  }

  public Graphics getGraphics() {
    return delegate.getGraphics();
  }

  public float getMaximumSpan(int axis) {
    return delegate.getMaximumSpan(axis);
  }

  public float getMinimumSpan(int axis) {
    return delegate.getMinimumSpan(axis);
  }

  public int getNextVisualPositionFrom(int pos, Bias b, Shape a, int direction, Bias[] biasRet) throws BadLocationException {
    return delegate.getNextVisualPositionFrom(pos, b, a, direction, biasRet);
  }

  public View getParent() {
    return delegate.getParent();
  }

  public float getPreferredSpan(int axis) {
    return delegate.getPreferredSpan(axis);
  }

  public int getResizeWeight(int axis) {
    return delegate.getResizeWeight(axis);
  }

  public int getStartOffset() {
    return delegate.getStartOffset();
  }

  public String getToolTipText(float x, float y, Shape allocation) {
    return delegate.getToolTipText(x, y, allocation);
  }

  public View getView(int n) {
    return delegate.getView(n);
  }

  public int getViewCount() {
    return delegate.getViewCount();
  }

  public ViewFactory getViewFactory() {
    return delegate.getViewFactory();
  }

  public int getViewIndex(float x, float y, Shape allocation) {
    return delegate.getViewIndex(x, y, allocation);
  }

  public int getViewIndex(int pos, Bias b) {
    return delegate.getViewIndex(pos, b);
  }

  public int hashCode() {
    return delegate.hashCode();
  }

  public void insert(int offs, View v) {
    delegate.insert(offs, v);
  }

  public void insertUpdate(DocumentEvent e, Shape a, ViewFactory f) {
    delegate.insertUpdate(e, a, f);
  }

  public boolean isVisible() {
    return delegate.isVisible();
  }

  public Shape modelToView(int p0, Bias b0, int p1, Bias b1, Shape a) throws BadLocationException {
    return delegate.modelToView(p0, b0, p1, b1, a);
  }

  public Shape modelToView(int pos, Shape a, Bias b) throws BadLocationException {
    return delegate.modelToView(pos, a, b);
  }

  public Shape modelToView(int pos, Shape a) throws BadLocationException {
    return delegate.modelToView(pos, a);
  }

  public void preferenceChanged(View child, boolean width, boolean height) {
    delegate.preferenceChanged(child, width, height);
  }

  public void remove(int i) {
    delegate.remove(i);
  }

  public void removeAll() {
    delegate.removeAll();
  }

  public void removeUpdate(DocumentEvent e, Shape a, ViewFactory f) {
    delegate.removeUpdate(e, a, f);
  }

  public void replace(int offset, int length, View[] views) {
    delegate.replace(offset, length, views);
  }

  public void setParent(View parent) {
    delegate.setParent(parent);
  }

  public void setSize(float width, float height) {
    delegate.setSize(width, height);
  }

  public String toString() {
    return delegate.toString();
  }

  public int viewToModel(float x, float y, Shape a, Bias[] biasReturn) {
    return delegate.viewToModel(x, y, a, biasReturn);
  }

  public int viewToModel(float x, float y, Shape a) {
    return delegate.viewToModel(x, y, a);
  }
}