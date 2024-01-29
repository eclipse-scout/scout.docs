/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.jswidgets.ui.html.app;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.scout.rt.jetty.IServletContributor;
import org.eclipse.scout.rt.jetty.IServletFilterContributor;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.rest.RestApplication;
import org.eclipse.scout.rt.server.commons.context.HttpRunContextFilter;
import org.eclipse.scout.rt.ui.html.app.UiServletContributors.GzipFilterContributor;
import org.eclipse.scout.rt.ui.html.app.UiServletContributors.UiServletContributor;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;

/**
 * {@link IServletContributor} and {@link IServletFilterContributor} for JS widgets app.
 */
public final class JsWidgetsServletContributors {

  private JsWidgetsServletContributors() {
  }

  // no auth filter on / for UiServlet required

  /**
   * After {@link GzipFilterContributor}.
   */
  @Order(4000)
  public static class ApiRunContextFilterContributor implements IServletFilterContributor {

    @Override
    public void contribute(ServletContextHandler handler) {
      handler.addFilter(HttpRunContextFilter.class, "/api/*", null);
    }
  }

  /**
   * JAX-RS Jersey Servlet.
   * <p>
   * After {@link UiServletContributor}.
   */
  @Order(3000)
  public static class ApiServletContributor implements IServletContributor {

    @Override
    public void contribute(ServletContextHandler handler) {
      ServletHolder servlet = handler.addServlet(ServletContainer.class, "/api/*");
      servlet.setInitParameter(ServerProperties.WADL_FEATURE_DISABLE, Boolean.TRUE.toString());
      servlet.setInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, RestApplication.class.getName());
      servlet.setInitOrder(1); // load-on-startup
    }
  }
}
