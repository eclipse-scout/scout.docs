/**
 * 
 */
package org.eclipsescout.contacts.shared;

import java.security.BasicPermission;

/**
 * @author mzi
 */
public class CreateCompanyPermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public CreateCompanyPermission() {
    super("CreateCompany");
  }
}