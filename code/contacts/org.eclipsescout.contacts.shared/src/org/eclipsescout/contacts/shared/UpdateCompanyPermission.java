/**
 * 
 */
package org.eclipsescout.contacts.shared;

import java.security.BasicPermission;

/**
 * @author mzi
 */
public class UpdateCompanyPermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public UpdateCompanyPermission() {
    super("UpdateCompany");
  }
}