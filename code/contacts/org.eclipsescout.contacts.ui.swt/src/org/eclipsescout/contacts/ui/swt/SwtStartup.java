package org.eclipsescout.contacts.ui.swt;

import org.eclipse.scout.rt.ui.swt.AbstractSwtStartup;
import org.eclipse.scout.rt.ui.swt.ISwtEnvironment;

/**
 * <h3>SwtStartup</h3>
 * The startup class is registered as an extension in the plugin.xml.
 * It is used to be aware that the Workbench is ready.
 * 
 * @see AbstractSwtStartup
 */
public class SwtStartup extends AbstractSwtStartup {
  @Override
  protected ISwtEnvironment getSwtEnvironment() {
    return Activator.getDefault().getEnvironment();
  }
}
