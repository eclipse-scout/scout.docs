/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipsescout.demo.widgets.client.services;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.services.common.icon.IconSpec;
import org.eclipse.scout.service.AbstractService;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;

/**
 * FontIconProviderService provides Scout icons from true-type fonts.
 * The class assumes the existence of a directory '/resources/fonts' in the application's client plugin
 * that contains the files 'fontawesome-webfont.ttf' and 'icons.properties'.
 * The file 'fontawesome-webfont.ttf' can be downloaded from 'http://fontawesome.io/' and the
 * file 'icons.properties' contains alias names for individual icons.
 * Individual lines have the following format:
 * <alias>;<font-file-name>=<character hex code>
 * plane;fontawesome-webfont=f072
 * If additional ttf font files are present in folder '/resources/fonts' they are available to the application too.
 */
public class FontIconProviderService extends AbstractService implements IFontIconProviderService {
  private static final IScoutLogger LOG = ScoutLogManager.getLogger(FontIconProviderService.class);

  public static final String ICON_FONT = "fontawesome-webfont";
  public static final String ICON_PROPERTIES = "icons.properties";
  public static final String ICON_FONT_PREFIX = "font:";
  public static final String ICON_FONT_DIRECTORY = "/resources/fonts";
  public static final String ICON_FONT_EXTENSION = "ttf";
  public static final String ICON_IMAGE_EXTENSION = "png";

  public static final String ICON_BACKGROUND_EMPTY = "empty";
  public static final String ICON_BACKGROUND_CIRCLE = "circle";
  public static final String ICON_BACKGROUND_SQUARE = "square";

  public static final String DEFAULT_FONT = ICON_FONT;
  public static final String DEFAULT_BACKGROND = ICON_BACKGROUND_EMPTY;
  public static final String DEFAULT_COLOR = "5b95c2";
  public static final String DEFAULT_COLOR_BACKGROUND = "ffffff";
  public static final int DEFAULT_SIZE = 24;
  public static final float DEFAULT_PADDING = 0.125f;

  public static final String FONT_SEPARATOR = ";";
  public static final String PATH_SEPARATOR = "/";
  public static final String PARAMETER_SEPARATOR = "!";
  public static final String COLOR_PREFIX = "#";

  private static final String FONT_FILE_TTF = ICON_FONT_DIRECTORY + PATH_SEPARATOR + ICON_FONT + "." + ICON_FONT_EXTENSION;

  public static final Pattern IMAGE_WITH_STATE_PATTERN = Pattern.compile("(.*)(_active|_disabled|_mouse|_mouse_over|_open|_over|_pressed|_rollover|_selected)", Pattern.CASE_INSENSITIVE);

  private Map<String, Font> m_fonts = new HashMap<String, Font>();
  private Properties m_properties = null;

  private int m_ranking = 0;
  private Bundle m_bundle = null;

  private String m_defaultFont = DEFAULT_FONT;
  private String m_defaultColor = DEFAULT_COLOR;
  private String m_defaultColorBackground = DEFAULT_COLOR_BACKGROUND;
  private String m_defaultBackground = DEFAULT_BACKGROND;
  private int m_defaultSize = DEFAULT_SIZE;

  @Override
  public void initializeService(ServiceRegistration registration) {
    super.initializeService(registration);
    Object rankingProp = registration.getReference().getProperty(Constants.SERVICE_RANKING);

    if (rankingProp instanceof Integer) {
      m_ranking = ((Integer) rankingProp).intValue();
    }

    setHostBundle(registration.getReference().getBundle());
    initializeFonts(ICON_FONT_DIRECTORY);
  }

  private void initializeFonts(String fontDirectory) {
    try {
      readFonts(fontDirectory);
      readFontProperties(fontDirectory);
    }
    catch (Exception e) {
      LOG.error("Failed to initialize font files from '" + ICON_FONT_DIRECTORY + "'", e);
    }
  }

  private void readFonts(String fontDirectory) throws Exception {
    for (Enumeration<String> e = m_bundle.getEntryPaths(fontDirectory); e.hasMoreElements();) {
      String filename = PATH_SEPARATOR + e.nextElement();

      if (filename.toLowerCase().endsWith(ICON_FONT_EXTENSION)) {
        String fontFile = getFontFileName(filename);
        Font font = Font.createFont(Font.TRUETYPE_FONT, getInputStream(filename));
        String fontId = fontFile.substring(0, fontFile.length() - ICON_FONT_EXTENSION.length() - 1);
        m_fonts.put(fontId, font);
      }
    }
  }

