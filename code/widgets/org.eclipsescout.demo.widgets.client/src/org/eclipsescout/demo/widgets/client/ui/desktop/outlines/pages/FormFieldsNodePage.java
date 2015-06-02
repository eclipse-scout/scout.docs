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
package org.eclipsescout.demo.widgets.client.ui.desktop.outlines.pages;

import java.util.Collection;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.ui.UserAgentUtility;
import org.eclipsescout.demo.widgets.client.ui.forms.BrowserFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.ButtonsForm;
import org.eclipsescout.demo.widgets.client.ui.forms.CalendarFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.CheckboxForm;
import org.eclipsescout.demo.widgets.client.ui.forms.DateFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.FileChooserFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.FormFieldTreeForm;
import org.eclipsescout.demo.widgets.client.ui.forms.GroupBoxForm;
import org.eclipsescout.demo.widgets.client.ui.forms.HTMLFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.KeyStrokeForm;
import org.eclipsescout.demo.widgets.client.ui.forms.LabelFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.ListBoxForm;
import org.eclipsescout.demo.widgets.client.ui.forms.MessageBoxesForm;
import org.eclipsescout.demo.widgets.client.ui.forms.NumberFieldsDecimalFieldsForm;
import org.eclipsescout.demo.widgets.client.ui.forms.PageFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.RadioButtonGroupForm;
import org.eclipsescout.demo.widgets.client.ui.forms.SVGFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.SequenceBoxForm;
import org.eclipsescout.demo.widgets.client.ui.forms.SmartFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.SplitBoxForm;
import org.eclipsescout.demo.widgets.client.ui.forms.StatusForm;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.TabBoxForm;
import org.eclipsescout.demo.widgets.client.ui.forms.TableFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.TreeBoxForm;
import org.eclipsescout.demo.widgets.client.ui.forms.TreeFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.WrappedFormFieldForm;
import org.eclipsescout.demo.widgets.client.ui.template.menu.AbstractViewSourceOnGitHubMenu;
import org.eclipsescout.demo.widgets.shared.Icons;

public class FormFieldsNodePage extends AbstractPageWithNodes {

  @Override
  protected String getConfiguredIconId() {
    return Icons.Forms;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("FormFields");
  }

  @Override
  protected void execCreateChildPages(Collection<IPage> pageList) throws ProcessingException {
    FormPage formPage0 = new FormPage(BrowserFieldForm.class);
    pageList.add(formPage0);
    FormPage formPage1 = new FormPage(ButtonsForm.class);
    pageList.add(formPage1);
    FormPage formPage2 = new FormPage(CalendarFieldForm.class);
    pageList.add(formPage2);
    FormPage formPage3 = new FormPage(CheckboxForm.class);
    pageList.add(formPage3);
    FormPage formPage5 = new FormPage(DateFieldForm.class);
    pageList.add(formPage5);
    FormPage formPage6 = new FormPage(FileChooserFieldForm.class);
    pageList.add(formPage6);
    FormPage formPage7 = new FormPage(GroupBoxForm.class);
    pageList.add(formPage7);
    FormPage formPage8 = new FormPage(HTMLFieldForm.class);
    pageList.add(formPage8);
    FormPage formPage9 = new FormPage(ImageFieldForm.class);
    pageList.add(formPage9);
    if (UserAgentUtility.isRichClient()) {
      FormPage formPage10 = new FormPage(KeyStrokeForm.class);
      pageList.add(formPage10);
    }
    FormPage formPage11 = new FormPage(LabelFieldForm.class);
    pageList.add(formPage11);
    FormPage formPage12 = new FormPage(ListBoxForm.class);
    pageList.add(formPage12);
    FormPage formPage14 = new FormPage(MessageBoxesForm.class);
    pageList.add(formPage14);
    FormPage formPage15 = new FormPage(NumberFieldsDecimalFieldsForm.class);
    pageList.add(formPage15);
    FormPage formPage16 = new FormPage(PageFieldForm.class);
    pageList.add(formPage16);
    FormPage formPage18 = new FormPage(RadioButtonGroupForm.class);
    pageList.add(formPage18);
    FormPage formPage19 = new FormPage(SmartFieldForm.class);
    pageList.add(formPage19);
    FormPage formPage20 = new FormPage(SequenceBoxForm.class);
    pageList.add(formPage20);
    FormPage formPage21 = new FormPage(SplitBoxForm.class);
    pageList.add(formPage21);
    FormPage formPage22 = new FormPage(StatusForm.class);
    pageList.add(formPage22);
    FormPage formPage23 = new FormPage(StringFieldForm.class);
    pageList.add(formPage23);
    FormPage formPage24 = new FormPage(SVGFieldForm.class);
    pageList.add(formPage24);
    FormPage formPage25 = new FormPage(TabBoxForm.class);
    pageList.add(formPage25);
    FormPage formPage26 = new FormPage(TableFieldForm.class);
    pageList.add(formPage26);
    FormPage formPage27 = new FormPage(TreeBoxForm.class);
    pageList.add(formPage27);
    FormPage formPage28 = new FormPage(TreeFieldForm.class);
    pageList.add(formPage28);
    FormPage formPage29 = new FormPage(WrappedFormFieldForm.class);
    pageList.add(formPage29);
  }

  @Override
  protected void execInitPage() throws ProcessingException {
  }

  @Override
  protected void execPageActivated() throws ProcessingException {
    FormFieldTreeForm form = new FormFieldTreeForm(this);
    setDetailForm(form);
    form.startPageForm();
    setTableVisible(false);
  }

  @Order(10.0)
  public class ViewSourceOnGitHubMenu extends AbstractViewSourceOnGitHubMenu {

    @Override
    protected Class<?> provideSourceClass() {
      return FormFieldTreeForm.class;
    }
  }
}
