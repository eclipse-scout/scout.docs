package org.eclipsescout.contacts.ui.swt.application;

import java.security.PrivilegedExceptionAction;

import javax.security.auth.Subject;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.scout.commons.security.SimplePrincipal;
import org.eclipse.scout.net.NetActivator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

/**
 * <h3>Activator</h3>
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {
  private Display m_display;

  @Override
  public Object start(final IApplicationContext context) throws Exception {
    m_display = getApplicationDisplay();
    try {
      Subject subject = new Subject();
      subject.getPrincipals().add(new SimplePrincipal(System.getProperty("user.name")));
      return Subject.doAs(subject, new PrivilegedExceptionAction<Object>() {
        @Override
        public Object run() throws Exception {
          return startSecure(context);
        }
      });
    }
    finally {
      if (m_display != null) {
        m_display.dispose();
      }
    }
  }

  public Integer startSecure(final IApplicationContext context) throws Exception {
    Display display = PlatformUI.createDisplay();
    NetActivator.install();
    if (PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor()) == PlatformUI.RETURN_RESTART) {
      return EXIT_RESTART;
    }
    return EXIT_OK;
  }

  @Override
  public void stop() {
    final IWorkbench workbench = PlatformUI.getWorkbench();
    if (workbench == null) return;
    final Display display = workbench.getDisplay();
    display.syncExec(new Runnable() {
      @Override
      public void run() {
        if (!display.isDisposed()) workbench.close();
      }
    });
  }

  public Display getApplicationDisplay() {
    if (m_display == null) {
      m_display = Display.getDefault();
    }
    return m_display;
  }
}
