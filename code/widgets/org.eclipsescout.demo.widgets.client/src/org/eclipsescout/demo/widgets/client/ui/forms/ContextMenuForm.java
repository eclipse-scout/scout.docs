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

import java.util.List;
import java.util.Set;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.CompareUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.ValueFieldMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.IFormFieldVisitor;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.colorpickerfield.AbstractColorField;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.filechooserfield.AbstractFileChooserField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipsescout.demo.widgets.client.services.lookup.CompanyTypeLookupCall;
import org.eclipsescout.demo.widgets.client.ui.forms.ContextMenuForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.ContextMenuForm.MainBox.TableTabBox.TableFieldBox.TableField;
import org.eclipsescout.demo.widgets.client.ui.forms.internal.AbstractTableFieldWithDisabledRows;
import org.eclipsescout.demo.widgets.shared.Icons;
import org.eclipsescout.demo.widgets.shared.services.code.CountryCodeType;
import org.eclipsescout.demo.widgets.shared.services.code.CountryCodeType.FranceCode;
import org.eclipsescout.demo.widgets.shared.services.code.CountryCodeType.USACode;

/**
 *
 */
public class ContextMenuForm extends AbstractForm implements IPageForm {

  /**
   * @throws ProcessingException
   */
  public ContextMenuForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ContextMenu");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(7)
    public class TableDisableButton extends AbstractButton {

      @Override
      protected int getConfiguredDisplayStyle() {
        return DISPLAY_STYLE_TOGGLE;
      }

      @Override
      protected String getConfiguredLabel() {
        return "Disable Table";
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        getFieldByClass(TableField.class).setEnabled(!isSelected());
      }
    }

    @Order(10.0)
    public class CountrySmartField extends AbstractSmartField<Long> {

      @Override
      protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
        return CountryCodeType.class;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Country");
      }

