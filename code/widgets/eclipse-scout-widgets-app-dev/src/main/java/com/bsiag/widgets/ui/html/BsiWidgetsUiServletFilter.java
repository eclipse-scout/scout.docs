package com.bsiag.widgets.ui.html; /*******************************************************************************
 * Copyright (c) 2010-2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the BSI CRM Software License v1.0
 * which accompanies this distribution as bsi-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.security.ConfigFileCredentialVerifier;
import org.eclipse.scout.rt.server.commons.authentication.AnonymousAccessController;
import org.eclipse.scout.rt.server.commons.authentication.CookieAccessController;
import org.eclipse.scout.rt.server.commons.authentication.DevelopmentAccessController;
import org.eclipse.scout.rt.server.commons.authentication.FormBasedAccessController;
import org.eclipse.scout.rt.server.commons.authentication.FormBasedAccessController.FormBasedAuthConfig;
import org.eclipse.scout.rt.server.commons.authentication.ServletFilterHelper;
import org.eclipse.scout.rt.server.commons.authentication.TrivialAccessController;
import org.eclipse.scout.rt.server.commons.authentication.TrivialAccessController.TrivialAuthConfig;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Frontend servlet filter to protected resources from unauthenticated access.
 *
 * @since 5.1
 */
public class BsiWidgetsUiServletFilter implements Filter {

  private CookieAccessController m_cookieAccessController;
  private TrivialAccessController m_trivialAccessController;
  private FormBasedAccessController m_formBasedAccessController;
  private DevelopmentAccessController m_developmentAccessController;
  private AnonymousAccessController m_anonymousAccessController;

  @Override
  public void init(final FilterConfig filterConfig) throws ServletException {
    m_cookieAccessController = BEANS.get(CookieAccessController.class);
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
  public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
    final HttpServletRequest req = (HttpServletRequest) request;
    final HttpServletResponse resp = (HttpServletResponse) response;

    if (m_cookieAccessController.handle(req, resp, chain)) {
      return;
    }

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
    m_anonymousAccessController.destroy();
    m_developmentAccessController.destroy();
    m_formBasedAccessController.destroy();
    m_trivialAccessController.destroy();
    m_cookieAccessController.destroy();
  }
}
