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

import org.eclipse.scout.rt.client.ui.desktop.hybrid.HybridActionType;

@HybridActionType(CreatePersonFormHybridAction.TYPE)
public class CreatePersonFormHybridAction extends OpenPersonFormHybridAction {

  protected final static String TYPE = CREATE_FORM_PREFIX + PERSON;

  @Override
  protected void prepareForm(PersonForm form) {
    super.prepareForm(form);
    form.setAskIfNeedSave(false);
  }
}
