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
package org.eclipsescout.demo.widgets.client.mobile.ui.forms;

import java.util.List;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.mobile.transformation.IDeviceTransformationService;
import org.eclipse.scout.rt.client.mobile.transformation.MobileDeviceTransformation;
import org.eclipse.scout.rt.client.mobile.ui.basic.table.AbstractMobileTable;
import org.eclipse.scout.rt.client.mobile.ui.desktop.MobileDesktopUtility;
import org.eclipse.scout.rt.client.mobile.ui.form.AbstractMobileForm;
import org.eclipse.scout.rt.client.mobile.ui.form.outline.IOutlineChooserForm;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractArrayTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.mobile.ui.forms.MobileHomeForm.MainBox.LogoutButton;
import org.eclipsescout.demo.widgets.client.mobile.ui.forms.MobileHomeForm.MainBox.OutlinesTableField;

public class MobileHomeForm extends AbstractMobileForm implements IOutlineChooserForm {

  public MobileHomeForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return DISPLAY_HINT_VIEW;
  }

  @Override
  protected String getConfiguredDisplayViewId() {
    return VIEW_ID_CENTER;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("MobileOutlineChooserTitle");
  }

  @Override
  protected boolean getConfiguredFooterVisible() {
    return true;
  }

  public LogoutButton getLogoutButton() {
    return getFieldByClass(LogoutButton.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public OutlinesTableField getOutlinesTableField() {
    return getFieldByClass(OutlinesTableField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected boolean getConfiguredBorderVisible() {
      return false;
    }

    @Override
    protected void execInitField() throws ProcessingException {
      // Table already is scrollable, it's not necessary to make the form scrollable too
      IDeviceTransformationService service = BEANS.get(IDeviceTransformationService.class);
      if (service != null && service.getDeviceTransformer() != null) {
        service.getDeviceTransformer().getDeviceTransformationConfig().excludeFieldTransformation(this, MobileDeviceTransformation.MAKE_MAINBOX_SCROLLABLE);
      }
    }

    @Order(10.0)
    public class OutlinesTableField extends AbstractArrayTableField<OutlinesTableField.Table> {

      @Order(10.0)
      public class Table extends AbstractMobileTable {

        @Override
        protected boolean execIsAutoCreateTableRowForm() {
          return false;
        }

        @Override
        protected boolean getConfiguredAutoDiscardOnDelete() {
          return true;
        }

        @Override
        protected String getConfiguredDefaultIconId() {
          return AbstractIcons.TreeNode;
        }

        @Override
        protected boolean getConfiguredAutoResizeColumns() {
          return true;
        }

        @Override
        protected boolean getConfiguredSortEnabled() {
          return false;
        }

        @Override
        protected void execDecorateRow(ITableRow row) throws ProcessingException {
          String outlineIcon = getOutlineColumn().getValue(row).getDefaultIconId();
          if (outlineIcon != null) {
            row.setIconId(outlineIcon);
          }
        }

        @Override
        protected void execRowsSelected(List<? extends ITableRow> rows) throws ProcessingException {
          if (CollectionUtility.hasElements(rows)) {
            IOutline outline = getOutlineColumn().getValue(CollectionUtility.firstElement(rows));
            MobileDesktopUtility.activateOutline(outline);
            getDesktop().hideForm(MobileHomeForm.this);
            clearSelectionDelayed();
          }
        }

        public LabelColumn getLabelColumn() {
          return getColumnSet().getColumnByClass(LabelColumn.class);
        }

        public OutlineColumn getOutlineColumn() {
          return getColumnSet().getColumnByClass(OutlineColumn.class);
        }

        @Order(10.0)
        public class OutlineColumn extends AbstractColumn<IOutline> {

          @Override
          protected boolean getConfiguredDisplayable() {
            return false;
          }
        }

        @Order(20.0)
        public class LabelColumn extends AbstractStringColumn {
        }
      }

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      @Override
      protected int getConfiguredGridH() {
        return 2;
      }
    }

    @Order(20.0)
    public class LogoutButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Logoff");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        ClientSessionProvider.currentSession().stop();
      }
    }

  }

  public class ViewHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() throws ProcessingException {
      OutlinesTableField.Table table = getOutlinesTableField().getTable();
      for (IOutline outline : getDesktop().getAvailableOutlines()) {
        if (outline.isVisible() && outline.getRootNode() != null) {
          ITableRow row = table.createRow(new Object[]{outline, outline.getTitle()});
          row.setEnabled(outline.isEnabled());
          table.addRow(row);
        }
      }
    }

    @Override
    protected void execFinally() throws ProcessingException {
      OutlinesTableField.Table table = getOutlinesTableField().getTable();
      table.discardAllRows();
    }
  }

  public void startView() throws ProcessingException {
    startInternal(new ViewHandler());
  }
}
