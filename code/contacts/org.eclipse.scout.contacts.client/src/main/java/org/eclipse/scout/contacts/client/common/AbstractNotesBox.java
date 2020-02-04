package org.eclipse.scout.contacts.client.common;

import org.eclipse.scout.contacts.shared.common.AbstractNotesBoxData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.DefaultSubtypeSdkCommand;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;

@ClassId("ea38d806-a38b-4511-bbda-ecb2fe694e70")
@FormData(value = AbstractNotesBoxData.class,
    sdkCommand = SdkCommand.CREATE,
    defaultSubtypeSdkCommand = DefaultSubtypeSdkCommand.CREATE)
public class AbstractNotesBox extends AbstractGroupBox {

  public NotesField getNotesField() {
    return getFieldByClass(NotesField.class);
  }

  @Override
  protected String getConfiguredLabel() {
    return TEXTS.get("Notes");
  }

  @Order(10)
  @ClassId("eb26a2e8-f717-4762-9563-c4136e84b61d")
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
