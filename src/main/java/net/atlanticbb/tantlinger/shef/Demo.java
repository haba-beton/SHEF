
package net.atlanticbb.tantlinger.shef;

import net.atlanticbb.tantlinger.io.IOUtils;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Bob Tantlinger
 */
public class Demo {

  private final JFrame frame;
  private final HTMLEditorPane editor;

  public Demo() {


    editor = new HTMLEditorPane(true);
    InputStream in = Demo.class.getResourceAsStream("/net/atlanticbb/tantlinger/shef/htmlsnip.txt");
    try {
      editor.setText(IOUtils.read(in));
    } catch (IOException ex) {
      ex.printStackTrace();
    } finally {
      IOUtils.close(in);
    }

    frame = new JFrame();
    JMenuBar menuBar = new JMenuBar();
    menuBar.add(editor.getEditMenu());
    menuBar.add(editor.getFormatMenu());
    menuBar.add(editor.getInsertMenu());
    frame.setJMenuBar(menuBar);

    frame.setTitle("HTML Editor Demo");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(800, 600);
    frame.getContentPane().add(editor);
    frame.setVisible(true);

  }

  private void printHtml() {
    System.out.println(editor.getText());
  }


  private static Demo demo;

  public static void main(String... args) throws InterruptedException, InvocationTargetException {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception ignore) {
    }

    SwingUtilities.invokeAndWait(() -> demo = new Demo());

    do {
      Thread.sleep(2000);

    } while (demo.frame.isVisible());

    demo.printHtml();

  }


}
