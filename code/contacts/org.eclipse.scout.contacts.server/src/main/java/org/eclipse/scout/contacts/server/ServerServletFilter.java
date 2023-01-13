/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
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
import org.eclipse.scout.rt.server.commons.authentication.DevelopmentAccessController;
import org.eclipse.scout.rt.server.commons.authentication.ServiceTunnelAccessTokenAccessController;
import org.eclipse.scout.rt.server.commons.authentication.TrivialAccessController;
import org.eclipse.scout.rt.server.commons.authentication.TrivialAccessController.TrivialAuthConfig;

/**
 * Backend servlet filter to protected resources from unauthenticated access.
 *
 * @since 5.1
 */
public class ServerServletFilter implements Filter {

  private TrivialAccessController trivialAccessController;
  private ServiceTunnelAccessTokenAccessController tunnelAccessController;
  private DevelopmentAccessController developmentAccessController;

  @Override
  public void init(final FilterConfig filterConfig) throws ServletException {
    trivialAccessController = BEANS.get(TrivialAccessController.class).init(new TrivialAuthConfig().withExclusionFilter(filterConfig.getInitParameter("filter-exclude")));
    tunnelAccessController = BEANS.get(ServiceTunnelAccessTokenAccessController.class).init();
    developmentAccessController = BEANS.get(DevelopmentAccessController.class).init();
  }

  @Override
  public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
    final HttpServletRequest req = (HttpServletRequest) request;
    final HttpServletResponse resp = (HttpServletResponse) response;

    if (trivialAccessController.handle(req, resp, chain)) {
      return;
    }

    if (tunnelAccessController.handle(req, resp, chain)) {
      return;
    }

    if (developmentAccessController.handle(req, resp, chain)) {
      return;
    }

    resp.sendError(HttpServletResponse.SC_FORBIDDEN);
  }

  @Override
  public void destroy() {
    developmentAccessController.destroy();
    tunnelAccessController.destroy();
    trivialAccessController.destroy();
  }
}
