package net.atlanticbb.tantlinger.i18n;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * @author Bob Tantlinger
 */
public class I18n {

  private static final File LANG_PACK_DIR = new File(System.getProperty("user.dir"), "languages");
  private static final String DEFAULT_BUNDLE_NAME = "messages";

  private static final String MNEM_POSTFIX = ".mnemonic";

  public static final Properties BUNDLE_PROPS = new Properties();
  public static final Map<String,I18n> I18NS = new HashMap<>();

  public static Locale locale = Locale.getDefault();

  private ResourceBundle bundle;
  private String _package;

  private I18n(String _package) {
    this._package = _package;
  }

  public String str(String key) {
    try {
      if (bundle == null) {
        bundle = createBundle(getLocale());
      }

      return bundle.getString(key);
    } catch (Exception ex) {
      return '!' + key + '!';
    }
  }

  public String str(String key, Locale locale) {
    try {
      return createBundle(locale).getString(key);
    } catch (Exception ex) {
      return '!' + key + '!';
    }
  }

  public char mnem(String key) {
    String s = str(key + MNEM_POSTFIX);
    if (s != null && s.length() > 0)
      return s.charAt(0);
    return '!';
  }

  public char mnem(String key, Locale loc) {
    String s = str(key + MNEM_POSTFIX, loc);
    if (s != null && s.length() > 0)
      return s.charAt(0);
    return '!';
  }

  private ResourceBundle createBundle(Locale loc) {
    String bun = getBundleForPackage(_package);

    File[] packs = getAvailableLanguagePacks();
    if (packs != null && packs.length > 0) {
      URL[] urls = new URL[packs.length];
      try {
        for (int i = 0; i < urls.length; i++) {
          urls[i] = packs[i].toURI().toURL();
        }
        return ResourceBundle.getBundle(bun, loc, URLClassLoader.newInstance(urls));
      }
      catch (MalformedURLException | MissingResourceException ignored) {

      }
    }

    return ResourceBundle.getBundle(bun, loc);
  }

  public static I18n getInstance(String _package) {
    I18n i18n = I18NS.get(_package);
    if (i18n == null) {
      i18n = new I18n(_package);
      I18NS.put(_package, i18n);
    }

    return i18n;
  }

  public static void setLocale(Locale loc) {
    locale = loc;
    for (I18n i18n : I18NS.values()) {
      i18n.bundle = null;
    }
  }

  public static void setLocale(String locStr) throws IllegalArgumentException {
    Locale loc = localeFromString(locStr);
    if (loc == null)
      throw new IllegalArgumentException("The locale " + locStr + " was not properly formatted");
    setLocale(loc);
  }

  public static Locale getLocale() {
    if (locale == null) {
      locale = Locale.getDefault();
    }
    return locale;
  }

  private static String slashesToDots(String path) {
    StringBuilder sb = new StringBuilder();
    StringTokenizer st = new StringTokenizer(path, "/");
    while (st.hasMoreTokens()) {
      sb.append(".");
      sb.append(st.nextToken());
    }

    if (sb.toString().startsWith("."))
      sb.deleteCharAt(0);

    return sb.toString();
  }

  private static String createBundleName(String bundlePackage, String bundleName) {
    StringBuilder sb = new StringBuilder(slashesToDots(bundlePackage));
    if (!sb.toString().endsWith("."))
      sb.append('.');
    sb.append(bundleName);
    return sb.toString();
  }

  public static void setBundleForPackage(String _package, String bundle) {
    if (bundle == null)
      BUNDLE_PROPS.remove(_package);
    else
      BUNDLE_PROPS.setProperty(_package, bundle);

    I18n i18n = I18NS.get(_package);
    if (i18n != null)
      i18n.bundle = null; //reset to null so the bundle is recreated for the new name
  }

  public static String getBundleForPackage(String _package) {
    String bun = BUNDLE_PROPS.getProperty(_package);
    if (bun == null)//use default
      bun = createBundleName(_package, DEFAULT_BUNDLE_NAME);

    return bun;
  }

  public static File getLanguagePackDirectory() {
    return LANG_PACK_DIR;
  }

  public static File[] getAvailableLanguagePacks() {
    Locale[] locs = getAvailableLanguagePackLocales();
    List<File> packs = new ArrayList<>();

    for (Locale loc : locs) {
      String name = loc.toString() + ".zip";
      File f = new File(LANG_PACK_DIR, name);
      if (f.isFile() && f.canRead())
        packs.add(f);
    }

    return packs.toArray(new File[0]);
  }

  public static Locale[] getAvailableLanguagePackLocales() {
    List<Locale> locs = new ArrayList<>();
    File dir = getLanguagePackDirectory();
    if (dir.isDirectory() && dir.canRead()) {
      File[] packs = dir.listFiles(new ZipFileFilter());
      if (packs != null) {
        for (File pack : packs) {
          String name = pack.getName();
          int p = name.lastIndexOf(".");
          if (p != -1) {
            String locStr = name.substring(0, p);
            Locale loc = localeFromString(locStr);
            if (loc != null)
              locs.add(loc);
          }
        }
      }
    }

    return locs.toArray(new Locale[0]);
  }

  public static Locale localeFromString(String locStr) {
    String[] parts = locStr.split("_");
    if (parts.length > 0 || parts.length <= 3) {
      String lang = parts[0];
      String country = (parts.length > 1) ? parts[1] : "";
      String varient = (parts.length > 2) ? parts[2] : "";
      return new Locale(lang, country, varient);
    }

    return null;
  }

  private static class ZipFileFilter implements FileFilter {
    public boolean accept(File f) {
      if (f.isFile() && f.canRead()) {
        return f.getName().toLowerCase().endsWith(".zip");
      }
      return false;
    }
  }
}
