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
package org.eclipsescout.demo.ibug;

/**
 * @author mzi
 */
public class BugzillaBug implements IBug {
  private String m_id = null;
  private String m_severety = null;
  private String m_priority = null;
  private String m_targetMilestone = null;
  private String m_status = null;
  private String m_resolution = null;
  private String m_component = null;
  private String m_assignee = null;
  private String m_summary = null;
  private String m_changed = null;
  private int m_sortValue = -1;

  @Override
  public String getId() {
    return m_id;
  }

  @Override
  public String getSeverety() {
    return m_severety;
  }

  @Override
  public String getPriority() {
    return m_priority;
  }

  @Override
  public String getTargetMilestone() {
    return m_targetMilestone;
  }

  @Override
  public String getStatus() {
    return m_status;
  }

  @Override
  public String getResolution() {
    // TODO Auto-generated method stub
    return m_resolution;
  }

  @Override
  public String getComponent() {
    return m_component;
  }

  @Override
  public String getAssignee() {
    return m_assignee;
  }

  @Override
  public String getSummary() {
    return m_summary;
  }

  @Override
  public String getChanged() {
    return m_changed;
  }

  @Override
  public int getSortValue() {
    return m_sortValue;
  }

  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer("id='" + m_id + "'");
    buf.append(", id='" + m_id + "'");
    buf.append(", sev='" + m_severety + "'");
    buf.append(", prio='" + m_priority + "'");
    buf.append(", targetMS='" + m_targetMilestone + "'");
    buf.append(", assignee='" + m_assignee + "'");
    buf.append(", summary='" + m_summary + "'");
    buf.append(", changed='" + m_changed + "'");
    buf.append(", sortValue='" + m_sortValue + "'");

    return buf.toString();
  }

  @Override
  public void setId(String id) {
    m_id = id;
  }

  @Override
  public void setSeverety(String severety) {
    m_severety = severety;
  }

  @Override
  public void setPriority(String priority) {
    m_priority = priority;
  }

  @Override
  public void setTargetMilestone(String targetMilestone) {
    m_targetMilestone = targetMilestone;
  }

  @Override
  public void setStatus(String status) {
    m_status = status;
  }

  @Override
  public void setResolution(String resolution) {
    m_resolution = resolution;
  }

  @Override
  public void setComponent(String component) {
    m_component = component;
  }

  @Override
  public void setAssignee(String assignee) {
    m_assignee = assignee;
  }

  @Override
  public void setSummary(String summary) {
    m_summary = summary;
  }

  @Override
  public void setChanged(String changed) {
    m_changed = changed;
  }

  @Override
  public void setSortValue(int sortValue) {
    m_sortValue = sortValue;
  }

}
