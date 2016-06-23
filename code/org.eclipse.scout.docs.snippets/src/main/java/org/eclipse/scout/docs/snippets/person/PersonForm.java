package org.eclipse.scout.docs.snippets.person;

import org.eclipse.scout.docs.snippets.person.PersonForm.MainBox.CancelButton;
import org.eclipse.scout.docs.snippets.person.PersonForm.MainBox.LastBox;
import org.eclipse.scout.docs.snippets.person.PersonForm.MainBox.NameField;
import org.eclipse.scout.docs.snippets.person.PersonForm.MainBox.OkButton;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.TEXTS;

@ClassId("7d73104f-4982-4b7d-856f-ce133b70bb26")
@FormData(value = PersonFormData.class, sdkCommand = SdkCommand.CREATE)
public class PersonForm extends AbstractForm {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Person");
  }

  public void startModify() {
    startInternalExclusive(new ModifyHandler());
  }

  public void startNew() {
    startInternal(new NewHandler());
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public LastBox getLastBox() {
    return getFieldByClass(LastBox.class);
  }

  public NameField getNameField() {
    return getFieldByClass(NameField.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  @Order(1000)
  @ClassId("526a35b6-9f5c-402d-a832-0646a012e6b7")
  public class MainBox extends AbstractGroupBox {

    @Order(1000)
    @ClassId("eafb7554-6295-4ec4-a7d4-45211ecbd69f")
    public class NameField extends AbstractStringField {
      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Name");
      }

      @Override
      protected int getConfiguredMaxLength() {
        return 128;
      }
    }

    @Order(100000)
    @ClassId("29170026-a0d4-4377-9b14-7f2295d2fd95")
    public class OkButton extends AbstractOkButton {
    }

    @Order(101000)
    @ClassId("a4197a74-a873-4759-9ac3-f02f02de3aa0")
    public class CancelButton extends AbstractCancelButton {
    }

    @Order(102000)
    @ClassId("d45e0930-b0aa-4bf9-8db5-4d54a9a44dc8")
    public class LastBox extends AbstractListBox<Long> {
      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Day");
      }

      @Override
      protected int getConfiguredGridH() {
        return 6;
      }
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() {
    }

    @Override
    protected void execStore() {
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() {
    }

    @Override
    protected void execStore() {
    }
  }
}
