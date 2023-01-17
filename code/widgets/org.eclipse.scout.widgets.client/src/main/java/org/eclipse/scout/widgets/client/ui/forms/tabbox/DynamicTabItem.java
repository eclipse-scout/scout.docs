/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.forms.tabbox;

import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.widgets.client.ui.forms.tabbox.DynamicTabItem.InnerBox.String01Field;
import org.eclipse.scout.widgets.client.ui.forms.tabbox.DynamicTabItem.InnerBox.String02Field;

/**
 * <h3>{@link DynamicTabItem}</h3>
 *
 * @author aho
 */
@ClassId("4db9172c-3345-4c28-a4d4-e74f75703d18")
public class DynamicTabItem extends AbstractGroupBox {

  public DynamicTabItem() {
    this("Dynamic Tab");
  }

  public DynamicTabItem(String name) {
    if (StringUtility.hasText(name)) {
      setLabel(name);
    }
  }

  @Override
  protected String getConfiguredLabel() {
    return "Dyn Tab";
  }

  public String02Field getString02Field() {
    return getFieldByClass(String02Field.class);
  }

  public String01Field getString01Field() {
    return getFieldByClass(String01Field.class);
  }

  public InnerBox getInnerBox() {
    return getFieldByClass(InnerBox.class);
  }

  public DateField getDateField() {
    return getFieldByClass(DateField.class);
  }

  @Order(100)
  @ClassId("c536305a-f827-42d3-b4d9-d5365283938d")
  public class SampleField extends AbstractStringField {
    @Override
    protected String getConfiguredLabel() {
      return "Sample";
    }
  }

  @Order(200)
  @ClassId("0d19fbfb-f9a7-4af9-b10b-aaaa87a0db91")
  public class DateField extends AbstractDateField {
    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("Date");
    }
  }

  @Order(2000)
  @ClassId("3125d2f3-c186-48ee-853f-aaa2f11c2ff0")
  public class InnerBox extends AbstractGroupBox {
    @Override
    protected String getConfiguredLabel() {
      return "Inner box";
    }

    @Override
    protected void initConfig() {
      super.initConfig();
    }

    @Order(1000)
    @ClassId("6e9ad07a-60df-4a66-8424-f9121109a5ed")
    public class String01Field extends AbstractStringField {
      @Override
      protected String getConfiguredLabel() {
        return "String field";
      }

      @Override
      protected int getConfiguredMaxLength() {
        return 128;
      }
    }

    @Order(2000)
    @ClassId("82b5b565-f2e4-4bd6-8911-aa7dcf678bfc")
    public class String02Field extends AbstractStringField {
      @Override
      protected String getConfiguredLabel() {
        return "Multiline";
      }

      @Override
      protected boolean getConfiguredMultilineText() {
        return true;
      }

      @Override
      protected int getConfiguredGridH() {
        return 2;
      }

      @Override
      protected int getConfiguredGridW() {
        return 2;
      }

      @Override
      protected int getConfiguredMaxLength() {
        return Integer.MAX_VALUE;
      }
    }

  }
}
