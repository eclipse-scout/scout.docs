package org.eclipsescout.contacts.ui.swing;

import java.security.PrivilegedExceptionAction;

import javax.security.auth.Subject;

import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.commons.security.SimplePrincipal;
import org.eclipse.scout.net.NetActivator;
import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.services.common.session.IClientSessionRegistryService;
import org.eclipse.scout.rt.ui.swing.AbstractSwingApplication;
import org.eclipse.scout.rt.ui.swing.ISwingEnvironment;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.contacts.client.ClientSession;

public class SwingApplication extends AbstractSwingApplication {
  private static final IScoutLogger LOG = ScoutLogManager.getLogger(SwingApplication.class);

  @Override
  public Object start(final IApplicationContext context) throws Exception {
    Subject subject = new Subject();
    subject.getPrincipals().add(new SimplePrincipal(System.getProperty("user.name")));
    return Subject.doAs(subject, new PrivilegedExceptionAction<Object>() {
      @Override
      public Object run() throws Exception {
        return startSecure(context);
      }
    });
  }

  @Override
  protected ISwingEnvironment createSwingEnvironment() {
    return new SwingEnvironment();
  }

  private Object startSecure(IApplicationContext context) throws Exception {
    try {
      NetActivator.install();
    }
    catch (Throwable t) {
      // no net handler found
      LOG.warn("NetActivator is not available", t);
    }
    return super.start(context);
  }

  @Override
  protected IClientSession getClientSession() {
    return SERVICES.getService(IClientSessionRegistryService.class).newClientSession(ClientSession.class, initUserAgent());
  }
}
