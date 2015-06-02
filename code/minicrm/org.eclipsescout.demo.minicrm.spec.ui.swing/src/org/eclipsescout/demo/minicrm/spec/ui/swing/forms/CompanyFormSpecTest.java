/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipsescout.demo.minicrm.spec.ui.swing.forms;

import java.util.List;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.FormListener;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.spec.client.AbstractFormSpecTest;
import org.eclipsescout.demo.minicrm.client.ui.desktop.form.CompanyForm;

public class CompanyFormSpecTest extends AbstractFormSpecTest {

  @Override
  protected IForm createAndStartForm(List<FormListener> formListeners) throws ProcessingException {
    CompanyForm form = new CompanyForm();
    for (FormListener listener : formListeners) {
      form.addFormListener(listener);
    }
    form.startNew();
    return form;
  }

}
