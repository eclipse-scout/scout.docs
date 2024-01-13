/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.ui.html.app;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.scout.rt.jetty.IServletContributor;
import org.eclipse.scout.rt.jetty.IServletFilterContributor;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.server.commons.HttpSessionMutex;
import org.eclipse.scout.rt.ui.html.UiHtmlConfigProperties.UiServletMultipartConfigProperty;
import org.eclipse.scout.rt.ui.html.UiServlet;
import org.eclipse.scout.widgets.ui.html.WidgetsUiServletFilter;

/**
 * {@link IServletContributor} and {@link IServletFilterContributor} for widgets UI.
 */
public final class WidgetsUiServletContributors {

  private WidgetsUiServletContributors() {
  }

  @Order(10)
  public static class HttpSessionMutexFilterContributor implements IServletFilterContributor {

    @Override
    public void contribute(ServletContextHandler handler) {
      handler.addEventListener(new HttpSessionMutex());
    }
  }

  @Order(20)
  public static class AuthFilterContributor implements IServletFilterContributor {

    @Override
    public void contribute(ServletContextHandler handler) {
      FilterHolder filter = handler.addFilter(WidgetsUiServletFilter.class, "/*", null);
      // values needs to be defined relative to application root path (which isn't always the same as servlet root path)
      List<String> filterExcludes = Arrays.asList(
          "/favicon/*",
          "/fonts/*",
          "/logo.png",
          "/*login*.js",
          "/*logout*.js",
          "/*widgets-theme*.css",
          "/*login*.js.map",
          "/*logout*.js.map",
          "/*widgets-theme*.css.map");
      filter.setInitParameter("filter-exclude", StringUtility.join("\n", filterExcludes));
    }
  }

  @Order(10)
  public static class UiServletContributor implements IServletContributor {

    @Override
    public void contribute(ServletContextHandler handler) {
      ServletHolder servletHolder = handler.addServlet(UiServlet.class, "/*");
      servletHolder.getRegistration().setMultipartConfig(CONFIG.getPropertyValue(UiServletMultipartConfigProperty.class));
    }
  }
}
