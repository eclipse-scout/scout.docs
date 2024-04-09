/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.template.formfield;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.action.keystroke.AbstractKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.filechooser.FileChooser;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBeanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.OpenUriAction;
import org.eclipse.scout.rt.client.ui.dnd.ResourceListTransferObject;
import org.eclipse.scout.rt.client.ui.dnd.TransferObject;
import org.eclipse.scout.rt.client.ui.form.fields.ModelVariant;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.FileUtility;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.widgets.client.ResourceBase;
import org.eclipse.scout.widgets.client.ui.forms.ExampleBean;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFileTableField.Table;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFileTableField.Table.DeleteMenu;
import org.eclipse.scout.widgets.shared.FileCodeType;
import org.eclipse.scout.widgets.shared.FileCodeType.JpgCode;
import org.eclipse.scout.widgets.shared.FileCodeType.PngCode;
import org.eclipse.scout.widgets.shared.FileCodeType.UknownCode;

@ClassId("b5d2ea93-5577-4982-84be-ca7e28f0b68d")
public abstract class AbstractFileTableField extends AbstractTableField<Table> {
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
   * @param resource
   *          the resource that is represented by the clicked row
   */
  protected void execResourceRowClick(BinaryResource resource) {
  }

  private Object[] fileToArray(BinaryResource file) {
    String type = FileUtility.getFileExtension(file.getFilename());
    int size = file.getContentLength();

    if (BEANS.get(FileCodeType.class).getCode(type) == null) {
      type = UknownCode.ID;
    }

    size /= FILE_SIZE_FACTOR;

    ExampleBean bean = new ExampleBean();
    bean.setHeader(file.getFilename());
    bean.setImage(file);

    return new Object[]{file, null, bean, file.getFilename(), size, type, new Date(file.getLastModified())};
  }

  @Order(10)
  @ClassId("1584dd5b-6a2a-494b-a969-09e6fd9b6aa9")
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

  @ClassId("436edaab-155c-4f33-bc33-38746bd0b617")
  public class Table extends AbstractTable {

    private final Set<BinaryResource> m_keys = new HashSet<>();

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
    protected int getConfiguredDropType() {
      return TYPE_FILE_TRANSFER;
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

    public HtmlImageColumn getHtmlImageColumn() {
      return getColumnSet().getColumnByClass(HtmlImageColumn.class);
    }

    public NameColumn getNameColumn() {
      return getColumnSet().getColumnByClass(NameColumn.class);
    }

    @Order(10)
    @ClassId("f7cbf367-528c-4271-9313-fb2d722afbc7")
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

    @Order(15)
    @ClassId("fa44a8c3-8055-4564-8f0d-44b2af093489")
    public class HtmlImageColumn extends AbstractStringColumn {

      @Override
      protected boolean getConfiguredHtmlEnabled() {
        return true;
      }

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Image_StringColumn");
      }

      @Override
      protected void execDecorateCell(Cell cell, ITableRow row) {
        if (StringUtility.equalsIgnoreCase(getTypeColumn().getValue(row), JpgCode.ID)
            || StringUtility.equalsIgnoreCase(getTypeColumn().getValue(row), PngCode.ID)) {
          BinaryResource value = getResourceColumn().getValue(row);
          if (value != null) {
            addAttachment(value);
            cell.setText(HTML.imgByBinaryResource(value.getFilename())
                .cssClass("table-cell-html-image")
                .addAttribute("draggable", "false") // avoid conflict with table selection handler
                .toHtml());
          }
        }
      }

      @Override
      protected int getConfiguredWidth() {
        return 150;
      }
    }

    @Order(18)
    @ModelVariant("widgets.Example")
    @ClassId("cb8042a0-934d-4914-b9c4-9019b0aa1d49")
    public class ImageBeanColumn extends AbstractBeanColumn<ExampleBean> {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Image_BeanColumn");
      }

      @Override
      protected int getConfiguredWidth() {
        return 150;
      }
    }

    @Order(20)
    @ClassId("58698e63-e617-4345-9757-02d9a1ecdb91")
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
    @ClassId("b6e37411-9c0d-4de6-bd39-c08fd7d8872b")
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
    @ClassId("02e1b538-9550-4d44-b953-8dadb4f317cc")
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
    @ClassId("cecde15c-a7ca-4229-ad4c-563453205d15")
    public class DateModifiedColumn extends AbstractDateTimeColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("DateModified");
      }
    }

    @Order(10)
    @ClassId("5279dc7f-375c-4dd0-a482-4d42c59d84ab")
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
    @ClassId("0c29ba27-53d4-4362-a790-5b365cab091a")
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
    @ClassId("4bcc945d-4d80-426c-8a39-ec8bc24c4b9e")
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
