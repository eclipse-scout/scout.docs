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

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.server.commons.servlet.filter.authentication.DevelopmentAuthenticator;
import org.eclipse.scout.rt.server.commons.servlet.filter.authentication.ServiceTunnelAccessTokenAuthenticator;
import org.eclipse.scout.rt.server.commons.servlet.filter.authentication.TrivialAuthenticator;
import org.eclipse.scout.rt.server.commons.servlet.filter.authentication.TrivialAuthenticator.TrivialAuthConfig;

/**
 * This is the main server side servlet filter.
 */
public class ServletFilter implements Filter {

  private TrivialAuthenticator m_trivialAuthenticator;
  private ServiceTunnelAccessTokenAuthenticator m_tunnelTokenAuthenticator;
  private DevelopmentAuthenticator m_devAuthenticator;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    m_trivialAuthenticator = BEANS.get(TrivialAuthenticator.class);
    m_tunnelTokenAuthenticator = BEANS.get(ServiceTunnelAccessTokenAuthenticator.class);
    m_devAuthenticator = BEANS.get(DevelopmentAuthenticator.class);

    m_trivialAuthenticator.init(new TrivialAuthConfig().withExclusionFilter(filterConfig.getInitParameter("filter-exclude")));
    m_tunnelTokenAuthenticator.init();
    m_devAuthenticator.init();
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    final HttpServletRequest req = (HttpServletRequest) request;
    final HttpServletResponse resp = (HttpServletResponse) response;

    if (m_trivialAuthenticator.handle(req, resp, chain)) {
      return;
    }

    if (m_tunnelTokenAuthenticator.handle(req, resp, chain)) {
      return;
    }

    if (m_devAuthenticator.handle(req, resp, chain)) {
      return;
    }

    resp.sendError(HttpServletResponse.SC_FORBIDDEN);
  }

  @Override
  public void destroy() {
    m_trivialAuthenticator.destroy();
    m_tunnelTokenAuthenticator.destroy();
    m_devAuthenticator.destroy();
  }
}
