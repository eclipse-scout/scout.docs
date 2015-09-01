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
package org.eclipsescout.demo.widgets.client.ui.desktop.pages;

import java.util.Date;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipsescout.demo.widgets.client.ui.desktop.pages.PageWithDetailForm.FileTable;
import org.eclipsescout.demo.widgets.client.ui.forms.FileDetailForm;
import org.eclipsescout.demo.widgets.client.ui.forms.FileDetailForm.MainBox.FileDetailsBox;
import org.eclipsescout.demo.widgets.client.ui.template.formfield.AbstractFileTable;

public class PageWithDetailForm extends AbstractPageWithTable<FileTable> {

  private FileDetailForm m_detailForm;

  public PageWithDetailForm() throws ProcessingException {
    super(true, FileDetailForm.class.getName());
  }

  @Override
  protected String getConfiguredIconId() {
    return org.eclipse.scout.rt.shared.AbstractIcons.TreeNodeOpen;
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("PageWithADetailForm");
  }

  @Override
  protected void execInitPage() throws ProcessingException {
    m_detailForm = new FileDetailForm();
    setDetailForm(m_detailForm);
    m_detailForm.startNew();
  }

  @Override
  protected void execLoadData(SearchFilter filter) throws ProcessingException {
    getTable().addSampleRow();
  }

  @Order(10.0)
  public class FileTable extends AbstractFileTable {

    @Override
    protected void onRowClicked(ITableRow row) {
      FileTable table = getTable();
      String name = table.getNameColumn().getValue(row);
      Long size = table.getSizeColumn().getValue(row);
      Date modified = table.getDateModifiedColumn().getValue(row);
      Boolean readonly = table.getReadOnlyColumn().getValue(row);

      FileDetailsBox box = ((FileDetailForm) getDetailForm()).getFileDetailsBox();
      box.getNameField().setValue(name);
      box.getSizeField().setValue(size);
      box.getModifiedField().setValue(modified);
      box.getReadOnlyField().setValue(readonly);
    }
  }
}
