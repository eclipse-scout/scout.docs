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
package org.eclipse.scout.contacts.client.organization;

import java.util.List;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.contacts.client.person.PersonTablePage;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.shared.TEXTS;

public class OrganizationDetailsNodePage extends AbstractPageWithNodes {

  private String m_organizationId;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("OrganizationDetails");
  }

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    PersonTablePage personTablePage = new PersonTablePage();
    personTablePage.setOrganizationId(getOrganizationId());
    pageList.add(personTablePage);

  }

  @FormData
  public String getOrganizationId() {
    return m_organizationId;
  }

  @FormData
  public void setOrganizationId(String organizationId) {
    m_organizationId = organizationId;
  }
}
