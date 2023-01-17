/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.jswidgets.rest;

public class Response {
  private String m_message;

  public Response(String message) {
    m_message = message;
  }

  public String getMessage() {
    return m_message;
  }

  public void setMessage(String message) {
    m_message = message;
  }
}
