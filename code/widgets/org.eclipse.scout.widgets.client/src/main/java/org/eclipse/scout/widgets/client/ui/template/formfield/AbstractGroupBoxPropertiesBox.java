/*
 * Copyright (c) 2018 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.template.formfield;

import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.IGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFormFieldPropertiesBox.DisabledStyleField;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFormFieldPropertiesBox.LoadingField;

public class AbstractGroupBoxPropertiesBox extends AbstractGroupBox {

  private IGroupBox m_groupBox;

  public IGroupBox getGroupBox() {
    return m_groupBox;
  }

  @Override
  protected String getConfiguredLabel() {
    return "Group Box Properties";
  }

  public void setGroupBox(IGroupBox groupBox) {
    m_groupBox = groupBox;
  }

  public DisabledStyleField getDisabledStyleField() {
    return getFieldByClass(DisabledStyleField.class);
  }

  public GridColumnCountField getGridColumnCountField() {
    return getFieldByClass(GridColumnCountField.class);
  }

  public LoadingField getLoadingField() {
    return getFieldByClass(LoadingField.class);
  }

  public BorderVisibleField getBorderVisibleField() {
    return getFieldByClass(BorderVisibleField.class);
  }

  public ExpandableField getExpandableField() {
    return getFieldByClass(ExpandableField.class);
  }

  @Override
  protected boolean getConfiguredExpandable() {
    return true;
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
      getGroupBox().setBorderVisible(getValue());
    }

    @Override
    protected void execInitField() {
      setValue(getGroupBox().isBorderVisible());
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
      getGroupBox().setExpandable(getValue());
    }

    @Override
    protected void execInitField() {
      setValue(getGroupBox().isExpandable());
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
      getGroupBox().setExpanded(getValue());
    }

    @Override
    protected void execInitField() {
      setValue(getGroupBox().isExpanded());
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
      getGroupBox().setSubLabel(getValue());
    }

    @Override
    protected void execInitField() {
      setValue(getGroupBox().getSubLabel());
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
      getGroupBox().setGridColumnCount(getValue());
    }

    @Override
    protected void execInitField() {
      setValue(getGroupBox().getGridColumnCount());
    }
  }
}
