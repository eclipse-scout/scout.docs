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
package org.eclipsescout.demo.widgets.client.ui.forms;

import java.util.Set;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.ValueFieldMenuType;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.ContentAssistFieldTable;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.IContentAssistFieldTable;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipsescout.demo.widgets.client.services.lookup.CompanyLookupCall;
import org.eclipsescout.demo.widgets.client.services.lookup.CompanyTypeLookupCall;
import org.eclipsescout.demo.widgets.client.services.lookup.ProductLookupCall;
import org.eclipsescout.demo.widgets.client.services.lookup.StatusTextLookupCall;
import org.eclipsescout.demo.widgets.client.ui.forms.SmartFieldForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.SmartFieldForm.MainBox.GroupBox;
import org.eclipsescout.demo.widgets.client.ui.forms.SmartFieldForm.MainBox.GroupBox.LeftBox;
import org.eclipsescout.demo.widgets.client.ui.forms.SmartFieldForm.MainBox.GroupBox.LeftBox.ListWithCodeTypeField;
import org.eclipsescout.demo.widgets.client.ui.forms.SmartFieldForm.MainBox.GroupBox.LeftBox.ListWithLookupField;
import org.eclipsescout.demo.widgets.client.ui.forms.SmartFieldForm.MainBox.GroupBox.LeftBox.TreeWithCodeTypeField;
import org.eclipsescout.demo.widgets.client.ui.forms.SmartFieldForm.MainBox.GroupBox.LeftBox.TreeWithLookupCallFullField;
import org.eclipsescout.demo.widgets.client.ui.forms.SmartFieldForm.MainBox.GroupBox.LeftBox.TreeWithLookupCallIncrementalField;
import org.eclipsescout.demo.widgets.client.ui.forms.SmartFieldForm.MainBox.GroupBox.RightBox;
import org.eclipsescout.demo.widgets.client.ui.forms.SmartFieldForm.MainBox.GroupBox.RightBox.CodeAssistField;
import org.eclipsescout.demo.widgets.shared.Icons;
import org.eclipsescout.demo.widgets.shared.services.code.CountryCodeType;
import org.eclipsescout.demo.widgets.shared.services.code.DateCodeType;

public class SmartFieldForm extends AbstractForm implements IPageForm {

