package org.eclipse.scout.docs.snippets.rest;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.rest.cancellation.AbstractRestRequestCancellationServletFilter;
import org.eclipse.scout.rt.shared.services.common.security.IAccessControlService;

//tag::class[]
public class RestRequestCancellationServletFilter extends AbstractRestRequestCancellationServletFilter {

  @Override
  protected Object resolveUserId(HttpServletRequest request) {
    return BEANS.get(IAccessControlService.class).getUserIdOfCurrentSubject(); // <1>
  }
}
//end::class[]
