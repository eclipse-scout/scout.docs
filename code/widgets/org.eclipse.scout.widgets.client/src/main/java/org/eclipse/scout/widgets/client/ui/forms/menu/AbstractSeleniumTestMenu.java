/*
 * Copyright (c) 2020 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.forms.menu;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.widgets.client.WidgetsClientConfigProperties.SeleniumProperty;

/**
 * Root menu for selenium tests. This menu is only visible when the config property 'widgets.selenium' is set to true.
 */
@ClassId("7a079afc-fbc3-41b4-9393-113cf82712d7")
public class AbstractSeleniumTestMenu extends AbstractMenu {

  @Override
  protected void execInitAction() {
    setVisibleGranted(CONFIG.getPropertyValue(SeleniumProperty.class));
  }

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("Selenium");
  }

}
