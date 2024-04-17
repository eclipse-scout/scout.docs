/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.docs.snippets.rest;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.rest.cancellation.AbstractCancellationResource;
import org.eclipse.scout.rt.security.IAccessControlService;

//tag::class[]
public class CancellationResource extends AbstractCancellationResource {

  @Override
  protected Object resolveCurrentUserId() {
    return BEANS.get(IAccessControlService.class).getUserIdOfCurrentSubject(); // <1>;
  }
}
//end::class[]
