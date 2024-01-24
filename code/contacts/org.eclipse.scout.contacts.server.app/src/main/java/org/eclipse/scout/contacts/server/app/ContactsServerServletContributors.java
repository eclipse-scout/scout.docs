/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.contacts.server.app;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.scout.contacts.server.ServerServletFilter;
import org.eclipse.scout.rt.jetty.IServletContributor;
import org.eclipse.scout.rt.jetty.IServletFilterContributor;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.server.ServiceTunnelServlet;
import org.eclipse.scout.rt.server.admin.diagnostic.DiagnosticServlet;
import org.eclipse.scout.rt.server.commons.healthcheck.HealthCheckServlet;

/**
 * {@link IServletContributor} and {@link IServletFilterContributor} for contacts server.
 */
public final class ContactsServerServletContributors {

  private ContactsServerServletContributors() {
  }

  @Order(10)
  public static class AuthFilterContributor implements IServletFilterContributor {

    @Override
    public void contribute(ServletContextHandler handler) {
      FilterHolder filter = handler.addFilter(ServerServletFilter.class, "/*", null);
      // values needs to be defined relative to application root path (which isn't always the same as servlet root path)
      List<String> filterExcludes = Arrays.asList("/status");
      filter.setInitParameter("filter-exclude", StringUtility.join("\n", filterExcludes));
    }
  }

  @Order(10)
  public static class ServiceTunnelServletContributor implements IServletContributor {

    @Override
    public void contribute(ServletContextHandler handler) {
      handler.addServlet(ServiceTunnelServlet.class, "/process");
    }
  }

  @Order(20)
  public static class StatusServletContributor implements IServletContributor {

    @Override
    public void contribute(ServletContextHandler handler) {
      handler.addServlet(HealthCheckServlet.class, "/status");
    }
  }

  @Order(30)
  public static class DiagnosticServletContributor implements IServletContributor {

    @Override
    public void contribute(ServletContextHandler handler) {
      handler.addServlet(DiagnosticServlet.class, "/diagnostics");
    }
  }
}
