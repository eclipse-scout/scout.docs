/**
 *
 */
package org.eclipsescout.contacts.client.ui.template.formfield;

import java.net.URL;

import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.imagebox.AbstractImageField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.contacts.client.ui.forms.ContactForm;
import org.eclipsescout.contacts.client.ui.forms.PictureUrlForm;
import org.eclipsescout.contacts.shared.Icons;
import org.eclipsescout.contacts.shared.ui.template.formfield.AbstractPictureBoxData;

/**
 * @author mzi
 */
@FormData(value = AbstractPictureBoxData.class, sdkCommand = FormData.SdkCommand.CREATE, defaultSubtypeSdkCommand = FormData.DefaultSubtypeSdkCommand.CREATE)
public abstract class AbstractPictureBox extends AbstractGroupBox {
  @Override
  protected boolean getConfiguredBorderVisible() {
    return false;
  }

  @Override
  protected int getConfiguredGridColumnCount() {
    return 1;
  }

  @Override
  protected int getConfiguredGridH() {
    return 5;
  }

  @Override
  protected int getConfiguredGridW() {
    return 1;
  }

  /**
   * @return the PictureField
   */
  public PictureField getPictureField() {
    return getFieldByClass(PictureField.class);
  }

  /**
   * @return the PictureUrlField
   */
  public PictureUrlField getPictureUrlField() {
    return getFieldByClass(PictureUrlField.class);
  }

  @Order(1000.0)
  public class PictureField extends AbstractImageField {

    @Override
    protected boolean getConfiguredAutoFit() {
      return true;
    }

    @Override
    protected int getConfiguredGridH() {
      return 5;
    }

    @Override
    protected String getConfiguredImageId() {
      return Icons.User;
    }

    @Override
    protected boolean getConfiguredLabelVisible() {
      return false;
    }

    @Override
    protected Class<? extends IValueField> getConfiguredMasterField() {
      return ContactForm.MainBox.GeneralBox.PictureBox.PictureUrlField.class;
    }

    @Override
    protected void execChangedMasterValue(Object newMasterValue) throws ProcessingException {
      try {
        URL url = new URL((String) newMasterValue);
        setImage(IOUtility.getContent(url.openStream()));
        setAutoFit(true);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }

    @Order(1000.0)
    public class EditURLMenu extends AbstractExtensibleMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("EditURL");
      }

      @Override
      protected void execAction() throws ProcessingException {
        PictureUrlForm form = new PictureUrlForm();
        String url = getPictureUrlField().getValue();

        if (StringUtility.hasText(url)) {
          form.getPictureUrlField().setValue(url);
        }

        form.startModify();
        form.waitFor();
        if (form.isFormStored()) {
          getPictureUrlField().setValue(form.getPictureUrlField().getValue());
        }
      }
    }
  }

  @Order(2000.0)
  public class PictureUrlField extends AbstractStringField {

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("PictureUrl");
    }

    @Override
    protected boolean getConfiguredVisible() {
      return false;
    }
  }

}
