/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.forms.groupbox;

import java.util.Locale;

import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.classid.ClassId;
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

  @ClassId("b76cbef7-731b-4009-aef3-70a9990729b9")
  public static final class DateField extends AbstractStringField {
    @Override
    protected String getConfiguredLabel() {
      return "Dyn Date Field";
    }
  }

  @ClassId("b9c6c071-953c-4416-97c9-3395b854a40c")
  public static final class IntegerField extends AbstractIntegerField {
    @Override
    protected String getConfiguredLabel() {
      return "Dyn Integer Field";
    }
  }

  @ClassId("78e69105-0fb5-434b-a8e5-628dade57533")
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

  @ClassId("47eadc84-455d-4dc4-bee4-a134d0e51d95")
  public static final class StringField extends AbstractStringField {
    @Override
    protected String getConfiguredLabel() {
      return "Dyn String Field";
    }
  }

}
