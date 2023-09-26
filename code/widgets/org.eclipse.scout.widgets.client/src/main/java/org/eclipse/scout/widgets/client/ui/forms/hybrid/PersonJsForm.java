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

import org.eclipse.scout.rt.client.ui.form.js.AbstractJsForm;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("12d00c10-68a9-433c-8b6a-11b3b13d2b73")
public class PersonJsForm extends AbstractJsForm<PersonDo, PersonDo> {

  @Override
  public String getConfiguredJsFormObjectType() {
    return "widgets.PersonJsForm";
  }
}
