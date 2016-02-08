/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.events.client.event;

import java.util.Set;

import org.eclipse.scout.contacts.client.Icons;
import org.eclipse.scout.contacts.client.common.AbstractDirtyFormHandler;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.CancelButton;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.DetailsBox;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.DetailsBox.CommentsBox;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.DetailsBox.CommentsBox.CommentsField;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.DetailsBox.EventDetailsBox;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.DetailsBox.EventDetailsBox.EmailField;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.DetailsBox.EventDetailsBox.LocationBox;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.DetailsBox.EventDetailsBox.PhoneField;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.DetailsBox.ParticipantsBox;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.DetailsBox.ParticipantsBox.ParticipantTableFieldField;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.GeneralBox;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.GeneralBox.EndsField;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.GeneralBox.HomepageField;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.GeneralBox.StartsField;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.GeneralBox.TitleField;
import org.eclipse.scout.contacts.events.client.event.EventForm.MainBox.OkButton;
import org.eclipse.scout.contacts.events.client.person.PersonChooserForm;
import org.eclipse.scout.contacts.client.person.PersonForm;
import org.eclipse.scout.contacts.client.template.AbstractAddressBox;
import org.eclipse.scout.contacts.client.template.AbstractEmailField;
import org.eclipse.scout.contacts.client.template.AbstractPhoneField;
import org.eclipse.scout.contacts.events.shared.event.EventFormData;
import org.eclipse.scout.contacts.events.shared.event.IEventService;
import org.eclipse.scout.contacts.events.shared.event.UpdateEventPermission;
import org.eclipse.scout.contacts.shared.organization.OrganizationLookupCall;
import org.eclipse.scout.contacts.shared.person.IPersonService;
import org.eclipse.scout.contacts.shared.person.PersonFormData;
import org.eclipse.scout.rt.client.dto.FormData;
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
import org.eclipse.scout.rt.client.ui.desktop.OpenUriAction;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
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
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@FormData(value = EventFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class EventForm extends AbstractForm {

  private String m_eventId;

  public EventForm() {
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

  public void startModify() {
    startInternalExclusive(new ModifyHandler());
  }

  public void startNew() {
    startInternal(new NewHandler());
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

  @Order(1)
  public class MainBox extends AbstractGroupBox {

    @Order(1)
    public class GeneralBox extends AbstractGroupBox {

      @Order(1)
      public class TitleField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Title");
        }
      }

      @Order(2)
      public class HomepageField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Homepage");
        }
      }

      @Order(3)
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
        protected void execClickAction() {
          getDesktop().openUri(getHomepageField().getValue(), OpenUriAction.NEW_WINDOW);
        }
      }

      @Order(4)
      public class StartsField extends AbstractDateTimeField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Starts");
        }
      }

      @Order(5)
      public class EndsField extends AbstractDateTimeField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Ends");
        }
      }
    }

    @Order(2)
    public class DetailsBox extends AbstractTabBox {

      @Order(1)
      public class EventDetailsBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Details");
        }

        @Order(1)
        public class LocationBox extends AbstractAddressBox {

          @Override
          protected void execInitField() {
            getStreetField().setVisible(false);
          }
        }

        @Order(2)
        public class PhoneField extends AbstractPhoneField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Phone");
          }
        }

        @Order(3)
        public class EmailField extends AbstractEmailField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Email");
          }
        }
      }

      @Order(2)
      public class ParticipantsBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Participants");
        }

        @Order(1)
        public class ParticipantTableFieldField extends AbstractTableField<ParticipantTableFieldField.Table> {

          @Override
          protected int getConfiguredGridH() {
            return 3;
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          public class Table extends AbstractTable {

            public LastNameColumn getLastNameColumn() {
              return getColumnSet().getColumnByClass(LastNameColumn.class);
            }

            @Override
            protected String getConfiguredDefaultIconId() {
              return Icons.Person;
            }

            @Override
            protected Class<? extends IMenu> getConfiguredDefaultMenu() {
              return EditMenu.class;
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

            @Order(1)
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

            @Order(2)
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

            @Order(3)
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

            @Order(4)
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

            @Order(1)
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
                personChooserForm.setFilteredPersons(getPersonIdColumn().getValues(false));
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

            @Order(1)
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

            @Order(2)
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
              protected void execAction() {
                getTable().deleteRows(getTable().getSelectedRows());
              }
            }
          }
        }
      }

      @Order(3)
      public class CommentsBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Comments");
        }

        @Order(1)
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

    @Order(100)
    public class OkButton extends AbstractOkButton {
    }

    @Order(101)
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
      formData = BEANS.get(IEventService.class).store(formData);
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
    }

    @Override
    protected void execDirtyStatusChanged(boolean dirty) {
      getForm().setSubTitle(getTitleField().getValue());
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
