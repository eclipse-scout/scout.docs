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
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.widgets.client.ui.desktop.pages.FormPage;
import org.eclipse.scout.widgets.client.ui.desktop.pages.FormPageParent;
import org.eclipse.scout.widgets.old.client.ui.forms.ColorFieldForm;
import org.eclipse.scout.widgets.old.client.ui.forms.ContextMenuForm;
import org.eclipse.scout.widgets.old.client.ui.forms.PageFieldForm;
import org.eclipse.scout.widgets.old.client.ui.forms.StatusForm;
import org.eclipse.scout.widgets.old.client.ui.forms.TreeDNDForm;
import org.eclipse.scout.widgets.old.client.ui.forms.WrappedFormFieldForm;
import org.eclipse.scout.widgets.shared.Icons;

/**
 * @author mzi
 */
@FormPageParent
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
    pageList.add(new FormPage(ColorFieldForm.class));
    pageList.add(new FormPage(ContextMenuForm.class));
    pageList.add(new FormPage(PageFieldForm.class));
    pageList.add(new FormPage(StatusForm.class));
    pageList.add(new FormPage(TreeDNDForm.class));
    pageList.add(new FormPage(WrappedFormFieldForm.class));
  }
}
