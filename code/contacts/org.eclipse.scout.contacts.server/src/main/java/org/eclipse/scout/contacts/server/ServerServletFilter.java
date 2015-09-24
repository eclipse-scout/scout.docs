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

/**
 * This is the main server side servlet filter.
 */
public class ServerServletFilter implements Filter {

  private ServiceTunnelAccessTokenAuthenticator m_tunnelAuthenticator;
  private DevelopmentAuthenticator m_devAuthenticator;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    m_tunnelAuthenticator = BEANS.get(ServiceTunnelAccessTokenAuthenticator.class);
    m_tunnelAuthenticator.init(filterConfig);

    m_devAuthenticator = BEANS.get(DevelopmentAuthenticator.class);
    m_devAuthenticator.init(filterConfig);
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    final HttpServletRequest req = (HttpServletRequest) request;
    final HttpServletResponse resp = (HttpServletResponse) response;

    // service tunnel token
    if (m_tunnelAuthenticator.handle(req, resp, chain)) {
      return;
    }

    // GET call
    if (isGetCall(req) && m_devAuthenticator.handle(req, resp, chain)) {
      return;
    }

    resp.sendError(HttpServletResponse.SC_FORBIDDEN);
  }

  @Override
  public void destroy() {
    m_tunnelAuthenticator.destroy();
    m_tunnelAuthenticator = null;
  }

  protected boolean isGetCall(HttpServletRequest req) {
    return "GET".equals(req.getMethod());
  }
}
