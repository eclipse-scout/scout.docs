package org.eclipse.scout.widgets.client.ui.forms;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.NullFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxDynamicFieldsForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxDynamicFieldsForm.MainBox.DetailBox;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxDynamicFieldsForm.MainBox.DetailBox.FirstNameField;

/**
 * <h3>{@link GroupBoxDynamicFieldsForm}</h3>
 *
 * @author aho
 */
public class GroupBoxDynamicFieldsForm extends AbstractForm implements IPageForm {
  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("GroupBoxWithDynamicFields");
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public FirstNameField getFirstNameField() {
    return getFieldByClass(FirstNameField.class);
  }

  public DetailBox getDetailBox() {
    return getFieldByClass(DetailBox.class);
  }

  @Override
  public void startPageForm() {
    startInternal(new NullFormHandler());
  }

  public class MainBox extends AbstractGroupBox {

    @Order(1000)
    @ClassId("48565377-244a-4870-9f38-2c26db811e78")
    public class DetailBox extends AbstractGroupBox {
      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("GroupBox");
      }

      @Order(1000)
      @ClassId("03a222ee-6692-4557-a262-7d1d7bfb6f26")
      public class FirstNameField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FirstName");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 128;
        }
      }

      @Order(1000)
      @ClassId("8caf25dc-1507-4aca-a5fd-2652dc988190")
      public class AddStringFieldMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return TEXTS.get("AddStringField");
        }

        @Override
        protected void execAction() {
          getDetailBox().addField(new AbstractStringField() {
            @Override
            protected String getConfiguredLabel() {
              return "Dynamic Stringfield";
            }
          });
        }
      }
    }

    @Order(2000)
    @ClassId("9ad50b0c-9080-4d90-b443-fe8e0b06c0bd")
    public class CloseButton extends AbstractCloseButton {
    }

  }
}
