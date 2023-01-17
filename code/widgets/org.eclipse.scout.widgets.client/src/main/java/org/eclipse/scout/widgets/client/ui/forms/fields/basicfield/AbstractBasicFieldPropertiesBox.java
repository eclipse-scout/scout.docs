/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.forms.fields.basicfield;

import org.eclipse.scout.rt.client.ui.form.fields.AbstractBasicField;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.widgets.client.ui.forms.fields.properties.AbstractPropertiesBox;

@ClassId("5fb63475-4bdb-48b3-b585-83e9c217d480")
public abstract class AbstractBasicFieldPropertiesBox<V> extends AbstractPropertiesBox<AbstractBasicField<V>> {

  @Override
  protected String getConfiguredLabel() {
    return "Basic Field Properties";
  }

}