  private void readFontProperties(String fontDirectory) throws Exception {
    m_properties = new Properties();
    m_properties.load(getInputStream(fontDirectory + PATH_SEPARATOR + ICON_PROPERTIES));

    // transform char (hex-)values to correct format for lookup
    for (Object k : m_properties.keySet()) {
      String key = (String) k;
      String value = (String) m_properties.getProperty(key);

      // assume hex code iff length == 4
      if (StringUtility.hasText(value) && value.length() == 4) {
        Character charValue = (char) Integer.parseInt(value, 16);
        m_properties.setProperty(key, charValue.toString());
      }
    }
  }

  private InputStream getInputStream(String fileName) throws IOException {
    return this.getClass().getResourceAsStream(fileName);
  }

  @Override
  public List<String> getIconKeys() {
    List<String> result = new ArrayList<String>();

    for (Enumeration e = m_properties.keys(); e.hasMoreElements();) {
      String key = (String) e.nextElement();
      String defaultSuffix = FONT_SEPARATOR + getDefaultFont();

      if (key.endsWith(defaultSuffix)) {
        result.add(ICON_FONT_PREFIX + key.substring(0, key.length() - defaultSuffix.length()));
      }
      else {
        result.add(ICON_FONT_PREFIX + key);
      }
    }

    return result;
  }

  private String getFontFileName(String filename) {
    String[] parts = StringUtility.split(filename, PATH_SEPARATOR);

    if (parts.length == 0) {
      return null;
    }
    else {
      return parts[parts.length - 1];
    }
  }

  /**
   * The icon lookup can be called with a font-awesome icon alias, e.g. 'plane'.
   * All font icons are assumed to have the prefix 'font:'.
   * Format description: font:icon[!size!color!background];fontname
   * Example for simple format: "font:rocket"
   * Example for full format: "font:music!16!#5b95c2!circle;fontawesome-webfont"
   *
   * @return the icon specification to the icon or null if not found.
   */
  @Override
  public IconSpec getIconSpec(String iconName) {
    BufferedImage image = getBufferedImageIcon(iconName);
    IconSpec icon = imageToIconSpec(image, iconName);

    return icon;
  }

  /**
   * Creates a buffered image for a font-awesome icon alias.
   * The format for the iconName is described in {@link #getIconSpec(String)}.
   *
   * @return a buffered image for the icon or null if not found.
   */
  @Override
  public BufferedImage getBufferedImageIcon(String iconName) {
    LOG.info("create image for name '" + iconName + "'");

    // icon name empty or wrong prefix
    if (StringUtility.isNullOrEmpty(iconName) || !iconName.toLowerCase().startsWith(ICON_FONT_PREFIX)) {
      LOG.info("not a font icon, returning null");
      return null;
    }
    // icon name for a specific state:
    Matcher m = IMAGE_WITH_STATE_PATTERN.matcher(iconName);
    if (m.matches()) {
      LOG.info("font icon with a state suffix, returning null");
      return null;
    }

    String icon = iconName.substring(ICON_FONT_PREFIX.length());

    String font = getDefaultFont();
    String color = getDefaultColor();
    String colorBackground = getDefaultColorBackground();
    String background = getDefaultBackground();
    int size = getDefaultSize();

    // check for font specification
    if (icon.contains(FONT_SEPARATOR)) {
      String[] token = StringUtility.split(icon, FONT_SEPARATOR);
      icon = token[0];
      font = token[1];
    }

    // check for additional icon parameters
    if (icon.contains(PARAMETER_SEPARATOR)) {
      String[] token = StringUtility.split(icon, PARAMETER_SEPARATOR);
      icon = token[0];

      for (int i = 1; i < token.length; i++) {
        Character ch = token[i].charAt(0);

        // check for color (starts with COLOR_PREFIX)
        if (COLOR_PREFIX.equals(ch.toString())) {
          color = token[i].substring(1);
        }
        // check for size (starts with digits)
        else if (Character.isDigit(ch)) {
          size = Integer.parseInt(token[i]);
        }
        // assume background type
        else {
          background = token[i];
        }
      }
    }

    Color fcolor = new Color(Integer.parseInt(color, 16));
    Color scolor = null;
    int fsize = size;
    int ssize = 0;
    float padding = DEFAULT_PADDING;

    // check for non-stacked icon
    if (!background.equals(DEFAULT_BACKGROND)) {
      scolor = fcolor;
      ssize = size;

      fcolor = new Color(Integer.parseInt(colorBackground, 16));
      fsize = (int) (0.66666 * size);

      // increase padding for large icons
      if (background.equals(ICON_BACKGROUND_CIRCLE) && size > 32) {
        fsize = (int) (0.55555 * size);
      }
    }

    return buildImage(icon, font, fsize, fcolor, background, ssize, scolor, padding);
  }

