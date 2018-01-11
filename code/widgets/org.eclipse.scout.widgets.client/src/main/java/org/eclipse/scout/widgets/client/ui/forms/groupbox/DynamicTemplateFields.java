/*******************************************************************************
 * Copyright (c) 2018 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.widgets.client.ui.forms.groupbox;

import java.util.Locale;

import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.services.lookup.LocaleLookupCall;
import org.eclipse.scout.widgets.client.ui.template.formfield.FieldTypeLookupCall.FIELD_TYPE;

public class DynamicTemplateFields {

  private DynamicTemplateFields() {
  }

  public static IFormField createField(FIELD_TYPE fieldType) {
    switch (fieldType) {
      case DateField:
        return new DateField();
      case IntegerField:
        return new IntegerField();
      case SmartField:
        return new SmartField();
      case StringField:
        return new StringField();

      default:
        return null;
    }
  }

  public static final class DateField extends AbstractStringField {
    @Override
    protected String getConfiguredLabel() {
      return "Dyn Date Field";
    }
  }

  public static final class IntegerField extends AbstractIntegerField {
    @Override
    protected String getConfiguredLabel() {
      return "Dyn Integer Field";
    }
  }

  public static final class SmartField extends AbstractSmartField<Locale> {
    @Override
    protected String getConfiguredLabel() {
      return "Dyn Smart Field";
    }

    @Override
    protected Class<? extends ILookupCall<Locale>> getConfiguredLookupCall() {
      return LocaleLookupCall.class;
    }
  }

  public static final class StringField extends AbstractStringField {
    @Override
    protected String getConfiguredLabel() {
      return "Dyn String Field";
    }
  }

}
