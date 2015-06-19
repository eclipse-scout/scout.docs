/**
 * 
 */
package org.eclipsescout.contacts.shared;

import java.security.BasicPermission;

/**
 * @author mzi
 */
public class CreateContactPermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public CreateContactPermission() {
    super("CreateContact");
  }
}