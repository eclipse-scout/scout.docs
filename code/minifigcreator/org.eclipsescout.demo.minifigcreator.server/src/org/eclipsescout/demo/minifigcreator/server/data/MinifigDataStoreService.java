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

import org.eclipse.scout.service.AbstractService;
import org.eclipsescout.demo.minifigcreator.shared.minifig.part.Part;

public class MinifigDataStoreService extends AbstractService implements IMinifigDataStoreService {

  @Override
  public List<Part> getAvailableHeads() {
    return MinifigDataStore.getInstance().getAvailableHeads();
  }

  @Override
  public List<Part> getAvailableTorsos() {
    return MinifigDataStore.getInstance().getAvailableTorsos();
  }

  @Override
  public List<Part> getAvailableLegs() {
    return MinifigDataStore.getInstance().getAvailableLegs();
  }

  @Override
  public Integer getQuantity(Part part) {
    return MinifigDataStore.getInstance().getQuantity(part);
  }

  @Override
  public void setQuantity(Part part, Integer quantity) {
    MinifigDataStore.getInstance().setQuantity(part, quantity);
  }

  @Override
  public void decreaseQuantity(Part part) {
    Integer quantity = getQuantity(part);
    if (quantity != null && quantity.intValue() > 0) {
      setQuantity(part, Integer.valueOf(quantity.intValue() - 1));
    }
  }

  @Override
  public boolean isAvailable(Part part) {
    Integer quantity = getQuantity(part);
    if (quantity != null) {
      return quantity.intValue() > 0;
    }
    return false;
  }

  @Override
  public List<PartQuantity> getAllParts() {
    return MinifigDataStore.getInstance().getAllParts();
  }
}
