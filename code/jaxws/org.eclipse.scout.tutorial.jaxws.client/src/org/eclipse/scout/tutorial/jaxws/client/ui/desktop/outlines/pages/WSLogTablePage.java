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
package org.eclipse.scout.tutorial.jaxws.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.service.SERVICES;
import org.eclipse.scout.tutorial.jaxws.client.ui.forms.WSLogForm;
import org.eclipse.scout.tutorial.jaxws.shared.services.outline.IStandardOutlineService;

public class WSLogTablePage extends AbstractPageWithTable<WSLogTablePage.Table> {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("WSLog");
  }

  @Override
  protected Object[][] execLoadTableData(SearchFilter filter) throws ProcessingException {
    return SERVICES.getService(IStandardOutlineService.class).getWsLogTableData();
  }

  @Order(10.0)
  public class Table extends AbstractTable {

    @Override
    protected boolean getConfiguredAutoResizeColumns() {
      return true;
    }

    public DateColumn getDateColumn() {
      return getColumnSet().getColumnByClass(DateColumn.class);
    }

    public OperationColumn getOperationColumn() {
      return getColumnSet().getColumnByClass(OperationColumn.class);
    }

    public PortColumn getPortColumn() {
      return getColumnSet().getColumnByClass(PortColumn.class);
    }

    public ServiceColumn getServiceColumn() {
      return getColumnSet().getColumnByClass(ServiceColumn.class);
    }

    public WsLogNrColumn getWsLogNrColumn() {
      return getColumnSet().getColumnByClass(WsLogNrColumn.class);
    }

    @Order(10.0)
    public class WsLogNrColumn extends AbstractLongColumn {

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }
    }

    @Order(20.0)
    public class DateColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Date");
      }
    }

    @Order(30.0)
    public class ServiceColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Service");
      }
    }

    @Order(40.0)
    public class PortColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Port");
      }
    }

    @Order(50.0)
    public class OperationColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Operation");
      }
    }

    @Order(10.0)
    public class ViewWSLogMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ViewWSLog");
      }

      @Override
      protected void execAction() throws ProcessingException {
        WSLogForm form = new WSLogForm();
        form.setWSLogNr(getWsLogNrColumn().getSelectedValue());
        form.startModify();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }
  }
}
