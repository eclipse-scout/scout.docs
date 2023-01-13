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
import java.util.stream.Stream;

import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.data.colorscheme.ColorScheme;
import org.eclipse.scout.rt.shared.data.colorscheme.IColorScheme;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

@ClassId("8302e181-9a58-4e92-8bad-0cc20c35d45d")
public class TileColorSchemeLookupCall extends LocalLookupCall<IColorScheme> {

  private static final long serialVersionUID = 1L;

  @Override
  protected List<LookupRow<IColorScheme>> execCreateLookupRows() {
    ArrayList<LookupRow<IColorScheme>> rows = new ArrayList<>();
    Stream.of(ColorScheme.values()).forEach(colorScheme -> rows.add(new LookupRow<>(colorScheme, colorScheme.getIdentifier())));
    return rows;
  }
}
