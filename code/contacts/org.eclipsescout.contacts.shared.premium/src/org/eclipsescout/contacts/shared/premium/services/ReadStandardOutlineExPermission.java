/**
 * 
 */
package org.eclipsescout.contacts.shared.premium.services;

import java.security.BasicPermission;

/**
 * @author mzi
 */
public class ReadStandardOutlineExPermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public ReadStandardOutlineExPermission() {
    super("ReadStandardOutlineEx");
  }
}