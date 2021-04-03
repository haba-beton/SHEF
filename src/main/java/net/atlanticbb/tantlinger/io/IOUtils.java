package net.atlanticbb.tantlinger.io;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Various IO utility methods
 *
 * @author Bob Tantlinger
 */
public class IOUtils {
  private static final int BUFFER_SIZE = 1024 * 4;
  private static final NullCopyMonitor nullMon = new NullCopyMonitor();


  private static void getDirectoryContents(List<File> onlyFiles, File rootdir) {
    File[] list = rootdir.listFiles();
    for (File file : list) {
      if (file.isDirectory()) {
        getDirectoryContents(onlyFiles,file);
      }
      else if (file.isFile()) {
        onlyFiles.add(file);
      }
    }
  }

  public static File[] getDirectoryContents(File rootdir) {
    List<File> list = new ArrayList<>();
    getDirectoryContents(list, rootdir);
    return list.toArray(new File[0]);
  }

  public static String sanitize(String x) {
        /* Characters that are OK in a file are described
        by regular expressions as:

         \w - alphanumeric (A-Za-z0-9)
         \. - dot
         \- - dash
         \: - colon
         \; - semicolon
         \# - number sign
         \_ - underscore

       Each \ above must be escaped to allow javac to parse
       it correctly. That's why it looks so bad below.

       Since we want to replace things that are not the above,
       set negation ([^ and ]) is used.
     */
    return x.replaceAll("[^\\w.\\-:;#_]", "_");
  }

  public static String getExtension(File f) {
    String name = f.getName();

    int i = name.lastIndexOf(".");
    if (i == -1 || i == name.length() - 1)
      return "";

    return name.substring(i + 1);
  }

  public static String getName(File f) {
    String name = f.getName();

    int i = name.lastIndexOf(".");
    if (i == -1)
      return name;

    return name.substring(0, i);
  }

  public static File createUniqueFile(File f) {
    while (f.exists()) {
      String ext = getExtension(f);
      String name = getName(f);
      int dashPos = name.lastIndexOf('-');
      if (dashPos == -1) {
        name = name + "-1";
      } else {
        String num = name.substring(dashPos + 1);
        String temp = name.substring(0, dashPos);
        try {
          int cur = Integer.parseInt(num) + 1;
          name = temp + "-" + cur;
        } catch (NumberFormatException nfe) {
          name = name + "-1";
        }
      }

      String uniqueName = name;
      if (!ext.equals(""))
        uniqueName = uniqueName + "." + ext;

      String parent = "";
      if (f.getParent() != null)
        parent = f.getParent();
      f = new File(parent, uniqueName);
    }

    return f;
  }

  public static void copy(Reader src, Writer dst) throws IOException {
    char[] buffer = new char[BUFFER_SIZE];
    int n;
    while ((n = src.read(buffer)) != -1) {
      dst.write(buffer, 0, n);
    }
  }

  public static void copy(InputStream src, OutputStream dst) throws IOException {
    copy(src, dst, nullMon);
  }

  public static void copy(InputStream src, OutputStream dst, CopyMonitor mon) throws IOException {
    byte[] buffer = new byte[BUFFER_SIZE];
    int n;
    while ((n = src.read(buffer)) != -1 && !mon.isCopyAborted()) {
      dst.write(buffer, 0, n);
      mon.bytesCopied(n);
    }
  }

  public static void copy(File src, File dst) throws IOException {
    copy(src, dst, nullMon);
  }

  public static void copy(File src, File dst, boolean overwrite) throws IOException {
    copy(src, dst, nullMon, overwrite);
  }

  public static void copy(File src, File dst, FileCopyMonitor mon) throws IOException {
    copy(src, dst, mon, true);
  }

  public static void copy(File src, File dst, FileCopyMonitor mon, boolean overwrite) throws IOException {
    if (!overwrite)
      dst = createUniqueFile(dst);

    //System.err.println("copy " + dst);
    mon.copyingFile(src);
    InputStream in = null;
    OutputStream out = null;
    try {
      in = new FileInputStream(src);
      out = new FileOutputStream(dst);
      copy(in, out, mon);
    }
    finally {
      close(in);
      close(out);
    }
  }

