package org.eclipse.scout.docs.snippets.rest;

import org.eclipse.scout.rt.rest.client.AbstractRestClientHelper;

//tag::class[]
public class ExampleRestClientHelper extends AbstractRestClientHelper {

  @Override
  protected String getBaseUri() {
    return "https://api.example.org/";
  }
}
//end::class[]
