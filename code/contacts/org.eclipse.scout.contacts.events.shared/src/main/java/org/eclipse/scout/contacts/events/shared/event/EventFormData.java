/*
 * Copyright (c) 2022 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.contacts.events.shared.event;

import java.util.Date;

import javax.annotation.Generated;

import org.eclipse.scout.contacts.shared.common.AbstractAddressBoxData;
import org.eclipse.scout.contacts.shared.common.AbstractNotesBoxData;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.fields.tablefield.AbstractTableFieldBeanData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@ClassId("e91f6c5c-1926-45f9-ab79-35f7e6740b91-formdata")
@Generated(value = "org.eclipse.scout.contacts.events.client.event.EventForm", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class EventFormData extends AbstractFormData {
  private static final long serialVersionUID = 1L;

  public Email getEmail() {
    return getFieldByClass(Email.class);
  }

  public Ends getEnds() {
    return getFieldByClass(Ends.class);
  }

  /**
   * access method for property EventId.
   */
  public String getEventId() {
    return getEventIdProperty().getValue();
  }

  /**
   * access method for property EventId.
   */
  public void setEventId(String eventId) {
    getEventIdProperty().setValue(eventId);
  }

  public EventIdProperty getEventIdProperty() {
    return getPropertyByClass(EventIdProperty.class);
  }

  public Homepage getHomepage() {
    return getFieldByClass(Homepage.class);
  }

  public LocationBox getLocationBox() {
    return getFieldByClass(LocationBox.class);
  }

  public NotesBox getNotesBox() {
    return getFieldByClass(NotesBox.class);
  }

  public ParticipantTableField getParticipantTableField() {
    return getFieldByClass(ParticipantTableField.class);
  }

  public Phone getPhone() {
    return getFieldByClass(Phone.class);
  }

  public Starts getStarts() {
    return getFieldByClass(Starts.class);
  }

  public Title getTitle() {
    return getFieldByClass(Title.class);
  }

  @ClassId("cad13c3a-4241-43b5-8b85-e728b24655bc-formdata")
  public static class Email extends AbstractValueFieldData<String> {
    private static final long serialVersionUID = 1L;
  }

  @ClassId("ce05f47a-f2e1-4fbc-9c91-1e5874dc46e4-formdata")
  public static class Ends extends AbstractValueFieldData<Date> {
    private static final long serialVersionUID = 1L;
  }

  public static class EventIdProperty extends AbstractPropertyData<String> {
    private static final long serialVersionUID = 1L;
  }

  @ClassId("380febfb-fa9a-48e4-b953-1159634d7dc7-formdata")
  public static class Homepage extends AbstractValueFieldData<String> {
    private static final long serialVersionUID = 1L;
  }

  @ClassId("c70bcbf9-3b6a-49c3-ae5a-714cbfc087e0-formdata")
  public static class LocationBox extends AbstractAddressBoxData {
    private static final long serialVersionUID = 1L;
  }

  @ClassId("72732dfc-e9f9-4b61-a260-a86bb3041e3d-formdata")
  public static class NotesBox extends AbstractNotesBoxData {
    private static final long serialVersionUID = 1L;
  }

  @ClassId("627f6a0e-349f-4846-8449-1ec41156657d-formdata")
  public static class ParticipantTableField extends AbstractTableFieldBeanData {
    private static final long serialVersionUID = 1L;

    @Override
    public ParticipantTableFieldRowData addRow() {
      return (ParticipantTableFieldRowData) super.addRow();
    }

    @Override
    public ParticipantTableFieldRowData addRow(int rowState) {
      return (ParticipantTableFieldRowData) super.addRow(rowState);
    }

    @Override
    public ParticipantTableFieldRowData createRow() {
      return new ParticipantTableFieldRowData();
    }

    @Override
    public Class<? extends AbstractTableRowData> getRowType() {
      return ParticipantTableFieldRowData.class;
    }

    @Override
    public ParticipantTableFieldRowData[] getRows() {
      return (ParticipantTableFieldRowData[]) super.getRows();
    }

    @Override
    public ParticipantTableFieldRowData rowAt(int index) {
      return (ParticipantTableFieldRowData) super.rowAt(index);
    }

    public void setRows(ParticipantTableFieldRowData[] rows) {
      super.setRows(rows);
    }

    public static class ParticipantTableFieldRowData extends AbstractTableRowData {
      private static final long serialVersionUID = 1L;
      public static final String personId = "personId";
      public static final String firstName = "firstName";
      public static final String lastName = "lastName";
      public static final String organization = "organization";
      private String m_personId;
      private String m_firstName;
      private String m_lastName;
      private String m_organization;

      public String getPersonId() {
        return m_personId;
      }

      public void setPersonId(String newPersonId) {
        m_personId = newPersonId;
      }

      public String getFirstName() {
        return m_firstName;
      }

      public void setFirstName(String newFirstName) {
        m_firstName = newFirstName;
      }

      public String getLastName() {
        return m_lastName;
      }

      public void setLastName(String newLastName) {
        m_lastName = newLastName;
      }

      public String getOrganization() {
        return m_organization;
      }

      public void setOrganization(String newOrganization) {
        m_organization = newOrganization;
      }
    }
  }

  @ClassId("d945d161-20d1-4958-89bd-d6d276bb07dd-formdata")
  public static class Phone extends AbstractValueFieldData<String> {
    private static final long serialVersionUID = 1L;
  }

  @ClassId("22fb39f0-622e-4564-b386-73308752fed9-formdata")
  public static class Starts extends AbstractValueFieldData<Date> {
    private static final long serialVersionUID = 1L;
  }

  @ClassId("a31ff8e4-b8ec-42dd-824b-73a1665c5435-formdata")
  public static class Title extends AbstractValueFieldData<String> {
    private static final long serialVersionUID = 1L;
  }
}
