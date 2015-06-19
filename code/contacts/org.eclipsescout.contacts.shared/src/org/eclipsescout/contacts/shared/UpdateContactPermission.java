/**
 * 
 */
package org.eclipsescout.contacts.shared;

import java.security.BasicPermission;

/**
 * @author mzi
 */
public class UpdateContactPermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public UpdateContactPermission() {
    super("UpdateContact");
  }
}