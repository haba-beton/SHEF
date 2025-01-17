package net.atlanticbb.tantlinger.ui;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {
  private static final long serialVersionUID = 1L;
  private JLabel titleLabel = null;
  private JLabel msgLabel = null;
  private JLabel iconLabel = null;

  public HeaderPanel() {
    super();
    initialize();
  }

  public HeaderPanel(String title, String desc, Icon ico) {
    super();
    initialize();
    setTitle(title);
    setDescription(desc);
    setIcon(ico);

  }

  public void setTitle(String title) {
    titleLabel.setText(title);
  }

  public void setDescription(String desc) {
    msgLabel.setText(desc);
  }

  public void setIcon(Icon icon) {
    iconLabel.setIcon(icon);
  }

  private void initialize() {
    GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
    gridBagConstraints3.gridx = 1;
    gridBagConstraints3.gridheight = 2;
    gridBagConstraints3.insets = new java.awt.Insets(0, 5, 0, 10);
    gridBagConstraints3.gridy = 0;
    iconLabel = new JLabel();
    iconLabel.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
    GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
    gridBagConstraints2.gridx = 0;
    gridBagConstraints2.insets = new java.awt.Insets(2, 25, 0, 0);
    gridBagConstraints2.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints2.weightx = 1.0;
    gridBagConstraints2.weighty = 1.0;
    gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints2.gridy = 1;
    msgLabel = new JLabel();
    msgLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
    msgLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.weightx = 0.0;
    gridBagConstraints.insets = new java.awt.Insets(6, 10, 0, 0);
    gridBagConstraints.weighty = 0.0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.gridy = 0;
    titleLabel = new JLabel();
    titleLabel.setFont(new Font("Dialog", Font.BOLD, 16));
    titleLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
    this.setLayout(new GridBagLayout());
    this.setSize(360, 56);
    this.setPreferredSize(new java.awt.Dimension(360, 56));
    this.add(titleLabel, gridBagConstraints);
    this.add(msgLabel, gridBagConstraints2);
    this.add(iconLabel, gridBagConstraints3);

    setBorder(BorderFactory.createLineBorder(Color.black, 1));
  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON);
    Rectangle bounds = getBounds();

    Color blue = new Color(153, 204, 255);

    Paint gradientPaint = new GradientPaint(bounds.width * 0.5f,
      bounds.y, Color.white, bounds.width, 0f, blue);
    g2.setPaint(gradientPaint);
    g2.fillRect(0, 0, bounds.width, bounds.height);

  }
}