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
package org.eclipsescout.demo.minifigcreator.server.data;

import java.util.List;

import org.eclipse.scout.service.IService;
import org.eclipsescout.demo.minifigcreator.shared.minifig.part.Part;

public interface IMinifigDataStoreService extends IService {

  List<Part> getAvailableHeads();

  List<Part> getAvailableTorsos();

  List<Part> getAvailableLegs();

  Integer getQuantity(Part part);

  void setQuantity(Part part, Integer quantity);

  void decreaseQuantity(Part part);

  boolean isAvailable(Part part);

  List<PartQuantity> getAllParts();

}
