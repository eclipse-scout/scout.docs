package org.eclipse.scout.contacts.client.common;

import java.util.Set;

import org.eclipse.scout.contacts.client.Icons;
import org.eclipse.scout.contacts.shared.common.AbstractUrlImageFieldData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.DefaultSubtypeSdkCommand;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.ImageFieldMenuType;
import org.eclipse.scout.rt.client.ui.form.fields.imagefield.AbstractImageField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;

// tag::template[]
@ClassId("73a4276f-77b2-4ad2-b414-7f806284bdb3")
@FormData(value = AbstractUrlImageFieldData.class, // <1>
    sdkCommand = SdkCommand.CREATE,
    defaultSubtypeSdkCommand = DefaultSubtypeSdkCommand.CREATE)
// tag::menu[]
public abstract class AbstractUrlImageField extends AbstractImageField {

  // end::menu[]
  private String url; // <2>

  @FormData // <2>
  public String getUrl() {
    return url;
  }

  @FormData // <2>
  public void setUrl(String url) {
    this.url = url;
    updateImage();
  }

  @Override
  protected boolean getConfiguredLabelVisible() {
    return false;
  }

  @Override
  protected int getConfiguredGridH() {
    return 5;
  }

  @Override
  protected boolean getConfiguredAutoFit() {
    return true;
  }

  @Override
  protected String getConfiguredImageId() {
    return Icons.User;
  }

  // end::template[]

  // tag::menu[]
  @Order(10)
  @ClassId("99c1c12a-84d4-4c1a-a009-dfd2b7b55ded")
  public class EditURLMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("EditURL");
    }

    @Override
    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
      return CollectionUtility.<IMenuType> hashSet(
          ImageFieldMenuType.ImageUrl,
          ImageFieldMenuType.ImageId,
          ImageFieldMenuType.Null);
    }

    @Override
    protected void execAction() {
      PictureUrlForm form = new PictureUrlForm();
      String oldUrl = getUrl();

      if (StringUtility.hasText(oldUrl)) { // <1>
        form.getUrlField().setValue(oldUrl);
      }

      form.startModify();
      form.waitFor(); // <2>

      if (form.isFormStored()) { // <3>
        setUrl(form.getUrlField().getValue());
        getForm().touch();
      }
    }
  }
  // end::menu[]
  // tag::template[]

  protected void updateImage() {
    setImageUrl(this.url);
  }
// tag::menu[]
}
// end::template[]
// end::menu[]
