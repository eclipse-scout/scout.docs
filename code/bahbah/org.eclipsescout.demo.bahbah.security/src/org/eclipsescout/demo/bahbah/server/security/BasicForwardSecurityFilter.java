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
import java.security.AccessController;
import java.security.Principal;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Map;

import javax.security.auth.Subject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.scout.commons.Base64Utility;
import org.eclipse.scout.commons.ConfigIniUtility;
import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.security.SimplePrincipal;
import org.eclipse.scout.rt.server.commons.cache.IHttpSessionCacheService;
import org.eclipse.scout.rt.server.commons.servletfilter.FilterConfigInjection;
import org.eclipse.scout.rt.server.commons.servletfilter.security.PrincipalHolder;
import org.eclipse.scout.rt.server.commons.servletfilter.security.SecureHttpServletRequestWrapper;
import org.eclipse.scout.service.SERVICES;

public class BasicForwardSecurityFilter implements Filter {

  private final static String AUTH_SERVLET_URL_PARAM = "authUrl";
  private final static String PROP_BASIC_ATTEMPT = "BahBahSecurityFilter.basicAttempt";
  private final static String AUTH_HEADER = "Authorization";
  private final static String AUTH_HEADER_PREFIX = "Basic ";

  public static final String PROP_SUBJECT = Subject.class.getName();

  public static final int STATUS_CONTINUE_CHAIN = 1;
  public static final int STATUS_BREAK_CHAIN = 2;
  public static final int STATUS_CONTINUE_WITH_PRINCIPAL = 3;

  private boolean m_failover;
  private String m_realm;
  private FilterConfigInjection m_injection;

  private URL m_url;

  @Override
  public final void doFilter(ServletRequest in, ServletResponse out, final FilterChain chain) throws IOException, ServletException {
    //ticket 94794
    FilterConfigInjection.FilterConfig config = m_injection.getConfig(in);
    if (!config.isActive()) {
      chain.doFilter(in, out);
      return;
    }
    //
    final HttpServletRequest req = (HttpServletRequest) in;
    final HttpServletResponse res = (HttpServletResponse) out;
    //touch the session so it is effectively used
    req.getSession();
    // check subject on session
    Subject subject = null;
    synchronized (req.getSession()) {
      subject = findSubject(req, res);
    }
    if (subject == null || subject.getPrincipals().size() == 0) {
      //try negotiate
      PrincipalHolder pHolder = new PrincipalHolder();
      switch (negotiate(req, res, pHolder)) {
        case STATUS_CONTINUE_CHAIN:
          if (m_failover) {
            chain.doFilter(req, res);
            return;
          }
          else {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
          }
        case STATUS_BREAK_CHAIN:
          return;
        case STATUS_CONTINUE_WITH_PRINCIPAL:
          if (subject == null || subject.isReadOnly()) {
            subject = new Subject();
          }
          subject.getPrincipals().add(pHolder.getPrincipal());
          subject.setReadOnly();
          synchronized (req.getSession()) {
            SERVICES.getService(IHttpSessionCacheService.class).put(PROP_SUBJECT, subject, req, res);
            //req.getSession().setAttribute(PROP_SUBJECT, subject);
          }
          break;
      }
    }
    //run in subject
    if (Subject.getSubject(AccessController.getContext()) != null) {
      doFilterInternal(req, res, chain);
    }
    else {
      try {
        Subject.doAs(subject, new PrivilegedExceptionAction<Object>() {
          @Override
          public Object run() throws Exception {
            HttpServletRequest secureReq = req;
            if (!(secureReq instanceof SecureHttpServletRequestWrapper)) {
              Principal principal = Subject.getSubject(AccessController.getContext()).getPrincipals().iterator().next();
              secureReq = new SecureHttpServletRequestWrapper(req, principal);
            }
            doFilterInternal(secureReq, res, chain);
            return null;
          }
        });
      }
      catch (PrivilegedActionException e) {
        Throwable t = e.getCause();
        if (t instanceof IOException) {
          throw (IOException) t;
        }
        else if (t instanceof ServletException) {
          throw (ServletException) t;
        }
        else {
          throw new ServletException(t);
        }
      }
    }
  }

  private void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
    chain.doFilter(req, res);
  }

  public String getRealm() {
    return m_realm;
  }

  public boolean isFailover() {
    return m_failover;
  }

  @Override
  public void destroy() {
    m_injection = null;
  }

  private Subject findSubject(HttpServletRequest req, HttpServletResponse resp) {
    Object o = null;
    Subject subject = null;
    o = SERVICES.getService(IHttpSessionCacheService.class).getAndTouch(PROP_SUBJECT, req, resp);

    if (o instanceof Subject) {
      subject = (Subject) o;
    }
    //check if we are already authenticated
    if (subject == null) {
      subject = Subject.getSubject(AccessController.getContext());
    }
    if (subject == null) {
      Principal principal = req.getUserPrincipal();
      if (principal == null || !StringUtility.hasText(principal.getName())) {
        principal = null;
        String name = req.getRemoteUser();
        if (StringUtility.hasText(name)) {
          principal = new SimplePrincipal(name);
        }
      }
      if (principal != null) {
        subject = new Subject();
        subject.getPrincipals().add(principal);
        subject.setReadOnly();
        SERVICES.getService(IHttpSessionCacheService.class).put(PROP_SUBJECT, subject, req, resp);
      }
    }
    return subject;
  }

  @Override
  public void init(FilterConfig config0) throws ServletException {
    m_injection = new FilterConfigInjection(config0, getClass());
    FilterConfigInjection.FilterConfig config = m_injection.getAnyConfig();
    String failoverString = config.getInitParameter("failover");
    m_failover = Boolean.parseBoolean(failoverString);
    String realmParam = config.getInitParameter("realm");
    if (realmParam == null) {
      realmParam = "Default";
    }
    m_realm = realmParam;
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
    Object attribute = SERVICES.getService(IHttpSessionCacheService.class).getAndTouch(PROP_BASIC_ATTEMPT, req, res);
    if (attribute instanceof Integer) {
      basicAtttempt = ((Integer) attribute).intValue();
    }
    return basicAtttempt;
  }

  private void setBasicAttept(HttpServletRequest req, HttpServletResponse res, int attempts) {
    SERVICES.getService(IHttpSessionCacheService.class).put(PROP_BASIC_ATTEMPT, attempts, req, res);
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
