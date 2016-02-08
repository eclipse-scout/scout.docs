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
package org.eclipse.scout.widgets.client.ui.desktop.outlines;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.client.ui.desktop.pages.FormPage;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm;
import org.eclipse.scout.widgets.client.ui.forms.CheckboxFieldForm;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm;
import org.eclipse.scout.widgets.client.ui.forms.DecimalFieldsForm;
import org.eclipse.scout.widgets.client.ui.forms.FormForm;
import org.eclipse.scout.widgets.client.ui.forms.LabelFieldForm;
import org.eclipse.scout.widgets.client.ui.forms.MessageBoxForm;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupFieldForm;
import org.eclipse.scout.widgets.client.ui.forms.StringFieldForm;

/**
 * @author mzi
 */
@Order(1000.0)
public class SimpleWidgetsOutline extends AbstractOutline {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("SimpleWidgets");
  }

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {

    FormPage labelFieldPage = new FormPage(LabelFieldForm.class);
    pageList.add(labelFieldPage);

    FormPage stringFieldPage = new FormPage(StringFieldForm.class);
    pageList.add(stringFieldPage);

    FormPage numberFieldPage = new FormPage(NumberFieldsForm.class);
    pageList.add(numberFieldPage);

    FormPage decimalFieldPage = new FormPage(DecimalFieldsForm.class);
    pageList.add(decimalFieldPage);

    FormPage dateTimeFieldPage = new FormPage(DateTimeFieldsForm.class);
    pageList.add(dateTimeFieldPage);

    FormPage checkboxFieldPage = new FormPage(CheckboxFieldForm.class);
    pageList.add(checkboxFieldPage);

    FormPage radioButtonFieldPage = new FormPage(RadioButtonGroupFieldForm.class);
    pageList.add(radioButtonFieldPage);

    FormPage buttonLinkFieldsPage = new FormPage(ButtonForm.class);
    pageList.add(buttonLinkFieldsPage);

    FormPage messageBoxPage = new FormPage(MessageBoxForm.class);
    pageList.add(messageBoxPage);

    FormPage formPage = new FormPage(FormForm.class);
    pageList.add(formPage);
  }
}
