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
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.mylyn.wikitext.core.parser.outline.OutlineItem;
import org.eclipse.mylyn.wikitext.core.parser.util.MarkupToEclipseToc;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 *
 */
public class PublishHelpUtility {

  private static final String IMAGES_SUB_PATH = "images/";
  private static final String HTML_SUB_PATH = "html/";
  private static final String IMAGE_HOME = "home.gif";
  private static final String IMAGE_NEXT = "next.gif";
  private static final String IMAGE_PREV = "prev.gif";

  public static void publishEclipseHelp(File rootInFolder, File rootOutFolder, File listOfPagesFile) throws IOException {
    if (!rootInFolder.exists() || !rootInFolder.isDirectory()) {
      throw new IllegalStateException("Folder rootInFolder '" + rootInFolder.getAbsolutePath() + "' not found.");
    }

    if (!listOfPagesFile.exists() || !listOfPagesFile.isFile()) {
      throw new IllegalStateException("File listOfPagesFile '" + listOfPagesFile.getAbsolutePath() + "' not found.");
    }

    List<String> pages = Files.readLines(listOfPagesFile, Charsets.ISO_8859_1);

    List<File> inFiles = new ArrayList<File>();
    for (String p : pages) {
      if (p != null && p.length() > 0) {
        File f = new File(rootInFolder, p);
        if (!f.exists() || !f.isFile()) {
          throw new IllegalStateException("File '" + f.getAbsolutePath() + "' not found.");
        }
        inFiles.add(f);
      }
    }

    OutlineItemEx root = new OutlineItemEx(null, 0, "id", 0, 0, "Eclipse Scout User Guide");
    root.setFilePath(HTML_SUB_PATH + inFiles.get(0).getName());

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Check if all files from the "rootInFolder" are contained in the inFiles list

    List<File> filesInFolder = new ArrayList<File>();
    filesInFolder.addAll(Arrays.asList(rootInFolder.listFiles(new FileFilter() {

      @Override
      public boolean accept(File f) {
        return f.getName().endsWith("html");
      }
    })));

    for (File f : filesInFolder) {
      if (!inFiles.contains(f)) {
        throw new IllegalStateException("File '" + f.getName() + "' is missing in the inFiles list");
      }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    File htmlFolder = new File(rootOutFolder, HTML_SUB_PATH);
    if (htmlFolder.exists()) {
      htmlFolder.delete();
    }

    copyNavImg(htmlFolder);

    Map<Integer, OutlineItemEx> nodeMap = new HashMap<Integer, OutlineItemEx>();
    putNode(nodeMap, root, root.getLevel());
    for (int i = 0; i < inFiles.size(); i++) {
      File inFile = inFiles.get(i);
      File outFile = new File(htmlFolder, inFile.getName());

      String html = Files.toString(inFile, Charsets.ISO_8859_1);
      String filePath = outFile.getAbsolutePath().substring(rootOutFolder.getAbsolutePath().length() + 1).replaceAll("\\\\", "/");
      Document doc = Jsoup.parse(html);
      doc.outputSettings().charset("ASCII");

      if (i > 0) {
        //Next files are taken into account in the outline tree:
        computeOutlineNodes(nodeMap, doc, filePath);
      }

      //Create the navigation section:
      String nextHref = null;
      String nextTitle = null;
      String prevHref = null;
      String prevTitle = null;

      if (i < inFiles.size() - 1) {
        nextHref = inFiles.get(i + 1).getName();
        nextTitle = readAndFindFirstHeader(inFiles.get(i + 1));
      }
      if (i > 0 && inFiles.size() > 0) {
        prevHref = inFiles.get(i - 1).getName();
        prevTitle = readAndFindFirstHeader(inFiles.get(i - 1));
      }
      String baseUri = doc.baseUri();

      String title = findFirstHeader(doc);
      Element tableTop = createNavigationTable(root, title, true, nextHref, prevHref, nextTitle, prevTitle, baseUri);
      doc.body().insertChildren(0, Collections.singleton(tableTop));
      Element tableBottom = createNavigationTable(root, title, false, nextHref, prevHref, nextTitle, prevTitle, baseUri);
      insertBeforeId(doc.body(), "footer", tableBottom);

      //Move and Copy Images:
      PublishUtility.moveAndcopyImages(doc, inFile.getParentFile(), htmlFolder, IMAGES_SUB_PATH);

      //Move and Copy CSS:
      PublishUtility.moveAndcopyCss(doc, inFile.getParentFile(), htmlFolder, "css/");

      //Fix Figure Links:
      PublishUtility.fixListingLink(doc);
      PublishUtility.fixFigureLink(doc);

      //Write as file:
      Files.createParentDirs(outFile);
      Files.write(doc.toString(), outFile, Charsets.ISO_8859_1);
    }

    MarkupToEclipseToc eclipseToc = new MarkupToEclipseToc() {
      @Override
      protected String computeFile(OutlineItem item) {
        if (item instanceof OutlineItemEx && ((OutlineItemEx) item).getFilePath() != null) {
          return ((OutlineItemEx) item).getFilePath();
        }
        return super.computeFile(item);
      }
    };
    eclipseToc.setBookTitle(root.getLabel());
    eclipseToc.setHtmlFile(root.getFilePath());
    String tocContent = eclipseToc.createToc(root);
    File outTocFile = new File(rootOutFolder, "toc.xml");
    Files.write(tocContent, outTocFile, Charsets.ISO_8859_1);
  }

  private static void insertBeforeId(Element container, String id, Element element) {
    for (int i = 0; i < container.childNodeSize(); i++) {
      Node childNode = container.childNode(i);
      if (id.equals(childNode.attr("id"))) {
        container.insertChildren(i, Collections.singleton(element));
        return;
      }
    }
    throw new IllegalStateException("ChildNode with id '" + id + "' not found");
  }

  private static Element createNavigationTable(OutlineItemEx root, String title, boolean isTop, String nextHref, String prevHref, String nextTitle, String prevTitle, String baseUri) {
    Element table = new Element(Tag.valueOf("table"), baseUri);
    table.attr("border", "0");
    table.attr("class", "navigation");
    table.attr("style", "width: 100%;");
    table.attr("summary", "navigation");

    Element prevLinkElement = createLinkElement(prevTitle, prevHref, "Previous", IMAGE_PREV, baseUri);
    Element nextLinkElement = createLinkElement(nextTitle, nextHref, "Next", IMAGE_NEXT, baseUri);

    Element homeLinkElement;
    if (isTop) {
      homeLinkElement = null;
    }
    else {
      homeLinkElement = createLinkElement(root.getLabel(), root.getFilePath().substring(HTML_SUB_PATH.length()), root.getLabel(), IMAGE_HOME, table.baseUri());
    }
    appendNavigationTableTR(table, prevLinkElement, homeLinkElement, nextLinkElement);
    appendNavigationTableTR(table, prevTitle, null, nextTitle);
    return table;
  }

  /**
   * @param title
   * @param href
   * @param imgAlt
   * @param imgSrc
   * @param baseUri
   * @return
   */
  private static Element createLinkElement(String title, String href, String imgAlt, String imgSrc, String baseUri) {
    if (title == null || href == null) {
      return null;
    }
    Element a = new Element(Tag.valueOf("a"), baseUri);
    a.attr("href", href);
    a.attr("shape", "rect");
    a.attr("title", title);
    Element img = new Element(Tag.valueOf("img"), baseUri);
    img.attr("alt", imgAlt);
    img.attr("border", "0");
    img.attr("src", imgSrc);
    a.appendChild(img);
    return a;
  }

  private static void appendNavigationTableTR(Element table, Object leftContent, Object midContent, Object rigthContent) {
    Element tr = new Element(Tag.valueOf("tr"), table.baseUri());
    table.appendChild(tr);
    appendNavigationTableTD(tr, "left", "width: 30%", leftContent);
    appendNavigationTableTD(tr, "center", "width: 40%", midContent);
    appendNavigationTableTD(tr, "right", "width: 30%", rigthContent);
  }

  private static void appendNavigationTableTD(Element tr, String align, String style, Object content) {
    Element td = new Element(Tag.valueOf("td"), tr.baseUri());
    td.attr("align", align);
    td.attr("colspan", "1");
    td.attr("rowspan", "1");
    td.attr("style", style);
    tr.appendChild(td);
    if (content == null) {
      //Do nothing
    }
    else if (content instanceof String) {
      td.text((String) content);
    }
    else if (content instanceof Element) {
      td.appendChild((Node) content);
    }
    else {
      System.err.println("Unexpected content type: " + content);
    }
  }

  private static void computeOutlineNodes(Map<Integer, OutlineItemEx> nodeMap, Document doc, String filePath) {
    Elements elements = doc.getAllElements();
    for (Element element : elements) {
      if (isHeaderTag(element)) {
        String id = findId(element);
        if (id == null) {
          System.err.println("id is not found for node " + element.nodeName() + " '" + element.text() + "'");
        }
        int level = Integer.parseInt(element.nodeName().substring(1));
        String title = sanitize(element.text());
        OutlineItem parent = findParent(nodeMap, level);
        OutlineItemEx node = new OutlineItemEx(parent, level, id, 0, 0, title);
        node.setFilePath(filePath);
        putNode(nodeMap, node, level);
      }
    }
  }

  private static boolean isHeaderTag(Element element) {
    return element.nodeName().matches("h[1-6]");
  }

  private static String sanitize(String text) {
    String result = text;
    result = result.replaceAll("”", "\"");
    return result;
  }

  /**
   * Put the node in
   *
   * @param nodeMap
   *          the map containing the last known node for each level
   * @param node
   *          the node that needs to be added
   * @param level
   *          the level (1 for h1, 2 for h2 ...)
   */
  private static void putNode(Map<Integer, OutlineItemEx> nodeMap, OutlineItemEx node, int level) {
    nodeMap.put(level, node);
  }

  /**
   * Find the parent node given a specific level.
   *
   * @param nodeMap
   *          the map containing the last known node for each level
   * @param level
   *          the level of the current node
   * @return parentNode
   */
  private static OutlineItem findParent(Map<Integer, OutlineItemEx> nodeMap, int level) {
    int i = level - 1;
    while (nodeMap.get(i) == null && i > 0) {
      i = i - 1;
    }
    return nodeMap.get(i);
  }

  /**
   * Find the id of a header tag. id is defined as id attribute of the header, or as id attribute of a nested "a" tag
   *
   * @param element
   *          element corresponding to the HTML header tag (h1, h2, h3, h4, h5 or h6)
   * @return id
   */
  private static String findId(Element element) {
    String id = findIdForElement(element);
    if (id == null) {
      Elements childElements = element.getElementsByTag("a");
      int i = 0;
      while (id == null && i < childElements.size()) {
        Element childElement = childElements.get(i);
        id = findIdForElement(childElement);
        i = i + 1;
      }
    }
    return id;
  }

  /**
   * Find the Id of a given element.
   *
   * @param element
   * @return id
   */
  private static String findIdForElement(Element element) {
    if (element.id() != null && element.id().length() > 0) {
      return element.id();
    }
    return null;
  }

  /**
   * @param htmlFolder
   * @throws IOException
   */
  private static void copyNavImg(File htmlFolder) throws IOException {
    File fromFolder = new File("nav_images");
    File toFolder = new File(htmlFolder, IMAGES_SUB_PATH);
    toFolder.mkdirs();
    Files.copy(new File(fromFolder, IMAGE_HOME), new File(toFolder, IMAGE_HOME));
    Files.copy(new File(fromFolder, IMAGE_NEXT), new File(toFolder, IMAGE_NEXT));
    Files.copy(new File(fromFolder, IMAGE_PREV), new File(toFolder, IMAGE_PREV));
  }

  /**
   * Read the file and apply {@link #findFirstHeader(Document)}
   *
   * @param file
   * @return title or null if not found
   * @throws IOException
   */
  private static String readAndFindFirstHeader(File file) throws IOException {
    String html = Files.toString(file, Charsets.ISO_8859_1);
    Document doc = Jsoup.parse(html);
    return findFirstHeader(doc);
  }

  /**
   * Find the first header (h1, h2, h3, h4, h5 or h6) and returns the text content .
   *
   * @param doc
   *          the html content as JSoup document
   * @return title or null if not found
   */
  private static String findFirstHeader(Document doc) {
    Elements elements = doc.getAllElements();
    for (Element element : elements) {
      if (isHeaderTag(element)) {
        return sanitize(element.text());
      }
    }
    return null;
  }
}
