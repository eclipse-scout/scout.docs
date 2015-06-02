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
package org.eclipsescout.demo.bahbah.ui.swt;

import org.eclipse.scout.rt.ui.swt.ISwtEnvironment;
import org.eclipsescout.demo.bahbah.client.ClientSession;
import org.eclipsescout.demo.bahbah.ui.swt.perspective.Perspective;
import org.eclipsescout.demo.bahbah.ui.swt.views.CenterView;
import org.eclipsescout.demo.bahbah.ui.swt.views.DetailView;
import org.eclipsescout.demo.bahbah.ui.swt.views.EastView;
import org.eclipsescout.demo.bahbah.ui.swt.views.OutlineView;
import org.eclipsescout.demo.bahbah.ui.swt.views.SearchView;
import org.eclipsescout.demo.bahbah.ui.swt.views.TableView;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator{

  // the plugin id
  public static final String BUNDLE_ID = "org.eclipsescout.demo.bahbah.ui.swt";

  // all view ID's commodity to access.
  public static final String CENTER_VIEW_ID = CenterView.class.getName();
  public static final String DETAIL_VIEW_ID = DetailView.class.getName();
  public static final String EAST_VIEW_ID = EastView.class.getName();
  public static final String OUTLINE_VIEW_ID = OutlineView.class.getName();
  public static final String TABLE_VIEW_ID = TableView.class.getName();
  public static final String SEARCH_VIEW_ID = SearchView.class.getName();

  private ISwtEnvironment m_environment;

  // the shared instance
  private static Activator m_bundle;

  @Override
  public void start(BundleContext context) throws Exception {
    m_bundle = this;
    m_environment = new SwtEnvironment(context.getBundle(), Perspective.ID, ClientSession.class);
  }

  @Override
  public void stop(BundleContext context) throws Exception {
    m_bundle = null;
  }

  public static Activator getDefault() {
    return m_bundle;
  }

  public ISwtEnvironment getEnvironment() {
    return m_environment;
  }
}

