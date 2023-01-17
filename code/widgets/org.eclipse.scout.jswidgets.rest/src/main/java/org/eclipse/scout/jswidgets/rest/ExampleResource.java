/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.jswidgets.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.scout.rt.rest.IRestResource;
import org.eclipse.scout.rt.rest.RestApplication;

/**
 * This is an example of a rest service provider. Implementing {@link IRestResource} makes sure the resource is
 * registered automatically if a {@link RestApplication} is registered in the web.xml.
 *
 * @see RestApplication
 * @see IRestResource
 */
@Path("example")
public class ExampleResource implements IRestResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getStatus() {
    return new Response("Hi client, thank you for your GET request.");
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response post(Object object) {
    return new Response("Hi client, thank you for your POST request. You sent me the following object: " + object);
  }

  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response put(Object object) {
    return new Response("Hi client, thank you for your PUT request. You sent me the following object: " + object);
  }

  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  public Response delete() {
    return new Response("Hi client, thank you for your DELETE request.");
  }

}
