/*
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.services.lookup;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

/**
 * @author mzi
 */
@ClassId("d2353f8c-d3bb-439f-980f-f38e1044200a")
public class FontStyleLookupCall extends LocalLookupCall<Integer> {

  private static final long serialVersionUID = 1L;

  @Override
  protected List<LookupRow<Integer>> execCreateLookupRows() {
    ArrayList<LookupRow<Integer>> rows = new ArrayList<>();
    rows.add(new LookupRow<>(0, TEXTS.get("Default")));
    rows.add(new LookupRow<>(1, TEXTS.get("Bold")));
    rows.add(new LookupRow<>(2, TEXTS.get("Italic")));
    rows.add(new LookupRow<>(3, TEXTS.get("Bold") + " " + TEXTS.get("Italic")));
    return rows;
  }
}
