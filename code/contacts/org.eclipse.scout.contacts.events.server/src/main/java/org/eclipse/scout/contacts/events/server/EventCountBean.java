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
package org.eclipse.scout.contacts.events.server;

public class EventCountBean {

  private String m_personId;
  private Long m_eventCount;

  public Long getEventCount() {
    return m_eventCount;
  }

  public void setEventCount(Long eventCount) {
    m_eventCount = eventCount;
  }

  public String getPersonId() {
    return m_personId;
  }

  public void setPersonId(String personId) {
    m_personId = personId;
  }
}
