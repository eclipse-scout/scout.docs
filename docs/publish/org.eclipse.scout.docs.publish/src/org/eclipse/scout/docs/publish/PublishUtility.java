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
package org.eclipse.scout.docs.publish;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 *
 */
public class PublishUtility {
  /**
   * Take a single HTML file and publish it to the outFolder.
   * Images and CSS resources are moved.
   *
   * @param inFolder
   *          root folder where the input HTML file is located.
   * @param inFileName
   *          name of the input HTML file (with extension)
   * @param outFolder
   *          directory where the post-processed HTML file is saved.
   * @throws IOException
   */
  public static void publishHtmlFile(File inFolder, String inFileName, File outFolder) throws IOException {
    if (!inFolder.exists() || !inFolder.isDirectory()) {
      throw new IllegalStateException("Folder inFolder '" + inFolder.getAbsolutePath() + "' not found.");
    }
    File inFile = new File(inFolder, inFileName);
    if (!inFile.exists()) {
      throw new IllegalStateException("File inFile '" + inFolder.getAbsolutePath() + "' does not exist.");
    }

    File outFile = new File(outFolder, inFile.getName());
    String html = Files.toString(inFile, Charsets.ISO_8859_1);

    Document doc = Jsoup.parse(html);
    doc.outputSettings().charset("ASCII");

    fixListingLink(doc);
    fixFigureLink(doc);

    Files.createParentDirs(outFile);
    moveAndcopyImages(doc, inFolder, outFolder, "images/");

    Files.write(doc.toString(), outFile, Charsets.ISO_8859_1);
  }

  /**
   * Take a single HTML file and publish it to the outFolder.
   * Images and CSS resources are moved.
   *
   * @param inFolder
   *          root folder where the input HTML file is located.
   * @param inFileName
   *          name of the input HTML file (with extension)
   * @param outFolder
   *          directory where the post-processed HTML file is saved.
   * @throws IOException
   */
  public static void publishPdfFile(File inFolder, String inFileName, File outFolder) throws IOException {
    if (!inFolder.exists() || !inFolder.isDirectory()) {
      throw new IllegalStateException("Folder inFolder '" + inFolder.getAbsolutePath() + "' not found.");
    }
    File inFile = new File(inFolder, inFileName);
    if (!inFile.exists()) {
      throw new IllegalStateException("File inFile '" + inFolder.getAbsolutePath() + "' does not exist.");
    }
    else if (!inFile.getName().endsWith("pdf")) {
      throw new IllegalStateException("File inFile '" + inFolder.getAbsolutePath() + "' is not a PDF");
    }
    File outFile = new File(outFolder, inFile.getName());

    Files.createParentDirs(outFile);
    Files.copy(inFile, outFile);
  }

  /**
   * Copy images to the outFolder
   *
   * @param doc
   *          the html content as JSoup document
   * @param inFolder
   * @param outFolder
   * @param imgSubPath
   *          image path relative to the outFolder
   * @throws IOException
   */
  public static void moveAndcopyImages(Document doc, File inFolder, File outFolder, String imgSubPath) throws IOException {
    if (imgSubPath == null) {
      throw new IllegalArgumentException("imgSubPath can not be null, use empty string if you do not want to modify the relative path of the image file");
    }
    else if (imgSubPath.length() > 0 && !imgSubPath.endsWith("/")) {
      throw new IllegalArgumentException("imgSubPath point to an other subdirectory. It should ends with '/'");
    }

    Elements elements = doc.getElementsByTag("img");
    for (Element element : elements) {
      String src = element.attr("src");
      if (src != null) {
        File inFile = new File(inFolder, src);
        String newSrc = imgSubPath + inFile.getName();
        element.attr("src", newSrc);
        File outFile = new File(outFolder, newSrc);
        if (inFile.exists() && inFile.isFile()) {
          Files.createParentDirs(outFile);
          Files.copy(inFile, outFile);
        }
        else if (outFile.exists() && outFile.isFile()) {
          //the in file was not found as inFile and it was not copied, but the outFile already exists. Nothing to do, no warning.
        }
        else {
          System.err.println("Image file '" + inFile.getAbsolutePath() + "' is missing");
        }
      }
    }
  }

