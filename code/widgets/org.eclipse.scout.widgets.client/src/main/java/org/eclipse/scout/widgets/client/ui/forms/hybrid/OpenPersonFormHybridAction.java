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

import org.eclipse.scout.rt.client.ui.desktop.hybrid.AbstractFormHybridAction;
import org.eclipse.scout.rt.client.ui.desktop.hybrid.HybridActionType;

@HybridActionType(OpenPersonFormHybridAction.TYPE)
public class OpenPersonFormHybridAction extends AbstractFormHybridAction<PersonForm, PersonDo> {

  protected final static String PERSON = "Person";
  protected final static String TYPE = OPEN_FORM_PREFIX + PERSON;

  @Override
  protected PersonForm createForm(PersonDo data) {
    PersonForm form = new PersonForm();
    if (data != null) {
      form.importData(data);
    }
    return form;
  }

  @Override
  protected void exportResult(PersonForm form, PersonDo result) {
    super.exportResult(form, result);
    form.exportData(result);
  }
}
