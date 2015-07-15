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
package org.eclipsescout.demo.widgets.client.ui.template.formfield;

import java.io.File;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.keystroke.AbstractKeyStroke;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipsescout.demo.widgets.client.ui.template.formfield.AbstractFileTable.DeleteMenu;

public abstract class AbstractFileTableField extends AbstractTableField<AbstractFileTableField.Table> {
// TODO cleanup
//  protected static final String FILE_SIZE_FORMAT = "#,### KB";
//  protected static final long FILE_SIZE_FACTOR = 1024;
//
//  private static final String BIRD = "bird.jpg";

//  @Override
//  protected void execInitField() throws ProcessingException {
//    URL url = ResourceUtility.getImageResource(BIRD);
//
//    try {
//      byte[] content = IOUtility.getContent(new BufferedInputStream(url.openStream()));
//      File file = IOUtility.createTempFile("bird.jpg", (byte[]) content);
//      getTable().addRowByArray(fileToArray(file));
//    }
//    catch (Exception e) {
//      throw new ProcessingException("", e);
//    }
//  }
//
  /**
   * Allows user of this template field to react to row selection events
   *
   * @param file
   *          the file that is represented by the clicked row
   * @throws ProcessingException
   */
  protected void execFileRowClick(File file) {
  }
//
//  private Object[] fileToArray(File file) {
//    String type = IOUtility.getFileExtension(file.getName());
//    Long size = file.length();
//
//    if (file.isDirectory()) {
//      type = FileCodeType.DirectoryCode.ID;
//      size = null;
//    }
//    else {
//      if (CODES.getCodeType(FileCodeType.class).getCode(type) == null) {
//        type = FileCodeType.UknownCode.ID;
//      }
//
//      size /= FILE_SIZE_FACTOR;
//    }
//
//    return new Object[]{file, file.getName(), size, type, new Date(file.lastModified()), !file.canWrite()};
//  }

  @Order(10.0)
  public class DeleteKeyStroke extends AbstractKeyStroke {

    @Override
    protected String getConfiguredKeyStroke() {
      return "delete";
    }

    @Override
    protected void execAction() throws ProcessingException {
      getTable().getMenu(DeleteMenu.class).doAction();
    }
  }

  @Order(10.0)
  public class Table extends AbstractFileTable {

    @Override
    protected void onRowClicked(ITableRow row) {
      execFileRowClick(getFile(row));
    }
  }
}
