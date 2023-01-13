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

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.rest.IRestResource;
import org.eclipse.scout.rt.rest.cancellation.RestRequestCancellationRegistry;
import org.eclipse.scout.rt.security.IAccessControlService;

//tag::class[]
@Path("cancellation")
public class CancellationResource implements IRestResource {

  @PUT
  @Path("{requestId}")
  public void cancel(@PathParam("requestId") String requestId) {
    String userId = BEANS.get(IAccessControlService.class).getUserIdOfCurrentSubject(); // <1>
    BEANS.get(RestRequestCancellationRegistry.class).cancel(requestId, userId); // <2>
  }
}
//end::class[]