  /**
   * Builds a buffered image using the specified parameters
   *
   * @param icon
   *          Name for icon, either character(s) directly or an alias defined in the icons.properties file
   * @param font
   *          Name of font to be used for icon
   * @param fsize
   *          Size of icon in foreground
   * @param fcolor
   *          Color of icon
   * @param sicon
   *          Name for background icon (if defined, circle and square are good options)
   * @param ssize
   *          Size of background icon
   * @param scolor
   *          Color of background icon
   * @param padding
   *          Padding to be used to make icon smaller than background (icon)
   * @return
   */
  private BufferedImage buildImage(
      String icon, String font, int fsize, Color fcolor,
      String background, int ssize, Color scolor, float padding)
  {
    String ficon = m_properties.getProperty(icon + FONT_SEPARATOR + font, icon);
    String sicon = background != null ? m_properties.getProperty(background + FONT_SEPARATOR + DEFAULT_FONT, null) : null;
    Font ffont = getFont(font, fsize, padding);
    Font sfont = sicon != null ? getFont(DEFAULT_FONT, ssize, padding) : null;
    int size = fsize > ssize ? (int) fsize : (int) ssize;

    BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = image.createGraphics();
    graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // AA

    if (sicon != null) { // Then draw background icon first
      graphics.setFont(sfont);
      graphics.setColor(scolor);
      Point spoint = calcDrawPoint(sfont, sicon, size, graphics);
      graphics.drawString(sicon, spoint.x, spoint.y);
    }

    graphics.setFont(ffont);
    graphics.setColor(fcolor);
    Point fpoint = calcDrawPoint(ffont, ficon, size, graphics);
    graphics.drawString(ficon, fpoint.x, fpoint.y);
    graphics.dispose();

    return image;
  }

  private Font getFont(String font, int size, float padding) {
    if (!m_fonts.containsKey(font)) {
      LOG.warn("font '" + font + "' not found, backing off to default font '" + getDefaultFont() + "' instead");
      font = getDefaultFont();
    }

    Font f = m_fonts.get(font);
    Font ff = f.deriveFont(size - size * padding);

    return ff;
  }

  private Point calcDrawPoint(Font font, String icon, int size, Graphics2D graphics) {
    int center = size / 2; // Center X and Center Y are the same
    Rectangle stringBounds = graphics.getFontMetrics().getStringBounds(icon, graphics).getBounds();
    Rectangle visualBounds = font.createGlyphVector(graphics.getFontRenderContext(), icon).getVisualBounds().getBounds();
    return new Point(center - stringBounds.width / 2, center - visualBounds.height / 2 - visualBounds.y);
  }

  private IconSpec imageToIconSpec(BufferedImage image, String iconName) {
    if (image == null || StringUtility.isNullOrEmpty(iconName)) {
      return null;
    }

    ByteArrayOutputStream bos = new ByteArrayOutputStream();

    try {
      ImageIO.write(image, ICON_IMAGE_EXTENSION, bos);
    }
    catch (IOException e) {
      LOG.error("Error writing buffered image to ByteArrayOutputStream", e);
    }

    IconSpec spec = new IconSpec();
    spec.setContent(bos.toByteArray());
    spec.setName(iconName);

    return spec;
  }

  @Override
  public int getRanking() {
    return m_ranking;
  }

  @Override
  public String toString() {
    return getClass().getName() + ", ranking=" + getRanking();
  }

  public void setHostBundle(Bundle bundle) {
    m_bundle = bundle;
  }

  @Override
  public Bundle getHostBundle() {
    return m_bundle;
  }

  public String getDefaultFont() {
    return m_defaultFont;
  }

  public void setDefaultFont(String defaultFont) {
    m_defaultFont = defaultFont;
  }

  public String getDefaultColor() {
    return m_defaultColor;
  }

  public void setDefaultColor(String defaultColor) {
    m_defaultColor = defaultColor;
  }

  public String getDefaultColorBackground() {
    return m_defaultColorBackground;
  }

  public void setDefaultColorBackground(String defaultColorBackground) {
    m_defaultColorBackground = defaultColorBackground;
  }

  public String getDefaultBackground() {
    return m_defaultBackground;
  }

  public void setDefaultBackground(String defaultBackground) {
    m_defaultBackground = defaultBackground;
  }

  public int getDefaultSize() {
    return m_defaultSize;
  }

  public void setDefaultSize(int defaultSize) {
    m_defaultSize = defaultSize;
  }

  /**
   * Returns the set of available font IDs.
   */
  @Override
  public List<String> getFontKeys() {
    return CollectionUtility.arrayList(m_fonts.keySet());
  }
}
