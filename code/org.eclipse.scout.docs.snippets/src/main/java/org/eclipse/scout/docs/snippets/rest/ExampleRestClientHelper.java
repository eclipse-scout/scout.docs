package org.eclipse.scout.docs.snippets.rest;

import javax.ws.rs.core.Response;

import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.rest.client.AbstractRestClientHelper;
import org.eclipse.scout.rt.rest.error.ErrorDo;
import org.eclipse.scout.rt.rest.error.ErrorResponse;

//tag::class[]
public class ExampleRestClientHelper extends AbstractRestClientHelper {

  @Override
  protected String getBaseUri() {
    return "https://api.example.org/"; // <1>
  }

  @Override
  protected RuntimeException transformException(RuntimeException e, Response response) { // <2>
    if (response != null && response.hasEntity()) {
      ErrorDo error = response.readEntity(ErrorResponse.class).getError();
      throw new VetoException(error.getMessage())
          .withTitle(error.getTitle());
    }
    return e;
  }
}
//end::class[]
