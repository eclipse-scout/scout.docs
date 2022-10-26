/*
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.contacts.events.client.event;

import java.util.Set;

import org.eclipse.scout.contacts.client.Icons;
import org.eclipse.scout.contacts.client.common.AbstractAddressBox;
import org.eclipse.scout.contacts.client.common.AbstractDirtyFormHandler;
import org.eclipse.scout.contacts.client.common.AbstractEmailField;
import org.eclipse.scout.contacts.client.common.AbstractNotesBox;
import org.eclipse.scout.contacts.client.common.AbstractNotesBox.NotesField;
import org.eclipse.scout.contacts.client.common.ContactsHelper;
import org.eclipse.scout.contacts.client.person.PersonForm;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.CancelButton;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.DetailsBox;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.DetailsBox.ContactInfoBox;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.DetailsBox.ContactInfoBox.EmailField;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.DetailsBox.ContactInfoBox.LocationBox;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.DetailsBox.ContactInfoBox.PhoneField;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.DetailsBox.NotesBox;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.DetailsBox.ParticipantsBox;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.DetailsBox.ParticipantsBox.ParticipantTableFieldField;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.DetailsBox.ParticipantsBox.ParticipantTableFieldField.Table;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.GeneralBox;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.GeneralBox.EndsField;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.GeneralBox.HomepageField;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.GeneralBox.StartsField;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.GeneralBox.TitleField;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.OkButton;
import org.eclipse.scout.contacts.events.client.person.PersonChooserForm;
import org.eclipse.scout.contacts.events.shared.event.EventFormData;
import org.eclipse.scout.contacts.events.shared.event.IEventService;
import org.eclipse.scout.contacts.events.shared.event.UpdateEventPermission;
import org.eclipse.scout.contacts.shared.organization.OrganizationLookupCall;
import org.eclipse.scout.contacts.shared.person.IPersonService;
import org.eclipse.scout.contacts.shared.person.PersonFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TreeMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.ValueFieldMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.OpenUriAction;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.collection.OrderedCollection;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@ClassId("e91f6c5c-1926-45f9-ab79-35f7e6740b91")
@FormData(value = EventFormData.class, sdkCommand = SdkCommand.CREATE)
public class EventForm extends AbstractForm {

  private String eventId;

  @FormData
  public String getEventId() {
    return eventId;
  }

  @FormData
  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Event");
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return IForm.DISPLAY_HINT_VIEW;
  }

  public void startModify() {
    startInternalExclusive(new ModifyHandler());
  }

  public void startNew() {
    startInternal(new NewHandler());
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public NotesBox getNotesBox() {
    return getFieldByClass(NotesBox.class);
  }

  public NotesField getNotesField() {
    return getFieldByClass(NotesField.class);
  }

  public ContactInfoBox getContactInfoBox() {
    return getFieldByClass(ContactInfoBox.class);
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

  public ParticipantTableFieldField getParticipantTableField() {
    return getFieldByClass(ParticipantTableFieldField.class);
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

  @Override
  public Object computeExclusiveKey() {
    return getEventId();
  }

  @Override
  protected void execInitForm() {
    BEANS.get(ContactsHelper.class).handleReadOnly(getOkButton());
  }

  @Order(1)
  @ClassId("3d45e0e1-e128-4aad-9cef-6dbd78aaeb7d")
  public class MainBox extends AbstractGroupBox {

    @Override
    protected void injectMenusInternal(OrderedCollection<IMenu> menus) {
      BEANS.get(ContactsHelper.class).injectReadOnlyMenu(menus);
    }

    @Order(10)
    @ClassId("f1821434-d98e-4796-b120-26245a5c81c8")
    public class GeneralBox extends AbstractGroupBox {

      @Order(10)
      @ClassId("a31ff8e4-b8ec-42dd-824b-73a1665c5435")
      public class TitleField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Title");
        }
      }

      @Order(20)
      @ClassId("380febfb-fa9a-48e4-b953-1159634d7dc7")
      public class HomepageField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Homepage");
        }
      }

      @Order(30)
      @ClassId("66be6ccf-f6f7-4b5c-b898-6d2b47410f99")
      public class OpenHomepageButtonBox extends AbstractSequenceBox {

        @Order(10)
        @ClassId("c508a404-9b2f-4836-818a-243d5a63b5d9")
        public class OpenHomepageButton extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("OpenHomepage");
          }

          @Override
          protected String getConfiguredIconId() {
            return Icons.Organization;
          }

          @Override
          protected Class<? extends IValueField> getConfiguredMasterField() {
            return HomepageField.class;
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
          protected void execClickAction() {
            getDesktop().openUri(getHomepageField().getValue(), OpenUriAction.NEW_WINDOW);
          }
        }
      }

      @Order(40)
      @ClassId("22fb39f0-622e-4564-b386-73308752fed9")
      public class StartsField extends AbstractDateTimeField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Starts");
        }
      }

      @Order(50)
      @ClassId("ce05f47a-f2e1-4fbc-9c91-1e5874dc46e4")
      public class EndsField extends AbstractDateTimeField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Ends");
        }
      }
    }

    @Order(20)
    @ClassId("adb73c67-72f3-463e-a07f-db4fc4018bcb")
    public class DetailsBox extends AbstractTabBox {

      @Order(10)
      @ClassId("9a57e2a0-ec0c-4d6d-ab91-ce688fcb7db2")
      public class ContactInfoBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ContactInfo");
        }

        @Order(10)
        @ClassId("c70bcbf9-3b6a-49c3-ae5a-714cbfc087e0")
        public class LocationBox extends AbstractAddressBox {

          @Override
          protected void execInitField() {
            getStreetField().setVisible(false);
          }
        }

        @Order(20)
        @ClassId("d945d161-20d1-4958-89bd-d6d276bb07dd")
        public class PhoneField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Phone");
          }
        }

        @Order(30)
        @ClassId("cad13c3a-4241-43b5-8b85-e728b24655bc")
        public class EmailField extends AbstractEmailField {
        }
      }

      @Order(20)
      @ClassId("8a767eb9-831c-433d-b852-52e6247865fe")
      public class ParticipantsBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Participants");
        }

        @Order(10)
        @ClassId("627f6a0e-349f-4846-8449-1ec41156657d")
        public class ParticipantTableFieldField extends AbstractTableField<Table> {

          @Override
          protected int getConfiguredGridH() {
            return 3;
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @ClassId("929bdd78-df3d-4700-bb9f-db7602e23b3b")
          public class Table extends AbstractTable {

            public LastNameColumn getLastNameColumn() {
              return getColumnSet().getColumnByClass(LastNameColumn.class);
            }

            @Override
            protected Class<? extends IMenu> getConfiguredDefaultMenu() {
              return EditMenu.class;
            }

            @Override
            protected boolean getConfiguredAutoResizeColumns() {
              return true;
            }

            public OrganizationColumn getOrganizationColumn() {
              return getColumnSet().getColumnByClass(OrganizationColumn.class);
            }

            public FirstNameColumn getFirstNameColumn() {
              return getColumnSet().getColumnByClass(FirstNameColumn.class);
            }

            public PersonIdColumn getPersonIdColumn() {
              return getColumnSet().getColumnByClass(PersonIdColumn.class);
            }

            @Order(10)
            @ClassId("6cf07694-9394-490e-8752-94e6efa82e93")
            public class PersonIdColumn extends AbstractStringColumn {

              @Override
              protected boolean getConfiguredDisplayable() {
                return false;
              }

              @Override
              protected boolean getConfiguredPrimaryKey() {
                return true;
              }
            }

            @Order(20)
            @ClassId("e12f5de1-e67f-486f-bd43-96e96808a7e6")
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

            @Order(30)
            @ClassId("f22e0446-243f-4434-a73b-95b2244d3a47")
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

            @Order(40)
            @ClassId("bdacc1ae-1354-435d-b1f8-5f7d274a0882")
            public class OrganizationColumn extends AbstractSmartColumn<String> {

              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("Organization");
              }

              @Override
              protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
                return OrganizationLookupCall.class;
              }

              @Override
              protected int getConfiguredWidth() {
                return 250;
              }
            }

            @Order(10)
            @ClassId("91d26f78-f646-460f-b989-d18b3aca544d")
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
              protected void execAction() {
                PersonChooserForm personChooserForm = new PersonChooserForm();
                personChooserForm.setExcludedPersons(getPersonIdColumn().getValues(false));
                personChooserForm.startNew();
                personChooserForm.waitFor();

                if (personChooserForm.isFormStored()) {
                  String personId = personChooserForm.getPersonField().getValue();
                  Table participantTable = getTable();

                  ITableRow row = participantTable.addRow(participantTable.createRow(), true);

                  PersonFormData personFormData = new PersonFormData();
                  personFormData.setPersonId(personId);
                  personFormData = BEANS.get(IPersonService.class).load(personFormData);

                  participantTable.getPersonIdColumn().setValue(row, personFormData.getPersonId());
                  participantTable.getFirstNameColumn().setValue(row, personFormData.getFirstName().getValue());
                  participantTable.getLastNameColumn().setValue(row, personFormData.getLastName().getValue());
                  participantTable.getOrganizationColumn().setValue(row, personFormData.getOrganization().getValue());
                }
              }
            }

            @Order(10)
            @ClassId("73e1c300-2429-48af-ab78-6ab47d633803")
            public class EditMenu extends AbstractMenu {

              @Override
              protected String getConfiguredText() {
                return TEXTS.get("Edit");
              }

              @Override
              protected void execAction() {
                PersonForm form = new PersonForm();
                form.setPersonId(getPersonIdColumn().getSelectedValue());
                form.startModify();
              }
            }

            @Order(20)
            @ClassId("1e509cbb-cfff-47d8-8648-a33c25266ace")
            public class RemoveMenu extends AbstractMenu {

              @Override
              protected String getConfiguredKeyStroke() {
                return "delete";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.<IMenuType> hashSet(
                    TreeMenuType.SingleSelection,
                    TableMenuType.MultiSelection,
                    TableMenuType.SingleSelection,
                    ValueFieldMenuType.NotNull);
              }

              @Override
              protected String getConfiguredText() {
                return TEXTS.get("Remove");
              }

              @Override
              protected void execAction() {
                getTable().deleteRows(getTable().getSelectedRows());
              }
            }
          }
        }
      }

      @Order(30)
      @ClassId("72732dfc-e9f9-4b61-a260-a86bb3041e3d")
      public class NotesBox extends AbstractNotesBox {
      }
    }

    @Order(100)
    @ClassId("3949273b-2f25-48a7-a80e-540094b1daba")
    public class OkButton extends AbstractOkButton {
    }

    @Order(101)
    @ClassId("bf36ecaa-9478-4985-9571-e00182e9df54")
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractDirtyFormHandler {

    @Override
    protected void execLoad() {
      EventFormData formData = new EventFormData();
      exportFormData(formData);
      formData = BEANS.get(IEventService.class).load(formData);
      importFormData(formData);
      setEnabledPermission(new UpdateEventPermission());

      getForm().setSubTitle(getTitleField().getValue());
    }

    @Override
    protected void execStore() {
      EventFormData formData = new EventFormData();
      exportFormData(formData);
      BEANS.get(IEventService.class).store(formData);
    }

    @Override
    protected void execDirtyStatusChanged(boolean dirty) {
      getForm().setSubTitle(getTitleField().getValue());
    }

    @Override
    protected boolean getConfiguredOpenExclusive() {
      return true;
    }
  }

  public class NewHandler extends AbstractDirtyFormHandler {

    @Override
    protected void execLoad() {
      EventFormData formData = new EventFormData();
      exportFormData(formData);
      formData = BEANS.get(IEventService.class).prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    protected void execStore() {
      EventFormData formData = new EventFormData();
      exportFormData(formData);
      formData = BEANS.get(IEventService.class).create(formData);
      importFormData(formData);
    }

    @Override
    protected void execDirtyStatusChanged(boolean dirty) {
      getForm().setSubTitle(getTitleField().getValue());
    }
  }
}
