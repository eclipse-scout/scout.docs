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

import java.util.Locale;

import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

/**
 * This class intentionally does not implement LocalLookupCall even tough it doesn't need a running server. We need the
 * class to test remove lookup call behavior which runs through other code than local lookup-calls.
 */
@ClassId("d1b4f871-1367-40d1-8425-4b0d2716e55e")
public class RemoteLocaleLookupCall extends LookupCall<Locale> {

  private static final long serialVersionUID = 1L;

  @Override
  protected Class<? extends ILookupService<Locale>> getConfiguredService() {
    return RemoteLocaleLookupService.class;
  }

}
