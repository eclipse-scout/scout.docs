/*******************************************************************************
 * Copyright (c) 2019 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
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
