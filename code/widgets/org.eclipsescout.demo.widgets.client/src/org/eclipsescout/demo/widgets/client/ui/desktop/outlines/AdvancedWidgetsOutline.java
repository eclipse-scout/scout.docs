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

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.extension.client.ui.desktop.outline.AbstractExtensibleOutline;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.ui.desktop.pages.FormPage;
import org.eclipsescout.demo.widgets.client.ui.forms.BrowserFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.CalendarFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.HtmlFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.ListBoxForm;
import org.eclipsescout.demo.widgets.client.ui.forms.PageFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.PlannerFieldDayForm;
import org.eclipsescout.demo.widgets.client.ui.forms.PlannerFieldIntradayForm;
import org.eclipsescout.demo.widgets.client.ui.forms.SmartFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.SvgFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.TableFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.TreeBoxForm;
import org.eclipsescout.demo.widgets.client.ui.forms.TreeFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.WrappedFormFieldForm;

/**
 * @author mzi
 */
@Order(2000.0)
public class AdvancedWidgetsOutline extends AbstractExtensibleOutline {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("AdvancedWidgets");
  }

  @Override
  protected void execCreateChildPages(List<IPage> pageList) throws ProcessingException {

    FormPage listBoxFieldPage = new FormPage(ListBoxForm.class);
    pageList.add(listBoxFieldPage);

    FormPage treeBoxPage = new FormPage(TreeBoxForm.class);
    pageList.add(treeBoxPage);

    FormPage smartFieldPage = new FormPage(SmartFieldForm.class);
    pageList.add(smartFieldPage);

    FormPage treeFieldPage = new FormPage(TreeFieldForm.class);
    pageList.add(treeFieldPage);

    FormPage tableFieldPage = new FormPage(TableFieldForm.class);
    pageList.add(tableFieldPage);

    FormPage calendarFieldPage = new FormPage(CalendarFieldForm.class);
    pageList.add(calendarFieldPage);

    FormPage imageFieldPage = new FormPage(ImageFieldForm.class);
    pageList.add(imageFieldPage);

    FormPage svgFieldPage = new FormPage(SvgFieldForm.class);
    pageList.add(svgFieldPage);

    FormPage htmlFieldPage = new FormPage(HtmlFieldForm.class);
    pageList.add(htmlFieldPage);

    FormPage browserFieldPage = new FormPage(BrowserFieldForm.class);
    pageList.add(browserFieldPage);

    FormPage pageFieldPage = new FormPage(PageFieldForm.class);
    pageList.add(pageFieldPage);

    FormPage wrappedFormFieldPage = new FormPage(WrappedFormFieldForm.class);
    pageList.add(wrappedFormFieldPage);

    pageList.add(new FormPage(PlannerFieldDayForm.class));
    pageList.add(new FormPage(PlannerFieldIntradayForm.class));
  }
}
