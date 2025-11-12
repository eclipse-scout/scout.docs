package org.eclipse.scout.docs.snippets.rest;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.scout.docs.snippets.dataobject.ExampleEntityDo;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.rest.client.IRestResourceClient;
import org.eclipse.scout.rt.rest.client.chunked.IChunkedDataReader;

public class ChunkedResourceClient implements IRestResourceClient {

  protected static final String RESOURCE_PATH = "chunked";

  protected ExampleRestClientHelper helper() {
    return BEANS.get(ExampleRestClientHelper.class);
  }

  //tag::method[]
  public void loadData() {
    Response response = helper()
        .target(RESOURCE_PATH)
        .path("data")
        .request()
        .accept(MediaType.APPLICATION_JSON)
        .get();

    IChunkedDataReader<ExampleEntityDo> reader = IChunkedDataReader.create(response, ExampleEntityDo.class, "\r\n");
    ExampleEntityDo chunk;
    while ((chunk = reader.read()) != null) {
      // process chunk
      System.out.println("Read chunk " + chunk);
    }
    response.close();
  }
  //end::method[]
}