      // context menus
      @Order(10.0)
      public class EditMenuGroup extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return TEXTS.get("Edit");
        }

        @Override
        protected boolean getConfiguredInheritAccessibility() {
          return true;
        }

        @Override
        protected String getConfiguredIconId() {
          return Icons.StarYellow;
        }

        @Override
        protected void execAction() throws ProcessingException {
          MessageBox.showOkMessage("Menu action", "Menu: '" + getLabel() + "'", "");
        }

        @Order(10.0)
        public class Edit01Menu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return "Edit (not empty and not France)";
          }

          @Override
          protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.hashSet(ValueFieldMenuType.NotNull);
          }

          @Override
          protected void execOwnerValueChanged(Object newOwnerValue) throws ProcessingException {
            setVisible(!CompareUtility.equals(newOwnerValue, FranceCode.ID));
          }
        }

        @Order(20.0)
        public class Edit02Menu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return "Edit (empty)";
          }

          @Override
          protected void execOwnerValueChanged(Object newOwnerValue) throws ProcessingException {
            super.execOwnerValueChanged(newOwnerValue);
          }

          @Override
          protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.hashSet(ValueFieldMenuType.Null);
          }
        }

        @Order(30.0)
        public class Edit03Menu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return "Edit (USA)";
          }

          @Override
          protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.hashSet(ValueFieldMenuType.NotNull);
          }

          @Override
          protected void execOwnerValueChanged(Object newOwnerValue) throws ProcessingException {
            setVisible(CompareUtility.equals(newOwnerValue, USACode.ID));
          }
        }
      }
    }

    @Order(10.0)
    public class TableTabBox extends AbstractTabBox {

      @Order(10)
      public class TableFieldBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("TableField");
        }

        @Order(5.0)
        public class TableField extends AbstractTableField<TableField.Table> {

          @Override
          protected int getConfiguredGridH() {
            return 3;
          }

          @Override
          protected int getConfiguredGridW() {
            return 2;
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected void execInitField() throws ProcessingException {
            super.execInitField();
            getTable().addRowByArray(new Object[]{"Baluu", "Boralimon"});
            getTable().addRowByArray(new Object[]{"Baluu1", "Boralimon"});
            getTable().addRowByArray(new Object[]{"Baluu2", "Boralimon"});
            getTable().addRowByArray(new Object[]{"Baluu3", "Boralimon"});
          }

          @Order(10)
          public class Table extends AbstractTable {

            @Override
            protected String getConfiguredDefaultIconId() {
              return Icons.StarRed;
            }

            public NameColumn getNameColumn() {
              return getColumnSet().getColumnByClass(NameColumn.class);
            }

            @Order(10)
            public class NameColumn extends AbstractStringColumn {

              @Override
              protected String getConfiguredHeaderText() {
                return "Name (editable)";
              }

              @Override
              protected int getConfiguredWidth() {
                return 250;
              }
            }

            @Order(20)
            public class PrenameColumn extends AbstractStringColumn {

              @Override
              protected String getConfiguredHeaderText() {
                return "Name (!editable)";
              }

              @Override
              protected int getConfiguredWidth() {
                return 250;
              }
            }

            @Order(90)
            public class EditMenuGroup extends AbstractMenu {

              @Override
              protected String getConfiguredText() {
                return "Edit group (single)";
              }

              @Order(10)
              public class EditSingle01 extends AbstractMenu {

                @Override
                protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                  return CollectionUtility.hashSet(TableMenuType.SingleSelection);
                }

                @Override
                protected String getConfiguredText() {
                  return "single selection m1";
                }
              }

              @Order(20)
              public class EditSingle02 extends AbstractMenu {

                @Override
                protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                  return CollectionUtility.hashSet(TableMenuType.SingleSelection);
                }

                @Override
                protected String getConfiguredText() {
                  return "single selection m2";
                }
              }

              @Order(20)
              public class EditSingle03 extends AbstractMenu {

                @Override
                protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                  return CollectionUtility.hashSet(TableMenuType.SingleSelection);
                }

                @Override
                protected String getConfiguredText() {
                  return "single selection m3";
                }
              }
            }

            @Order(91)
            public class EditMultiMenuGroup extends AbstractMenu {

              @Override
              protected String getConfiguredText() {
                return "Edit multi group";
              }

              @Order(10)
              public class EditMulti01 extends AbstractMenu {

                @Override
                protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                  return CollectionUtility.hashSet(TableMenuType.MultiSelection);
                }

                @Override
                protected String getConfiguredText() {
                  return "delete multi selection";
                }
              }

              @Order(10)
              public class EditMulti02 extends AbstractMenu {

                @Override
                protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                  return CollectionUtility.hashSet(TableMenuType.MultiSelection);
                }

                @Override
                protected String getConfiguredText() {
                  return "multi action (permission)";
                }

                @Override
                protected void execInitAction() throws ProcessingException {
                  super.execInitAction();
                  setVisibleGranted(false);
                }
              }
            }

            @Order(92)
            public class EditEmptyMenuGroup extends AbstractMenu {

              @Override
              protected String getConfiguredText() {
                return "Edit empty group";
              }

              @Order(10)
              public class EditEmpty01 extends AbstractMenu {

                @Override
                protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                  return CollectionUtility.hashSet(TableMenuType.EmptySpace);
                }

                @Override
                protected String getConfiguredText() {
                  return "empty 01";
                }
              }

              @Order(10)
              public class EditEmpty02 extends AbstractMenu {

                @Override
                protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                  return CollectionUtility.hashSet(TableMenuType.EmptySpace);
                }

                @Override
                protected String getConfiguredText() {
                  return "empty 02 (permission)";
                }

                @Override
                protected void execInitAction() throws ProcessingException {
                  super.execInitAction();
                  setVisibleGranted(false);
                }
              }
            }

            @Order(100)
            public class SingleSelectionMenu extends AbstractMenu {

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TableMenuType.SingleSelection);
              }

              @Override
              protected String getConfiguredText() {
                return "Single selection";
              }

              @Override
              protected void execAction() throws ProcessingException {
                System.out.println("Menu: '" + getClass().getSimpleName() + "'");
              }
            }

            @Order(110)
            public class MultiSelectionMenu extends AbstractMenu {

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TableMenuType.MultiSelection);
              }

              @Override
              protected String getConfiguredText() {
                return getClass().getSimpleName();
              }

              @Override
              protected void execAction() throws ProcessingException {
                System.out.println("Menu: '" + getClass().getSimpleName() + "'");
              }
            }

            @Order(120)
            public class EmptySpaceMenu extends AbstractMenu {

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TableMenuType.EmptySpace);
              }

              @Override
              protected String getConfiguredText() {
                return getClass().getSimpleName();
              }

              @Override
              protected void execAction() throws ProcessingException {
                System.out.println("Menu: '" + getClass().getSimpleName() + "'");
              }
            }

            @Order(150)
            public class NotFirstRowGroup extends AbstractMenu {

              @Override
              protected String getConfiguredText() {
                return getClass().getSimpleName();
              }

              @Override
              protected void execOwnerValueChanged(Object newOwnerValue) throws ProcessingException {
                setVisible(!CompareUtility.equals(getNameColumn().getValue(getSelectedRow()), "Baluu"));
              }

              @Order(10)
              public class Menu01 extends AbstractMenu {

                @Override
                protected String getConfiguredText() {
                  return getClass().getSimpleName();
                }
              }

              @Order(20)
              public class Menu02 extends AbstractMenu {

                @Override
                protected String getConfiguredText() {
                  return getClass().getSimpleName();
                }
              }
            }
          }
        }
      }

      @Order(20)
      public class DisabledRowBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DisabledRows");
        }

        @Order(10)
        public class TableField extends AbstractTableFieldWithDisabledRows {

        }
      }
    }

    @Order(50.0)
    public class CompanySmartField extends AbstractSmartField<Long> {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Company");
      }

      @Override
      protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
        return CompanyTypeLookupCall.class;
      }
    }

    @Order(60)
    public class StringField extends AbstractStringField {

      @Override
      protected int getConfiguredHorizontalAlignment() {
        return 1;
      }

      @Override
      protected String getConfiguredLabel() {
        return "String field";
      }

      @Order(10)
      public class ContextMenuItem extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Context menu (only empty)";
        }

        @Override
        protected void execInitAction() throws ProcessingException {
          //          setVisibleGranted(false);
        }

        @Override
        protected boolean getConfiguredInheritAccessibility() {
          return false;
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.hashSet(ValueFieldMenuType.Null);
        }

        @Override
        protected void execOwnerValueChanged(Object newOwnerValue) throws ProcessingException {
          setVisible(newOwnerValue == null);
        }
      }
    }

    @Order(65)
    public class PasswordField extends AbstractStringField {

      @Override
      protected int getConfiguredHorizontalAlignment() {
        return -1;
      }

      @Override
      protected boolean getConfiguredInputMasked() {
        return true;
      }

      @Override
      protected String getConfiguredLabel() {
        return "Password field";
      }

      @Order(10)
      public class ContextMenuItem extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Context menu (only empty)";
        }

        @Override
        protected void initConfig() {
          super.initConfig();
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.hashSet(ValueFieldMenuType.Null);
        }

        @Override
        protected void execOwnerValueChanged(Object newOwnerValue) throws ProcessingException {
          setVisible(newOwnerValue == null);
        }
      }
    }

    @Order(70)
    public class DateField extends AbstractDateField {

      @Override
      protected boolean getConfiguredHasTime() {
        return true;
      }

      @Override
      protected String getConfiguredLabel() {
        return "Date field";
      }

      @Order(10)
      public class ContextMenuItem extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Context menu (not empty)";
        }

      }
    }

    @Order(80)
    public class IntegerField extends AbstractIntegerField {

      @Override
      protected int getConfiguredHorizontalAlignment() {
        return 1;
      }

      @Override
      protected String getConfiguredLabel() {
        return "Integer field";
      }

      @Order(10)
      public class ContextMenuItem extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Context menu (not empty)";
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.hashSet(ValueFieldMenuType.NotNull);
        }
      }
    }

    @Order(90.0)
    public class FileChooserField extends AbstractFileChooserField {

      @Override
      protected List<String> getConfiguredFileExtensions() {
        return CollectionUtility.arrayList("png", "bmp", "jpg", "jpeg", "gif");
      }

      @Override
      protected String getConfiguredLabel() {
        return "File chooser";
      }

      @Override
      protected boolean getConfiguredTypeLoad() {
        return true;
      }

      @Order(10)
      public class ContextMenuItem extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Context menu (not abc)";
        }
      }
    }

    @Order(90.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(100)
    public class Button extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return "Button";
      }

      @Override
      protected boolean getConfiguredProcessButton() {
        return false;
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        System.out.println("button selected");
      }

      @Order(10)
      public class ContextMenuItem extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Context menu";
        }

      }
    }

    @Order(100.0)
    public class CloseButton extends AbstractCloseButton {
    }

    @Order(110)
    public class LinkButton extends AbstractButton {

      @Override
      protected int getConfiguredDisplayStyle() {
        return DISPLAY_STYLE_LINK;
      }

      @Override
      protected String getConfiguredLabel() {
        return "Link";
      }

      @Override
      protected boolean getConfiguredProcessButton() {
        return false;
      }

      @Order(10)
      public class ContextMenuItem extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Context menu";
        }

      }
    }

    @Order(120)
    public class RadioButton extends AbstractButton {

      @Override
      protected int getConfiguredDisplayStyle() {
        return DISPLAY_STYLE_RADIO;
      }

      @Override
      protected String getConfiguredLabel() {
        return "Radio";
      }

      @Override
      protected boolean getConfiguredProcessButton() {
        return false;
      }

      @Order(10)
      public class ContextMenuItem extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Context menu";
        }

      }
    }

    @Order(130)
    public class ToggleButton extends AbstractButton {

      @Override
      protected int getConfiguredDisplayStyle() {
        return DISPLAY_STYLE_TOGGLE;
      }

      @Override
      protected String getConfiguredLabel() {
        return "Disable all fields";
      }

      @Override
      protected boolean getConfiguredProcessButton() {
        return false;
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        getMainBox().visitFields(new IFormFieldVisitor() {

          @Override
          public boolean visitField(IFormField field, int level, int fieldIndex) {
            if (field instanceof IValueField<?>) {
              field.setEnabled(!isSelected());
            }
            return true;
          }
        }, 0);
        //        getFieldByClass(CountrySmartField.class).setEnabled(isSelected());
      }

      @Order(10)
      public class ContextMenuItem extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Context menu";
        }

      }
    }

    @Order(140)
    public class ColorField extends AbstractColorField {

      @Override
      protected String getConfiguredLabel() {
        return "Color field";
      }

      @Order(10)
      public class ContextMenuItem extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Context menu";
        }
      }
    }

    @Order(150)
    public class ColorField2 extends AbstractColorField {

      @Override
      protected String getConfiguredIconId() {
        return null;
      }

      @Override
      protected String getConfiguredLabel() {
        return "Color field (no icon)";
      }

      @Order(10)
      public class ContextMenuItem extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Context menu";
        }
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
