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
package org.eclipsescout.demo.minifigcreator.server.services.process;

import java.util.List;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.demo.minifigcreator.server.data.IMinifigDataStoreService;
import org.eclipsescout.demo.minifigcreator.server.data.PartQuantity;
import org.eclipsescout.demo.minifigcreator.shared.minifig.part.Part;
import org.eclipsescout.demo.minifigcreator.shared.services.process.IServerProcessService;
import org.eclipsescout.demo.minifigcreator.shared.services.process.ServerFormData;

public class ServerProcessService extends AbstractService implements IServerProcessService {

  @Override
  public ServerFormData load(ServerFormData formData) throws ProcessingException {
    List<PartQuantity> parts = SERVICES.getService(IMinifigDataStoreService.class).getAllParts();
    for (PartQuantity pq : parts) {
      int i = formData.getTable().addRow();
      formData.getTable().setPart(i, pq.getPart());
      formData.getTable().setType(i, pq.getPart().getType().name());
      formData.getTable().setName(i, pq.getPart().getName());
      formData.getTable().setQuantity(i, pq.getQuantity());
    }
    return formData;
  }

  @Override
  public ServerFormData store(ServerFormData formData) throws ProcessingException {
    for (int i = 0; i < formData.getTable().getRowCount(); i++) {
      Part part = formData.getTable().getPart(i);
      Integer quantity = formData.getTable().getQuantity(i);
      SERVICES.getService(IMinifigDataStoreService.class).setQuantity(part, quantity);
    }

    return formData;
  }
}
