/**
 * 
 */
package org.eclipsescout.contacts.shared.services;

import org.eclipse.scout.service.IService;
import org.eclipse.scout.commons.exception.ProcessingException;

/**
 * @author mzi
 */
public interface IStandardOutlineService extends IService {

  /**
   * @param companyId
   * @return
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  Object[][] getPersonTableData(String companyId) throws ProcessingException;

  /**
   * @return
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  Object[][] getCompanyTableData() throws ProcessingException;
}
