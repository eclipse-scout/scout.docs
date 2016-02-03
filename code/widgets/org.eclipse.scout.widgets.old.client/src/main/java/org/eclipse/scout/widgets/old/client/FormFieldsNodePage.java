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
package org.eclipse.scout.widgets.old.client;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.old.client.ui.forms.BrowserFieldForm;
import org.eclipse.scout.widgets.old.client.ui.forms.CalendarFieldForm;
import org.eclipse.scout.widgets.old.client.ui.forms.ColorFieldForm;
import org.eclipse.scout.widgets.old.client.ui.forms.ContextMenuForm;
import org.eclipse.scout.widgets.old.client.ui.forms.FileChooserFieldForm;
import org.eclipse.scout.widgets.old.client.ui.forms.PageFieldForm;
import org.eclipse.scout.widgets.old.client.ui.forms.StatusForm;
import org.eclipse.scout.widgets.old.client.ui.forms.TreeDNDForm;
import org.eclipse.scout.widgets.old.client.ui.forms.WrappedFormFieldForm;
import org.eclipse.scout.widgets.client.ui.desktop.pages.FormPage;
import org.eclipse.scout.widgets.shared.Icons;

/**
 * @author mzi
 */
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
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    FormPage formPage0 = new FormPage(BrowserFieldForm.class);
    pageList.add(formPage0);

    FormPage formPage2 = new FormPage(CalendarFieldForm.class);
    pageList.add(formPage2);

    FormPage formPage4 = new FormPage(ColorFieldForm.class);
    pageList.add(formPage4);

    FormPage formPage4_1 = new FormPage(ContextMenuForm.class);
    pageList.add(formPage4_1);

    FormPage formPage6 = new FormPage(FileChooserFieldForm.class);
    pageList.add(formPage6);

    FormPage formPage16 = new FormPage(PageFieldForm.class);
    pageList.add(formPage16);

    FormPage formPage22 = new FormPage(StatusForm.class);
    pageList.add(formPage22);

    FormPage formPage29 = new FormPage(TreeDNDForm.class);
    pageList.add(formPage29);

    FormPage formPage30 = new FormPage(WrappedFormFieldForm.class);
    pageList.add(formPage30);

  }
}
