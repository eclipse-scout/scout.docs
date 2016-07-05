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
package org.eclipse.scout.widgets.old.client.ui.forms;

import java.util.List;
import java.util.Set;

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
import org.eclipse.scout.rt.client.ui.form.fields.colorfield.AbstractColorField;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.filechooserfield.AbstractFileChooserField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.CompareUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.services.lookup.CompanyTypeLookupCall;
import org.eclipse.scout.widgets.client.ui.forms.IPageForm;
import org.eclipse.scout.widgets.old.client.ui.forms.ContextMenuForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.old.client.ui.forms.ContextMenuForm.MainBox.TableTabBox.TableFieldBox.TableField;
import org.eclipse.scout.widgets.old.client.ui.forms.internal.AbstractTableFieldWithDisabledRows;
import org.eclipse.scout.widgets.shared.Icons;
import org.eclipse.scout.widgets.shared.services.code.CountryCodeType;
import org.eclipse.scout.widgets.shared.services.code.CountryCodeType.FranceCode;
import org.eclipse.scout.widgets.shared.services.code.CountryCodeType.USACode;

public class ContextMenuForm extends AbstractForm implements IPageForm {

  public ContextMenuForm() {
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
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    public class TableTabBox extends AbstractTabBox {

      @Order(10)
      public class TableFieldBox extends AbstractGroupBox {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("TableField");
        }

        @Order(5)
        public class TableField extends AbstractTableField<TableField.Table> {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected int getConfiguredGridW() {
            return 2;
          }

          @Override
          protected int getConfiguredGridH() {
            return 3;
          }

          @Override
          protected void execInitField() {
            super.execInitField();
            getTable().addRowByArray(new Object[]{"Baluu", "Boralimon"});
            getTable().addRowByArray(new Object[]{"Baluu1", "Boralimon"});
            getTable().addRowByArray(new Object[]{"Baluu2", "Boralimon"});
            getTable().addRowByArray(new Object[]{"Baluu3", "Boralimon"});
          }

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
                protected String getConfiguredText() {
                  return "single selection m1";
                }

                @Override
                protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                  return CollectionUtility.hashSet(TableMenuType.SingleSelection);
                }

              }

              @Order(20)
              public class EditSingle02 extends AbstractMenu {
                @Override
                protected String getConfiguredText() {
                  return "single selection m2";
                }

                @Override
                protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                  return CollectionUtility.hashSet(TableMenuType.SingleSelection);
                }
              }

              @Order(20)
              public class EditSingle03 extends AbstractMenu {
                @Override
                protected String getConfiguredText() {
                  return "single selection m3";
                }

                @Override
                protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                  return CollectionUtility.hashSet(TableMenuType.SingleSelection);
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
                protected String getConfiguredText() {
                  return "delete multi selection";
                }

