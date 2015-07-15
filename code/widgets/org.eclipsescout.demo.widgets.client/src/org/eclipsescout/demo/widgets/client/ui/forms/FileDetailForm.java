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
package org.eclipsescout.demo.widgets.client.ui.forms;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipsescout.demo.widgets.client.ui.forms.FileDetailForm.MainBox.FileDetailsBox;
import org.eclipsescout.demo.widgets.client.ui.template.formfield.AbstractFileDetailsBox;

public class FileDetailForm extends AbstractForm {

  public FileDetailForm() throws ProcessingException {
    super();
  }

  public void startNew() throws ProcessingException {
    startInternal(new NewHandler());
  }

  public FileDetailsBox getFileDetailsBox() {
    return getFieldByClass(FileDetailsBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class FileDetailsBox extends AbstractFileDetailsBox {
    }
  }

  public class NewHandler extends AbstractFormHandler {
  }
}
