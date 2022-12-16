/*
 * Copyright (c) 2022 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.forms.hybrid;

import org.eclipse.scout.rt.client.ui.form.js.AbstractJsForm;
import org.eclipse.scout.rt.dataobject.IDoEntity;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("82ad45d8-aa29-4d4d-a6da-bef34fdd38a3")
public class HybridJsForm extends AbstractJsForm<IDoEntity, IDoEntity> {
  @Override
  public String getJsFormObjectType() {
    return "widgets.HybridJsForm";
  }
}
