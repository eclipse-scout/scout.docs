/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.event.client.event;

import java.util.List;
import java.util.Set;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.contacts.client.Icons;
import org.eclipse.scout.contacts.client.contact.ContactForm;
import org.eclipse.scout.contacts.client.template.AbstractAddressBox;
import org.eclipse.scout.contacts.client.template.AbstractEmailField;
import org.eclipse.scout.contacts.client.template.AbstractPhoneField;
import org.eclipse.scout.contacts.event.client.contact.ContactChooserForm;
import org.eclipse.scout.contacts.event.client.event.EventForm.MainBox.CancelButton;
import org.eclipse.scout.contacts.event.client.event.EventForm.MainBox.DetailsBox;
import org.eclipse.scout.contacts.event.client.event.EventForm.MainBox.DetailsBox.CommentsBox;
import org.eclipse.scout.contacts.event.client.event.EventForm.MainBox.DetailsBox.CommentsBox.CommentsField;
import org.eclipse.scout.contacts.event.client.event.EventForm.MainBox.DetailsBox.EventDetailsBox;
import org.eclipse.scout.contacts.event.client.event.EventForm.MainBox.DetailsBox.EventDetailsBox.EmailField;
import org.eclipse.scout.contacts.event.client.event.EventForm.MainBox.DetailsBox.EventDetailsBox.LocationBox;
import org.eclipse.scout.contacts.event.client.event.EventForm.MainBox.DetailsBox.EventDetailsBox.PhoneField;
import org.eclipse.scout.contacts.event.client.event.EventForm.MainBox.DetailsBox.ParticipantsBox;
import org.eclipse.scout.contacts.event.client.event.EventForm.MainBox.DetailsBox.ParticipantsBox.AddButton;
import org.eclipse.scout.contacts.event.client.event.EventForm.MainBox.DetailsBox.ParticipantsBox.ParticipantsField;
import org.eclipse.scout.contacts.event.client.event.EventForm.MainBox.DetailsBox.ParticipantsBox.ParticipantsField.Table.AddMenu;
import org.eclipse.scout.contacts.event.client.event.EventForm.MainBox.DetailsBox.ParticipantsBox.ParticipantsField.Table.RemoveMenu;
import org.eclipse.scout.contacts.event.client.event.EventForm.MainBox.DetailsBox.ParticipantsBox.RemoveButton;
import org.eclipse.scout.contacts.event.client.event.EventForm.MainBox.GeneralBox;
import org.eclipse.scout.contacts.event.client.event.EventForm.MainBox.GeneralBox.EndsField;
import org.eclipse.scout.contacts.event.client.event.EventForm.MainBox.GeneralBox.HomepageField;
import org.eclipse.scout.contacts.event.client.event.EventForm.MainBox.GeneralBox.StartsField;
import org.eclipse.scout.contacts.event.client.event.EventForm.MainBox.GeneralBox.TitleField;
import org.eclipse.scout.contacts.event.client.event.EventForm.MainBox.OkButton;
import org.eclipse.scout.contacts.event.shared.event.EventFormData;
import org.eclipse.scout.contacts.event.shared.event.IEventService;
import org.eclipse.scout.contacts.event.shared.event.UpdateEventPermission;
import org.eclipse.scout.contacts.shared.company.CompanyLookupCall;
import org.eclipse.scout.contacts.shared.contact.ContactFormData;
import org.eclipse.scout.contacts.shared.contact.IContactService;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.ActivityMapMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TreeMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.ValueFieldMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.OpenUriHint;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@FormData(value = EventFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class EventForm extends AbstractForm {

  private String m_eventId;

  public EventForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Event");
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return IForm.DISPLAY_HINT_VIEW;
  }

  public void startModify() throws ProcessingException {
    startInternal(new ModifyHandler());
  }

  public void startNew() throws ProcessingException {
    startInternal(new NewHandler());
  }

  public AddButton getAddButton() {
    return getFieldByClass(AddButton.class);
  }

  public RemoveButton getRemoveButton() {
    return getFieldByClass(RemoveButton.class);
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public CommentsBox getCommentsBox() {
    return getFieldByClass(CommentsBox.class);
  }

  public CommentsField getCommentsField() {
    return getFieldByClass(CommentsField.class);
  }

  public EventDetailsBox getEventDetailsBox() {
    return getFieldByClass(EventDetailsBox.class);
  }

  public DetailsBox getDetailsBox() {
    return getFieldByClass(DetailsBox.class);
  }

  public EmailField getEmailField() {
    return getFieldByClass(EmailField.class);
  }

  public EndsField getEndsField() {
    return getFieldByClass(EndsField.class);
  }

  public GeneralBox getGeneralBox() {
    return getFieldByClass(GeneralBox.class);
  }

  public HomepageField getHomepageField() {
    return getFieldByClass(HomepageField.class);
  }

  public LocationBox getLocationBox() {
    return getFieldByClass(LocationBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public ParticipantsBox getParticipantsBox() {
    return getFieldByClass(ParticipantsBox.class);
  }

  public ParticipantsField getParticipantsField() {
    return getFieldByClass(ParticipantsField.class);
  }

  public PhoneField getPhoneField() {
    return getFieldByClass(PhoneField.class);
  }

  public StartsField getStartsField() {
    return getFieldByClass(StartsField.class);
  }

  public TitleField getTitleField() {
    return getFieldByClass(TitleField.class);
  }

  @Order(1_000.0)
  public class MainBox extends AbstractGroupBox {

    @Order(1_000.0)
    public class GeneralBox extends AbstractGroupBox {

      @Order(1_000.0)
      public class TitleField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Title");
        }
      }

      @Order(2_000.0)
      public class HomepageField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Homepage");
        }
      }

      @Order(3_000.0)
      public class OpenHomepageButton extends AbstractLinkButton {

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("OpenHomepage");
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
          getDesktop().openUri(getHomepageField().getValue(), OpenUriHint.NEW_WINDOW);
        }
      }

      @Order(4_000.0)
      public class StartsField extends AbstractDateTimeField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Starts");
        }
      }

      @Order(5_000.0)
      public class EndsField extends AbstractDateTimeField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Ends");
        }
      }
    }

    @Order(2_000.0)
    public class DetailsBox extends AbstractTabBox {

      @Order(1_000.0)
      public class EventDetailsBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Details");
        }

        @Order(1_000.0)
        public class LocationBox extends AbstractAddressBox {

          @Override
          protected void execInitField() throws ProcessingException {
            getStreetField().setVisible(false);
          }
        }

        @Order(2_000.0)
        public class PhoneField extends AbstractPhoneField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Phone");
          }
        }

        @Order(3_000.0)
        public class EmailField extends AbstractEmailField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Email");
          }
        }
      }

      @Order(2_000.0)
      public class ParticipantsBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Participants");
        }

        @Order(1_000.0)
        public class ParticipantsField extends AbstractTableField<ParticipantsField.Table> {

          @Override
          protected int getConfiguredGridH() {
            return 3;
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Order(1_000.0)
          public class Table extends AbstractTable {

            public LastNameColumn getLastNameColumn() {
              return getColumnSet().getColumnByClass(LastNameColumn.class);
            }

            @Override
            protected String getConfiguredDefaultIconId() {
              return Icons.Contact;
            }

            @Override
            protected Class<? extends IMenu> getConfiguredDefaultMenu() {
              return EditMenu.class;
            }

            @Override
            protected void execRowsSelected(List<? extends ITableRow> rows) throws ProcessingException {
              getRemoveButton().setEnabled(rows.size() > 0);
            }

            public CompanyColumn getCompanyColumn() {
              return getColumnSet().getColumnByClass(CompanyColumn.class);
            }

            public FirstNameColumn getFirstNameColumn() {
              return getColumnSet().getColumnByClass(FirstNameColumn.class);
            }

            public ContactIdColumn getContactIdColumn() {
              return getColumnSet().getColumnByClass(ContactIdColumn.class);
            }

            @Order(1_000.0)
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

            @Order(2_000.0)
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

            @Order(3_000.0)
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

            @Order(4_000.0)
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

            @Order(1_000.0)
            public class AddMenu extends AbstractMenu {

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
                ContactChooserForm contactChooserForm = new ContactChooserForm();
                contactChooserForm.setFilteredContacts(getContactIdColumn().getValues(false));
                contactChooserForm.startNew();
                contactChooserForm.waitFor();

                if (contactChooserForm.isFormStored()) {
                  String contactId = contactChooserForm.getContactField().getValue();
                  Table participantTable = getTable();

                  // TODO [mzi] what is this code good for?
                  for (ITableRow deletedParticipantRow : participantTable.getDeletedRows()) {
                    if (participantTable.getContactIdColumn().getValue(deletedParticipantRow) == contactId) {
                      participantTable.discardRow(deletedParticipantRow);
                    }
                    participantTable.setRowState(deletedParticipantRow, ITableRow.STATUS_NON_CHANGED);
                    participantTable.addRow(deletedParticipantRow);

                    return;
                  }

                  ITableRow row = participantTable.addRow(participantTable.createRow(), true);

                  ContactFormData contactFormData = new ContactFormData();
                  contactFormData.setContactId(contactId);
                  contactFormData = BEANS.get(IContactService.class).load(contactFormData);

                  participantTable.getContactIdColumn().setValue(row, contactFormData.getContactId());
                  participantTable.getFirstNameColumn().setValue(row, contactFormData.getFirstName().getValue());
                  participantTable.getLastNameColumn().setValue(row, contactFormData.getLastName().getValue());
                  participantTable.getCompanyColumn().setValue(row, contactFormData.getCompany().getValue());
                }
              }
            }

            @Order(1_500.0)
            public class EditMenu extends AbstractMenu {

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

            @Order(2_000.0)
            public class RemoveMenu extends AbstractMenu {

              @Override
              protected String getConfiguredKeyStroke() {
                return "delete";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.<IMenuType> hashSet(
                    TreeMenuType.SingleSelection,
                    ActivityMapMenuType.Activity,
                    TableMenuType.MultiSelection,
                    TableMenuType.SingleSelection,
                    ValueFieldMenuType.NotNull);
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

        @Order(2_000.0)
        public class AddButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Add");
          }

          @Override
          protected void execClickAction() throws ProcessingException {
            getParticipantsField().getTable().getMenuByClass(AddMenu.class).doAction();
          }
        }

        @Order(3_000.0)
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
            getParticipantsField().getTable().getMenuByClass(RemoveMenu.class).doAction();
          }
        }
      }

      @Order(3_000.0)
      public class CommentsBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Comments");
        }

        @Order(1_000.0)
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

    @Order(100_000.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(101_000.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() throws ProcessingException {
      EventFormData formData = new EventFormData();
      exportFormData(formData);
      formData = BEANS.get(IEventService.class).load(formData);
      importFormData(formData);
      setEnabledPermission(new UpdateEventPermission());
    }

    @Override
    protected void execStore() throws ProcessingException {
      EventFormData formData = new EventFormData();
      exportFormData(formData);
      formData = BEANS.get(IEventService.class).store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() throws ProcessingException {
      EventFormData formData = new EventFormData();
      exportFormData(formData);
      formData = BEANS.get(IEventService.class).prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() throws ProcessingException {
      EventFormData formData = new EventFormData();
      exportFormData(formData);
      formData = BEANS.get(IEventService.class).create(formData);
    }

  }

  @FormData
  public String getEventId() {
    return m_eventId;
  }

  @FormData
  public void setEventId(String eventId) {
    m_eventId = eventId;
  }
}
