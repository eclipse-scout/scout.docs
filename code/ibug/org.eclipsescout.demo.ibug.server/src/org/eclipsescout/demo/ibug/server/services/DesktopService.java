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
package org.eclipsescout.demo.ibug.server.services;

import java.util.List;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.service.AbstractService;
import org.eclipsescout.demo.ibug.BugzillaHtmlFetcher;
import org.eclipsescout.demo.ibug.IBug;
import org.eclipsescout.demo.ibug.IBugFetcher;
import org.eclipsescout.demo.ibug.shared.services.DesktopFormData;
import org.eclipsescout.demo.ibug.shared.services.DesktopFormData.Bugs;
import org.eclipsescout.demo.ibug.shared.services.IDesktopService;

public class DesktopService extends AbstractService implements IDesktopService {

  private static final IScoutLogger LOG = ScoutLogManager.getLogger(DesktopService.class);

  private IBugFetcher m_bugFetcher = null;

  public DesktopService() {
    super();
    m_bugFetcher = new BugzillaHtmlFetcher();
  }

  public String getCriteria() {
    return m_bugFetcher.getQueryCriteria();
  }

  public int getMaxNumberOfBugs() {
    return m_bugFetcher.getMaxNumberOfBugs();
  }

  public String getAssignee() {
    return m_bugFetcher.getAssignee();
  }

  public void setCriteria(String criteria) {
    m_bugFetcher.setQueryCriteria(criteria);
  }

  public void setMaxNumberOfBugs(int bugs) {
    m_bugFetcher.setMaxNumberOfBugs(bugs);
  }

  @Override
  public DesktopFormData load(DesktopFormData formData) throws ProcessingException {

    verifySearchCriteria(formData, m_bugFetcher, "Scout");

    List<IBug> bugs = m_bugFetcher.fetchBugs();

    Bugs table = formData.getBugs();
    table.clearRows();

    for (IBug bug : bugs) {
      LOG.info("adding bug " + bug);

      int row = table.addRow();
      table.setID(row, bug.getId());
      table.setSummary(row, bug.getSummary());
      table.setLastChanged(row, bug.getChanged());
      table.setSeverety(row, bug.getSeverety());
      table.setPriority(row, bug.getPriority());
      table.setTargetMilestone(row, bug.getTargetMilestone());
      table.setStatus(row, bug.getStatus());
      table.setResolution(row, bug.getResolution());
      table.setComponent(row, bug.getComponent());
      table.setAssignee(row, bug.getAssignee());
      table.setSortValue(row, bug.getSortValue());
    }

    return formData;
  }

  private void verifySearchCriteria(DesktopFormData formData, IBugFetcher bugFetcher, String defaultProduct) {
    String assignee = formData.getAssignee().getValue();
    String product = formData.getProduct().getValue();

    if (StringUtility.isNullOrEmpty(assignee) && StringUtility.isNullOrEmpty(product)) {
      LOG.info("no assignee or product provided, setting product to '" + defaultProduct + "'");
      formData.getProduct().setValue(defaultProduct);
      product = defaultProduct;
    }

    bugFetcher.setAssignee(assignee);
    bugFetcher.setProduct(product);
  }
}
