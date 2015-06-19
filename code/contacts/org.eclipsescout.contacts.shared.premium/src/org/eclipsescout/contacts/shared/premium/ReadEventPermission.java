/**
 * 
 */
package org.eclipsescout.contacts.shared.premium;

import java.security.BasicPermission;

/**
 * @author mzi
 */
public class ReadEventPermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public ReadEventPermission() {
    super("ReadEvent");
  }
}