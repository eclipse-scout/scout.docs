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
package org.eclipsescout.demo.bahbah.server.security;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.security.SimplePrincipal;
import org.eclipse.scout.rt.platform.util.Base64Utility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.server.commons.cache.IHttpSessionCacheService;
import org.eclipse.scout.rt.server.commons.servlet.filter.authentication.AbstractChainableSecurityFilter;
import org.eclipse.scout.rt.server.commons.servlet.filter.authentication.PrincipalHolder;

/**
 * @deprecated will be removed in release 6.0; is to be replaced with a project specific ServletFilter with the
 *             authenticators chained yourself; see depreciation note of {@link AbstractChainableSecurityFilter}
 */
@SuppressWarnings("deprecation")
@Deprecated
public class BasicForwardSecurityFilter extends AbstractChainableSecurityFilter {

  private final static String AUTH_SERVLET_URL_PARAM = "authUrl";
  private final static String PROP_BASIC_ATTEMPT = "BahBahSecurityFilter.basicAttempt";
  private final static String AUTH_HEADER = "Authorization";
  private final static String AUTH_HEADER_PREFIX = "Basic ";

  private URL m_url;

  @Override
  public void init(FilterConfig config) throws ServletException {
    super.init(config);
    String url = config.getInitParameter(AUTH_SERVLET_URL_PARAM);
    if (!StringUtility.hasText(url)) {
      throw new IllegalArgumentException("Missing config parameter '" + AUTH_SERVLET_URL_PARAM + "'.");
    }
    else {
      try {
        m_url = new URL(url);
      }
      catch (MalformedURLException e) {
        throw new ServletException("unable to parse authentication servlet", e);
      }
    }
  }

  @Override
  public void destroy() {
  }

  @Override
  protected int negotiate(HttpServletRequest req, HttpServletResponse resp, PrincipalHolder holder) throws IOException, ServletException {
    String h = req.getHeader(AUTH_HEADER);
    if (h != null && h.startsWith(AUTH_HEADER_PREFIX)) {
      String[] a = new String(Base64Utility.decode(h.substring(AUTH_HEADER_PREFIX.length())), "ISO-8859-1").split(":", 2);
      String user = a[0];
      String pass = a[1];
      if (user != null && pass != null) {
        if (validateUser(user.toLowerCase(), pass)) {
          holder.setPrincipal(new SimplePrincipal(user));
          return STATUS_CONTINUE_WITH_PRINCIPAL;
        }
      }
    }
    int attempts = getBasicAttempt(req, resp);
    if (attempts > 2) {
      return STATUS_CONTINUE_CHAIN;
    }
    else {
      setBasicAttept(req, resp, attempts + 1);
      resp.setHeader("WWW-Authenticate", "Basic realm=\"" + getRealm() + "\"");
      return STATUS_CONTINUE_CHAIN;
    }
  }

  private int getBasicAttempt(HttpServletRequest req, HttpServletResponse res) {
    int basicAtttempt = 0;
    Object attribute = BEANS.get(IHttpSessionCacheService.class).getAndTouch(PROP_BASIC_ATTEMPT, req, res);
    if (attribute instanceof Integer) {
      basicAtttempt = ((Integer) attribute).intValue();
    }
    return basicAtttempt;
  }

  private void setBasicAttept(HttpServletRequest req, HttpServletResponse res, int attempts) {
    BEANS.get(IHttpSessionCacheService.class).put(PROP_BASIC_ATTEMPT, attempts, req, res);
  }

  protected boolean validateUser(String user, String pass) throws ServletException {
    try {
      // forward user name and password to the server. there it will be checked whether the combination is correct or not
      HttpURLConnection c = (HttpURLConnection) m_url.openConnection();
      c.setRequestProperty("User", user);
      c.setRequestProperty("Pass", pass);
      c.setDefaultUseCaches(false);
      c.setUseCaches(false);
      int sc = c.getResponseCode();
      return sc == HttpServletResponse.SC_OK;
    }
    catch (IOException e) {
      return false;
    }
  }
}
