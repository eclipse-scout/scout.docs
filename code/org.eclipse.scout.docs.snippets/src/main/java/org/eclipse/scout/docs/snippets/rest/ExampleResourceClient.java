package org.eclipse.scout.docs.snippets.rest;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.scout.docs.snippets.dataobject.ExampleEntityDo;
import org.eclipse.scout.rt.platform.BEANS;

//tag::class[]
public class ExampleResourceClient {

  protected static final String RESOURCE_PATH = "example";

  protected ExampleRestClientHelper m_helper = BEANS.get(ExampleRestClientHelper.class);

  public ExampleEntityDo getExamlpeEntity(String id) {
    WebTarget target = m_helper.target(RESOURCE_PATH)
        .path("/{id}")
        .resolveTemplate("id", id);

    Response response = target.request()
        .accept(MediaType.APPLICATION_JSON)
        .get();
    m_helper.throwOnResponseError(target, response);

    return response.readEntity(ExampleEntityDo.class);
  }
}
//end::class[]
