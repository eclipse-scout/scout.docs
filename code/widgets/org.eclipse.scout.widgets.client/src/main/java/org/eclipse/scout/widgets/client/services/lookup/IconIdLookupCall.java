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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.scout.rt.client.ui.IIconIdPrefix;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.shared.Icons;

@ClassId("8886aab2-f0ba-4bd8-a73f-29d31a0e6f81")
public class IconIdLookupCall extends LocalLookupCall<String> {
  private static final long serialVersionUID = 1L;

  @Override
  protected List<? extends ILookupRow<String>> execCreateLookupRows() {
    List<ILookupRow<String>> rows = new ArrayList<>();

    // Read constant values from Icons class
    for (Field field : Icons.class.getFields()) {
      if (Modifier.isStatic(field.getModifiers()) && field.getType().equals(String.class)) {
        try {
          final String iconId = (String) field.get(null);
          rows.add(new LookupRow<>(iconId, field.getName()).withIconId(iconId));
        }
        catch (RuntimeException | IllegalAccessException e) {
          // nop
        }
      }
    }

    rows.sort((Comparator<ILookupRow>) (o1, o2) -> {
      if (o1 == null && o2 == null) {
        return 0;
      }
      if (o1 == null) {
        return -1;
      }
      if (o2 == null) {
        return 1;
      }
      // sort font icons first, then other icons
      String iconId1 = (String) o1.getKey();
      String iconId2 = (String) o2.getKey();
      if (iconId1 != null && iconId1.startsWith(IIconIdPrefix.FONT) && (iconId2 == null || !iconId2.startsWith(IIconIdPrefix.FONT))) {
        return -1;
      }
      if (iconId2 != null && iconId2.startsWith(IIconIdPrefix.FONT) && (iconId1 == null || !iconId1.startsWith(IIconIdPrefix.FONT))) {
        return 1;
      }
      // then, sort by name
      return ObjectUtility.compareTo(o1.getText(), o2.getText());
    });

    return rows;
  }
}
