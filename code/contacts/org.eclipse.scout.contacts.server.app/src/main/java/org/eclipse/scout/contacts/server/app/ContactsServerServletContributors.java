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

import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.scout.contacts.server.ServerServletFilter;
import org.eclipse.scout.rt.jetty.IServletContributor;
import org.eclipse.scout.rt.jetty.IServletFilterContributor;
import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.server.app.ServerServletContributors.AuthFilterContributor;

/**
 * {@link IServletContributor} and {@link IServletFilterContributor} for contacts server.
 */
public final class ContactsServerServletContributors {

  private ContactsServerServletContributors() {
  }

  @Replace
  public static class ContactsAuthFilterContributor extends AuthFilterContributor {

    @Override
    public void contribute(ServletContextHandler handler) {
      FilterHolder filter = handler.addFilter(ServerServletFilter.class, "/*", null);
      filter.setInitParameter("filter-exclude", StringUtility.join("\n", getFilterExcludes()));
    }
  }
}