  public static void copy(String srcPath, String dstPath) throws IOException {
    copy(new File(srcPath), new File(dstPath));
  }

  public static void copy(String srcPath, String dstPath, FileCopyMonitor mon) throws IOException {
    copy(new File(srcPath), new File(dstPath), mon);
  }

  public static void copy(String srcPath, String dstPath, boolean overwrite) throws IOException {
    copy(new File(srcPath), new File(dstPath), overwrite);
  }

  public static void copy(String srcPath, String dstPath, FileCopyMonitor mon, boolean overwrite) throws IOException {
    copy(new File(srcPath), new File(dstPath), mon, overwrite);
  }

  public static void copyFiles(File src, File dest) throws IOException {
    copyFiles(src, dest, nullMon);
  }

  public static void copyFiles(File src, File dest, FileCopyMonitor mon) throws  IOException {
    copyFilesRecursively(src, dest, mon);
  }

  private static void copyFilesRecursively(File src, File dest, FileCopyMonitor mon) throws  IOException {
    if (mon.isCopyAborted()) {
      return;
    }

    if (!src.exists())
      throw new FileNotFoundException("File not found:" + src);

    if (src.isDirectory()) {
      if (!dest.exists()) {
        dest.mkdirs();
      }
      // Go through the contents of the directory
      String[] list = src.list();

      Arrays.sort(list);

      for (String s : list) {
        copyFilesRecursively(new File(src, s), new File(dest, s), mon);
      }
    }
    else {
      copy(src, dest, mon, true);
    }
  }

  public static long getTotalBytes(File file) {
    long bytes = 0;

    if (file.isDirectory()) {
      for (File value : file.listFiles()) {
        bytes += getTotalBytes(value);
      }
    }
    else {
      bytes = file.length();
    }

    return bytes;
  }


  public static String read(InputStream input) throws IOException {
    return read(new InputStreamReader(input));
  }

  public static String read(File file) throws IOException {
    return read(new FileReader(file));
  }

  public static String read(Reader input) throws IOException {
    BufferedReader reader = new BufferedReader(input);
    StringBuilder sb = new StringBuilder();
    int ch;

    while ((ch = reader.read()) != -1) {
      sb.append((char) ch);
    }

    close(reader);

    return sb.toString();
  }

  public static void write(File file, String str) throws IOException {
    PrintWriter out = new PrintWriter(new FileOutputStream(file));
    out.print(str);
    close(out);
  }

  public static void write(File file, InputStream input) throws IOException {
    InputStream in = new BufferedInputStream(input);
    OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
    int ch;
    while ((ch = in.read()) != -1)
      out.write(ch);

    close(in);
    close(out);
  }

  public static boolean deleteRecursively(File file) {
    if (file.isDirectory()) {
      File[] children = file.listFiles();
      for (File child : children) {
        boolean success = deleteRecursively(child);
        if (!success)
          return false;
      }
    }

    return file.delete();
  }

  public static void close(InputStream c) {
    if (c != null) {
      try {
        c.close();
      }
      catch (IOException ignored) {
      }
    }
  }

  public static void close(OutputStream c) {
    if (c != null) {
      try {
        c.close();
      }
      catch (IOException ignored) {
      }
    }
  }

  public static void close(Reader c) {
    if (c != null) {
      try {
        c.close();
      }
      catch (IOException ignored) {
      }
    }
  }

  public static void close(Writer c) {
    if (c != null) {
      try {
        c.close();
      }
      catch (IOException ignored) {
      }
    }
  }

  private static class NullCopyMonitor implements FileCopyMonitor {
    public void bytesCopied(int numBytes) {
    }

    public void copyingFile(File f) {
    }

    public boolean isCopyAborted() {
      return false;
    }
  }
}