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
package org.eclipse.scout.widgets.client.ui.forms;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenuSeparator;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.IGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.placeholder.AbstractPlaceholderField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.FieldVisibilityBox;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.FieldVisibilityBox.Placeholder1Field;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.FieldVisibilityBox.VisibleDocumentsField;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox.CommentsBox;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox.CommentsBox.CommentsField;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox.DocumentsBox;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox.DocumentsBox.FileTableField;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox.MonthsBox;
import org.eclipse.scout.widgets.client.ui.forms.TabBoxForm.MainBox.TabBox.MonthsBox.MonthDetailsBox;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFileTableField;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractMonthsBox;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractStatusButton;
import org.eclipse.scout.widgets.shared.Icons;

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

  public Placeholder1Field getPlaceholder1Field() {
    return getFieldByClass(Placeholder1Field.class);
  }

  public TabBox getTabBox() {
    return getFieldByClass(TabBox.class);
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(10)
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
    public class TabBox extends AbstractTabBox {

      @Override
      protected String getConfiguredTooltipText() {
        return TEXTS.get("TabBoxTooltip");
      }

      @Order(10)
      public class MonthsBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Months");
        }

        @Order(10)
        public class MonthDetailsBox extends AbstractMonthsBox {

          @Override
          protected int getConfiguredGridH() {
            return 5;
          }
        }
      }

      @Order(20)
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
      public class SayHelloMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return "Say Hello";
        }

        @Override
        protected void execAction() {
          MessageBoxes.createOk().withBody("Hello!").show();
        }
      }

      @Order(10)
      public class CountMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
          return "Count";
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
      public class SeparatorMenu extends AbstractMenuSeparator {
      }

      @Order(20)
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

    @Order(30)
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
      public class Placeholder1Field extends AbstractPlaceholderField {
      }

    }

    @Order(40)
    public class SetSelectedTabMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("SelectTab");
      }

      @Order(10)
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
    public class SetFocusTabMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("FocusTab");
      }

      @Order(10)
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
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
