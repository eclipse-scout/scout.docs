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
package org.eclipsescout.demo.ibug;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author mzi
 */
public class BugzillaHtmlFetcher implements IBugFetcher {

  private static final IScoutLogger LOG = ScoutLogManager.getLogger(BugzillaHtmlFetcher.class);

  private String m_criteria = null;
  private String m_assignee = null;
  private String m_product = null;
  private int m_maxNumberOfBugs = 5;

  @Override
  public void setQueryCriteria(String criteria) {
    m_criteria = criteria;
  }

  @Override
  public String getQueryCriteria() {
    return m_criteria;
  }

  @Override
  public void setAssignee(String assignee) {
    m_assignee = assignee;
  }

  @Override
  public String getAssignee() {
    return m_assignee;
  }

  @Override
  public void setProduct(String product) {
    m_product = product;
  }

  @Override
  public String getProduct() {
    return m_product;
  }

  @Override
  public void setMaxNumberOfBugs(int bugs) {
    m_maxNumberOfBugs = bugs;
  }

  @Override
  public int getMaxNumberOfBugs() {
    return m_maxNumberOfBugs;
  }

  @Override
  public List<IBug> fetchBugs() throws ProcessingException {
    List<IBug> bugs = new ArrayList<IBug>();

    String url = buildQueryUrl();
    Element bugTable = getBugTableElement(url, 10000);
    Elements bugElements = getBugElements(bugTable);

    int i = 0;
    for (Element e : bugElements) {
      if (i++ >= getMaxNumberOfBugs()) break;
      bugs.add(createBugFromElement(e, i));
    }

    return bugs;
  }

  private String buildQueryUrl() {
    String url = getQueryCriteria();
    String assignee = StringUtility.nvl(getAssignee(), "").trim();
    String product = StringUtility.nvl(getProduct(), "").trim();

    if (assignee.length() > 0) {
      LOG.info("assignee='" + assignee + "'");
      url += "&emailtype1=substring&emailassigned_to1=1&email1=" + assignee;
    }

    if (product.length() > 0) {
      LOG.info("product='" + product + "'");
      url += "&product=" + product;
    }

    LOG.info("using query url='" + url + "'");

    return url;
  }

  private Element getBugTableElement(String url, int timeout) throws ProcessingException {
    Document doc = null;

    if (StringUtility.isNullOrEmpty(url)) {
      throw new ProcessingException("No bugzilla base query url provided, check your config.ini file");
    }

    try {
      doc = Jsoup.parse(new URL(url), timeout);
      if (doc == null) throw new ProcessingException("Empty document received, check your connection");
    }
    catch (Exception e) {
      throw new ProcessingException("Exception ", "check your connection and url: '" + url + "'", e);
    }

    return doc.getElementsByClass("bz_buglist").first();
  }

  private Elements getBugElements(Element bugTable) {
    if (bugTable == null) return new Elements();
    else return bugTable.getElementsByClass("bz_bugitem");
  }

  /**
   * columns are parsed out or bugzilla html response.
   * this implies that column list provided in the criteria url and the parsing implemented in this method must
   * correspond.
   * see the config.ini entry
   * org.eclipsescout.demo.ibug.server.services.DesktopService#criteria=https://bugs.eclipse.org/bugs/...
   */
  private IBug createBugFromElement(Element e, int i) {
    IBug bug = new BugzillaBug();
    Elements columnElements = e.getElementsByTag("td");

    bug.setId(getColumnTextContent(columnElements, 0));
    bug.setSeverety(getColumnTextContent(columnElements, 1));
    bug.setPriority(getColumnTextContent(columnElements, 2));
    bug.setTargetMilestone(getColumnTextContent(columnElements, 3));
    bug.setStatus(getColumnTextContent(columnElements, 4));
    bug.setResolution(getColumnTextContent(columnElements, 5));
    bug.setComponent(getColumnTextContent(columnElements, 6));
    bug.setAssignee(getColumnTextContent(columnElements, 7));
    bug.setSummary(getColumnTextContent(columnElements, 8));
    bug.setChanged(getColumnTextContent(columnElements, 9));
    bug.setSortValue(i);

    return bug;
  }

  private String getColumnTextContent(Elements rowElements, int i) {
    Element content = rowElements.get(i);
    Element a = content.getElementsByTag("a").first();
    Element span = content.getElementsByTag("span").first();
    StringBuffer text = new StringBuffer();

    text.append(content.ownText());
    if (a != null) text.append(a.ownText());
    if (span != null) text.append(span.ownText());

    return text.toString();
  }

}
