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
package org.eclipsescout.demo.minifigcreator.shared.minifig.part;

import org.eclipse.scout.commons.StringUtility;

public final class PartUtility {

  public static String calculateSummary(String name, Part head, Part torso, Part legs) {
    StringBuilder sb = new StringBuilder();
    if (StringUtility.isNullOrEmpty(name)) {
      sb.append("...");
    }
    else {
      sb.append(name);
    }

    sb.append(" ");
    if ((head != null) || (torso != null) || (legs != null)) {
      String headName = (head == null) ? null : head.getName();
      String torsoName = (torso == null) ? null : torso.getName();
      String legsName = (legs == null) ? null : legs.getName();

      sb.append("[");
      sb.append(StringUtility.join(", ", headName, torsoName, legsName));
      sb.append("]");
      sb.append(" ");
    }

    sb.append("- ");
    sb.append("value: ");
    sb.append(calculateValue(head, torso, legs));
    return sb.toString();
  }

  public static int calculateValue(Part head, Part torso, Part legs) {
    int headValue = (head == null) ? 0 : head.getValue();
    int torsoValue = (torso == null) ? 0 : torso.getValue();
    int legsValue = (legs == null) ? 0 : legs.getValue();
    return headValue + torsoValue + legsValue;
  }

  public static String calculateImageId(Part head, Part torso, Part legs) {
    String headValue = partContribution("H", head);
    String torsoValue = partContribution("T", torso);
    String legsValue = partContribution("L", legs);
    return "Minifig_" + headValue + "_" + torsoValue + "_" + legsValue;
  }

  private static String partContribution(String prefix, Part part) {
    int id = (part == null) ? 0 : part.getId();
    return prefix + String.format("%02d", id);
  }

  public static String calculateSmallIconId(Part singlePart) {
    int id;
    String letter;
    if (singlePart == null) {
      id = 0;
      letter = "X";
    }
    else {
      id = singlePart.getId();
      letter = singlePart.getType().name().substring(0, 1);
    }
    return "Part_" + letter + String.format("%02d", id);
  }

  private PartUtility() {
  }
}
