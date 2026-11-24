/*
 * Copyright (c) 2010, 2026 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.services.lookup;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

/**
 * Images that are available in the WebContent folder.
 */
@ClassId("efc6e68a-e2d8-44e1-9033-a8178aa12f5d")
public class ImageLookupCall extends LocalLookupCall<String> {

  @Serial
  private static final long serialVersionUID = 1L;

  @Override
  protected List<LookupRow<String>> execCreateLookupRows() {
    ArrayList<LookupRow<String>> rows = new ArrayList<>();
    rows.add(new LookupRow<>("img/bird.jpg", "img/bird.jpg"));
    rows.add(new LookupRow<>("img/eclipse_scout_logo.svg", "img/eclipse_scout_logo.svg"));
    return rows;
  }
}
