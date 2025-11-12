package org.eclipse.scout.docs.snippets.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.scout.docs.snippets.dataobject.ExampleEntityDo;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.job.Jobs;
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

    Jobs.schedule(() -> {
      try (writer) {
        // write data objects to writer
        writer.write(BEANS.get(ExampleEntityDo.class).withName("example1"));
        writer.write(BEANS.get(ExampleEntityDo.class).withName("example2"));
        // ...
      }
    }, Jobs.newInput());

    return Response.ok(writer.toEntity()).build();
  }
  //end::method[]
}
