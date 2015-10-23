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
package org.eclipsescout.demo.widgets.client.services.lookup;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

/**
 * @author mzi
 */
public class IconIdLookupCall extends LocalLookupCall<String> {

  private static final long serialVersionUID = 1L;

  @Override
  protected List<LookupRow<String>> execCreateLookupRows() {
    ArrayList<LookupRow<String>> rows = new ArrayList<LookupRow<String>>();

    // TODO: there might be a better way via reflection over AbstractIcon
    rows.add(new LookupRow<String>("null", "null"));
    rows.add(new LookupRow<String>("empty", "empty"));
    rows.add(new LookupRow<String>("cog", "cog"));
    rows.add(new LookupRow<String>("status_info", "status_info"));
    rows.add(new LookupRow<String>("status_warning", "status_warning"));
    rows.add(new LookupRow<String>("status_error", "status_error"));
    rows.add(new LookupRow<String>("wizard_back", "wizard_back"));
    rows.add(new LookupRow<String>("wizard_next", "wizard_next"));
    rows.add(new LookupRow<String>("bookmark", "bookmark"));

    return rows;
  }
}
