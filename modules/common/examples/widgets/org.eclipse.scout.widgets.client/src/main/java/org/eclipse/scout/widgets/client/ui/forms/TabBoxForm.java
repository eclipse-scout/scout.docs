/*
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.forms;

import java.util.Set;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenuSeparator;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TabBoxMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.form.fields.AbstractFormFieldMenu;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.IGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.placeholder.AbstractPlaceholderField;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.ITabBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.BooleanUtility;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.ConfigurationBox;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.ConfigurationBox.TabAreaStyleSpreadEvenField;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.FieldVisibilityBox;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.FieldVisibilityBox.Placeholder1Field;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.FieldVisibilityBox.VisibleDocumentsField;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox.AddTabContainerMenu.SequenceBox.AddTabButton;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox.AddTabContainerMenu.SequenceBox.TabNameField;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox.CommentsBox;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox.CommentsBox.CommentsField;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox.DocumentsBox;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox.DocumentsBox.FileTableField;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox.MonthsBox;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox.MonthsBox.MonthDetailsBox;
import org.eclipse.scout.widgets.client.ui.forms.tabbox.DynamicTabItem;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFileTableField;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractMonthsBox;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractStatusButton;
import org.eclipse.scout.widgets.shared.Icons;

@ClassId("8e96f11d-2271-40d7-8d0b-1d6b9f852164")
public class TabBoxForm extends AbstractForm implements IPageForm {

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("TabBox");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public DocumentsBox getDocumentsBox() {
    return getFieldByClass(DocumentsBox.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public FieldVisibilityBox getFieldVisibilityBox() {
    return getFieldByClass(FieldVisibilityBox.class);
  }

  public FileTableField getFileTableField() {
    return getFieldByClass(FileTableField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public CommentsBox getCommentsBox() {
    return getFieldByClass(CommentsBox.class);
  }

  public CommentsField getCommentsField() {
    return getFieldByClass(CommentsField.class);
  }

  public VisibleDocumentsField getVisibleDocumentsField() {
    return getFieldByClass(VisibleDocumentsField.class);
  }

  public MonthDetailsBox getMonthDetailsBox() {
    return getFieldByClass(MonthDetailsBox.class);
  }

  public MonthsBox getMonthsBox() {
    return getFieldByClass(MonthsBox.class);
  }

  public TabNameField getTabNameField() {
    return getFieldByClass(TabNameField.class);
  }

  public AddTabButton getAddTabButton() {
    return getFieldByClass(AddTabButton.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  public TabAreaStyleSpreadEvenField getTabAreaStyleSpreadEvenField() {
    return getFieldByClass(TabAreaStyleSpreadEvenField.class);
  }

  public Placeholder1Field getPlaceholder1Field() {
    return getFieldByClass(Placeholder1Field.class);
  }

  public TabBox getTabBox() {
    return getFieldByClass(TabBox.class);
  }

  @Order(10)
  @ClassId("c9764c26-2dcc-4292-8dcb-0f018d5ccdae")
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    @ClassId("57183ffb-8458-4e88-b384-b709f1c1fda5")
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

    }

    @Order(20)
    @ClassId("803aa5d2-a928-4401-8f9a-8ed930eeb648")
    public class TabBox extends AbstractTabBox {

      @Override
      protected String getConfiguredTooltipText() {
        return TEXTS.get("TabBoxTooltip");
      }

      @Order(10)
      @ClassId("7b7743a9-bc03-4061-8580-f0f39df3b3b3")
      public class MonthsBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Months");
        }

        @Order(10)
        @ClassId("d463263e-2ea0-4cc4-a2b3-2cea2b698ed9")
        public class MonthDetailsBox extends AbstractMonthsBox {

          @Override
          protected int getConfiguredGridH() {
            return 5;
          }
        }
      }

      @Order(20)
      @ClassId("f9008cef-ba0e-47af-8746-a76ba61aab65")
      public class CommentsBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Comments");
        }

        @Order(1000.0)
        @ClassId("4cf584fb-c3ef-44ce-8e0d-3e3a29a1b43e")
        public class ClearTextMenu extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ClearText");
          }

          @Override
          protected void execAction() {
            getCommentsField().setValue(null);
          }
        }

        @Order(2000.0)
        @ClassId("5bc64195-0c6d-4028-ba40-fe1d7ee65a1f")
        public class ShowTooltipMenu extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ToggleTooltip");
          }

          @Override
          protected void execAction() {
            if (getCommentsBox().getTooltipText() == null) {
              getCommentsBox().setTooltipText(TEXTS.get("DynamicTooltip"));
            }
            else {
              getCommentsBox().setTooltipText(null);
            }
          }
        }

        @Order(2500)
        @ClassId("bdbbd8ca-0cf3-4993-b266-69d4730b0c56")
        public class StatusButton extends AbstractStatusButton {

          @Override
          protected IFormField getField() {
            return CommentsBox.this;
          }
        }

        @Order(3000.0)
        @ClassId("4b87f7ec-d4c6-4891-affc-aba45e56c08d")
        public class CommentsField extends AbstractStringField {

          @Override
          protected int getConfiguredGridH() {
            return 5;
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected boolean getConfiguredMultilineText() {
            return true;
          }
        }
      }

      @Order(30)
      @ClassId("9aa6fa8d-8bbe-4309-84eb-b9bbdc9baa12")
      public class DocumentsBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Documents");
        }

        @Override
        protected String getConfiguredTooltipText() {
          return TEXTS.get("TooltipOfTab", getConfiguredLabel());
        }

        @Order(10)
        @ClassId("d90ab648-3aac-4542-8457-5938e51fc64d")
        public class FileTableField extends AbstractFileTableField {

          @Override
          protected int getConfiguredGridH() {
            return 5;
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }
        }
      }

      @Order(5)
      @ClassId("ba44b14d-0560-4ab4-b328-2708a78d6d0b")
      public class AddTabContainerMenu extends AbstractFormFieldMenu {

        @Override
        protected boolean getConfiguredStackable() {
          return false;
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.hashSet(TabBoxMenuType.Header);
        }

        @ClassId("90786abd-ea95-406a-a4fe-311a2e8e552c")
        @Order(1000)
        public class SequenceBox extends AbstractSequenceBox {
          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected boolean getConfiguredAutoCheckFromTo() {
            return false;
          }

          @Order(1000)
          @ClassId("9d931f8a-56c9-489d-85ba-1b6bb57c54b9")
          public class TabNameField extends AbstractStringField {
            @Override
            protected byte getConfiguredLabelPosition() {
              return LABEL_POSITION_ON_FIELD;
            }

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("TabName");
            }

            @Override
            protected int getConfiguredMaxLength() {
              return 128;
            }

            @Override
            protected boolean getConfiguredGridUseUiWidth() {
              return true;
            }
          }

          @Order(2000)
          @ClassId("43f2230c-c895-470a-86a1-b4d1aba4f716")
          public class AddTabButton extends AbstractButton {
            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("Add");
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected boolean getConfiguredGridUseUiWidth() {
              return true;
            }

            @Override
            protected void execClickAction() {
              TabNameField nameField = getTabNameField();
              getTabBox().addField(new DynamicTabItem(nameField.getValue()));
              nameField.setValue(null);
            }
          }
        }
      }

      @Order(6)
      @ClassId("bc69a55b-98ad-4b28-9e64-6854519a5c86")
      public class SayHelloMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return "Say Hello";
        }

        @Override
        protected void execAction() {
          MessageBoxes.createOk().withBody("Hello!").show();
        }

        @Override
        protected boolean getConfiguredStackable() {
          return false;
        }

        @Override
        protected byte getConfiguredHorizontalAlignment() {
          return HORIZONTAL_ALIGNMENT_RIGHT;
        }
      }

      @Order(10)
      @ClassId("7c02bc2d-b7ee-435c-b34c-0511d5e44b83")
      public class CountMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return "Count";
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
        protected void execAction() {
          int size = 0;
          for (IGroupBox gb : getTabBox().getGroupBoxes()) {
            if (gb.isVisible()) {
              size++;
            }
          }
          MessageBoxes.createOk().withBody("There " + (size == 1 ? "is" : "are") + " " + size + " tab box" + (size == 1 ? "" : "es") + ".").show();
        }
      }

      @Order(15)
      @ClassId("87900bf3-eace-4e7e-b1b0-b07a5a06a879")
      public class SeparatorMenu extends AbstractMenuSeparator {
      }

      @Order(20)
      @ClassId("e4a3c92d-c1ac-4408-9a85-f4e6c5e906a4")
      public class OptionsMenu extends AbstractMenu {

        @Override
        protected String getConfiguredIconId() {
          return Icons.Gear;
        }

        @Override
        protected boolean getConfiguredStackable() {
          return false;
        }

        @Override
        protected void execAction() {
          MessageBoxes.createOk().withHeader("Sorry").withBody("There are currently no options available.").show();
        }

        @Override
        protected byte getConfiguredHorizontalAlignment() {
          return HORIZONTAL_ALIGNMENT_RIGHT;
        }
      }
    }

    @Order(25)
    @ClassId("bb4d6161-e629-40be-aa9b-c60f815479b6")
    public class ConfigurationBox extends AbstractGroupBox {
      @Override
      protected String getConfiguredLabel() {
        return "Configuration";
      }

      @Order(1000)
      @ClassId("43b3ad4e-ea75-4bbc-8497-67c34b5fceb3")
      public class TabAreaStyleSpreadEvenField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return "Spread Tabs evenly";
        }

        @Override
        protected void execChangedValue() {
          getTabBox().setTabAreaStyle(BooleanUtility.nvl(getValue()) ? ITabBox.TAB_AREA_STYLE_SPREAD_EVEN : ITabBox.TAB_AREA_STYLE_DEFAULT);
        }
      }
    }

    @Order(30)
    @ClassId("33918c6f-aed1-4f86-b1f0-fd7c2eabefcb")
    public class FieldVisibilityBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Override
      protected double getConfiguredGridWeightY() {
        return 0.0;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("TabVisibility");
      }

      @Order(10)
      @ClassId("0be16e4e-d7c4-42f2-bf4d-f3fff4082ad3")
      public class VisibleMonthsField extends AbstractBooleanField {

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("VisibleMonths");
        }

        @Override
        protected void execChangedValue() {
          getMonthsBox().setVisible(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getMonthsBox().isVisible());
        }
      }

      @Order(20)
      @ClassId("aafc477f-7e95-4e18-9706-466bd94428a9")
      public class VisibleCommentsField extends AbstractBooleanField {

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("VisibleComments0");
        }

        @Override
        protected void execChangedValue() {
          getCommentsBox().setVisible(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getCommentsBox().isVisible());
        }

      }

      @Order(30)
      @ClassId("fa7ffa1f-ab31-4399-b133-b054365c15bc")
      public class VisibleDocumentsField extends AbstractBooleanField {

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("VisibleDocuments0");
        }

        @Override
        protected void execChangedValue() {
          getDocumentsBox().setVisible(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getDocumentsBox().isVisible());
        }
      }

      @Order(40)
      @ClassId("77b0cce4-f0aa-4d65-ad44-e8265f989472")
      public class Placeholder1Field extends AbstractPlaceholderField {
      }

    }

    @Order(40)
    @ClassId("7492850e-7abc-4f02-852f-83cff39d00b8")
    public class SetSelectedTabMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("SelectTab");
      }

      @Order(10)
      @ClassId("7d65777f-2890-490b-be40-d7b02a2e57d5")
      public class SelectMonthsMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("Months");
        }

        @Override
        protected void execAction() {
          getTabBox().setSelectedTab(getMonthsBox());
        }
      }

      @Order(20)
      @ClassId("c8734ec6-ea4e-4e2d-8336-8527e288a9ee")
      public class SelectCommentsMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("Comments");
        }

        @Override
        protected void execAction() {
          getTabBox().setSelectedTab(getCommentsBox());
        }
      }

      @Order(30)
      @ClassId("138ca39a-21ce-4391-95b0-6b957bab292d")
      public class SelectDocumentsMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("Documents");
        }

        @Override
        protected void execAction() {
          getTabBox().setSelectedTab(getDocumentsBox());
        }
      }

    }

    @Order(50)
    @ClassId("d3bfa99d-2bde-4a2b-a19e-493960000045")
    public class SetFocusTabMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("FocusTab");
      }

      @Order(10)
      @ClassId("9d60ab9d-6e0f-420c-8932-14f40c5a40c2")
      public class FocusMonthsMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("Months");
        }

        @Override
        protected void execAction() {
          getMonthsBox().requestFocus();
        }
      }

      @Order(20)
      @ClassId("f65ea46c-6fa2-4e51-a9e8-a8116164267c")
      public class FocusCommentsMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("Comments");
        }

        @Override
        protected void execAction() {
          getCommentsBox().requestFocus();
        }
      }

      @Order(30)
      @ClassId("ddcc9512-683a-43fe-add0-d53427d8b98f")
      public class FocusDocumentsMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("Documents");
        }

        @Override
        protected void execAction() {
          getDocumentsBox().requestFocus();
        }
      }

      @Order(40)
      @ClassId("d45542a6-5bd9-4fea-b9bc-019d1624a345")
      public class FocusTabBoxMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("TabBox");
        }

        @Override
        protected void execAction() {
          getTabBox().requestFocus();
        }
      }

    }

    @Order(60)
    @ClassId("d7c6b110-c70c-41ec-98dc-73f7e30c9add")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
