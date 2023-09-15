/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.desktop.pages;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.js.AbstractJsPage;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("b03fa823-7164-4e29-a176-cf0c0f5e241a")
public class HybridJsPage extends AbstractJsPage {

  @Override
  public String getConfiguredJsPageObjectType() {
    return "widgets.HybridJsPage";
  }
}
