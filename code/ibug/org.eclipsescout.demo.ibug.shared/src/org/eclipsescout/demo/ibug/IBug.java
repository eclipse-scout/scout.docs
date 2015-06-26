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

public interface IBug {
  public String getId();

  public String getSeverety();

  public String getPriority();

  public String getTargetMilestone();

  public String getStatus();

  public String getResolution();

  public String getComponent();

  public String getAssignee();

  public String getSummary();

  public String getChanged();

  @Override
  public String toString();

  public int getSortValue();

  public void setId(String id);

  public void setSeverety(String severety);

  public void setPriority(String priority);

  public void setTargetMilestone(String targetMilestone);

  public void setStatus(String status);

  public void setResolution(String resolution);

  public void setComponent(String component);

  public void setAssignee(String assignee);

  public void setSummary(String summary);

  public void setChanged(String changed);

  public void setSortValue(int sortValue);

}
