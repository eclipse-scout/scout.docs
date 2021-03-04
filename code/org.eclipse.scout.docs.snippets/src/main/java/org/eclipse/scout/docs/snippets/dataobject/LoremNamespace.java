/*
 * Copyright (c) 2021 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.docs.snippets.dataobject;

import org.eclipse.scout.rt.platform.namespace.INamespace;

//tag::class[]
public final class LoremNamespace implements INamespace {

  public static final String ID = "lorem";
  public static final double ORDER = 9000;

  @Override
  public String getId() {
    return ID;
  }

  @Override
  public double getOrder() {
    return ORDER;
  }
}
//end::class[]
