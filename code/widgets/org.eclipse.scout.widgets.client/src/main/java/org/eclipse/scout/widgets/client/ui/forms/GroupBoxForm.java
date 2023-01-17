/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.forms;

import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;

@ClassId("c388c77e-a2af-4a80-ac1c-190671eb4c98")
public class GroupBoxForm extends GroupBoxHorizontalScrollingForm {
  public GroupBoxForm() {
    m_disableHorizontalScrolling = true;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("GroupBox");
  }
}
