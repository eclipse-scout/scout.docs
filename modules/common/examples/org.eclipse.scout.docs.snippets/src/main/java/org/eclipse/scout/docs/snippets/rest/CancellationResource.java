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
