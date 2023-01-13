/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
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
