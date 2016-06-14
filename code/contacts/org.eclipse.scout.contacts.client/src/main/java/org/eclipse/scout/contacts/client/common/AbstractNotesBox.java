package org.eclipse.scout.contacts.client.common;

import org.eclipse.scout.contacts.shared.common.AbstractNotesBoxData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;

@FormData(value = AbstractNotesBoxData.class,
    sdkCommand = FormData.SdkCommand.CREATE,
    defaultSubtypeSdkCommand = FormData.DefaultSubtypeSdkCommand.CREATE)
public class AbstractNotesBox extends AbstractGroupBox {

  public NotesField getNotesField() {
    return getFieldByClass(NotesField.class);
  }

  @Override
  protected String getConfiguredLabel() {
    return TEXTS.get("Notes");
  }

  @Order(10)
  public class NotesField extends AbstractStringField {

    @Override
    protected int getConfiguredGridH() {
      return 3;
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
