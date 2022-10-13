/*
 * Copyright (c) 2020 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.docs.snippets.mobile;

import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableCompactHandler;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.CompactBean;
import org.eclipse.scout.rt.client.ui.basic.table.columns.CompactLine;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;

public class CompactTable {

  @ClassId("2450f369-4d5e-4cda-9985-0cd34be6a063")
  //tag::CompactHandler[]
  public class Table extends AbstractTable {
    @Override
    protected ITableCompactHandler createCompactHandler() {
      return super.createCompactHandler()
          .withTitleColumnSupplier(this::getAColumn)
          .withSubtitleColumnSupplier(this::getAnotherColumn);
    }

    //end::CompactHandler[]
    public AColumn getAColumn() {
      return getColumnSet().getColumnByClass(AColumn.class);
    }

    public AnotherColumn getAnotherColumn() {
      return getColumnSet().getColumnByClass(AnotherColumn.class);
    }

    @Order(1000)
    @ClassId("67ffc149-12c7-43af-8119-3e250eab68bc")
    public class AColumn extends AbstractStringColumn {
    }

    @Order(2000)
    @ClassId("db0a75a6-ba89-446e-8f9e-d4cf42e2ef94")
    public class AnotherColumn extends AbstractStringColumn {
    }
  }

  @ClassId("ceadc7ed-aafc-4704-861a-55c28e457b3d")
  //tag::CustomBeanBuilder[]
  public class Table2 extends AbstractTable {
    @Override
    protected ITableCompactHandler createCompactHandler() {
      return super.createCompactHandler()
          .withBeanBuilder((columns, row) -> {
            CompactBean bean = new CompactBean();
            bean.setTitle("custom title");
            bean.addContentLine(new CompactLine("label", "text"));
            return bean;
          });
    }
  }
  //end::CustomBeanBuilder[]

}
