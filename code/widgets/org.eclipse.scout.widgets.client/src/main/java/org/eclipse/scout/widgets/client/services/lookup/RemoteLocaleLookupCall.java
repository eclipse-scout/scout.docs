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

import java.util.Locale;

import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

/**
 * This class intentionally does not implement LocalLookupCall even tough it doesn't need a running server. We need the
 * class to test remove lookup call behavior which runs through other code than local lookup-calls.
 */
public class RemoteLocaleLookupCall extends LookupCall<Locale> {

  private static final long serialVersionUID = 1L;

  @Override
  protected Class<? extends ILookupService<Locale>> getConfiguredService() {
    return RemoteLocaleLookupService.class;
  }

}
