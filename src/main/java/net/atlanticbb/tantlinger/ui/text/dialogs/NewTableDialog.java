package net.atlanticbb.tantlinger.ui.text.dialogs;

import net.atlanticbb.tantlinger.i18n.I18n;
import net.atlanticbb.tantlinger.ui.OptionDialog;
import net.atlanticbb.tantlinger.ui.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;
import java.util.Map;

public class NewTableDialog extends OptionDialog {
  private static final I18n i18n = I18n.getInstance("net.atlanticbb.tantlinger.ui.text.dialogs");

  private LayoutPanel layoutPanel = new LayoutPanel();
  private TableAttributesPanel propsPanel;
  private static Icon icon = UIUtils.getIcon(UIUtils.X48, "table.png");

  public NewTableDialog(Frame parent) {
    super(parent, i18n.str("new_table"), i18n.str("new_table_desc"), icon);
    init();
  }

  public NewTableDialog(Dialog parent) {
    super(parent, i18n.str("new_table"), i18n.str("new_table_desc"), icon);
    init();
  }

  private void init() {
    Hashtable<String,String> ht = new Hashtable<>();
    ht.put("border", "1");
    ht.put("width", "100%");
    propsPanel = new TableAttributesPanel();
    propsPanel.setAttributes(ht);

    propsPanel.setBorder(
      BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(i18n.str("properties")), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(layoutPanel, BorderLayout.NORTH);
    mainPanel.add(propsPanel, BorderLayout.CENTER);
    mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    setContentPane(mainPanel);
    setSize(new Dimension(330, 380));
    setResizable(false);
  }

  public String getHTML() {
    String html = "<table";
    Map<String,String> attribs = propsPanel.getAttributes();

    for (String s : attribs.keySet()) {
      String val = attribs.get(s);
      html += ' ' + s + "=\"" + val + "\"";
    }

    html += ">\n";

    int numRows = layoutPanel.getRows();
    int numCols = layoutPanel.getColumns();
    for (int row = 1; row <= numRows; row++) {
      html += "<tr>\n";
      for (int col = 1; col <= numCols; col++) {
        html += "\t<td>\n</td>\n";
      }
      html += "</tr>\n";
    }

    return html + "</table>";
  }

  private static class LayoutPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JLabel rowsLabel = null;
    private JLabel colsLabel = null;
    private int iRows, iCols;
    private JSpinner rowsField = null;
    private JSpinner colsField = null;

    public LayoutPanel() {
      this(1, 1);
    }

    public LayoutPanel(int r, int c) {
      super();
      iRows = (r > 0) ? r : 1;
      iCols = (c > 0) ? c : 1;
      initialize();
    }

    public int getRows() {
      return Integer.parseInt(rowsField.getModel().getValue().toString());
    }

    public int getColumns() {
      return Integer.parseInt(colsField.getModel().getValue().toString());
    }

    private void initialize() {
      GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
      gridBagConstraints7.fill = java.awt.GridBagConstraints.NONE;
      gridBagConstraints7.gridy = 0;
      gridBagConstraints7.weightx = 1.0;
      gridBagConstraints7.anchor = java.awt.GridBagConstraints.WEST;
      gridBagConstraints7.gridx = 3;
      GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
      gridBagConstraints6.fill = java.awt.GridBagConstraints.NONE;
      gridBagConstraints6.gridy = 0;
      gridBagConstraints6.weightx = 0.0;
      gridBagConstraints6.anchor = java.awt.GridBagConstraints.WEST;
      gridBagConstraints6.insets = new java.awt.Insets(0, 0, 0, 15);
      gridBagConstraints6.gridx = 1;
      GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
      gridBagConstraints1.gridx = 2;
      gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
      gridBagConstraints1.insets = new java.awt.Insets(0, 0, 0, 5);
      gridBagConstraints1.gridy = 0;
      colsLabel = new JLabel();
      colsLabel.setText(i18n.str("columns"));
      GridBagConstraints gridBagConstraints = new GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
      gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
      gridBagConstraints.weighty = 0.0;
      gridBagConstraints.gridy = 0;
      rowsLabel = new JLabel();
      rowsLabel.setText(i18n.str("rows"));
      this.setLayout(new GridBagLayout());
      this.setSize(330, 60);
      this.setPreferredSize(new java.awt.Dimension(330, 60));
      this.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createTitledBorder(null, i18n.str("layout"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null), javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5))); //$NON-NLS-1$
      this.add(rowsLabel, gridBagConstraints);
      this.add(colsLabel, gridBagConstraints1);
      this.add(getRowsField(), gridBagConstraints6);
      this.add(getColsField(), gridBagConstraints7);
    }

    private JSpinner getRowsField() {
      if (rowsField == null) {
        rowsField = new JSpinner(new SpinnerNumberModel(iRows, 1, 999, 1));
      }
      return rowsField;
    }

    private JSpinner getColsField() {
      if (colsField == null) {
        colsField = new JSpinner(new SpinnerNumberModel(iCols, 1, 999, 1));
      }
      return colsField;
    }
  }

}
