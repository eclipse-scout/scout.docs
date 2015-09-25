/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.server;

import javax.security.auth.Subject;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.contacts.server.ServerConfigProperties.SuperUserSubjectProperty;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.server.context.ServerRunContext;
import org.eclipse.scout.rt.server.context.ServerRunContexts;
import org.eclipse.scout.rt.server.session.ServerSessionProviderWithCache;
import org.eclipse.scout.rt.shared.ui.UserAgent;

@ApplicationScoped
public class SuperUserRunContextProvider {

  private final Subject m_subject = CONFIG.getPropertyValue(SuperUserSubjectProperty.class);

  public ServerRunContext provide() throws ProcessingException {
    ServerRunContext serverRunContext = ServerRunContexts.empty().withUserAgent(UserAgent.createDefault()).withSubject(m_subject);
    return serverRunContext.withSession(BEANS.get(ServerSessionProviderWithCache.class).provide(serverRunContext.copy()));
  }

  public Subject getSubject() {
    return m_subject;
  }
}
