/**
 *
 */
package org.eclipsescout.contacts.client.premium.ui.forms;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipsescout.contacts.client.premium.ui.forms.SelectContactForm.MainBox.CancelButton;
import org.eclipsescout.contacts.client.premium.ui.forms.SelectContactForm.MainBox.ContactBox;
import org.eclipsescout.contacts.client.premium.ui.forms.SelectContactForm.MainBox.ContactBox.ContactField;
import org.eclipsescout.contacts.client.premium.ui.forms.SelectContactForm.MainBox.OkButton;
import org.eclipsescout.contacts.shared.services.lookup.ContactLookupCall;

/**
 * @author mzi
 */
public class SelectContactForm extends AbstractForm {

  private List<String> m_existingContacts;

  /**
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  public SelectContactForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("SelectParticipant");
  }

  /**
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  public void startNew() throws ProcessingException {
    startInternal(new NewHandler());
  }

  /**
   * @return the CancelButton
   */
  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  /**
   * @return the ContactBox
   */
  public ContactBox getContactBox() {
    return getFieldByClass(ContactBox.class);
  }

  /**
   * @return the ContactField
   */
  public ContactField getContactField() {
    return getFieldByClass(ContactField.class);
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

  @Order(1000.0)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(1000.0)
    public class ContactBox extends AbstractGroupBox {

      @Order(1000.0)
      public class ContactField extends AbstractSmartField<String> {

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return ContactLookupCall.class;
        }

        @Override
        protected void execFilterLookupResult(ILookupCall<String> call, List<ILookupRow<String>> result) throws ProcessingException {
          List<ILookupRow<String>> toRemove = new ArrayList<>();

          for (ILookupRow<String> row : result) {
            if (getExistingContacts().contains(row.getKey())) {
              toRemove.add(row);
            }
          }

          result.removeAll(toRemove);
        }
      }
    }

    @Order(100000.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(101000.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class NewHandler extends AbstractFormHandler {
  }

  @FormData
  public List<String> getExistingContacts() {
    return m_existingContacts;
  }

  @FormData
  public void setExistingContacts(List<String> existingContacts) {
    m_existingContacts = existingContacts;
  }
}
