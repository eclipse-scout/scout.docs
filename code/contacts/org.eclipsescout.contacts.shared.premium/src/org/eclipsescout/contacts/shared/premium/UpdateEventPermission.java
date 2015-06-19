/**
 * 
 */
package org.eclipsescout.contacts.shared.premium;

import java.security.BasicPermission;

/**
 * @author mzi
 */
public class UpdateEventPermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public UpdateEventPermission() {
    super("UpdateEvent");
  }
}