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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.eclipse.scout.rt.dataobject.exception.AccessForbiddenException;
import org.eclipse.scout.rt.dataobject.exception.ResourceNotFoundException;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.context.RunContext;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.exception.ProcessingStatus;
import org.eclipse.scout.rt.platform.exception.RemoteSystemUnavailableException;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.concurrent.ThreadInterruptedError;
import org.eclipse.scout.rt.rest.IRestResource;
import org.eclipse.scout.rt.rest.RestApplication;
import org.eclipse.scout.rt.security.IAccessControlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * This is an example of a rest service provider. Implementing {@link IRestResource} makes sure the resource is
 * registered automatically if a {@link RestApplication} is registered in the web.xml.
 *
 * @see RestApplication
 * @see IRestResource
 */
@Path("example")
public class ExampleResource implements IRestResource {
  private static final Logger LOG = LoggerFactory.getLogger(ExampleResource.class);

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getStatus() {
    String userId = BEANS.get(IAccessControlService.class).getUserIdOfCurrentSubject();
    LOG.info("New get request for /example. RunContext: {}.", RunContext.CURRENT.get());
    return new Response("Hi " + userId + ", thank you for your GET request.");
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

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("error/{id}")
  public Response error(@PathParam("id") int id) throws Exception {
    // the list of IDs is specified in RestExceptionLookupCall
    switch (id) {
      case 1000:
        throw new VetoException(new ProcessingStatus("VetoException", "This is a VetoException with severity 'warning'", new Exception("cause"), 123, IStatus.WARNING));
      case 2000:
        throw new AccessForbiddenException(TEXTS.get("YouAreNotAuthorizedToPerformThisAction"));
      case 3000:
        throw new ResourceNotFoundException(TEXTS.get("TheRequestedResourceCouldNotBeFound"));
      case 4000:
        throw new ThreadInterruptedError("Interrupted");
      case 5000:
        throw new JsonMappingException(null, "JSON Mapping failed");
      case 6000:
        throw new IllegalArgumentException("Invalid argument");
      case 7000:
        throw new RuntimeException("Custom runtime exception");
      case 8000:
        throw new ProcessingException();
      case 9000:
        throw new RemoteSystemUnavailableException("Server temporarily not available");
      default:
        throw new WebApplicationException(id); // other values are interpreted as HTTP status code
    }
  }
}
