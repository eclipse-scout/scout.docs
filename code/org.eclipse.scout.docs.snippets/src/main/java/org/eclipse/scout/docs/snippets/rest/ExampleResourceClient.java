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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.scout.docs.snippets.dataobject.ExampleEntityDo;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.rest.client.IRestResourceClient;
import org.eclipse.scout.rt.rest.client.RestClientProperties;

//tag::class[]
public class ExampleResourceClient implements IRestResourceClient {

  protected static final String RESOURCE_PATH = "example";

  protected ExampleRestClientHelper helper() {
    return BEANS.get(ExampleRestClientHelper.class);
  }

  //tag::property-request[]
  public ExampleEntityDo getExampleEntity(String id) {
    WebTarget target = helper().target(RESOURCE_PATH)
        .property(RestClientProperties.FOLLOW_REDIRECTS, false)
        .path("/{id}")
        .resolveTemplate("id", id);

    return target.request()
        .accept(MediaType.APPLICATION_JSON)
        .get(ExampleEntityDo.class); // <1>
  }
  //end::property-request[]

  public ExampleEntityDo updateExampleEntity(String id, ExampleEntityDo entity) {
    WebTarget target = helper().target(RESOURCE_PATH)
        .path("/{id}")
        .resolveTemplate("id", id);

    return target.request()
        .accept(MediaType.APPLICATION_JSON)
        .post(Entity.json(entity), ExampleEntityDo.class); // <2>
  }

  public void deleteExampleEntity(String id) {
    WebTarget target = helper().target(RESOURCE_PATH)
        .path("/{id}")
        .resolveTemplate("id", id);

    Response response = target.request().delete(); // <3>
    response.close();
  }

  public ExampleEntityDo getExampleEntityCustomExceptionHandling(String id) {
    WebTarget target = helper().target(RESOURCE_PATH, this::transformCustomException) // <4>
        .path("/{id}")
        .resolveTemplate("id", id);

    return target.request()
        .accept(MediaType.APPLICATION_JSON)
        .get(ExampleEntityDo.class);
  }

  protected RuntimeException transformCustomException(RuntimeException e, Response r) {
    if (r != null && r.hasEntity() && MediaType.TEXT_PLAIN_TYPE.equals(r.getMediaType())) {
      String message = r.readEntity(String.class);
      throw new VetoException(message);
    }
    return e;
  }
}
//end::class[]
