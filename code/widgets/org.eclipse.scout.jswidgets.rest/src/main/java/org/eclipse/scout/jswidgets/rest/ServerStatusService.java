package org.eclipse.scout.jswidgets.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.status.Status;
import org.eclipse.scout.rt.rest.IRestResource;
import org.eclipse.scout.rt.rest.RestApplication;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * <h3>{@link ServerStatusService}</h3> This is an example of a rest service provider. A resource implementing
 * {@link IRestResource} is provided as rest resource in case the {@link ServletContainer} with a
 * {@link RestApplication} is registered in the web.xml.
 *
 * @see RestApplication
 * @see IRestResource
 * @see ServletContainer
 */
@Path("serverstatus")
public class ServerStatusService implements IRestResource {

  /**
   * @return {@link IStatus#OK} in case the service is available.
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Status getStatus() {
    return new Status(IStatus.OK);
  }
}
