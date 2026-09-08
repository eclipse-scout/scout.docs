/*
 * Copyright (c) 2010, 2026 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
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

  @SuppressWarnings("resource")
  //tag::method[]
  @GET
  @Path("data")
  @Produces(MediaType.APPLICATION_JSON)
  public Response loadData() {
    IChunkedDataWriter<ExampleEntityDo> writer = IChunkedDataWriter.create(ExampleEntityDo.class, "\r\n", 100);
    return writer.toResponse(this::fetchData);
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
