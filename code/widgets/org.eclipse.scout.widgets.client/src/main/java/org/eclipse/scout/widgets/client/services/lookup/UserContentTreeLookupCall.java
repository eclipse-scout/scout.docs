/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.services.lookup;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

/**
 * @author mzi
 */
@ClassId("35c398e9-e5ed-4e19-bf79-ee7c0a9bea0b")
public class UserContentTreeLookupCall extends LocalLookupCall<String> {

  private static final long serialVersionUID = 1L;
  private List<LookupRow<String>> m_rows = new ArrayList<>();

  public void setLookupRows(List<LookupRow<String>> rows) {
    m_rows = rows;
  }

  @Override
  protected List<LookupRow<String>> execCreateLookupRows() {
    return m_rows;
  }
}
