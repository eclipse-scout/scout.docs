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
import org.eclipse.scout.widgets.client.ui.forms.BooleanFieldForm;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm;
import org.eclipse.scout.widgets.client.ui.forms.DateTimeFieldsForm;
import org.eclipse.scout.widgets.client.ui.forms.DecimalFieldsForm;
import org.eclipse.scout.widgets.client.ui.forms.FormFieldForm;
import org.eclipse.scout.widgets.client.ui.forms.FormForm;
import org.eclipse.scout.widgets.client.ui.forms.LabelFieldForm;
import org.eclipse.scout.widgets.client.ui.forms.MessageBoxForm;
import org.eclipse.scout.widgets.client.ui.forms.NumberFieldsForm;
import org.eclipse.scout.widgets.client.ui.forms.RadioButtonGroupForm;
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
    pageList.add(new FormPage(LabelFieldForm.class));
    pageList.add(new FormPage(StringFieldForm.class));
    pageList.add(new FormPage(NumberFieldsForm.class));
    pageList.add(new FormPage(DecimalFieldsForm.class));
    pageList.add(new FormPage(DateTimeFieldsForm.class));
    pageList.add(new FormPage(RadioButtonGroupForm.class));
    pageList.add(new FormPage(ButtonForm.class));
    pageList.add(new FormPage(MessageBoxForm.class));
    pageList.add(new FormPage(FormForm.class));
    pageList.add(new FormPage(FormFieldForm.class));

    FormPage page = new FormPage(BooleanFieldForm.class);
    page.getCellForUpdate().setText("BooleanField (CheckBox)");
    pageList.add(page);

    FormPage.sort(pageList);
  }
}
