/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.contacts.shared.organization;

import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

//tag::all[]
@ClassId("22789824-ad89-4208-bc11-5c08b56ce998")
public class OrganizationLookupCall extends LookupCall<String> {

  private static final long serialVersionUID = 1L;

  @Override
  protected Class<? extends ILookupService<String>> getConfiguredService() {
    return IOrganizationLookupService.class;
  }
}
//end::all[]
