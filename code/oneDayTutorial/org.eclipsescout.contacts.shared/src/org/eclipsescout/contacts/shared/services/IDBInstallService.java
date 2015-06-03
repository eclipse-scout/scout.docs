/**
 * 
 */
package org.eclipsescout.contacts.shared.services;

import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;
import org.eclipse.scout.commons.exception.ProcessingException;

/**
 * @author mzi
 */
@InputValidation(IValidationStrategy.PROCESS.class)
public interface IDBInstallService extends IService {

  /**
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  void installStorage() throws ProcessingException;
}
