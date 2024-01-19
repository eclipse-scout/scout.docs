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

import java.util.concurrent.TimeUnit;

import org.eclipse.scout.rt.dataobject.exception.AccessForbiddenException;
import org.eclipse.scout.rt.dataobject.exception.ResourceNotFoundException;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.exception.ProcessingStatus;
import org.eclipse.scout.rt.platform.exception.RemoteSystemUnavailableException;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.SleepUtil;
import org.eclipse.scout.rt.platform.util.concurrent.ThreadInterruptedError;
import org.eclipse.scout.rt.rest.IRestResource;
import org.eclipse.scout.rt.rest.RestApplication;

import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;

/**
 * This is an example of a rest service provider. Implementing {@link IRestResource} makes sure the resource is
 * registered automatically if a {@link RestApplication} is registered via
 * {@link org.eclipse.scout.rt.jetty.IServletContributor}.
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

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("slowGet")
  public Response slowGet() {
    long secondsToWait = 7;
    SleepUtil.sleepElseLog(secondsToWait, TimeUnit.SECONDS);
    return new Response("Waited for " + secondsToWait + " seconds.");
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
