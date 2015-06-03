/**
 * 
 */
package org.eclipsescout.contacts.shared.ui.forms;

import java.security.BasicPermission;

/**
 * @author mzi
 */
public class ReadCompanyPermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  /**
 * 
 */
  public ReadCompanyPermission() {
    super("ReadCompany");
  }
}
