/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author awe
 */
public class OutputLocale {

  public static void main(String[] args) {
    List<Locale> list = new ArrayList<>(Arrays.asList(Locale.getAvailableLocales()));
    list.sort((o1, o2) -> o1.getDisplayName().compareTo(o2.getDisplayName()));

    for (Locale l : list) {
      String s = "['" + l.getDisplayName() + "', '" + l.toString() + "'],";
      System.out.println(s);
    }
  }

}
