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
package org.eclipse.scout.widgets.client.services.lookup;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.platform.util.TriState;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

/**
 * @author mzi
 */
public class UserContentListLookupCall extends LocalLookupCall<String> {

  private static final long serialVersionUID = 1L;
  private List<LookupRow<String>> m_rows = new ArrayList<LookupRow<String>>();

  public void setLookupRows(List<LookupRow<String>> rows) {
    m_rows = rows;
  }

  @Override
  protected List<LookupRow<String>> execCreateLookupRows() {
    // simulate active-filter logic which happens usually in an SQL statement
    // so we can test the active-filter with a local lookup call
    TriState active = getActive();
    if (TriState.UNDEFINED == active) {
      return m_rows;
    }
    List<LookupRow<String>> filteredRows = new ArrayList<>();
    for (LookupRow<String> row : m_rows) {
      if (active.isTrue() && row.isActive()) {
        filteredRows.add(row);
      }
      else if (active.isFalse() && !row.isActive()) {
        filteredRows.add(row);
      }
    }
    return filteredRows;
  }
}
