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
package org.eclipsescout.demo.ibug.client.ui.forms;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIntegerColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.shell.IShellService;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.demo.ibug.client.ui.forms.DesktopForm.MainBox.DesktopBox;
import org.eclipsescout.demo.ibug.client.ui.forms.DesktopForm.MainBox.DesktopBox.BugsField;
import org.eclipsescout.demo.ibug.client.ui.forms.DesktopForm.MainBox.DesktopBox.SearchBox;
import org.eclipsescout.demo.ibug.client.ui.forms.DesktopForm.MainBox.DesktopBox.SearchBox.AssigneeField;
import org.eclipsescout.demo.ibug.client.ui.forms.DesktopForm.MainBox.DesktopBox.SearchBox.ProductField;
import org.eclipsescout.demo.ibug.client.ui.forms.DesktopForm.MainBox.DesktopBox.SearchBox.RefreshButton;
import org.eclipsescout.demo.ibug.shared.Icons;
import org.eclipsescout.demo.ibug.shared.services.DesktopFormData;
import org.eclipsescout.demo.ibug.shared.services.IDesktopService;

@FormData(value = DesktopFormData.class, sdkCommand = SdkCommand.CREATE)
public class DesktopForm extends AbstractForm {

  public DesktopForm() throws ProcessingException {
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
  protected String getConfiguredIconId() {
    return Icons.EclipseScout;
  }

  public void startView() throws ProcessingException {
    startInternal(new ViewHandler());
  }

  public AssigneeField getAssigneeField() {
    return getFieldByClass(AssigneeField.class);
  }

  public BugsField getBugsField() {
    return getFieldByClass(BugsField.class);
  }

  public DesktopBox getDesktopBox() {
    return getFieldByClass(DesktopBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public ProductField getProductField() {
    return getFieldByClass(ProductField.class);
  }

  public RefreshButton getRefreshButton() {
    return getFieldByClass(RefreshButton.class);
  }

  public SearchBox getSearchBox() {
    return getFieldByClass(SearchBox.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(10.0)
    public class DesktopBox extends AbstractGroupBox {

      @Order(10.0)
      public class SearchBox extends AbstractSequenceBox {

        @Override
        protected boolean getConfiguredAutoCheckFromTo() {
          return false;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Order(10.0)
        public class AssigneeField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Assignee");
          }

          @Override
          protected int getConfiguredLabelPosition() {
            return LABEL_POSITION_ON_FIELD;
          }
        }

        @Order(20.0)
        public class ProductField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Product");
          }

          @Override
          protected int getConfiguredLabelPosition() {
            return LABEL_POSITION_ON_FIELD;
          }
        }

        @Order(30.0)
        public class RefreshButton extends AbstractButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Refresh");
          }

          @Override
          protected void execClickAction() throws ProcessingException {
            ((ViewHandler) getHandler()).execLoad();
          }
        }
      }

      @Order(30.0)
      public class BugsField extends AbstractTableField<BugsField.Table> {

        @Override
        protected int getConfiguredGridH() {
          return 4;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected boolean getConfiguredTableStatusVisible() {
          return true;
        }

        @Order(10.0)
        public class Table extends AbstractExtensibleTable {

          @Override
          protected void execRowAction(ITableRow row) throws ProcessingException {
            getMenu(OpenBugMenu.class).execAction();
          }

          public AssigneeColumn getAssigneeColumn() {
            return getColumnSet().getColumnByClass(AssigneeColumn.class);
          }

          public ComponentColumn getComponentColumn() {
            return getColumnSet().getColumnByClass(ComponentColumn.class);
          }

          public IDColumn getIDColumn() {
            return getColumnSet().getColumnByClass(IDColumn.class);
          }

          public LastChangedColumn getLastChangedColumn() {
            return getColumnSet().getColumnByClass(LastChangedColumn.class);
          }

          public PriorityColumn getPriorityColumn() {
            return getColumnSet().getColumnByClass(PriorityColumn.class);
          }

          public ResolutionColumn getResolutionColumn() {
            return getColumnSet().getColumnByClass(ResolutionColumn.class);
          }

          public SeveretyColumn getSeveretyColumn() {
            return getColumnSet().getColumnByClass(SeveretyColumn.class);
          }

          public SortValueColumn getSortValueColumn() {
            return getColumnSet().getColumnByClass(SortValueColumn.class);
          }

          public StatusColumn getStatusColumn() {
            return getColumnSet().getColumnByClass(StatusColumn.class);
          }

          public SummaryColumn getSummaryColumn() {
            return getColumnSet().getColumnByClass(SummaryColumn.class);
          }

          public TargetMilestoneColumn getTargetMilestoneColumn() {
            return getColumnSet().getColumnByClass(TargetMilestoneColumn.class);
          }

          @Order(10.0)
          public class IDColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("ID");
            }

            @Override
            protected boolean getConfiguredPrimaryKey() {
              return true;
            }

            @Override
            protected int getConfiguredWidth() {
              return 60;
            }
          }

          @Order(20.0)
          public class SummaryColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Summary");
            }

            @Override
            protected int getConfiguredWidth() {
              return 470;
            }
          }

          @Order(30.0)
          public class LastChangedColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("LastChanged");
            }

            @Override
            protected int getConfiguredWidth() {
              return 100;
            }
          }

          @Order(40.0)
          public class SeveretyColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Severety");
            }

            @Override
            protected int getConfiguredWidth() {
              return 60;
            }
          }

          @Order(50.0)
          public class PriorityColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Priority");
            }

            @Override
            protected int getConfiguredWidth() {
              return 60;
            }
          }

          @Order(60.0)
          public class TargetMilestoneColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("TargetMilestone");
            }

            @Override
            protected int getConfiguredWidth() {
              return 110;
            }
          }

          @Order(70.0)
          public class StatusColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Status");
            }

            @Override
            protected int getConfiguredWidth() {
              return 60;
            }
          }

          @Order(80.0)
          public class ResolutionColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Resolution");
            }

            @Override
            protected int getConfiguredWidth() {
              return 80;
            }
          }

          @Order(90.0)
          public class ComponentColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Component");
            }

            @Override
            protected int getConfiguredWidth() {
              return 89;
            }
          }

          @Order(100.0)
          public class AssigneeColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Assignee");
            }

            @Override
            protected int getConfiguredWidth() {
              return 130;
            }
          }

          @Order(110.0)
          public class SortValueColumn extends AbstractIntegerColumn {

            @Override
            protected boolean getConfiguredDisplayable() {
              return false;
            }

            @Override
            protected int getConfiguredSortIndex() {
              return 0;
            }

            @Override
            protected boolean getConfiguredVisible() {
              return false;
            }
          }

          @Order(10.0)
          public class OpenBugMenu extends AbstractExtensibleMenu {

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("OpenBug");
            }

            @Override
            protected void execAction() throws ProcessingException {
              IShellService shell = SERVICES.getService(IShellService.class);
              shell.shellOpen("https://bugs.eclipse.org/bugs/show_bug.cgi?id=" + getTable().getSelectedRow().getKeyValues()[0]);
            }
          }
        }
      }
    }
  }

  public class ViewHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() throws ProcessingException {
      IDesktopService service = SERVICES.getService(IDesktopService.class);
      DesktopFormData formData = new DesktopFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);

    }
  }
}
