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
package org.eclipsescout.demo.minifigcreator.client.ui.forms;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIntegerColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.demo.minifigcreator.client.ui.forms.ServerForm.MainBox.CancelButton;
import org.eclipsescout.demo.minifigcreator.client.ui.forms.ServerForm.MainBox.ContainerBox;
import org.eclipsescout.demo.minifigcreator.client.ui.forms.ServerForm.MainBox.ContainerBox.InfoField;
import org.eclipsescout.demo.minifigcreator.client.ui.forms.ServerForm.MainBox.ContainerBox.TableField;
import org.eclipsescout.demo.minifigcreator.client.ui.forms.ServerForm.MainBox.ContainerBox.TableField.Table;
import org.eclipsescout.demo.minifigcreator.client.ui.forms.ServerForm.MainBox.OkButton;
import org.eclipsescout.demo.minifigcreator.client.ui.forms.ServerForm.MainBox.RandomButton;
import org.eclipsescout.demo.minifigcreator.shared.minifig.part.Part;
import org.eclipsescout.demo.minifigcreator.shared.minifig.part.PartUtility;
import org.eclipsescout.demo.minifigcreator.shared.services.process.IServerProcessService;
import org.eclipsescout.demo.minifigcreator.shared.services.process.ServerFormData;

@FormData(value = ServerFormData.class, sdkCommand = SdkCommand.CREATE)
public class ServerForm extends AbstractForm {

  public ServerForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Server");
  }

  public void startModify() throws ProcessingException {
    startInternal(new ModifyHandler());
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public ContainerBox getContainerBox() {
    return getFieldByClass(ContainerBox.class);
  }

  public InfoField getInfoField() {
    return getFieldByClass(InfoField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public RandomButton getRandomButton() {
    return getFieldByClass(RandomButton.class);
  }

  public TableField getTableField() {
    return getFieldByClass(TableField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(30.0)
    public class ContainerBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Order(10.0)
      @FormData(sdkCommand = SdkCommand.IGNORE)
      public class InfoField extends AbstractLabelField {

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execInitField() throws ProcessingException {
          setValue(TEXTS.get("WarningMinifigMightBeLost"));
        }
      }

      @Order(20.0)
      public class TableField extends AbstractTableField<TableField.Table> {

        @Override
        protected int getConfiguredGridH() {
          return 18;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Order(10.0)
        public class Table extends AbstractTable {

          public NameColumn getNameColumn() {
            return getColumnSet().getColumnByClass(NameColumn.class);
          }

          public PartColumn getPartColumn() {
            return getColumnSet().getColumnByClass(PartColumn.class);
          }

          public QuantityColumn getQuantityColumn() {
            return getColumnSet().getColumnByClass(QuantityColumn.class);
          }

          public TypeColumn getTypeColumn() {
            return getColumnSet().getColumnByClass(TypeColumn.class);
          }

          @Order(10.0)
          public class PartColumn extends AbstractColumn<Part> {

            @Override
            protected boolean getConfiguredDisplayable() {
              return false;
            }

            @Override
            protected void execDecorateCell(Cell cell, ITableRow row) throws ProcessingException {
              Part part = getValue(row);
              row.setIconId(PartUtility.calculateSmallIconId(part));
            }
          }

          @Order(20.0)
          public class TypeColumn extends AbstractStringColumn {

            @Override
            protected boolean getConfiguredAlwaysIncludeSortAtBegin() {
              return true;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Type");
            }

            @Override
            protected int getConfiguredSortIndex() {
              return 1;
            }

            @Override
            protected int getConfiguredWidth() {
              return 100;
            }
          }

          @Order(30.0)
          public class NameColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Name");
            }

            @Override
            protected int getConfiguredWidth() {
              return 120;
            }
          }

          @Order(40.0)
          public class QuantityColumn extends AbstractIntegerColumn {

            @Override
            protected boolean getConfiguredEditable() {
              return true;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Quantity");
            }

            @Override
            protected int getConfiguredWidth() {
              return 80;
            }
          }
        }
      }
    }

    @Order(40.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(50.0)
    public class CancelButton extends AbstractCancelButton {
    }

    @Order(60.0)
    public class RandomButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("RandomValues");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        Table table = getTableField().getTable();
        ITableRow[] rows = table.getRows();
        for (ITableRow r : rows) {
          int q = (int) (Math.random() * 10) + 1;
          table.getQuantityColumn().setValue(r, Integer.valueOf(q));
        }
      }
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    public void execLoad() throws ProcessingException {
      IServerProcessService service = SERVICES.getService(IServerProcessService.class);
      ServerFormData formData = new ServerFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() throws ProcessingException {
      IServerProcessService service = SERVICES.getService(IServerProcessService.class);
      ServerFormData formData = new ServerFormData();
      exportFormData(formData);
      formData = service.store(formData);
    }
  }
}
