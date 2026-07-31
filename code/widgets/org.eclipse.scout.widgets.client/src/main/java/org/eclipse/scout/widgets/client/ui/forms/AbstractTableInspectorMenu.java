/*
 * Copyright (c) 2010, 2026 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.forms;

import java.util.Set;
import java.util.function.Function;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.ITable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.form.FormEvent;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.widgets.shared.Icons;

@ClassId("24ffb35c-6239-41ec-9536-4c20e84afe29")
public abstract class AbstractTableInspectorMenu extends AbstractMenu {

  protected TableInspectorForm m_inspectorForm;

  protected ITable getInspectedTable() {
    return getContainer() instanceof ITable table ? table : null;
  }

  protected Function<ITableRow, String> getNameProvider() {
    return null;
  }

  @Override
  protected Set<? extends IMenuType> getConfiguredMenuTypes() {
    return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
  }

  @Override
  protected String getConfiguredIconId() {
    return Icons.Info;
  }

  @Override
  protected String getConfiguredTooltipText() {
    return "Table Inspector";
  }

  @Override
  protected byte getConfiguredHorizontalAlignment() {
    return HORIZONTAL_ALIGNMENT_RIGHT;
  }

  @Override
  protected boolean getConfiguredStackable() {
    return false;
  }

  @Override
  @SuppressWarnings("CodeBlock2Expr")
  protected void execAction() {
    if (m_inspectorForm != null) {
      m_inspectorForm.doClose();
    }
    else {
      m_inspectorForm = new TableInspectorForm(getInspectedTable(), getNameProvider());
      m_inspectorForm.addFormListener(event -> {
        m_inspectorForm = null;
      }, FormEvent.TYPE_CLOSED);
      m_inspectorForm.start();
    }
  }
}
