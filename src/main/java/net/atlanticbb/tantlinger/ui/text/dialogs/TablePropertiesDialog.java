package net.atlanticbb.tantlinger.ui.text.dialogs;

import net.atlanticbb.tantlinger.i18n.I18n;
import net.atlanticbb.tantlinger.ui.OptionDialog;
import net.atlanticbb.tantlinger.ui.UIUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Map;

public class TablePropertiesDialog extends OptionDialog {

  private static final I18n i18n = I18n.getInstance("net.atlanticbb.tantlinger.ui.text.dialogs");

  private static Icon icon = UIUtils.getIcon(UIUtils.X48, "table.png");
  private static String title = i18n.str("table_properties");
  private static String desc = i18n.str("table_properties_desc");

  private TableAttributesPanel tableProps = new TableAttributesPanel();
  private RowAttributesPanel rowProps = new RowAttributesPanel();
  private CellAttributesPanel cellProps = new CellAttributesPanel();

  public TablePropertiesDialog(Frame parent) {
    super(parent, title, desc, icon);
    init();
  }

  public TablePropertiesDialog(Dialog parent) {
    super(parent, title, desc, icon);
    init();
  }

  private void init() {
    Border emptyBorder = new EmptyBorder(5, 5, 5, 5);
    Border titleBorder = BorderFactory.createTitledBorder(i18n.str("table_properties"));

    tableProps.setBorder(BorderFactory.createCompoundBorder(emptyBorder, titleBorder));
    rowProps.setBorder(emptyBorder);
    cellProps.setBorder(emptyBorder);

    JTabbedPane tabs = new JTabbedPane();
    tabs.add(tableProps, i18n.str("table"));
    tabs.add(rowProps, i18n.str("row"));
    tabs.add(cellProps, i18n.str("cell"));

    setContentPane(tabs);
    setSize(440, 375);
    setResizable(false);
  }

  public void setTableAttributes(Map<String,String> at) {
    tableProps.setAttributes(at);
  }

  public void setRowAttributes(Map<String,String> at) {
    rowProps.setAttributes(at);
  }

  public void setCellAttributes(Map<String,String> at) {
    cellProps.setAttributes(at);
  }

  public Map<String,String> getTableAttributes() {
    return tableProps.getAttributes();
  }

  public Map<String,String> getRowAttribures() {
    return rowProps.getAttributes();
  }

  public Map<String,String> getCellAttributes() {
    return cellProps.getAttributes();
  }
}
