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
package org.eclipse.scout.contacts.event.server;

public class EventCountBean {

  private String m_contactId;
  private Long m_eventCount;

  public Long getEventCount() {
    return m_eventCount;
  }

  public void setEventCount(Long eventCount) {
    m_eventCount = eventCount;
  }

  public String getContactId() {
    return m_contactId;
  }

  public void setContactId(String contactId) {
    m_contactId = contactId;
  }
}
