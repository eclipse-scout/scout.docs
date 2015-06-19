/**
 * 
 */
package org.eclipsescout.contacts.shared;

import java.security.BasicPermission;

/**
 * @author mzi
 */
public class UpdatePictureURLPermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public UpdatePictureURLPermission() {
    super("UpdatePictureURL");
  }
}