  /**
   * Copy the CSS files to the outFolder
   *
   * @param doc
   *          the html content as JSoup document
   * @param inFolder
   * @param outFolder
   * @param cssSubPath
   * @throws IOException
   */
  public static void moveAndcopyCss(Document doc, File inFolder, File outFolder, String cssSubPath) throws IOException {
    if (cssSubPath == null) {
      throw new IllegalArgumentException("cssSubPath can not be null, use empty string if you do not want to change the relative path of the css file");
    }
    else if (cssSubPath.length() > 0 && !cssSubPath.endsWith("/")) {
      throw new IllegalArgumentException("cssSubPath point to an other subdirectory. It should ends with '/'");
    }

    Elements elements = doc.getElementsByTag("link");
    for (Element element : elements) {
      String rel = element.attr("rel");
      if ("stylesheet".equals(rel)) {
        String href = element.attr("href");
        if (href != null && !href.startsWith("http")) {
          File inFile = new File(inFolder, href);
          String newHref = cssSubPath + inFile.getName();
          element.attr("href", newHref);
          File outFile = new File(outFolder, newHref);
          if (inFile.exists() && inFile.isFile()) {
            Files.createParentDirs(outFile);
            Files.copy(inFile, outFile);
          }
          else if (outFile.exists() && outFile.isFile()) {
            //the in file was not found as inFile and it was not copied, but the outFile already exists. Nothing to do, no warning.
          }
          else {
            System.err.println("CSS file '" + inFile.getAbsolutePath() + "' is missing");
          }
        }
      }
    }
  }

  static private Pattern listingPattern = Pattern.compile("(Listing [0-9]+)\\.");

  /**
   * #858 workaround for listing
   * https://github.com/asciidoctor/asciidoctor/issues/858
   *
   * @param doc
   *          JSoup document (type is {@link org.jsoup.nodes.Document})
   */
  public static void fixListingLink(Document doc) {
    Elements elements = doc.getElementsByTag("a");
    for (Element link : elements) {
      String href = link.attr("href");
      if (href != null && href.startsWith("#")) {
        String id = href.substring(1);
        Element idElement = doc.getElementById(id);
        if (idElement != null && "listingblock".equals(idElement.attr("class"))) {
          fixLink(link, idElement, listingPattern);
        }
      }
    }
  }

  static private Pattern figurePattern = Pattern.compile("(Figure [0-9]+)\\.");

  /**
   * #858 workaround for figure
   * https://github.com/asciidoctor/asciidoctor/issues/858
   *
   * @param doc
   *          JSoup document (type is {@link org.jsoup.nodes.Document})
   */
  public static void fixFigureLink(Document doc) {
    Elements elements = doc.getElementsByTag("a");
    for (Element link : elements) {
      String href = link.attr("href");
      if (href != null && href.startsWith("#")) {
        String id = href.substring(1);
        Element idElement = doc.getElementById(id);
        boolean fixedText = false;
        if (idElement != null && "imageblock".equals(idElement.attr("class"))) {
          fixedText = fixLink(link, idElement, figurePattern);
        }
        //Support for the multiple Images in one figure workaround (see Issue 1287)
        if (!fixedText && idElement != null) {
          Element container = idElement.parent();
          boolean checkNext = false;
          for (int i = 0; i < container.childNodeSize(); i++) {
            Node childNode = container.childNode(i);
            if (id.equals(childNode.attr("id")) && "imageblock".equals(childNode.attr("class"))) {
              checkNext = true;
            }
            if (!fixedText && checkNext) {
              if ("imageblock".equals(childNode.attr("class")) && childNode instanceof Element) {
                fixedText = fixLink(link, (Element) childNode, figurePattern);
              }
              else if (!(childNode instanceof Element)) {
                //do nothing.
              }
              else {
                //found an other element that does not correspond.
                checkNext = false;
              }
            }
          }
        }
      }
    }
  }

  private static boolean fixLink(Element link, Element element, Pattern pattern) {
    Element titleDiv = findTitleDiv(element);
    if (titleDiv != null) {
      Matcher matcher = pattern.matcher(titleDiv.text());
      if (matcher.find()) {
        link.text(matcher.group(1));
        return true;
      }
    }
    return false;
  }

  private static Element findTitleDiv(Element element) {
    Elements elements = element.getElementsByTag("div");
    for (Element e : elements) {
      if ("title".equals(e.attr("class"))) {
        return e;
      }
    }
    return null;
  }
}
