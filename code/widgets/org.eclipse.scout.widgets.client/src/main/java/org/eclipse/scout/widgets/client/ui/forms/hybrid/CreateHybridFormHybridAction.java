/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.forms.hybrid;

import org.eclipse.scout.rt.client.ui.desktop.hybrid.AbstractFormHybridAction;
import org.eclipse.scout.rt.client.ui.desktop.hybrid.HybridActionType;
import org.eclipse.scout.rt.dataobject.IDoEntity;

@HybridActionType(CreateHybridFormHybridAction.TYPE)
public class CreateHybridFormHybridAction extends AbstractFormHybridAction<HybridForm, IDoEntity> {

  protected static final String HYBRID_FORM = "Hybrid";
  protected static final String TYPE = CREATE_FORM_PREFIX + HYBRID_FORM;

  @Override
  protected HybridForm createForm(IDoEntity data) {
    return new HybridForm();
  }
}
