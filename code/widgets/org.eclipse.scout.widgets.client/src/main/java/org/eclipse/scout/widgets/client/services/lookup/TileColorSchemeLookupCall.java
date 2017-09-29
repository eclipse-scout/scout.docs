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

import org.eclipse.scout.rt.shared.data.tile.ITileColorScheme;
import org.eclipse.scout.rt.shared.data.tile.TileColorScheme;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

public class TileColorSchemeLookupCall extends LocalLookupCall<ITileColorScheme> {

  private static final long serialVersionUID = 1L;

  @Override
  protected List<LookupRow<ITileColorScheme>> execCreateLookupRows() {
    ArrayList<LookupRow<ITileColorScheme>> rows = new ArrayList<>();
    rows.add(new LookupRow<>(TileColorScheme.DEFAULT, "default"));
    rows.add(new LookupRow<>(TileColorScheme.DEFAULT_INVERTED, "default_inverted"));
    rows.add(new LookupRow<>(TileColorScheme.ALTERNATIVE, "alternative"));
    rows.add(new LookupRow<>(TileColorScheme.ALTERNATIVE_INVERTED, "alternative_inverted"));
    return rows;
  }
}
