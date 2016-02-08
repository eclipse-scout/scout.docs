package org.eclipse.scout.widgets.client.ui.forms;

import org.eclipse.scout.rt.platform.html.AppLink;

public class ExampleBean {

  private String m_header;
  private String m_description;
  private AppLink m_appLink;

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
}
