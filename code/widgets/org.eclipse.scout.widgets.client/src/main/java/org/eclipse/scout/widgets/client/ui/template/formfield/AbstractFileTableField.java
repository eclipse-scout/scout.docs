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
package org.eclipse.scout.widgets.client.ui.template.formfield;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.action.keystroke.AbstractKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.filechooser.FileChooser;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.OpenUriAction;
import org.eclipse.scout.rt.client.ui.dnd.ResourceListTransferObject;
import org.eclipse.scout.rt.client.ui.dnd.TransferObject;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.widgets.client.ResourceBase;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFileTableField.Table.DeleteMenu;
import org.eclipse.scout.widgets.shared.FileCodeType;

public abstract class AbstractFileTableField extends AbstractTableField<AbstractFileTableField.Table> {
  protected static final String FILE_SIZE_FORMAT = "#,### KB";
  protected static final long FILE_SIZE_FACTOR = 1024;

  @Override
  protected void execInitField() {
    URL url = ResourceBase.class.getResource("images/bird_1008.jpg");

    try (InputStream in = url.openStream()) {
      byte[] content = IOUtility.readBytes(in);
      BinaryResource file = new BinaryResource("bird.jpg", content);
      getTable().addRowByArray(fileToArray(file));
    }
    catch (IOException e) {
      throw new ProcessingException("", e);
    }
  }

  /**
   * Allows user of this template field to react to row selection events
   *
   * @param Resource
   *          the resource that is represented by the clicked row
   */
  protected void execResourceRowClick(BinaryResource resource) {
  }

  private Object[] fileToArray(BinaryResource file) {
    String type = IOUtility.getFileExtension(file.getFilename());
    int size = file.getContentLength();

    if (BEANS.get(FileCodeType.class).getCode(type) == null) {
      type = FileCodeType.UknownCode.ID;
    }

    size /= FILE_SIZE_FACTOR;

    return new Object[]{file, file.getFilename(), size, type, new Date(file.getLastModified())};
  }

  @Order(10)
  public class DeleteKeyStroke extends AbstractKeyStroke {

    @Override
    protected String getConfiguredKeyStroke() {
      return "delete";
    }

    @Override
    protected void execAction() {
      getTable().getMenuByClass(DeleteMenu.class).doAction();
    }
  }

  public class Table extends AbstractTable {

    private Set<BinaryResource> m_keys = new HashSet<>();

    public SizeColumn getSizeColumn() {
      return getColumnSet().getColumnByClass(SizeColumn.class);
    }

    public TypeColumn getTypeColumn() {
      return getColumnSet().getColumnByClass(TypeColumn.class);
    }

    @Override
    protected boolean getConfiguredAutoResizeColumns() {
      return true;
    }

    @Override
    protected boolean getConfiguredMultiSelect() {
      return false;
    }

    @Override
    protected int getConfiguredDragType() {
      return TYPE_FILE_TRANSFER;
    }

    @Override
    protected int getConfiguredDropType() {
      return TYPE_FILE_TRANSFER;
    }

    @Override
    protected TransferObject execDrag(List<ITableRow> rows) {
      List<BinaryResource> resources = new ArrayList<BinaryResource>();

      for (ITableRow row : rows) {
        resources.add(getTable().getResourceColumn().getValue(row));
      }

      return new ResourceListTransferObject(resources);
    }

    @Override
    protected void execDrop(ITableRow row, TransferObject t) {
      clearErrorStatus();

      try {
        if (t instanceof ResourceListTransferObject) {
          for (BinaryResource resource : ((ResourceListTransferObject) t).getResources()) {
            addFile(resource);
          }
        }
      }
      catch (RuntimeException e) {
        addErrorStatus(e.getMessage());
        throw new ProcessingException(e.getMessage(), e);
      }
    }

    protected void addFile(BinaryResource file) {
      if (m_keys.contains(file)) {
        return;
      }
      addRowByArray(fileToArray(file));
      m_keys.add(file);
    }

    @Override
    protected void execRowAction(ITableRow row) {
      getMenuByClass(OpenMenu.class).doAction();
    }

    @Override
    protected void execRowsSelected(List<? extends ITableRow> rows) {
      BinaryResource resource = CollectionUtility.firstElement(getResourceColumn().getValues(rows));
      execResourceRowClick(resource);
    }

    public DateModifiedColumn getDateModifiedColumn() {
      return getColumnSet().getColumnByClass(DateModifiedColumn.class);
    }

    public ResourceColumn getResourceColumn() {
      return getColumnSet().getColumnByClass(ResourceColumn.class);
    }

    public NameColumn getNameColumn() {
      return getColumnSet().getColumnByClass(NameColumn.class);
    }

    @Order(10)
    public class ResourceColumn extends AbstractColumn<BinaryResource> {

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }

      @Override
      protected boolean getConfiguredPrimaryKey() {
        return true;
      }

      @Override
      protected boolean getConfiguredVisible() {
        return false;
      }
    }

    @Order(20)
    public class NameColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Name");
      }

      @Override
      protected int getConfiguredSortIndex() {
        return 0;
      }
    }

    @Order(30)
    public class SizeColumn extends AbstractLongColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Size");
      }

      @Override
      protected void execInitColumn() {
        setFormat(new DecimalFormat(FILE_SIZE_FORMAT));
      }
    }

    @Order(40)
    public class TypeColumn extends AbstractSmartColumn<String> {

      @Override
      protected Class<? extends ICodeType<?, String>> getConfiguredCodeType() {
        return FileCodeType.class;
      }

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Type");
      }
    }

    @Order(50)
    public class DateModifiedColumn extends AbstractDateTimeColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("DateModified");
      }
    }

    @Order(10)
    public class OpenMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("OpenFile");
      }

      @Override
      protected void execAction() {
        List<BinaryResource> resources = getResourceColumn().getSelectedValues();
        for (BinaryResource resource : resources) {
          ClientSessionProvider.currentSession().getDesktop().openUri(resource, OpenUriAction.DOWNLOAD);
        }
      }
    }

    @Order(20)
    public class AddMenu extends AbstractMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("AddFile");
      }

      @Override
      protected void execAction() {
        FileChooser fc = new FileChooser(true);
        List<BinaryResource> files = fc.startChooser();
        for (BinaryResource file : files) {
          addFile(file);
        }
      }

    }

    @Order(30)
    public class DeleteMenu extends AbstractMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(TableMenuType.MultiSelection, TableMenuType.SingleSelection);
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("DeleteFile");
      }

      @Override
      protected void execAction() {
        for (ITableRow row : getSelectedRows()) {
          m_keys.remove(row.getKeyValues().get(0));
          row.delete();
        }
      }
    }
  }
}
