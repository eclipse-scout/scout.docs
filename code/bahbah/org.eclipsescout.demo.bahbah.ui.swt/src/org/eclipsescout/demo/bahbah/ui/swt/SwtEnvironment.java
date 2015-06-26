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

import org.eclipse.scout.rt.client.AbstractClientSession;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.ui.swt.AbstractSwtEnvironment;
import org.eclipse.scout.rt.ui.swt.ISwtEnvironmentListener;
import org.eclipse.scout.rt.ui.swt.SwtEnvironmentEvent;
import org.eclipse.ui.PlatformUI;
import org.eclipsescout.demo.bahbah.ui.swt.editor.ScoutEditorPart;
import org.eclipsescout.demo.bahbah.ui.swt.views.CenterView;
import org.eclipsescout.demo.bahbah.ui.swt.views.DetailView;
import org.eclipsescout.demo.bahbah.ui.swt.views.EastView;
import org.eclipsescout.demo.bahbah.ui.swt.views.OutlineView;
import org.eclipsescout.demo.bahbah.ui.swt.views.SearchView;
import org.eclipsescout.demo.bahbah.ui.swt.views.TableView;
import org.osgi.framework.Bundle;

public class SwtEnvironment extends AbstractSwtEnvironment {

  public SwtEnvironment(Bundle bundle, String perspectiveId, Class<? extends AbstractClientSession> clientSessionClazz) {
    super(bundle, perspectiveId, clientSessionClazz);

    registerPart(IForm.VIEW_ID_OUTLINE, OutlineView.class.getName());
    registerPart(IForm.VIEW_ID_PAGE_DETAIL, DetailView.class.getName());
    registerPart(IForm.VIEW_ID_CENTER, CenterView.class.getName());
    registerPart(IForm.VIEW_ID_PAGE_TABLE, TableView.class.getName());
    registerPart(IForm.VIEW_ID_PAGE_SEARCH, SearchView.class.getName());
    registerPart(IForm.VIEW_ID_E, EastView.class.getName());
    registerPart(IForm.EDITOR_ID, ScoutEditorPart.class.getName());

    addEnvironmentListener(new ISwtEnvironmentListener() {
      @Override
      public void environmentChanged(SwtEnvironmentEvent e) {
        if (e.getType() == SwtEnvironmentEvent.STOPPED) {
          if (!PlatformUI.getWorkbench().isClosing()) {
            PlatformUI.getWorkbench().close();
          }
        }
      }
    });

  }

}
