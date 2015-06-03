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
package org.eclipsescout.demo.widgets.client.ui.desktop.outlines;

import java.util.List;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.extension.client.ui.desktop.outline.AbstractExtensibleOutline;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.ui.desktop.pages.FormPage;
import org.eclipsescout.demo.widgets.client.ui.forms.ButtonLinkFieldsForm;
import org.eclipsescout.demo.widgets.client.ui.forms.CheckboxFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.DateTimeFieldsForm;
import org.eclipsescout.demo.widgets.client.ui.forms.DecimalFieldsForm;
import org.eclipsescout.demo.widgets.client.ui.forms.LabelFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxForm;
import org.eclipsescout.demo.widgets.client.ui.forms.NumberFieldsForm;
import org.eclipsescout.demo.widgets.client.ui.forms.RadioButtonGroupFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm;

/**
 * @author mzi
 */
public class SimpleWidgetsOutline extends AbstractExtensibleOutline {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("SimpleWidgets");
  }

  @Override
  protected void execCreateChildPages(List<IPage> pageList) throws ProcessingException {

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

    FormPage buttonLinkFieldsPage = new FormPage(ButtonLinkFieldsForm.class);
    pageList.add(buttonLinkFieldsPage);

    FormPage messageBoxPage = new FormPage(MessageBoxForm.class);
    pageList.add(messageBoxPage);
  }
}
