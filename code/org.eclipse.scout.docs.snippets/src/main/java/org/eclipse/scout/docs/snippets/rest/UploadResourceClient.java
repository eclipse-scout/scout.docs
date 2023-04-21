package org.eclipse.scout.docs.snippets.rest;

import java.io.ByteArrayInputStream;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.rest.client.IRestResourceClient;
import org.eclipse.scout.rt.rest.client.multipart.MultipartMessage;
import org.eclipse.scout.rt.rest.client.multipart.MultipartPart;

//tag::class[]
public class UploadResourceClient implements IRestResourceClient {

  protected static final String RESOURCE_PATH = "upload";

  protected ExampleRestClientHelper helper() {
    return BEANS.get(ExampleRestClientHelper.class);
  }

  //tag::method[]
  public void pushContent(BinaryResource resource, String displayText) {
    MultipartMessage multipartMessage = BEANS.get(MultipartMessage.class)
        .addPart(MultipartPart.ofFile("resource", resource.getFilename(), new ByteArrayInputStream(resource.getContent())))
        .addPart(MultipartPart.ofField("displayText", displayText));

    WebTarget target = helper()
        .target(RESOURCE_PATH)
        .path("/content");

    Response response = target.request()
        .accept(MediaType.APPLICATION_JSON)
        .post(multipartMessage.toEntity());
    response.close();
  }
  //end::method[]
}
//end::class[]
