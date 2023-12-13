package org.eclipse.scout.docs.snippets.rest;

import java.io.File;
import java.io.InputStream;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.scout.rt.platform.exception.PlatformException;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.util.FileUtility;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.eclipse.scout.rt.rest.IRestResource;
import org.eclipse.scout.rt.rest.multipart.IMultipartMessage;
import org.eclipse.scout.rt.rest.multipart.IMultipartPart;

//tag::class[]
@Path("upload")
public class UploadResource implements IRestResource {

  //tag::method[]
  @POST
  @Path("content")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public Response pushContent(@HeaderParam("Content-Type") MediaType mediaType, InputStream inputStream) {
    File file = null;
    String displayText = null;

    IMultipartMessage multipartMessage = IMultipartMessage.of(mediaType, inputStream);
    while (multipartMessage.hasNext()) {
      try (IMultipartPart part = multipartMessage.next()) {
        switch (part.getPartName()) {
          case "resource": // file field part
            String[] parts = FileUtility.getFilenameParts(part.getFilename());
            file = IOUtility.createTempFile(part.getInputStream(), parts[0], parts[1]);
            break;
          case "displayText": // text field part
            displayText = IOUtility.readStringUTF8(part.getInputStream());
            break;
          default:
            throw new VetoException("Unexpected part {}", part.getPartName());
        }
      }
      catch (Exception e) {
        throw new PlatformException("Failed to handle multipart", e);
      }
    }

    // do something
    System.out.println(displayText + ": " + file.getAbsolutePath());

    return Response.ok().build();
  }
  //end::method[]
}
//end::class[]
