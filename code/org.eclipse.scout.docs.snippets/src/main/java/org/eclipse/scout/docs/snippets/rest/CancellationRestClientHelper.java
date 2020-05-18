package org.eclipse.scout.docs.snippets.rest;

import javax.ws.rs.client.ClientBuilder;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.rest.cancellation.RestRequestCancellationClientRequestFilter;
import org.eclipse.scout.rt.rest.client.AbstractRestClientHelper;

//tag::class[]
public class CancellationRestClientHelper extends AbstractRestClientHelper {

  @Override
  protected String getBaseUri() {
    return "https://api.example.org/";
  }

  @Override
  protected void registerRequestFilters(ClientBuilder clientBuilder) {
    super.registerRequestFilters(clientBuilder);
    clientBuilder.register(new RestRequestCancellationClientRequestFilter(this::cancelRequest)); // <1>
  }

  protected void cancelRequest(String requestId) {
    BEANS.get(CancellationResourceClient.class).cancel(requestId); // <2>
  }
}
//end::class[]
