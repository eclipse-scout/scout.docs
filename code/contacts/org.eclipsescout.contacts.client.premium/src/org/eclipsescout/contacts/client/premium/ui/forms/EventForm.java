/**
 *
 */
package org.eclipsescout.contacts.client.premium.ui.forms;

import java.util.List;
import java.util.Set;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.ActivityMapMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TreeMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.ValueFieldMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.shell.IShellService;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.contacts.client.premium.ui.forms.EventForm.MainBox.CancelButton;
import org.eclipsescout.contacts.client.premium.ui.forms.EventForm.MainBox.DetailsBox;
import org.eclipsescout.contacts.client.premium.ui.forms.EventForm.MainBox.DetailsBox.CommentsBox;
import org.eclipsescout.contacts.client.premium.ui.forms.EventForm.MainBox.DetailsBox.CommentsBox.CommentsField;
import org.eclipsescout.contacts.client.premium.ui.forms.EventForm.MainBox.DetailsBox.EventDetailsBox;
import org.eclipsescout.contacts.client.premium.ui.forms.EventForm.MainBox.DetailsBox.EventDetailsBox.EmailField;
import org.eclipsescout.contacts.client.premium.ui.forms.EventForm.MainBox.DetailsBox.EventDetailsBox.LocationBox;
import org.eclipsescout.contacts.client.premium.ui.forms.EventForm.MainBox.DetailsBox.EventDetailsBox.PhoneField;
import org.eclipsescout.contacts.client.premium.ui.forms.EventForm.MainBox.DetailsBox.ParticipantsBox;
import org.eclipsescout.contacts.client.premium.ui.forms.EventForm.MainBox.DetailsBox.ParticipantsBox.AddButton;
import org.eclipsescout.contacts.client.premium.ui.forms.EventForm.MainBox.DetailsBox.ParticipantsBox.ParticipantsField;
import org.eclipsescout.contacts.client.premium.ui.forms.EventForm.MainBox.DetailsBox.ParticipantsBox.ParticipantsField.Table.AddMenu;
import org.eclipsescout.contacts.client.premium.ui.forms.EventForm.MainBox.DetailsBox.ParticipantsBox.ParticipantsField.Table.RemoveMenu;
import org.eclipsescout.contacts.client.premium.ui.forms.EventForm.MainBox.DetailsBox.ParticipantsBox.RemoveButton;
import org.eclipsescout.contacts.client.premium.ui.forms.EventForm.MainBox.GeneralBox;
import org.eclipsescout.contacts.client.premium.ui.forms.EventForm.MainBox.GeneralBox.EndsField;
import org.eclipsescout.contacts.client.premium.ui.forms.EventForm.MainBox.GeneralBox.HomepageField;
import org.eclipsescout.contacts.client.premium.ui.forms.EventForm.MainBox.GeneralBox.StartsField;
import org.eclipsescout.contacts.client.premium.ui.forms.EventForm.MainBox.GeneralBox.TitleField;
import org.eclipsescout.contacts.client.premium.ui.forms.EventForm.MainBox.OkButton;
import org.eclipsescout.contacts.client.ui.forms.ContactForm;
import org.eclipsescout.contacts.client.ui.template.formfield.AbstractAddressBox;
import org.eclipsescout.contacts.client.ui.template.formfield.AbstractEmailField;
import org.eclipsescout.contacts.client.ui.template.formfield.AbstractPhoneField;
import org.eclipsescout.contacts.shared.ContactFormData;
import org.eclipsescout.contacts.shared.Icons;
import org.eclipsescout.contacts.shared.premium.EventFormData;
import org.eclipsescout.contacts.shared.premium.IEventService;
import org.eclipsescout.contacts.shared.premium.UpdateEventPermission;
import org.eclipsescout.contacts.shared.services.IContactService;
import org.eclipsescout.contacts.shared.services.lookup.CompanyLookupCall;

/**
 * @author mzi
 */
