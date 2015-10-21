/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.client.company;

import java.util.List;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.contacts.client.contact.ContactTablePage;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.shared.TEXTS;

public class CompanyDetailsNodePage extends AbstractPageWithNodes {

  private String m_companyId;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("CompanyDetails");
  }

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) throws ProcessingException {
    ContactTablePage contactsTablePage = new ContactTablePage();
    contactsTablePage.setCompanyId(getCompanyId());
    pageList.add(contactsTablePage);

  }

  @FormData
  public String getCompanyId() {
    return m_companyId;
  }

  @FormData
  public void setCompanyId(String companyId) {
    m_companyId = companyId;
  }
}