                @Override
                protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                  return CollectionUtility.hashSet(TableMenuType.MultiSelection);
                }
              }

              @Order(10)
              public class EditMulti02 extends AbstractMenu {
                @Override
                protected String getConfiguredText() {
                  return "multi action (permission)";
                }

                @Override
                protected void execInitAction() {
                  super.execInitAction();
                  setVisibleGranted(false);
                }

                @Override
                protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                  return CollectionUtility.hashSet(TableMenuType.MultiSelection);
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
                protected String getConfiguredText() {
                  return "empty 01";
                }

                @Override
                protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                  return CollectionUtility.hashSet(TableMenuType.EmptySpace);
                }
              }

              @Order(10)
              public class EditEmpty02 extends AbstractMenu {
                @Override
                protected String getConfiguredText() {
                  return "empty 02 (permission)";
                }

                @Override
                protected void execInitAction() {
                  super.execInitAction();
                  setVisibleGranted(false);
                }

                @Override
                protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                  return CollectionUtility.hashSet(TableMenuType.EmptySpace);
                }
              }

            }

            @Order(100)
            public class SingleSelectionMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return "Single selection";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TableMenuType.SingleSelection);
              }

              @Override
              protected void execAction() {
                System.out.println("Menu: '" + getClass().getSimpleName() + "'");
              }
            }

            @Order(110)
            public class MultiSelectionMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return getClass().getSimpleName();
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TableMenuType.MultiSelection);
              }

              @Override
              protected void execAction() {
                System.out.println("Menu: '" + getClass().getSimpleName() + "'");
              }
            }

            @Order(120)
            public class EmptySpaceMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return getClass().getSimpleName();
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TableMenuType.EmptySpace);
              }

              @Override
              protected void execAction() {
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
              protected void execOwnerValueChanged(Object newOwnerValue) {
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

    @Order(7)
    public class TableDisableButton extends AbstractButton {
      @Override
      protected String getConfiguredLabel() {
        return "Disable Table";
      }

      @Override
      protected int getConfiguredDisplayStyle() {
        return DISPLAY_STYLE_TOGGLE;
      }

      @Override
      protected void execClickAction() {
        getFieldByClass(TableField.class).setEnabled(!isSelected());
      }
    }

    @Order(10)
    public class CountrySmartField extends AbstractSmartField<Long> {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Country");
      }

      @Override
      protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
        return CountryCodeType.class;
      }

      // context menus
      @Order(10)
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
        protected void execAction() {
          MessageBoxes.createOk().withHeader("Menu action").withBody("Menu: '" + getLabel() + "'").show();
        }

        @Order(10)
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
          protected void execOwnerValueChanged(Object newOwnerValue) {
            setVisible(!CompareUtility.equals(newOwnerValue, FranceCode.ID));
          }
        }

        @Order(20)
        public class Edit02Menu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return "Edit (empty)";
          }

          @Override
          protected void execOwnerValueChanged(Object newOwnerValue) {
            super.execOwnerValueChanged(newOwnerValue);
          }

          @Override
          protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.hashSet(ValueFieldMenuType.Null);
          }
        }

        @Order(30)
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
          protected void execOwnerValueChanged(Object newOwnerValue) {
            setVisible(CompareUtility.equals(newOwnerValue, USACode.ID));
          }
        }
      }

    }

    @Order(50)
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
      protected String getConfiguredLabel() {
        return "String field";
      }

      @Override
      protected int getConfiguredHorizontalAlignment() {
        return 1;
      }

      @Order(10)
      public class ContextMenuItem extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Context menu (only empty)";
        }

        @Override
        protected void execInitAction() {
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
        protected void execOwnerValueChanged(Object newOwnerValue) {
          setVisible(newOwnerValue == null);
        }
      }
    }

    @Order(65)
    public class PasswordField extends AbstractStringField {
      @Override
      protected String getConfiguredLabel() {
        return "Password field";
      }

      @Override
      protected boolean getConfiguredInputMasked() {
        return true;
      }

      @Override
      protected int getConfiguredHorizontalAlignment() {
        return -1;
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
        protected void execOwnerValueChanged(Object newOwnerValue) {
          setVisible(newOwnerValue == null);
        }
      }
    }

    @Order(70)
    public class DateField extends AbstractDateField {
      @Override
      protected String getConfiguredLabel() {
        return "Date field";
      }

      @Override
      protected boolean getConfiguredHasTime() {
        return true;
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
      protected String getConfiguredLabel() {
        return "Integer field";
      }

      @Override
      protected int getConfiguredHorizontalAlignment() {
        return 1;
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

    @Order(90)
    public class FileChooserField extends AbstractFileChooserField {

      @Override
      protected List<String> getConfiguredFileExtensions() {
        return CollectionUtility.arrayList("png", "bmp", "jpg", "jpeg", "gif");
      }

      @Override
      protected String getConfiguredLabel() {
        return "File chooser";
      }

      @Order(10)
      public class ContextMenuItem extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Context menu (not abc)";
        }
      }
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
      protected void execClickAction() {
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

    @Order(110)
    public class LinkButton extends AbstractButton {
      @Override
      protected String getConfiguredLabel() {
        return "Link";
      }

      @Override
      protected int getConfiguredDisplayStyle() {
        return DISPLAY_STYLE_LINK;
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
      protected String getConfiguredLabel() {
        return "Radio";
      }

      @Override
      protected int getConfiguredDisplayStyle() {
        return DISPLAY_STYLE_RADIO;
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
      protected String getConfiguredLabel() {
        return "Disable all fields";
      }

      @Override
      protected int getConfiguredDisplayStyle() {
        return DISPLAY_STYLE_TOGGLE;
      }

      @Override
      protected boolean getConfiguredProcessButton() {
        return false;
      }

      @Override
      protected void execClickAction() {
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

    @Order(90)
    public class OkButton extends AbstractOkButton {
    }

    @Order(100)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

}
