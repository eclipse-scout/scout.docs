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
package org.eclipsescout.demo.bahbah.ui.swt.application;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipsescout.demo.bahbah.ui.swt.Activator;
import org.eclipsescout.demo.bahbah.ui.swt.SwtEnvironment;
import org.eclipse.scout.rt.client.ui.action.view.IViewButton;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

/**
 * <h3>ApplicationActionBarAdvisor</h3> Used for menu contributions.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

  // change this number if you change the maximum number of view-buttons on the desktop!
  private static final int NUM_OUTLINE_BUTTONS = 2;

  private OutlineViewAction[] m_outlineButton = new OutlineViewAction[NUM_OUTLINE_BUTTONS];

  public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
    super(configurer);
    ((SwtEnvironment) Activator.getDefault().getEnvironment()).setAdvisor(this);
  }

  @Override
  protected void fillMenuBar(IMenuManager menuBar) {
    menuBar.add(new MenuManager("", IWorkbenchActionConstants.M_FILE));
  }

  @Override
  protected void makeActions(IWorkbenchWindow window) {
    for (int i = 0; i < NUM_OUTLINE_BUTTONS; i++) {
      m_outlineButton[i] = new OutlineViewAction();
    }
  }

  public void initViewButtons(IDesktop d) {
    IViewButton[] viewButtons = d.getViewButtons();
    int min = Math.min(m_outlineButton.length, viewButtons.length);
    for (int i = 0; i < min; i++) {
      OutlineViewAction b = m_outlineButton[i];
      IViewButton v = viewButtons[i];

      b.setEnabled(v.isEnabled() && v.isEnabledGranted());
      if (v.isVisible() && v.isVisibleGranted()) {
        b.init(v);
        register(b);
      }
      else {
        b.setEnabled(false);
      }
    }
  }

  @Override
  protected void fillCoolBar(ICoolBarManager coolBar) {
    IToolBarManager toolbar = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
    coolBar.add(new ToolBarContributionItem(toolbar, "main"));
    for (Action a : m_outlineButton) {
      toolbar.add(a);
    }
  }
}
