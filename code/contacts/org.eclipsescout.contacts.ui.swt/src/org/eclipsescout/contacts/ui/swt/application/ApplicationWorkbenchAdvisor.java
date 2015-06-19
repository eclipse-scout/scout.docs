package org.eclipsescout.contacts.ui.swt.application;

import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipsescout.contacts.ui.swt.perspective.Perspective;

/**
 * <h3>ApplicationWorkbenchAdvisor</h3>
 * Used for getting the initial perspective.
 */
public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

  @Override
  public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
    return new ApplicationWorkbenchWindowAdvisor(configurer);
  }

  @Override
  public String getInitialWindowPerspectiveId() {
    return Perspective.ID;
  }

  @Override
  public void initialize(IWorkbenchConfigurer configurer) {
    super.initialize(configurer);
    configurer.setExitOnLastWindowClose(true);
    configurer.setSaveAndRestore(false);
  }
}
