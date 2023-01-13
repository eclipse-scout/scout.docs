/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
