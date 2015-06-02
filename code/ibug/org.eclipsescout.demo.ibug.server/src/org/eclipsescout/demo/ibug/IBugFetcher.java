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

import java.util.List;

import org.eclipse.scout.commons.exception.ProcessingException;

/**
 * @author mzi
 */
public interface IBugFetcher {
  public void setQueryCriteria(String criteria);

  public void setMaxNumberOfBugs(int bugs);

  public void setAssignee(String assignee);

  public void setProduct(String product);

  public String getQueryCriteria();

  public int getMaxNumberOfBugs();

  public String getAssignee();

  public String getProduct();

  public List<IBug> fetchBugs() throws ProcessingException;
}
