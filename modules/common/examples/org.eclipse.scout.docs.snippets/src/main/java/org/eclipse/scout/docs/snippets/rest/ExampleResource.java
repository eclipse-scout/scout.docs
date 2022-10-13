package org.eclipse.scout.docs.snippets.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.scout.docs.snippets.dataobject.ExampleEntityDo;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.rest.IRestResource;

//tag::class[]
@Path("example")
public class ExampleResource implements IRestResource {

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public ExampleEntityDo getExamlpeEntity(@PathParam("id") String id) {
    return BEANS.get(ExampleEntityDo.class)
        .withName("example-" + id)
        .withValues(1);
  }
}
//end::class[]
