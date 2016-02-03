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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.scout.rt.platform.util.CompareUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.shared.Icons;

/**
 * @author mzi
 */
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
          rows.add(new LookupRow<String>(iconId, field.getName()).withIconId(iconId));
        }
        catch (RuntimeException | IllegalAccessException e) {
          // nop
        }
      }
    }

    Collections.sort(rows, new Comparator<ILookupRow>() {
      @Override
      public int compare(ILookupRow o1, ILookupRow o2) {
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
        if (iconId1 != null && iconId1.startsWith("font:") && (iconId2 == null || !iconId2.startsWith("font:"))) {
          return -1;
        }
        if (iconId2 != null && iconId2.startsWith("font:") && (iconId1 == null || !iconId1.startsWith("font:"))) {
          return 1;
        }
        // then, sort by name
        return CompareUtility.compareTo(o1.getText(), o2.getText());
      }
    });

    return rows;
  }
}
