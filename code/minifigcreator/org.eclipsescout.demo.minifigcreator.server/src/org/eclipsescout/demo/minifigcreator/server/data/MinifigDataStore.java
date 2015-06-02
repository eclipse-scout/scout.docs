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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.eclipsescout.demo.minifigcreator.shared.minifig.part.Part;
import org.eclipsescout.demo.minifigcreator.shared.minifig.part.PartType;

public final class MinifigDataStore {
  private static final MinifigDataStore INSTANCE = new MinifigDataStore();

  private HashMap<Part, Integer> m_data;

  static MinifigDataStore getInstance() {
    return INSTANCE;
  }

  private MinifigDataStore() {
    m_data = new HashMap<Part, Integer>();
    initDataStore();
  }

  private void initDataStore() {
    add(2, new Part(PartType.HEAD, 1, "Normal", 12));
    add(3, new Part(PartType.HEAD, 2, "Pirate", 13));
    add(2, new Part(PartType.HEAD, 4, "Talky & Walky", 8));
    add(5, new Part(PartType.HEAD, 5, "Bart", 16));
    add(4, new Part(PartType.LEGS, 1, "Yellow", 10));
    add(7, new Part(PartType.LEGS, 2, "Odd", 24));
    add(5, new Part(PartType.LEGS, 3, "Jeans", 15));
    add(9, new Part(PartType.LEGS, 4, "Gray", 15));
    add(8, new Part(PartType.LEGS, 5, "Overall", 15));
    add(3, new Part(PartType.LEGS, 6, "Blue", 15));
    add(9, new Part(PartType.LEGS, 7, "Orange", 38));
    add(3, new Part(PartType.TORSO, 1, "Yellow", 19));
    add(5, new Part(PartType.TORSO, 2, "Police", 28));
    add(1, new Part(PartType.TORSO, 3, "Suit", 25));
    add(9, new Part(PartType.TORSO, 4, "Scouty", 32));
  }

  private void add(int quantity, Part part) {
    m_data.put(part, Integer.valueOf(quantity));
  }

  public List<Part> getAvailableHeads() {
    return getAvailableParts(PartType.HEAD);
  }

  public List<Part> getAvailableTorsos() {
    return getAvailableParts(PartType.TORSO);
  }

  public List<Part> getAvailableLegs() {
    return getAvailableParts(PartType.LEGS);
  }

  private List<Part> getAvailableParts(PartType type) {
    List<Part> result = new ArrayList<Part>();
    for (Entry<Part, Integer> entry : m_data.entrySet()) {
      if (entry.getKey().getType() == type) {
        if (entry.getValue().intValue() > 0) {
          result.add(entry.getKey());
        }
      }
    }
    return result;
  }

  public List<PartQuantity> getAllParts() {
    List<PartQuantity> result = new ArrayList<PartQuantity>();
    for (Entry<Part, Integer> entry : m_data.entrySet()) {
      result.add(new PartQuantity(entry.getKey(), entry.getValue()));
    }
    return result;
  }

  /**
   * @param part
   * @return
   */
  public Integer getQuantity(Part part) {
    return m_data.get(part);
  }

  /**
   * @param part
   * @param quantity
   */
  public void setQuantity(Part part, Integer quantity) {
    if (quantity == null) {
      quantity = Integer.valueOf(0);
    }
    m_data.put(part, quantity);
  }
}
