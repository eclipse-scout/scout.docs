/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.forms;

import org.eclipse.scout.rt.platform.html.AppLink;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.util.StringUtility;

public class ExampleBean implements Comparable<ExampleBean> {

  private String m_header;
  private String m_description;
  private AppLink m_appLink;
  private BinaryResource m_image;

  public String getHeader() {
    return m_header;
  }

  public void setHeader(String header) {
    m_header = header;
  }

  public String getDescription() {
    return m_description;
  }

  public void setDescription(String description) {
    m_description = description;
  }

  public AppLink getAppLink() {
    return m_appLink;
  }

  public void setAppLink(AppLink appLink) {
    m_appLink = appLink;
  }

  public BinaryResource getImage() {
    return m_image;
  }

  public void setImage(BinaryResource image) {
    m_image = image;
  }

  @Override
  public int compareTo(ExampleBean o) {
    return StringUtility.compareIgnoreCase(getHeader(), o.getHeader());
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((m_appLink == null) ? 0 : m_appLink.hashCode());
    result = prime * result + ((m_description == null) ? 0 : m_description.hashCode());
    result = prime * result + ((m_header == null) ? 0 : m_header.hashCode());
    result = prime * result + ((m_image == null) ? 0 : m_image.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ExampleBean other = (ExampleBean) obj;
    if (m_appLink == null) {
      if (other.m_appLink != null) {
        return false;
      }
    }
    else if (!m_appLink.equals(other.m_appLink)) {
      return false;
    }
    if (m_description == null) {
      if (other.m_description != null) {
        return false;
      }
    }
    else if (!m_description.equals(other.m_description)) {
      return false;
    }
    if (m_header == null) {
      if (other.m_header != null) {
        return false;
      }
    }
    else if (!m_header.equals(other.m_header)) {
      return false;
    }
    if (m_image == null) {
      if (other.m_image != null) {
        return false;
      }
    }
    else if (!m_image.equals(other.m_image)) {
      return false;
    }
    return true;
  }

}
