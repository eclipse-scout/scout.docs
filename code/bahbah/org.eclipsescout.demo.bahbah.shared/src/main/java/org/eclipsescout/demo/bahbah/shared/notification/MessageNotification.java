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
package org.eclipsescout.demo.bahbah.shared.notification;

import java.io.Serializable;

public class MessageNotification implements Serializable {

  private static final long serialVersionUID = 1L;

  private final String m_message;
  private final String m_sender;
  private final String m_originalServerNode;

  public MessageNotification(String senderName, String message, String originalServerNode) {
    m_sender = senderName;
    m_message = message;
    m_originalServerNode = originalServerNode;
  }

  public String getMessage() {
    return m_message;
  }

  public String getSenderName() {
    return m_sender;
  }

  public String getOriginalServerNode() {
    return m_originalServerNode;
  }
}
