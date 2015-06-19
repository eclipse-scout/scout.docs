/**
 *
 */
package org.eclipsescout.contacts.client.ui.desktop.outlines;

import java.util.List;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.extension.client.ui.desktop.outline.pages.AbstractExtensiblePageWithNodes;
import org.eclipse.scout.rt.shared.TEXTS;

/**
 * @author mzi
 */
public class CompanyDetailsNodePage extends AbstractExtensiblePageWithNodes {

  private String m_companyId;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("CompanyDetails");
  }

  @Override
  protected void execCreateChildPages(List<IPage> pageList) throws ProcessingException {
    ContactsTablePage contactsTablePage = new ContactsTablePage();
    contactsTablePage.setCompanyId(getCompanyId());
    pageList.add(contactsTablePage);

  }

  /**
   * @return the CompanyId
   */
  @FormData
  public String getCompanyId() {
    return m_companyId;
  }

  /**
   * @param companyId
   *          the CompanyId to set
   */
  @FormData
  public void setCompanyId(String companyId) {
    m_companyId = companyId;
  }
}
