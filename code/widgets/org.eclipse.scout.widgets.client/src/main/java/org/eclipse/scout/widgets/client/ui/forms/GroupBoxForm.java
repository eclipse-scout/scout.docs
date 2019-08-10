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
package org.eclipse.scout.widgets.client.ui.forms;

import org.eclipse.scout.rt.platform.text.TEXTS;

public class GroupBoxForm extends GroupBoxHorizontalScrollingForm {
  public GroupBoxForm() {
    m_disableHorizontalScrolling = true;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("GroupBox");
  }
}
