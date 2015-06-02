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
package org.eclipsescout.demo.minifigcreator.ui.swt;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.scout.rt.client.AbstractClientSession;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.ui.swt.AbstractSwtEnvironment;
import org.eclipse.scout.rt.ui.swt.ISwtEnvironmentListener;
import org.eclipse.scout.rt.ui.swt.SwtEnvironmentEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipsescout.demo.minifigcreator.ui.swt.application.ApplicationActionBarAdvisor;
import org.eclipsescout.demo.minifigcreator.ui.swt.editor.ScoutEditorPart;
import org.eclipsescout.demo.minifigcreator.ui.swt.views.CenterView;
import org.eclipsescout.demo.minifigcreator.ui.swt.views.EastView;
import org.eclipsescout.demo.minifigcreator.ui.swt.views.NorthEastView;
import org.eclipsescout.demo.minifigcreator.ui.swt.views.NorthView;
import org.eclipsescout.demo.minifigcreator.ui.swt.views.NorthWestView;
import org.eclipsescout.demo.minifigcreator.ui.swt.views.SouthEastView;
import org.eclipsescout.demo.minifigcreator.ui.swt.views.SouthView;
import org.eclipsescout.demo.minifigcreator.ui.swt.views.SouthWestView;
import org.eclipsescout.demo.minifigcreator.ui.swt.views.WestView;
import org.osgi.framework.Bundle;

public class SwtEnvironment extends AbstractSwtEnvironment {

  private ApplicationActionBarAdvisor m_advisor;

  public SwtEnvironment(Bundle bundle, String perspectiveId, Class<? extends AbstractClientSession> clientSessionClazz) {
    super(bundle, perspectiveId, clientSessionClazz);

    registerPart(IForm.VIEW_ID_OUTLINE, NorthWestView.class.getName());
    registerPart(IForm.VIEW_ID_OUTLINE_SELECTOR, SouthWestView.class.getName());
    registerPart(IForm.VIEW_ID_CENTER, CenterView.class.getName());
    registerPart(IForm.VIEW_ID_PAGE_TABLE, CenterView.class.getName());
    registerPart(IForm.VIEW_ID_PAGE_DETAIL, NorthView.class.getName());
    registerPart(IForm.VIEW_ID_PAGE_SEARCH, SouthView.class.getName());
    registerPart(IForm.VIEW_ID_N, NorthView.class.getName());
    registerPart(IForm.VIEW_ID_NW, NorthWestView.class.getName());
    registerPart(IForm.VIEW_ID_W, WestView.class.getName());
    registerPart(IForm.VIEW_ID_SW, SouthWestView.class.getName());
    registerPart(IForm.VIEW_ID_S, SouthView.class.getName());
    registerPart(IForm.VIEW_ID_SE, SouthEastView.class.getName());
    registerPart(IForm.VIEW_ID_E, EastView.class.getName());
    registerPart(IForm.VIEW_ID_NE, NorthEastView.class.getName());

    registerPart(IForm.EDITOR_ID, ScoutEditorPart.class.getName());

    addEnvironmentListener(new ISwtEnvironmentListener() {
      @Override
      public void environmentChanged(SwtEnvironmentEvent e) {
        if (e.getType() == SwtEnvironmentEvent.STOPPED) {
          PlatformUI.getWorkbench().close();
        }
      }
    });
    addEnvironmentListener(new ISwtEnvironmentListener() {
      @Override
      public void environmentChanged(SwtEnvironmentEvent e) {
        if (e.getType() == SwtEnvironmentEvent.STARTED) {
          removeEnvironmentListener(this);
          IDesktop d = getClientSession().getDesktop();
          if (d != null) {
            setWindowTitle(d.getTitle());
            d.addPropertyChangeListener(IDesktop.PROP_TITLE, new PropertyChangeListener() {
              @Override
              public void propertyChange(PropertyChangeEvent evt) {
                setWindowTitle((String) evt.getNewValue());
              }
            });
            if (m_advisor != null) {
              m_advisor.initViewButtons(d);
            }
          }
        }
      }
    });
  }

  public void setAdvisor(ApplicationActionBarAdvisor advisor) {
    m_advisor = advisor;
  }

  private void setWindowTitle(final String title) {
    for (IWorkbenchWindow w : PlatformUI.getWorkbench().getWorkbenchWindows()) {
      final Shell s = w.getShell();
      if (!s.isDisposed()) {
        s.getDisplay().asyncExec(new Runnable() {
          @Override
          public void run() {
            s.setText(title);
          }
        });
      }
    }
  }
}
