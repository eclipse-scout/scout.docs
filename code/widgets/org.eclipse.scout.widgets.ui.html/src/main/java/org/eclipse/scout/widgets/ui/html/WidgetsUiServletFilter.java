/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.ui.html;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.security.ConfigFileCredentialVerifier;
import org.eclipse.scout.rt.server.commons.authentication.AnonymousAccessController;
import org.eclipse.scout.rt.server.commons.authentication.DevelopmentAccessController;
import org.eclipse.scout.rt.server.commons.authentication.FormBasedAccessController;
import org.eclipse.scout.rt.server.commons.authentication.FormBasedAccessController.FormBasedAuthConfig;
import org.eclipse.scout.rt.server.commons.authentication.ServletFilterHelper;
import org.eclipse.scout.rt.server.commons.authentication.TrivialAccessController;
import org.eclipse.scout.rt.server.commons.authentication.TrivialAccessController.TrivialAuthConfig;

/**
 * Frontend servlet filter to protected resources from unauthenticated access.
 *
 * @since 5.1
 */
public class WidgetsUiServletFilter implements Filter {

  private TrivialAccessController m_trivialAccessController;
  private FormBasedAccessController m_formBasedAccessController;
  private DevelopmentAccessController m_developmentAccessController;
  private AnonymousAccessController m_anonymousAccessController;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    m_trivialAccessController = BEANS.get(TrivialAccessController.class)
        .init(new TrivialAuthConfig()
            .withExclusionFilter(filterConfig.getInitParameter("filter-exclude"))
            .withLoginPageInstalled(true));
    m_formBasedAccessController = BEANS.get(FormBasedAccessController.class)
        .init(new FormBasedAuthConfig()
            .withCredentialVerifier(BEANS.get(ConfigFileCredentialVerifier.class)));
    m_developmentAccessController = BEANS.get(DevelopmentAccessController.class).init();
    m_anonymousAccessController = BEANS.get(AnonymousAccessController.class).init();
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    final HttpServletRequest req = (HttpServletRequest) request;
    final HttpServletResponse resp = (HttpServletResponse) response;

    if (m_trivialAccessController.handle(req, resp, chain)) {
      return;
    }

    if (m_formBasedAccessController.handle(req, resp, chain)) {
      return;
    }

    if (m_developmentAccessController.handle(req, resp, chain)) {
      return;
    }

    if (m_anonymousAccessController.handle(req, resp, chain)) {
      return;
    }

    BEANS.get(ServletFilterHelper.class).forwardToLoginForm(req, resp);
  }

  @Override
  public void destroy() {
    m_developmentAccessController.destroy();
    m_formBasedAccessController.destroy();
    m_trivialAccessController.destroy();
  }
}
