/**
 * 
 */
package org.eclipsescout.contacts.shared.ui.forms;

import java.security.BasicPermission;

/**
 * @author mzi
 */
public class ReadPersonPermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  /**
 * 
 */
  public ReadPersonPermission() {
    super("ReadPerson");
  }
}
