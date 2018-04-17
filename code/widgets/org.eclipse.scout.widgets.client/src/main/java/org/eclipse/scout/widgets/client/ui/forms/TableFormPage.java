/*******************************************************************************
 * Copyright (c) 2018 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.widgets.client.ui.forms;

import org.eclipse.scout.widgets.client.ui.desktop.pages.FormPage;
import org.eclipse.scout.widgets.client.ui.desktop.pages.IAdvancedFormPage;

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
  protected void execCreateChildPages(java.util.List<org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage<?>> pageList) {
    pageList.add(new FormPage(HierarchicalTableFieldForm.class));
  }

}
