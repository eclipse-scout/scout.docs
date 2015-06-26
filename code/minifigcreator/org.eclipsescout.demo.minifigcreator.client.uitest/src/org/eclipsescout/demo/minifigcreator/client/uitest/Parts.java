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
package org.eclipsescout.demo.minifigcreator.client.uitest;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipsescout.demo.minifigcreator.shared.minifig.part.Part;
import org.eclipsescout.demo.minifigcreator.shared.minifig.part.PartType;

/**
 *
 */
public class Parts {
  public static final Part PART_HEAD_1 = new Part(PartType.HEAD, 1, "Normal", 12);
  public static final Part PART_HEAD_2 = new Part(PartType.HEAD, 2, "Pirate", 13);
  public static final Part PART_HEAD_3 = new Part(PartType.HEAD, 4, "Talky & Walky", 8);
  public static final Part PART_HEAD_4 = new Part(PartType.HEAD, 5, "Bart", 16);

  public static final Part PART_TORSO_1 = new Part(PartType.TORSO, 1, "Yellow", 19);
  public static final Part PART_TORSO_2 = new Part(PartType.TORSO, 2, "Police", 28);
  public static final Part PART_TORSO_3 = new Part(PartType.TORSO, 3, "Suit", 25);
  public static final Part PART_TORSO_4 = new Part(PartType.TORSO, 4, "Scouty", 32);

  public static final Part PART_LEGS_1 = new Part(PartType.LEGS, 1, "Yellow", 10);
  public static final Part PART_LEGS_2 = new Part(PartType.LEGS, 2, "Odd", 24);
  public static final Part PART_LEGS_3 = new Part(PartType.LEGS, 3, "Jeans", 15);
  public static final Part PART_LEGS_4 = new Part(PartType.LEGS, 4, "Gray", 15);
  public static final Part PART_LEGS_5 = new Part(PartType.LEGS, 5, "Overall", 15);
  public static final Part PART_LEGS_6 = new Part(PartType.LEGS, 6, "Blue", 15);
  public static final Part PART_LEGS_7 = new Part(PartType.LEGS, 7, "Orange", 38);

  public static List<? extends ILookupRow<Part>> toLookupRows(Part... parts) {
    List<ILookupRow<Part>> result = new ArrayList<ILookupRow<Part>>();
    for (Part part : parts) {
      result.add(toLookupRow(part));
    }
    return result;
  }

  public static ILookupRow<Part> toLookupRow(Part part) {
    return new LookupRow<Part>(part, part.getName());
  }
}
