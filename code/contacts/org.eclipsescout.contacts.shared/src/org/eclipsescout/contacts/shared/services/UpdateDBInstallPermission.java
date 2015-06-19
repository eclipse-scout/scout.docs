/**
 * 
 */
package org.eclipsescout.contacts.shared.services;

import java.security.BasicPermission;

/**
 * @author mzi
 */
public class UpdateDBInstallPermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public UpdateDBInstallPermission() {
    super("UpdateDBInstall");
  }
}