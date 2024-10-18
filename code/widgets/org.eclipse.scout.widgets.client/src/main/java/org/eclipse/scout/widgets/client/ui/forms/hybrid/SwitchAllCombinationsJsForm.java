/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.forms.hybrid;

import org.eclipse.scout.rt.client.ui.form.js.AbstractJsForm;
import org.eclipse.scout.rt.dataobject.IDoEntity;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("f7147967-0749-4488-9330-bb31139300f2")
public class SwitchAllCombinationsJsForm extends AbstractJsForm<IDoEntity, IDoEntity> {

  @Override
  public String getConfiguredJsFormObjectType() {
    return "widgets.SwitchAllCombinationsJsForm";
  }
}
