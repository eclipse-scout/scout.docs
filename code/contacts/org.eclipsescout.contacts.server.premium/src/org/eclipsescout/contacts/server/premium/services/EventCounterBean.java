/**
 *
 */
package org.eclipsescout.contacts.server.premium.services;

public class EventCounterBean {

  private String m_contactId;
  private Long m_events;

  public Long getEvents() {
    return m_events;
  }

  public void setEvents(Long events) {
    m_events = events;
  }

  public String getContactId() {
    return m_contactId;
  }

  public void setContactId(String contactId) {
    m_contactId = contactId;
  }

}
