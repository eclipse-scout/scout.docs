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
import java.util.Map;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.scout.commons.Base64Utility;
import org.eclipse.scout.commons.ConfigIniUtility;
import org.eclipse.scout.commons.security.SimplePrincipal;
import org.eclipse.scout.http.servletfilter.security.AbstractChainableSecurityFilter;
import org.eclipse.scout.http.servletfilter.security.PrincipalHolder;

public class BasicForwardSecurityFilter extends AbstractChainableSecurityFilter {

  private final static String AUTH_SERVLET_URL_PARAM = "authUrl";
  private final static String PROP_BASIC_ATTEMPT = "BahBahSecurityFilter.basicAttempt";
  private final static String AUTH_HEADER = "Authorization";
  private final static String AUTH_HEADER_PREFIX = "Basic ";

  private URL m_url;

  @Override
  public void init(FilterConfig config) throws ServletException {
    super.init(config);
    Map<String, String> conf = ConfigIniUtility.getProperties(getClass());

    String url = conf.get(AUTH_SERVLET_URL_PARAM);
    if (url == null) {
      throw new IllegalArgumentException("Missing config parameter '" + AUTH_SERVLET_URL_PARAM + "'");
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
    int attempts = getBasicAttempt(req);
    if (attempts > 2) {
      return STATUS_CONTINUE_CHAIN;
    }
    else {
      setBasicAttept(req, attempts + 1);
      resp.setHeader("WWW-Authenticate", "Basic realm=\"" + getRealm() + "\"");
      return STATUS_CONTINUE_CHAIN;
    }
  }

  private int getBasicAttempt(HttpServletRequest req) {
    int basicAtttempt = 0;
    Object attribute = req.getSession().getAttribute(PROP_BASIC_ATTEMPT);
    if (attribute instanceof Integer) {
      basicAtttempt = ((Integer) attribute).intValue();
    }
    return basicAtttempt;
  }

  private void setBasicAttept(HttpServletRequest req, int attempts) {
    req.getSession().setAttribute(PROP_BASIC_ATTEMPT, attempts);
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
