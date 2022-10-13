package org.eclipse.scout.docs.snippets.rest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

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
