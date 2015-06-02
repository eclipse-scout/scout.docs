package org.eclipsescout.contacts.ui.swt;

import org.eclipse.scout.rt.client.AbstractClientSession;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.ui.swt.AbstractSwtEnvironment;
import org.eclipse.scout.rt.ui.swt.ISwtEnvironmentListener;
import org.eclipse.scout.rt.ui.swt.SwtEnvironmentEvent;
import org.eclipse.ui.PlatformUI;
import org.eclipsescout.contacts.ui.swt.editor.ScoutEditorPart;
import org.eclipsescout.contacts.ui.swt.views.CenterView;
import org.eclipsescout.contacts.ui.swt.views.EastView;
import org.eclipsescout.contacts.ui.swt.views.NorthEastView;
import org.eclipsescout.contacts.ui.swt.views.NorthView;
import org.eclipsescout.contacts.ui.swt.views.NorthWestView;
import org.eclipsescout.contacts.ui.swt.views.SouthEastView;
import org.eclipsescout.contacts.ui.swt.views.SouthView;
import org.eclipsescout.contacts.ui.swt.views.SouthWestView;
import org.eclipsescout.contacts.ui.swt.views.WestView;
import org.osgi.framework.Bundle;

public class SwtEnvironment extends AbstractSwtEnvironment {

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
          if (!PlatformUI.getWorkbench().isClosing()) {
            PlatformUI.getWorkbench().close();
          }
        }
      }
    });

  }
}
