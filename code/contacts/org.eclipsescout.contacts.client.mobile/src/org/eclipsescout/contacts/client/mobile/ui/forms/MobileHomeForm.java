/**
 * 
 */
package org.eclipsescout.contacts.client.mobile.ui.forms;

import java.util.List;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ClientJob;
import org.eclipse.scout.rt.client.mobile.transformation.DeviceTransformationConfig;
import org.eclipse.scout.rt.client.mobile.transformation.DeviceTransformationUtility;
import org.eclipse.scout.rt.client.mobile.transformation.MobileDeviceTransformation;
import org.eclipse.scout.rt.client.mobile.ui.basic.table.AbstractMobileTable;
import org.eclipse.scout.rt.client.mobile.ui.desktop.MobileDesktopUtility;
import org.eclipse.scout.rt.client.mobile.ui.form.AbstractMobileForm;
import org.eclipse.scout.rt.client.mobile.ui.form.outline.IOutlineChooserForm;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.extension.client.ui.form.fields.button.AbstractExtensibleButton;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.contacts.client.mobile.ui.forms.MobileHomeForm.MainBox.LogoutButton;
import org.eclipsescout.contacts.client.mobile.ui.forms.MobileHomeForm.MainBox.OutlinesTableField;
import org.eclipsescout.contacts.client.mobile.ui.forms.MobileHomeForm.MainBox.OutlinesTableField.Table;

/**
 * @author mzi
 */
public class MobileHomeForm extends AbstractMobileForm implements IOutlineChooserForm {

  /**
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
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
  protected boolean getConfiguredFooterVisible() {
    return true;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("MobileOutlineChooserTitle");
  }

  /**
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  public void startView() throws ProcessingException {
    startInternal(new ViewHandler());
  }

  /**
   * @return the LogoutButton
   */
  public LogoutButton getLogoutButton() {
    return getFieldByClass(LogoutButton.class);
  }

  /**
   * @return the MainBox
   */
  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  /**
   * @return the OutlinesTableField
   */
  public OutlinesTableField getOutlinesTableField() {
    return getFieldByClass(OutlinesTableField.class);
  }

  @Order(1000.0)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected boolean getConfiguredBorderVisible() {
      return false;
    }

    @Override
    protected void execInitField() throws ProcessingException {
      // Table already is scrollable, it's not necessary to make the form scrollable too
      DeviceTransformationConfig config = DeviceTransformationUtility.getDeviceTransformationConfig();
      if (config != null) {
        config.excludeFieldTransformation(this, MobileDeviceTransformation.MAKE_MAINBOX_SCROLLABLE);
      }
    }

    @Order(1000.0)
    public class OutlinesTableField extends AbstractTableField<Table> {

      @Override
      protected int getConfiguredGridH() {
        return 2;
      }

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      @Order(1000.0)
      public class Table extends AbstractMobileTable {

        @Override
        protected boolean getConfiguredAutoDiscardOnDelete() {
          return true;
        }

        @Override
        protected boolean getConfiguredAutoResizeColumns() {
          return true;
        }

        @Override
        protected String getConfiguredDefaultIconId() {
          return AbstractIcons.TreeNode;
        }

        @Override
        protected boolean getConfiguredSortEnabled() {
          return false;
        }

        @Override
        protected void execDecorateRow(ITableRow row) throws ProcessingException {
          String outlineIcon = getOutlineColumn().getValue(row).getIconId();
          if (outlineIcon != null) {
            row.setIconId(outlineIcon);
          }
        }

        @Override
        protected boolean execIsAutoCreateTableRowForm() {
          return false;
        }

        @Override
        protected void execRowsSelected(List<? extends ITableRow> rows) throws ProcessingException {
          if (rows == null || rows.size() == 0) {
            return;
          }
          IOutline outline = getOutlineColumn().getValue(rows.get(0));
          MobileDesktopUtility.activateOutline(outline);
          getDesktop().removeForm(MobileHomeForm.this);
          clearSelectionDelayed();
        }

        /**
         * @return the LabelColumn
         */
        public LabelColumn getLabelColumn() {
          return getColumnSet().getColumnByClass(LabelColumn.class);
        }

        /**
         * @return the OutlineColumn
         */
        public OutlineColumn getOutlineColumn() {
          return getColumnSet().getColumnByClass(OutlineColumn.class);
        }

        @Order(1000.0)
        public class OutlineColumn extends AbstractColumn<IOutline> {

          @Override
          protected boolean getConfiguredDisplayable() {
            return false;
          }
        }

        @Order(2000.0)
        public class LabelColumn extends AbstractStringColumn {
        }
      }
    }

    @Order(2000.0)
    public class LogoutButton extends AbstractExtensibleButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Logoff");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        ClientJob.getCurrentSession().stopSession();
      }
    }
  }

  public class ViewHandler extends AbstractFormHandler {

    @Override
    protected void execFinally() throws ProcessingException {
      Table table = getOutlinesTableField().getTable();
      table.discardAllRows();
    }

    @Override
    protected void execLoad() throws ProcessingException {
      Table table = getOutlinesTableField().getTable();
      List<IOutline> outlines = getDesktop().getAvailableOutlines();
      for (IOutline outline : outlines) {
        if (outline.isVisible() && outline.getRootNode() != null) {
          ITableRow row = table.createRow(new Object[]{outline, outline.getTitle()});
          row.setEnabled(outline.isEnabled());
          table.addRow(row);
        }
      }

    }
  }
}