@FormData(value = EventFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class EventForm extends AbstractForm {

  private String m_eventId;

  /**
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  public EventForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Event");
  }

  /**
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  public void startModify() throws ProcessingException {
    startInternal(new ModifyHandler());
  }

  /**
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  public void startNew() throws ProcessingException {
    startInternal(new NewHandler());
  }

  /**
   * @return the AddButton
   */
  public AddButton getAddButton() {
    return getFieldByClass(AddButton.class);
  }

  /**
   * @return the AddButton
   */
  public RemoveButton getRemoveButton() {
    return getFieldByClass(RemoveButton.class);
  }

  /**
   * @return the CancelButton
   */
  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  /**
   * @return the CommentsBox
   */
  public CommentsBox getCommentsBox() {
    return getFieldByClass(CommentsBox.class);
  }

  /**
   * @return the CommentsField
   */
  public CommentsField getCommentsField() {
    return getFieldByClass(CommentsField.class);
  }

  /**
   * @return the CompanyDetailsBox
   */
  public EventDetailsBox getEventDetailsBox() {
    return getFieldByClass(EventDetailsBox.class);
  }

  /**
   * @return the DetailsBox
   */
  public DetailsBox getDetailsBox() {
    return getFieldByClass(DetailsBox.class);
  }

  /**
   * @return the EmailField
   */
  public EmailField getEmailField() {
    return getFieldByClass(EmailField.class);
  }

  /**
   * @return the EndsField
   */
  public EndsField getEndsField() {
    return getFieldByClass(EndsField.class);
  }

  /**
   * @return the GeneralBox
   */
  public GeneralBox getGeneralBox() {
    return getFieldByClass(GeneralBox.class);
  }

  /**
   * @return the HomepageField
   */
  public HomepageField getHomepageField() {
    return getFieldByClass(HomepageField.class);
  }

  /**
   * @return the LocationOldBox
   */
  public LocationBox getLocationBox() {
    return getFieldByClass(LocationBox.class);
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
   * @return the ParticipantsBox
   */
  public ParticipantsBox getParticipantsBox() {
    return getFieldByClass(ParticipantsBox.class);
  }

  /**
   * @return the ParticipantsField
   */
  public ParticipantsField getParticipantsField() {
    return getFieldByClass(ParticipantsField.class);
  }

  /**
   * @return the PhoneField
   */
  public PhoneField getPhoneField() {
    return getFieldByClass(PhoneField.class);
  }

  /**
   * @return the StartsField
   */
  public StartsField getStartsField() {
    return getFieldByClass(StartsField.class);
  }

  /**
   * @return the TitleField
   */
  public TitleField getTitleField() {
    return getFieldByClass(TitleField.class);
  }

  @Order(1000.0)
  public class MainBox extends AbstractGroupBox {

    @Order(1000.0)
    public class GeneralBox extends AbstractGroupBox {

      @Order(1000.0)
      public class TitleField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Title");
        }
      }

      @Order(2000.0)
      public class HomepageField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Homepage");
        }
      }

      @Order(3000.0)
      public class OpenInBrowserButton extends AbstractLinkButton {

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("OpenInBrowser");
        }

        @Override
        protected Class<? extends IValueField> getConfiguredMasterField() {
          return EventForm.MainBox.GeneralBox.HomepageField.class;
        }

        @Override
        protected boolean getConfiguredMasterRequired() {
          return true;
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() throws ProcessingException {
          SERVICES.getService(IShellService.class).shellOpen(getHomepageField().getValue());
        }
      }

      @Order(4000.0)
      public class StartsField extends AbstractDateTimeField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Starts");
        }
      }

      @Order(5000.0)
      public class EndsField extends AbstractDateTimeField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Ends");
        }
      }
    }

    @Order(2000.0)
    public class DetailsBox extends AbstractTabBox {

      @Order(1000.0)
      public class EventDetailsBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Details");
        }

        @Order(1000.0)
        public class LocationBox extends AbstractAddressBox {

          @Override
          protected void execInitField() throws ProcessingException {
            getStreetField().setVisible(false);
          }
        }

        @Order(2000.0)
        public class PhoneField extends AbstractPhoneField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Phone");
          }
        }

        @Order(3000.0)
        public class EmailField extends AbstractEmailField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Email");
          }
        }
      }

      @Order(1500.0)
      public class ParticipantsBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Participants");
        }

        @Order(1000.0)
        public class ParticipantsField extends AbstractTableField<ParticipantsField.Table> {

          @Override
          protected int getConfiguredGridH() {
            return 3;
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Order(1000.0)
          public class Table extends AbstractExtensibleTable {

            /**
             * @return the LastNameColumn
             */
            public LastNameColumn getLastNameColumn() {
              return getColumnSet().getColumnByClass(LastNameColumn.class);
            }

            @Override
            protected String getConfiguredDefaultIconId() {
              return Icons.Contact;
            }

            @Override
            protected void execRowAction(ITableRow row) throws ProcessingException {
              getMenu(EditMenu.class).doAction();
            }

            @Override
            protected void execRowsSelected(List<? extends ITableRow> rows) throws ProcessingException {
              getRemoveButton().setEnabled(rows.size() > 0);
            }

            /**
             * @return the CompanyColumn
             */
            public CompanyColumn getCompanyColumn() {
              return getColumnSet().getColumnByClass(CompanyColumn.class);
            }

            /**
             * @return the FirstNameColumn
             */
            public FirstNameColumn getFirstNameColumn() {
              return getColumnSet().getColumnByClass(FirstNameColumn.class);
            }

            /**
             * @return the ContactIdColumn
             */
            public ContactIdColumn getContactIdColumn() {
              return getColumnSet().getColumnByClass(ContactIdColumn.class);
            }

            @Order(1000.0)
            public class ContactIdColumn extends AbstractStringColumn {

              @Override
              protected boolean getConfiguredDisplayable() {
                return false;
              }

              @Override
              protected boolean getConfiguredPrimaryKey() {
                return true;
              }
            }

            @Order(2000.0)
            public class FirstNameColumn extends AbstractStringColumn {

              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("FirstName");
              }

              @Override
              protected int getConfiguredSortIndex() {
                return 0;
              }

              @Override
              protected int getConfiguredWidth() {
                return 120;
              }
            }

            @Order(3000.0)
            public class LastNameColumn extends AbstractStringColumn {

              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("LastName");
              }

              @Override
              protected int getConfiguredWidth() {
                return 120;
              }
            }

            @Order(4000.0)
            public class CompanyColumn extends AbstractSmartColumn<String> {

              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("Company");
              }

              @Override
              protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
                return CompanyLookupCall.class;
              }

              @Override
              protected int getConfiguredWidth() {
                return 250;
              }
            }

            @Order(1000.0)
            public class AddMenu extends AbstractExtensibleMenu {

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
              }

              @Override
              protected String getConfiguredText() {
                return TEXTS.get("Add");
              }

              @Override
              protected void execAction() throws ProcessingException {
                SelectContactForm form = new SelectContactForm();
                form.setExistingContacts(getContactIdColumn().getValues(false));
                form.startNew();
                form.waitFor();

                if (form.isFormStored()) {
                  String id = form.getContactField().getValue();
                  Table table = getTable();

                  for (ITableRow row : table.getDeletedRows()) {
                    if (row.getKeyValues().get(0).equals(id)) {
                      table.discardRow(row);
                      table.setRowState(row, ITableRow.STATUS_NON_CHANGED);
                      table.addRow(row);

                      return;
                    }
                  }

                  ITableRow row = table.addRow(table.createRow(), true);

                  ContactFormData fd = new ContactFormData();
                  fd.setContactId(id);
                  fd = SERVICES.getService(IContactService.class).load(fd);

                  table.getContactIdColumn().setValue(row, fd.getContactId());
                  table.getFirstNameColumn().setValue(row, fd.getFirstName().getValue());
                  table.getLastNameColumn().setValue(row, fd.getLastName().getValue());
                  table.getCompanyColumn().setValue(row, fd.getCompany().getValue());
                }
              }
            }

            @Order(1500.0)
            public class EditMenu extends AbstractExtensibleMenu {

              @Override
              protected String getConfiguredText() {
                return TEXTS.get("Edit");
              }

              @Override
              protected void execAction() throws ProcessingException {
                ContactForm form = new ContactForm();
                form.setContactId(getContactIdColumn().getSelectedValue());
                form.startModify();
              }
            }

            @Order(2000.0)
            public class RemoveMenu extends AbstractExtensibleMenu {

              @Override
              protected String getConfiguredKeyStroke() {
                return "delete";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.<IMenuType> hashSet(TreeMenuType.SingleSelection, ActivityMapMenuType.Activity, TableMenuType.MultiSelection, TableMenuType.SingleSelection, ValueFieldMenuType.NotNull);
              }

              @Override
              protected String getConfiguredText() {
                return TEXTS.get("Remove");
              }

              @Override
              protected void execAction() throws ProcessingException {
                getTable().deleteRows(getTable().getSelectedRows());
              }
            }
          }
        }

        @Order(2000.0)
        public class AddButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Add");
          }

          @Override
          protected void execClickAction() throws ProcessingException {
            getParticipantsField().getTable().getMenu(AddMenu.class).doAction();
          }
        }

        @Order(3000.0)
        public class RemoveButton extends AbstractLinkButton {

          @Override
          protected boolean getConfiguredEnabled() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Remove");
          }

          @Override
          protected void execClickAction() throws ProcessingException {
            getParticipantsField().getTable().getMenu(RemoveMenu.class).doAction();
          }
        }
      }

      @Order(2000.0)
      public class CommentsBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Comments");
        }

        @Order(1000.0)
        public class CommentsField extends AbstractStringField {

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
    }

    @Order(100000.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(101000.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() throws ProcessingException {
      IEventService service = SERVICES.getService(IEventService.class);
      EventFormData formData = new EventFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
      setEnabledPermission(new UpdateEventPermission());

    }

    @Override
    protected void execStore() throws ProcessingException {
      IEventService service = SERVICES.getService(IEventService.class);
      EventFormData formData = new EventFormData();
      exportFormData(formData);
      formData = service.store(formData);

    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() throws ProcessingException {
      IEventService service = SERVICES.getService(IEventService.class);
      EventFormData formData = new EventFormData();
      exportFormData(formData);
      formData = service.prepareCreate(formData);
      importFormData(formData);

    }

    @Override
    protected void execStore() throws ProcessingException {
      IEventService service = SERVICES.getService(IEventService.class);
      EventFormData formData = new EventFormData();
      exportFormData(formData);
      formData = service.create(formData);

    }

  }

  /**
   * @return the EventId
   */
  @FormData
  public String getEventId() {
    return m_eventId;
  }

  /**
   * @param eventId
   *          the EventId to set
   */
  @FormData
  public void setEventId(String eventId) {
    m_eventId = eventId;
  }
}
