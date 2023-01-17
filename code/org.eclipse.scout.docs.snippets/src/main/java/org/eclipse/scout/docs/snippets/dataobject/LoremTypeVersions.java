/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.docs.snippets.dataobject;

import org.eclipse.scout.rt.dataobject.AbstractTypeVersion;

//tag::class[]
public final class LoremTypeVersions {

  private LoremTypeVersions() {
  }

  public static final class Lorem_1_0_0 extends AbstractTypeVersion {
  }

  public static final class Lorem_1_2_0 extends AbstractTypeVersion {
  }
}
//end::class[]
