/**
 * 
 */
package org.eclipsescout.contacts.client.ui.forms;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.contacts.client.ui.forms.PictureURLForm.MainBox.CancelButton;
import org.eclipsescout.contacts.client.ui.forms.PictureURLForm.MainBox.OkButton;
import org.eclipsescout.contacts.client.ui.forms.PictureURLForm.MainBox.URLBox;
import org.eclipsescout.contacts.client.ui.forms.PictureURLForm.MainBox.URLBox.PictureURLField;

/**
 * @author mzi
 */
public class PictureURLForm extends AbstractForm {

  private Long m_pictureURLNr;

  /**
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  public PictureURLForm() throws ProcessingException {
    super();
  }

  /**
   * @return the PictureURLNr
   */
  @FormData
  public Long getPictureURLNr() {
    return m_pictureURLNr;
  }

  /**
   * @param pictureURLNr
   *          the PictureURLNr to set
   */
  @FormData
  public void setPictureURLNr(Long pictureURLNr) {
    m_pictureURLNr = pictureURLNr;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("PictureURL");
  }

  /**
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  public void startModify() throws ProcessingException {
    startInternal(new ModifyHandler());
  }

  /**
   * @return the CancelButton
   */
  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  /**
   * @return the MainBox
   */
  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  /**
   * @return the OkButton
   */
  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  /**
   * @return the PictureURLField
   */
  public PictureURLField getPictureURLField(){
    return getFieldByClass(PictureURLField.class);
  }

  /**
   * @return the URLBox
   */
  public URLBox getURLBox(){
    return getFieldByClass(URLBox.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class URLBox extends AbstractGroupBox {

      @Order(10.0)
      public class PictureURLField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("PictureURL");
        }
      }
    }

    @Order(20.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(30.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
  }
}
