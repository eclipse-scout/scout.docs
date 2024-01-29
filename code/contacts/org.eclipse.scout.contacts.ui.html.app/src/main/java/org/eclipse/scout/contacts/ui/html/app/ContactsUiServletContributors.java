/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.contacts.ui.html.app;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.scout.contacts.ui.html.UiServletFilter;
import org.eclipse.scout.rt.jetty.IServletContributor;
import org.eclipse.scout.rt.jetty.IServletFilterContributor;
import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.ui.html.app.UiServletContributors.AuthFilterContributor;

/**
 * {@link IServletContributor} and {@link IServletFilterContributor} for contacts UI.
 */
public final class ContactsUiServletContributors {

  private ContactsUiServletContributors() {
  }

  @Replace
  public static class ContactsAuthFilterContributor extends AuthFilterContributor {

    @Override
    public void contribute(ServletContextHandler handler) {
      FilterHolder filter = handler.addFilter(UiServletFilter.class, "/*", null);
      filter.setInitParameter("filter-exclude", StringUtility.join("\n", getFilterExcludes()));
    }

    @Override
    protected List<String> getFilterExcludes() {
      List<String> filterExcludes = super.getFilterExcludes();
      filterExcludes.addAll(Arrays.asList(
          "/favicon/*",
          "/fonts/*",
          "/logo.png",
          "/*login*.js",
          "/*logout*.js",
          "/*contacts-theme*.css",
          "/*login*.js.map",
          "/*logout*.js.map",
          "/*contacts-theme*.css.map"));
      return filterExcludes;
    }
  }
}
