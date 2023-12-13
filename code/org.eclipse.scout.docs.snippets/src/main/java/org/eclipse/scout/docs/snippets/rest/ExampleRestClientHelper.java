/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.docs.snippets.rest;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;

import org.apache.hc.client5.http.cookie.StandardCookieSpec;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.rest.client.AbstractRestClientHelper;
import org.eclipse.scout.rt.rest.client.RestClientProperties;
import org.eclipse.scout.rt.rest.error.ErrorDo;
import org.eclipse.scout.rt.rest.error.ErrorResponse;

//tag::class[]
public class ExampleRestClientHelper extends AbstractRestClientHelper {

  @Override
  protected String getBaseUri() {
    return "https://api.example.org/"; // <1>
  }

  //tag::client-properties[]
  @Override
  protected void configureClientBuilder(ClientBuilder clientBuilder) {
    super.configureClientBuilder(clientBuilder);
    clientBuilder.property(RestClientProperties.COOKIE_SPEC, StandardCookieSpec.RELAXED);
    clientBuilder.property(RestClientProperties.PROXY_URI, "http://my.proxy.com");
  }
  //end::client-properties[]

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
