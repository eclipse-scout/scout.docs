package org.eclipse.scout.docs.snippets.rest;

import java.util.Iterator;
import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.scout.docs.snippets.dataobject.ExampleEntityDo;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.rest.IRestResource;
import org.eclipse.scout.rt.rest.chunked.IChunkedDataWriter;

@Path("chunked")
public class ChunkedResource implements IRestResource {

  //tag::method[]
  @GET
  @Path("data")
  @Produces(MediaType.APPLICATION_JSON)
  public Response loadData() {
    @SuppressWarnings("resource")
    IChunkedDataWriter<ExampleEntityDo> writer = IChunkedDataWriter.create(ExampleEntityDo.class, "\r\n", 100);
    Iterator<ExampleEntityDo> iterator = fetchData();
    return writer.toResponse(iterator);
  }

  /**
   * load data from source, e.g. database
   */
  protected Iterator<ExampleEntityDo> fetchData() {
    return List.of(
            BEANS.get(ExampleEntityDo.class).withName("example1"),
            BEANS.get(ExampleEntityDo.class).withName("example2"))
        // ...
        .iterator();
  }
  //end::method[]
}
