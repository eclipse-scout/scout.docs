package org.eclipse.scout.contacts.client.common;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.scout.contacts.client.Icons;
import org.eclipse.scout.contacts.shared.common.AbstractUrlImageFieldData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.form.fields.imagefield.AbstractImageField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.status.Status;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.TEXTS;

// tag::template[]
@FormData(value = AbstractUrlImageFieldData.class, // <1>
    sdkCommand = FormData.SdkCommand.CREATE,
    defaultSubtypeSdkCommand = FormData.DefaultSubtypeSdkCommand.CREATE)
// tag::menu[]
public class AbstractUrlImageField extends AbstractImageField {

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
  // end::template[]

  @Override
  protected String getConfiguredImageId() {
    return Icons.Person;
  }

  // tag::menu[]
  @Order(10)
  public class EditURLMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("EditURL");
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
      }
    }
  }
  // end::menu[]
  // tag::template[]

  protected void updateImage() {
    clearErrorStatus();

    if (url == null) {
      setImage(null);
    }
    else {
      try {
        setImage(IOUtility.readFromUrl(new URL((String) url)));
        setAutoFit(true);
      }
      // end::template[]
      catch (MalformedURLException e) {
        addErrorStatus(new Status(TEXTS.get("InvalidImageUrl"), IStatus.WARNING));
      }
      // tag::template[]
      catch (Exception e) {
        addErrorStatus(new Status(TEXTS.get("FailedToAccessImageFromUrl"), IStatus.WARNING));
      }
    }

    getForm().touch();
  }
}
// end::template[]
// end::menu[]
