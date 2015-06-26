/**
 * 
 */
package org.eclipsescout.contacts.shared.services;

import java.security.BasicPermission;

/**
 * @author mzi
 */
public class CreateLinkedInPermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  /**
 * 
 */
  public CreateLinkedInPermission() {
    super("CreateLinkedIn");
  }
}
