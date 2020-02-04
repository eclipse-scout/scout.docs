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
package org.eclipse.scout.widgets.client.ui.forms;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.widgets.client.ui.desktop.pages.FormPage;
import org.eclipse.scout.widgets.client.ui.desktop.pages.IAdvancedFormPage;

@ClassId("4afbc42b-3d15-4ced-abcb-4979ac2b0542")
public class TableFormPage extends FormPage implements IAdvancedFormPage {

  public TableFormPage() {
    super(TableFieldForm.class);
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return false;
  }

  @Override
  protected boolean getConfiguredExpanded() {
    return true;
  }

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    pageList.add(new FormPage(HierarchicalTableFieldForm.class));
  }

}
