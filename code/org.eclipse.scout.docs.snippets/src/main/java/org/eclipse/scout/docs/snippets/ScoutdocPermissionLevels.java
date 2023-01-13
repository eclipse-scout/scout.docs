/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.docs.snippets;

import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.PermissionLevel;

//tag::ScoutdocPermissionLevels[]
public final class ScoutdocPermissionLevels {

  private ScoutdocPermissionLevels() {
  }

  public static final int LEVEL_NONE = PermissionLevel.LEVEL_NONE;
  public static final int LEVEL_OWN = 10;
  public static final int LEVEL_ALL = PermissionLevel.LEVEL_ALL;

  public static final PermissionLevel NONE = PermissionLevel.NONE;
  public static final PermissionLevel OWN =
      PermissionLevel.register(LEVEL_OWN, "OWN", true, () -> TEXTS.get("Own"));
  public static final PermissionLevel ALL = PermissionLevel.ALL;

  public static void init() {
    // ensures all static initializers have been called
  }
}
//end::ScoutdocPermissionLevels[]
