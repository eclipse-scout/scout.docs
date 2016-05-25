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
package org.eclipse.scout.contacts.server.sql;

import java.util.concurrent.Callable;

import javax.annotation.PostConstruct;
import javax.security.auth.Subject;

import org.eclipse.scout.contacts.server.sql.DatabaseProperties.SuperUserSubjectProperty;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.IPlatform.State;
import org.eclipse.scout.rt.platform.IPlatformListener;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.PlatformEvent;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.context.RunMonitor;
import org.eclipse.scout.rt.platform.exception.PlatformException;
import org.eclipse.scout.rt.platform.util.FinalValue;
import org.eclipse.scout.rt.server.IServerSession;
import org.eclipse.scout.rt.server.context.ServerRunContext;
import org.eclipse.scout.rt.server.context.ServerRunContextProducer;
import org.eclipse.scout.rt.server.context.ServerRunContexts;
import org.eclipse.scout.rt.server.session.ServerSessionProvider;
import org.eclipse.scout.rt.shared.ui.UserAgents;

/**
 * Central point to obtain run contexts with super user rights, and to get the super user subject and session.
 * <p>
 * A super user run context is a {@link ServerRunContext} with a user that has administrator privileges.
 */
public class SuperUserRunContextProducer extends ServerRunContextProducer {

  private final FinalValue<IServerSession> session = new FinalValue<>();
  private final FinalValue<Subject> subject = new FinalValue<>();

  @PostConstruct
  protected void initSuperUserSubject() {
    subject.set(CONFIG.getPropertyValue(SuperUserSubjectProperty.class));
  }

  /**
   * Produces a new {@link ServerRunContext} with super user rights.
   * <p>
   * This method delegates to {@link #produce()}, meaning that the subject specified is ignored.
   *
   * @param subject
   *          is ignored, and {@link SuperUserRunContextProducer#getSubject()} used instead.
   * @see #produce()
   */
  @Override
  public final ServerRunContext produce(final Subject inputSubject) {
    return produce();
  }

  /**
   * Produces a new {@link ServerRunContext} with super user rights.
   * <p>
   * This implies a new {@link ServerRunContext} initialized with the superuser's {@link Subject}, with a dedicated
   * {@link RunMonitor} set, and the superuser's shared {@link IServerSession} set.
   */
  public ServerRunContext produce() {
    final ServerRunContext superUserRunContext = ServerRunContexts.empty()
        .withRunMonitor(BEANS.get(RunMonitor.class))
        .withUserAgent(UserAgents.createDefault())
        .withSubject(subject.get());

    return superUserRunContext
        .withSession(session.setIfAbsent(new Callable<IServerSession>() {

          @Override
          public IServerSession call() throws Exception {
            return createServerSession(superUserRunContext);
          }
        }));
  }

  /**
   * Returns the superuser's {@link Subject}; is not <code>null</code>.
   */
  public Subject getSubject() {
    return subject.get();
  }

  /**
   * Returns the superuser's {@link IServerSession}; is not <code>null</code>.
   */
  public IServerSession getSession() {
    return produce().getSession(); // via provide(), because the session is created on first usage.
  }

  /**
   * Method invoked to create the superuser's {@link IServerSession} with data as specified by the given
   * {@link ServerRunContext}.
   */
  protected IServerSession createServerSession(final ServerRunContext serverRunContext) {
    return BEANS.get(ServerSessionProvider.class).provide(serverRunContext.copy());
  }

  /**
   * It is important that this platform listener is the last platform listener invoked because it is stopping the
   * superuser session (thus having highest possible order).
   * <p>
   * Stopping the session when the platform stated is stopped (instead of stopping) isn't going to work because the
   * BEANS.get (and others) cannot be used anymore when that platform is stopped.
   */
  @Order(Long.MAX_VALUE)
  public static class PlatformListener implements IPlatformListener {

    @Override
    public void stateChanged(final PlatformEvent event) throws PlatformException {
      if (event.getState() == State.PlatformStopping) {
        final IServerSession serverSession = BEANS.get(SuperUserRunContextProducer.class).getSession();
        if (serverSession != null) {
          serverSession.stop();
        }
      }
    }
  }
}
