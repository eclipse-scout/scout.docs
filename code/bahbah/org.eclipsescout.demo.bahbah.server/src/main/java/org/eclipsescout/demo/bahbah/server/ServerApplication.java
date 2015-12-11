/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipsescout.demo.bahbah.server;

import javax.security.auth.Subject;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.IPlatform;
import org.eclipse.scout.rt.platform.IPlatformListener;
import org.eclipse.scout.rt.platform.PlatformEvent;
import org.eclipse.scout.rt.platform.exception.DefaultExceptionTranslator;
import org.eclipse.scout.rt.platform.exception.PlatformException;
import org.eclipse.scout.rt.platform.security.SimplePrincipal;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.server.context.ServerRunContext;
import org.eclipse.scout.rt.server.context.ServerRunContexts;
import org.eclipse.scout.rt.server.services.common.clustersync.IClusterSynchronizationService;
import org.eclipse.scout.rt.server.session.ServerSessionProviderWithCache;
import org.eclipsescout.demo.bahbah.server.services.db.IDbSetupService;
import org.eclipsescout.demo.bahbah.server.services.notification.RegisterUserNotificationListener;
import org.eclipsescout.demo.bahbah.server.services.notification.UnregisterUserNotificationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Bean
public class ServerApplication implements IPlatformListener {
  private static Logger LOG = LoggerFactory.getLogger(ServerApplication.class);

  public static Subject s_subject;

  static {
    s_subject = new Subject();
    s_subject.getPrincipals().add(new SimplePrincipal("bahbah"));
    s_subject.setReadOnly();
  }

  @Override
  public void stateChanged(PlatformEvent event) throws PlatformException {
    if (event.getState() == IPlatform.State.PlatformStarted) {
      try {
        ServerRunContext runContext = ServerRunContexts.empty();
        runContext.withSubject(s_subject);
        runContext.withSession(BEANS.get(ServerSessionProviderWithCache.class).provide(runContext.copy()));
        runContext.run(new IRunnable() {
          @Override
          public void run() throws Exception {
            BEANS.get(IDbSetupService.class).installDb();
            BEANS.get(IClusterSynchronizationService.class).addListener(new RegisterUserNotificationListener());
            BEANS.get(IClusterSynchronizationService.class).addListener(new UnregisterUserNotificationListener());
          }
        }, DefaultExceptionTranslator.class);
      }
      catch (Exception e) {
        throw new PlatformException("Unable to start server application.", e);
      }

      LOG.info("bahbah server initialized");
    }
  }

  public static Subject getSubject() {
    return s_subject;
  }
}
