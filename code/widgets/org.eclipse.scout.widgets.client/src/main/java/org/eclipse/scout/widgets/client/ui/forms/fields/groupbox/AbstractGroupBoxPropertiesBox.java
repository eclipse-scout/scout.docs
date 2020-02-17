/*
 * Copyright (c) 2020 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.forms.fields.groupbox;

import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.widgets.client.ui.forms.fields.properties.AbstractPropertiesBox;

@ClassId("11712235-408c-40b3-b90c-1b84877d6630")
public abstract class AbstractGroupBoxPropertiesBox extends AbstractPropertiesBox<AbstractGroupBox> {

  @Override
  protected String getConfiguredLabel() {
    return "Group Box Properties";
  }

  @Order(1000)
  @ClassId("8640d673-899f-44e7-a7fa-75735cd81a00")
  public class BorderVisibleField extends AbstractBooleanField {

    @Override
    protected String getConfiguredLabel() {
      return "Border Visible";
    }

    @Override
    protected boolean getConfiguredLabelVisible() {
      return false;
    }

    @Override
    protected void execChangedValue() {
      m_field.setBorderVisible(getValue());
    }

    @Override
    protected void execInitField() {
      setValue(m_field.isBorderVisible());
    }
  }

  @Order(2000)
  @ClassId("748ce748-addf-4c27-93c9-c6a84ab06416")
  public class ExpandableField extends AbstractBooleanField {

    @Override
    protected String getConfiguredLabel() {
      return "Expandable";
    }

    @Override
    protected boolean getConfiguredLabelVisible() {
      return false;
    }

    @Override
    protected void execChangedValue() {
      m_field.setExpandable(getValue());
    }

    @Override
    protected void execInitField() {
      setValue(m_field.isExpandable());
    }
  }

  @Order(3000)
  @ClassId("00a12faa-b323-472d-b108-aabfad14ebf3")
  public class ExpandedField extends AbstractBooleanField {

    @Override
    protected String getConfiguredLabel() {
      return "Expanded";
    }

    @Override
    protected boolean getConfiguredLabelVisible() {
      return false;
    }

    @Override
    protected void execChangedValue() {
      m_field.setExpanded(getValue());
    }

    @Override
    protected void execInitField() {
      setValue(m_field.isExpanded());
    }
  }

  @Order(7000)
  @ClassId("28559f97-5092-4b98-8042-199378c06b73")
  public class SubLabelField extends AbstractStringField {

    @Override
    protected String getConfiguredLabel() {
      return "Sub Label";
    }

    @Override
    protected void execChangedValue() {
      m_field.setSubLabel(getValue());
    }

    @Override
    protected void execInitField() {
      setValue(m_field.getSubLabel());
    }
  }

  @Order(8000)
  @ClassId("01915e5e-d15a-4dff-a71a-735c0ef33c67")
  public class GridColumnCountField extends AbstractIntegerField {

    @Override
    protected String getConfiguredLabel() {
      return "Grid Column Count";
    }

    @Override
    protected void execChangedValue() {
      m_field.setGridColumnCount(getValue());
    }

    @Override
    protected void execInitField() {
      setValue(m_field.getGridColumnCount());
    }
  }
}