  public SmartFieldForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("SmartField");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public CodeAssistField getCodeAssistField() {
    return getFieldByClass(CodeAssistField.class);
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public LeftBox getLeftBox() {
    return getFieldByClass(LeftBox.class);
  }

  public ListWithCodeTypeField getListWithCodeTypeField() {
    return getFieldByClass(ListWithCodeTypeField.class);
  }

  public ListWithLookupField getListWithLookupField() {
    return getFieldByClass(ListWithLookupField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public RightBox getRightBox() {
    return getFieldByClass(RightBox.class);
  }

  public TreeWithCodeTypeField getTreeWithCodeTypeField() {
    return getFieldByClass(TreeWithCodeTypeField.class);
  }

  public TreeWithLookupCallFullField getTreeWithLookupCallFullField() {
    return getFieldByClass(TreeWithLookupCallFullField.class);
  }

  public TreeWithLookupCallIncrementalField getTreeWithLookupCallIncrementalField() {
    return getFieldByClass(TreeWithLookupCallIncrementalField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class GroupBox extends AbstractGroupBox {

      @Order(10.0)
      public class LeftBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Order(10.0)
        public class TreeWithCodeTypeField extends AbstractSmartField<Long> {

          @Override
          protected boolean getConfiguredActiveFilterEnabled() {
            return true;
          }

          @Override
          protected String getConfiguredBackgroundColor() {
            return "8080FF";
          }

          @Override
          protected boolean getConfiguredBrowseHierarchy() {
            return true;
          }

          @Override
          protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
            return DateCodeType.class;
          }

          @Override
          protected boolean getConfiguredEnabled() {
            return true;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("TreeWithCodeType");
          }
        }

        @Order(20.0)
        public class TreeWithLookupCallFullField extends AbstractSmartField<Long> {

          @Override
          protected boolean getConfiguredBrowseHierarchy() {
            return true;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("TreeWithLookupCallFull");
          }

          @Override
          protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
            return ProductLookupCall.class;
          }
        }

        @Order(30.0)
        public class TreeWithLookupCallIncrementalField extends AbstractSmartField<Long> {

          @Override
          protected boolean getConfiguredBrowseAutoExpandAll() {
            return false;
          }

          @Override
          protected boolean getConfiguredBrowseHierarchy() {
            return true;
          }

          @Override
          protected boolean getConfiguredBrowseLoadIncremental() {
            return true;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("TreeWithLookupCallIncremental");
          }

          @Override
          protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
            return ProductLookupCall.class;

          }

          @Override
          protected boolean getConfiguredVisible() {
            return false;
          }

          @Override
          public boolean acceptBrowseHierarchySelection(Long value, int level, boolean leaf) {
            return leaf;
          }
        }

        @Order(40.0)
        public class ListWithCodeTypeField extends AbstractSmartField<Long> {

          @Override
          protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
            return CountryCodeType.class;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("ListWithCodeType");
          }
        }

        @Order(50.0)
        public class ListWithLookupField extends AbstractSmartField<Long> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("ListWithLookup");
          }

          @Override
          protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
            return CompanyTypeLookupCall.class;
          }
        }

        @Order(60.0)
        public class CompanySmartField extends AbstractSmartField<Long> {

          @Override
          protected int getConfiguredBrowseMaxRowCount() {
            return 50;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Company");
          }

          @Override
          protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
            return CompanyLookupCall.class;
          }
        }

        @Order(70.0)
        public class ListWithTableProposalField extends AbstractSmartField<Long> {

          @Override
          protected int getConfiguredBrowseMaxRowCount() {
            return 50;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("ListWithTableProposal");
          }

          @Override
          protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
            return CompanyLookupCall.class;
          }

          @Override
          protected Class<? extends IContentAssistFieldTable<Long>> getConfiguredContentAssistTable() {
            return CompanySmartTable.class;
          }

          @Order(100)
          public class SmartFieldMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
              return "A Menu";
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.hashSet(ValueFieldMenuType.NotNull);
            }

            @Override
            protected void execAction() throws ProcessingException {
              MessageBox.showOkMessage("Menu clicked", "some detail", "some info");
            }
          }
        }

        @Order(80.0)
        public class ListWithInnerTableProposalField extends AbstractSmartField<Long> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("ListWithInnerTableProposal");
          }

          @Override
          protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
            return CompanyLookupCall.class;
          }

          public class Table extends ContentAssistFieldTable<Long> {

            @Override
            protected String getConfiguredDefaultIconId() {
              return Icons.WeatherSnow;
            }

            @Override
            protected boolean getConfiguredHeaderVisible() {
              return true;
            }

            @Order(40)
            public class AdditionalInfoColumn extends AbstractStringColumn {
              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("AdditionalInfo");
              }

              @Override
              protected int getConfiguredWidth() {
                return 200;
              }
            }

            @Order(50)
            public class CompanyTypeColumn extends AbstractSmartColumn<Long> {
              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("CompanyType");
              }

              @Override
              protected int getConfiguredWidth() {
                return 200;
              }

              @Override
              protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                return CompanyTypeLookupCall.class;
              }

              @Override
              protected void execDecorateCell(Cell cell, ITableRow row) throws ProcessingException {
                decorateCellWithLookupRow(cell, row);
              }
            }
          }
        }
      }

      @Order(30.0)
      public class RightBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Order(10.0)
        public class CodeAssistField extends AbstractSmartField<Long> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("CodeAssistField");
          }

          @Override
          protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
            return StatusTextLookupCall.class;
          }
        }
      }
    }

    @Order(90.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(100.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

  public static class CompanySmartTable extends ContentAssistFieldTable<Long> {

    @Override
    protected String getConfiguredDefaultIconId() {
      return Icons.WeatherSnow;
    }

    @Override
    protected boolean getConfiguredHeaderVisible() {
      return true;
    }

    @Order(40)
    public class AdditionalInfoColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("AdditionalInfo");
      }

      @Override
      protected int getConfiguredWidth() {
        return 200;
      }
    }

    @Order(50)
    public class CompanyTypeColumn extends AbstractSmartColumn<Long> {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("CompanyType");
      }

      @Override
      protected int getConfiguredWidth() {
        return 200;
      }

      @Override
      protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
        return CompanyTypeLookupCall.class;
      }

      @Override
      protected void execDecorateCell(Cell cell, ITableRow row) throws ProcessingException {
        decorateCellWithLookupRow(cell, row);
      }
    }
  }
}
