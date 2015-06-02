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
package org.eclipse.scout.tutorial.jaxws.server.services.process;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.tutorial.jaxws.shared.services.process.IWSLogProcessService;
import org.eclipse.scout.tutorial.jaxws.shared.services.process.WSLogFormData;

public class WSLogProcessService extends AbstractService implements IWSLogProcessService {

  @Override
  public WSLogFormData load(WSLogFormData formData) throws ProcessingException {
    SQL.selectInto("" +
        "SELECT EVT_DATE, " +
        "       SERVICE, " +
        "       PORT, " +
        "       OPERATION, " +
        "       REQUEST, " +
        "       RESPONSE " +
        "FROM   WS_LOG " +
        "WHERE  WS_LOG_NR = :wSLogNr " +
        "INTO   :date, " +
        "       :service, " +
        "       :port, " +
        "       :operation, " +
        "       :request, " +
        "       :response"
        , formData);
    return formData;
  }
}
