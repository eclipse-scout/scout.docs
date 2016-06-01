package org.eclipse.scout.contacts.client.common;

import java.io.InputStream;
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

@FormData(value = AbstractUrlImageFieldData.class, sdkCommand = FormData.SdkCommand.CREATE, defaultSubtypeSdkCommand = FormData.DefaultSubtypeSdkCommand.CREATE)
public class AbstractUrlImageField extends AbstractImageField {

  private String url;

  @FormData
  public String getUrl() {
    return url;
  }

  @FormData
  public void setUrl(String url) {
    this.url = url;
    clearErrorStatus();
    updateImage();
    getForm().touch();
  }

  @Override
  protected boolean getConfiguredLabelVisible() {
    return false;
  }

  @Override
  protected String getConfiguredImageId() {
    return Icons.Person;
  }

  @Order(1)
  public class EditURLMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("EditURL");
    }

    @Override
    protected void execAction() {
      updateUrl();
    }
  }

  protected void updateImage() {
    if (url == null) {
      setImage(null);
    }
    else {
      try (InputStream in = new URL((String) url).openStream()) {
        setImage(IOUtility.readBytes(in));
        setAutoFit(true);
      }
      catch (MalformedURLException e) {
        addErrorStatus(new Status(TEXTS.get("InvalidImageUrl"), IStatus.WARNING));
      }
      catch (Exception e) {
        addErrorStatus(new Status(TEXTS.get("FailedToAccessImageFromUrl"), IStatus.WARNING));
      }
    }
  }

  protected void updateUrl() {
    String oldUrl = getUrl();
    PictureUrlForm form = new PictureUrlForm();

    if (StringUtility.hasText(oldUrl)) {
      form.getUrlField().setValue(oldUrl);
    }

    form.startModify();
    form.waitFor();

    if (form.isFormStored()) {
      setUrl(form.getUrlField().getValue());
    }
  }
}
