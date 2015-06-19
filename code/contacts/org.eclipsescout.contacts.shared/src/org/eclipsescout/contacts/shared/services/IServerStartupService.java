/**
 *
 */
package org.eclipsescout.contacts.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;

/**
 * Service to do application specific installation tasks for the server side at startup time.
 * The ServerApplication calls all implementing services.
 * 
 * @author mzi
 */
@InputValidation(IValidationStrategy.PROCESS.class)
public interface IServerStartupService extends IService {

  /**
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  void init() throws ProcessingException;
}
