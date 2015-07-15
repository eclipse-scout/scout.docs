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
package org.eclipsescout.demo.widgets.client.old;

import java.util.List;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.extension.client.ui.desktop.outline.pages.AbstractExtensiblePageWithNodes;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.ui.UserAgentUtility;
import org.eclipsescout.demo.widgets.client.old.ui.forms.ColorFieldForm;
import org.eclipsescout.demo.widgets.client.old.ui.forms.ContextMenuForm;
import org.eclipsescout.demo.widgets.client.old.ui.forms.FileChooserFieldForm;
import org.eclipsescout.demo.widgets.client.old.ui.forms.FormWithToolbuttonsForm;
import org.eclipsescout.demo.widgets.client.old.ui.forms.KeyStrokeForm;
import org.eclipsescout.demo.widgets.client.old.ui.forms.StatusForm;
import org.eclipsescout.demo.widgets.client.old.ui.forms.TreeDNDForm;
import org.eclipsescout.demo.widgets.client.ui.desktop.pages.FormPage;
import org.eclipsescout.demo.widgets.shared.old.Icons;

/**
 * @author mzi
 */
public class FormFieldsNodePage extends AbstractExtensiblePageWithNodes {

  @Override
  protected String getConfiguredIconId() {
    return Icons.Forms;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("FormFields");
  }

  @Override
  protected void execCreateChildPages(List<IPage> pageList) throws ProcessingException {
    FormPage formPage4 = new FormPage(ColorFieldForm.class);
    pageList.add(formPage4);

    FormPage formPage4_1 = new FormPage(ContextMenuForm.class);
    pageList.add(formPage4_1);

    FormPage formPage6 = new FormPage(FileChooserFieldForm.class);
    pageList.add(formPage6);

    if (UserAgentUtility.isRichClient()) {
      FormPage formPage10 = new FormPage(KeyStrokeForm.class);
      pageList.add(formPage10);
    }

    FormPage formPage22 = new FormPage(StatusForm.class);
    pageList.add(formPage22);

    FormPage formPage29 = new FormPage(TreeDNDForm.class);
    pageList.add(formPage29);

    if (UserAgentUtility.isSwtUi() || UserAgentUtility.isWebClient()) {
      FormPage formPage31 = new FormPage(FormWithToolbuttonsForm.class);
      pageList.add(formPage31);
    }
  }
}
