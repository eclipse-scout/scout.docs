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
