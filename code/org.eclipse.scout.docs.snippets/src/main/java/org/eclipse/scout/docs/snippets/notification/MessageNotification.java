/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.docs.snippets.notification;

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
