/**
 * 
 */
package org.eclipsescout.contacts.shared.ui.forms;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;

/**
 * @author mzi
 */
@InputValidation(IValidationStrategy.PROCESS.class)
public interface ICompanyService extends IService {

  /**
   * @param formData
   * @return
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  CompanyFormData create(CompanyFormData formData) throws ProcessingException;

  /**
   * @param formData
   * @return
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  CompanyFormData load(CompanyFormData formData) throws ProcessingException;

  /**
   * @param formData
   * @return
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  CompanyFormData prepareCreate(CompanyFormData formData) throws ProcessingException;

  /**
   * @param formData
   * @return
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  CompanyFormData store(CompanyFormData formData) throws ProcessingException;
}
