/*
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.desktop;

import java.security.AccessController;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.action.keystroke.IKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktop;
import org.eclipse.scout.rt.client.ui.desktop.IDesktopExtension;
import org.eclipse.scout.rt.client.ui.desktop.notification.NativeNotificationDefaults;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.form.FormEvent;
import org.eclipse.scout.rt.client.ui.form.FormListener;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.ScoutInfoForm;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.context.PropertyMap;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.AdvancedWidgetsOutline;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.LayoutWidgetsOutline;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.PagesOutline;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.SimpleWidgetsOutline;
import org.eclipse.scout.widgets.client.ui.forms.OptionsForm;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm;

@ClassId("235a000b-de88-45a5-b11d-8b1a51c8a595")
public class Desktop extends AbstractDesktop {

  private IForm m_benchModeForm = null;
  private final FormListener m_benchModeFormListener = new P_BenchModeFormListener();

  @Override
  protected String getConfiguredDisplayStyle() {
    return resolveDesktopStyle();
  }

  /**
   * Returns the 'desktopStyle' provided as part of the URL, or the default style otherwise.<br/>
   * E.g. http://[host:port]/?desktopStyle=BENCH to start in bench mode.
   */
  protected String resolveDesktopStyle() {
    String desktopStyle = PropertyMap.CURRENT.get().get("desktopStyle");
    if (desktopStyle != null) {
      return desktopStyle.toLowerCase();
    }
    else {
      return DISPLAY_STYLE_DEFAULT;
    }
  }

  @Override
  protected List<Class<? extends IOutline>> getConfiguredOutlines() {
    List<Class<? extends IOutline>> outlines = new ArrayList<>();
    outlines.add(SimpleWidgetsOutline.class);
    outlines.add(AdvancedWidgetsOutline.class);
    outlines.add(LayoutWidgetsOutline.class);
    outlines.add(PagesOutline.class);
    return outlines;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ScoutWidgetsDemoApp");
  }

  @Override
  protected String getConfiguredLogoId() {
    return "application_logo.svg";
  }

  @Override
  protected boolean getConfiguredLogoActionEnabled() {
    return true;
  }

  @Override
  protected NativeNotificationDefaults getConfiguredNativeNotificationDefaults() {
    return super.getConfiguredNativeNotificationDefaults().withIconId("application_logo.png");
  }

  @Override
  protected void execLogoAction() {
    ScoutInfoForm form = new ScoutInfoForm();
    form.startModify();
  }

  @Override
  protected void execDefaultView() {
    if (DISPLAY_STYLE_BENCH.equals(getDisplayStyle())) {
      // "bench-only" desktop
      if (m_benchModeForm != null) {
        m_benchModeForm.activate();
      }
      else {
        IForm benchForm = null;
        for (IDesktopExtension ext : getDesktopExtensions()) {
          if (ext instanceof IBenchFormProvider) {
            benchForm = ((IBenchFormProvider) ext).provideBenchForm();
          }
        }
        if (benchForm == null) {
          benchForm = new StringFieldForm();
        }
        benchForm.setDisplayHint(IForm.DISPLAY_HINT_VIEW);
        benchForm.start();
        setBenchModeForm(benchForm);
      }
    }
    else {
      // default desktop
      IOutline firstOutline = CollectionUtility.firstElement(getAvailableOutlines());
      if (firstOutline != null) {
        activateOutline(firstOutline);
      }
    }
  }

  protected void setBenchModeForm(IForm form) {
    // Uninstall
    if (m_benchModeForm != null) {
      m_benchModeForm.removeFormListener(m_benchModeFormListener);
      m_benchModeForm = null;
    }
    // Install
    if (form != null) {
      m_benchModeForm = form;
      m_benchModeForm.addFormListener(m_benchModeFormListener);
    }
  }

  protected IForm getBenchModeForm() {
    return m_benchModeForm;
  }

  @Order(100)
  @ClassId("3c252f67-ee22-4922-aedf-e908ff2ca8fe")
  public class UserProfileMenu extends AbstractMenu {

    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F10;
    }

    @Override
    protected String getConfiguredIconId() {
      return AbstractIcons.PersonSolid;
    }

    @Override
    protected String getConfiguredCssClass() {
      return "profile-menu";
    }

    @Override
    protected String getConfiguredText() {
      Subject subject = Subject.getSubject(AccessController.getContext());
      Principal firstPrincipal = CollectionUtility.firstElement(subject.getPrincipals());
      return StringUtility.uppercaseFirst(firstPrincipal.getName());
    }

    @Order(1000)
    @ClassId("0d2384b0-e367-46f1-a137-10c0a40b5439")
    public class OptionsMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Options");
      }

      @Override
      protected void execAction() {
        OptionsForm form = new OptionsForm();
        form.startNew();
      }
    }

    @Order(2000)
    @ClassId("0de10681-9e56-4828-859d-d399d456d9ec")
    public class LogoutMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Logout");
      }

      @Override
      protected void execAction() {
        ClientSessionProvider.currentSession().stop();
      }
    }

  }

  @Order(10)
  @ClassId("ad73ca5c-3d2e-4ce9-a77b-75c312f7d10a")
  public class SimpleWidgetsOutlineViewButton extends AbstractOutlineViewButton {
    public SimpleWidgetsOutlineViewButton() {
      super(Desktop.this, SimpleWidgetsOutline.class);
    }

    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F4;
    }
  }

  @Order(20)
  @ClassId("ae712831-e55f-4db0-a55e-0b8480ce2ef7")
  public class AdvancedWidgetsOutlineViewButton extends AbstractOutlineViewButton {
    public AdvancedWidgetsOutlineViewButton() {
      super(Desktop.this, AdvancedWidgetsOutline.class);
    }
  }

  @Order(30)
  @ClassId("bc31be84-84cc-4d54-a6a8-c06539e16694")
  public class LayoutWidgetsOutlineViewButton extends AbstractOutlineViewButton {
    public LayoutWidgetsOutlineViewButton() {
      super(Desktop.this, LayoutWidgetsOutline.class);
    }
  }

  @Order(40)
  @ClassId("e32d5276-cbe6-40c5-b748-4b8e404dec6c")
  public class PagesOutlineViewButton extends AbstractOutlineViewButton {
    public PagesOutlineViewButton() {
      super(Desktop.this, PagesOutline.class);
    }
  }

  protected class P_BenchModeFormListener implements FormListener {

    @Override
    public void formChanged(FormEvent e) {
      if (e.getType() == FormEvent.TYPE_CLOSED) {
        setBenchModeForm(null);
      }
    }
  }
}
