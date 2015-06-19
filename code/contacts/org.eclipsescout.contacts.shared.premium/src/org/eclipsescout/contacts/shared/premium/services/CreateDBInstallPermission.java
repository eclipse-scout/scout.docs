/**
 * 
 */
package org.eclipsescout.contacts.shared.premium.services;

import java.security.BasicPermission;

/**
 * @author mzi
 */
public class CreateDBInstallPermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public CreateDBInstallPermission() {
    super("CreateDBInstall");
  }
}