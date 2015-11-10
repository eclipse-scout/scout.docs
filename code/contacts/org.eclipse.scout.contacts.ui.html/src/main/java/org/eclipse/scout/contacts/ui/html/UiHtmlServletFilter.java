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
package org.eclipse.scout.contacts.ui.html;

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
import org.eclipse.scout.rt.server.commons.authentication.ConfigFileCredentialVerifier;
import org.eclipse.scout.rt.server.commons.authentication.FormAuthenticator;
import org.eclipse.scout.rt.server.commons.authentication.FormAuthenticator.FormAuthConfig;
import org.eclipse.scout.rt.server.commons.servlet.filter.authentication.DevelopmentAuthenticator;
import org.eclipse.scout.rt.server.commons.servlet.filter.authentication.TrivialAuthenticator;
import org.eclipse.scout.rt.server.commons.servlet.filter.authentication.TrivialAuthenticator.TrivialAuthConfig;

public class UiHtmlServletFilter implements Filter {

  private TrivialAuthenticator m_trivialAuthenticator;
  private DevelopmentAuthenticator m_devAuthenticator;
  private FormAuthenticator m_formAuthenticator;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    m_trivialAuthenticator = BEANS.get(TrivialAuthenticator.class);
    m_devAuthenticator = BEANS.get(DevelopmentAuthenticator.class);
    m_formAuthenticator = BEANS.get(FormAuthenticator.class);

    m_trivialAuthenticator.init(new TrivialAuthConfig().withExclusionFilter(filterConfig.getInitParameter("filter-exclude")));
    m_devAuthenticator.init();
    m_formAuthenticator.init(new FormAuthConfig().withCredentialVerifier(BEANS.get(ConfigFileCredentialVerifier.class)));
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    final HttpServletRequest req = (HttpServletRequest) request;
    final HttpServletResponse resp = (HttpServletResponse) response;

    // requests to /login, /logout, /auth
    if (m_formAuthenticator.handle(req, resp, chain)) {
      return;
    }

    // cached with exclusion for CSS/JS-login and -logout resources
    if (m_trivialAuthenticator.handle(req, resp, chain)) {
      return;
    }

    // Dev
    if (m_devAuthenticator.handle(req, resp, chain)) {
      return;
    }

    m_formAuthenticator.forwardToLoginForm(req, resp);
  }

  @Override
  public void destroy() {
    m_trivialAuthenticator.destroy();
    m_devAuthenticator.destroy();
    m_formAuthenticator.destroy();
  }
}
