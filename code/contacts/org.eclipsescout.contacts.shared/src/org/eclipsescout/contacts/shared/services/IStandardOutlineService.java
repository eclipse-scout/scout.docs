/**
 *
 */
package org.eclipsescout.contacts.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.service.IService;
import org.eclipsescout.contacts.shared.ui.desktop.outlines.CompanyTablePageData;
import org.eclipsescout.contacts.shared.ui.desktop.outlines.ContactsTablePageData;

/**
 * @author mzi
 */
public interface IStandardOutlineService extends IService {

  /**
   * @param filter
   * @return
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  ContactsTablePageData getContactsTableData(SearchFilter filter, String pageCompanyId) throws ProcessingException;

  /**
   * @return
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  CompanyTablePageData getCompanyTableData(SearchFilter filter) throws ProcessingException;
}
