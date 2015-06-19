/**
 *
 */
package org.eclipsescout.contacts.client.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.IService;

/**
 * Service to do application specific installation tasks on the client side at startup time.
 * In method execLoadSession of the client session all services implementing this interface are called.
 * This mechanism may be used for registration of ui extensions implemented in various client modules.
 * 
 * @author mzi
 */
public interface IClientStartupService extends IService {
  void init() throws ProcessingException;
}
