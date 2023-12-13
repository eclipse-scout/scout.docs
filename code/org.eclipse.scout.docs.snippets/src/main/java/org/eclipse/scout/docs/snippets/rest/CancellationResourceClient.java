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

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.rest.client.IRestResourceClient;

//tag::class[]
public class CancellationResourceClient implements IRestResourceClient {

  protected static final String RESOURCE_PATH = "cancellation";

  protected CancellationRestClientHelper helper() {
    return BEANS.get(CancellationRestClientHelper.class);
  }

  public void cancel(String requestId) {
    WebTarget target = helper().target(RESOURCE_PATH)
        .path("{requestId}")
        .resolveTemplate("requestId", requestId);

    Response response = target.request()
        .put(Entity.json(""));
    response.close();
  }
}
//end::class[]
