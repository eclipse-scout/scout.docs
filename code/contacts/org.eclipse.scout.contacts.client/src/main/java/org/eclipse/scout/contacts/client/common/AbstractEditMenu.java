/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.contacts.client.common;

import org.eclipse.scout.contacts.client.Icons;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;

@ClassId("4b0e3cbd-94db-482e-962d-89c4c9bf2e67")
public class AbstractEditMenu extends AbstractMenu {

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("Edit");
  }

  @Override
  protected String getConfiguredIconId() {
    return Icons.Pencil;
  }

  @Override
  protected String getConfiguredKeyStroke() {
    return "alt-e";
  }
}
