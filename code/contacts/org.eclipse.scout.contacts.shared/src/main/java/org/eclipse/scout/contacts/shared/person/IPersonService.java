/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.shared.person;

import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

//tag::all[]
@ApplicationScoped
@TunnelToServer
public interface IPersonService {

  PersonTablePageData getPersonTableData(SearchFilter filter, String organizationId); // <1>

  PersonFormData create(PersonFormData formData);

  PersonFormData load(PersonFormData formData);

  PersonFormData store(PersonFormData formData);
}
//end::all[]
