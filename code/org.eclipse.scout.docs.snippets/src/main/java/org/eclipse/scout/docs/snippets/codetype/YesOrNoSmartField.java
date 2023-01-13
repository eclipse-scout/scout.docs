/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.docs.snippets.codetype;

import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;

//tag::YesOrNoSmartField[]
@ClassId("08ccc68e-7b72-4fe0-b666-245ddb8b8441")
public class YesOrNoSmartField extends AbstractSmartField<Boolean> {

  // other configuration of properties.

  @Override
  protected Class<? extends ICodeType<?, Boolean>> getConfiguredCodeType() {
    return YesOrNoCodeType.class;
  }
}
//end::YesOrNoSmartField[]
