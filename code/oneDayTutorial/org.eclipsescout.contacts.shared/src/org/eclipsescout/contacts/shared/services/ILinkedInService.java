/**
 *
 */
package org.eclipsescout.contacts.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;

/**
 * @author mzi
 */
@InputValidation(IValidationStrategy.PROCESS.class)
public interface ILinkedInService extends IService {

  /**
   * @return
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  String getAuthUrl() throws ProcessingException;

  /**
   * @param token
   * @param secret
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  void refreshToken(String securityCode) throws ProcessingException;

  /**
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  void updateContacts() throws ProcessingException;
}
