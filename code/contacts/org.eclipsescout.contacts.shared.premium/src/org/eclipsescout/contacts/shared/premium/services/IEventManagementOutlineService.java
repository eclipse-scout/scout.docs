/**
 *
 */
package org.eclipsescout.contacts.shared.premium.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;
import org.eclipsescout.contacts.shared.premium.ui.desktop.outlines.EventsTablePageData;

/**
 * @author mzi
 */
@InputValidation(IValidationStrategy.PROCESS.class)
public interface IEventManagementOutlineService extends IService {

  /**
   * @param filter
   * @param pageCompanyId
   * @return
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  EventsTablePageData getEventsTableData(SearchFilter filter, String companyId) throws ProcessingException;
}
