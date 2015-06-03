/**
 * 
 */
package org.eclipsescout.contacts.shared.services;

import java.security.BasicPermission;

/**
 * @author mzi
 */
public class ReadLinkedInPermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  /**
 * 
 */
  public ReadLinkedInPermission() {
    super("ReadLinkedIn");
  }
}
