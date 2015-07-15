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
package org.eclipsescout.demo.widgets.client.ui.template.formfield;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.fields.checkbox.AbstractCheckBox;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.shared.ui.template.formfield.AbstractFileDetailsBoxData;

/**
 *
 */
@FormData(value = AbstractFileDetailsBoxData.class, sdkCommand = FormData.SdkCommand.CREATE, defaultSubtypeSdkCommand = FormData.DefaultSubtypeSdkCommand.CREATE)
public abstract class AbstractFileDetailsBox extends AbstractGroupBox {
  @Override
  protected int getConfiguredGridColumnCount() {
    return 1;
  }

  @Override
  protected String getConfiguredLabel() {
    return TEXTS.get("Details");
  }

  @Override
  protected void execInitField() throws ProcessingException {
    getNameField().setEnabled(false);
    getSizeField().setEnabled(false);
    getModifiedField().setEnabled(false);
    getReadOnlyField().setEnabled(false);
  }

  public ModifiedField getModifiedField() {
    return getFieldByClass(ModifiedField.class);
  }

  public NameField getNameField() {
    return getFieldByClass(NameField.class);
  }

  public ReadOnlyField getReadOnlyField() {
    return getFieldByClass(ReadOnlyField.class);
  }

  public SizeField getSizeField() {
    return getFieldByClass(SizeField.class);
  }

  @Order(10.0)
  public class NameField extends AbstractStringField {

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("Name");
    }
  }

  @Order(20.0)
  public class SizeField extends AbstractLongField {

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("SizeInKB");
    }
  }

  @Order(40.0)
  public class ModifiedField extends AbstractDateTimeField {

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("Modified");
    }
  }

  @Order(50.0)
  public class ReadOnlyField extends AbstractCheckBox {

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("ReadOnly");
    }
  }

}
