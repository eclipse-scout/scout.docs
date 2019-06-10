/*******************************************************************************
 * Copyright (c) 2019 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.widgets.client.ui.custom.chartfield;

import javax.annotation.Generated;

import org.eclipse.scout.rt.dataobject.DoEntity;
import org.eclipse.scout.rt.dataobject.DoValue;

public class ChartDatasetDo extends DoEntity {

  public DoValue<int[]> data() {
    return doValue("data");
  }

  public DoValue<String[]> backgroundColor() {
    return doValue("backgroundColor");
  }

  public DoValue<String[]> borderColor() {
    return doValue("borderColor");
  }

  public DoValue<Integer> borderWidth() {
    return doValue("borderWidth");
  }

  public DoValue<String> label() {
    return doValue("label");
  }

  /* **************************************************************************
   * GENERATED CONVENIENCE METHODS
   * *************************************************************************/

  @Generated("DoConvenienceMethodsGenerator")
  public ChartDatasetDo withData(int[] data) {
    data().set(data);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public int[] getData() {
    return data().get();
  }

  @Generated("DoConvenienceMethodsGenerator")
  public ChartDatasetDo withBackgroundColor(String[] backgroundColor) {
    backgroundColor().set(backgroundColor);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public String[] getBackgroundColor() {
    return backgroundColor().get();
  }

  @Generated("DoConvenienceMethodsGenerator")
  public ChartDatasetDo withBorderColor(String[] borderColor) {
    borderColor().set(borderColor);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public String[] getBorderColor() {
    return borderColor().get();
  }

  @Generated("DoConvenienceMethodsGenerator")
  public ChartDatasetDo withBorderWidth(Integer borderWidth) {
    borderWidth().set(borderWidth);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public Integer getBorderWidth() {
    return borderWidth().get();
  }

  @Generated("DoConvenienceMethodsGenerator")
  public ChartDatasetDo withLabel(String label) {
    label().set(label);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public String getLabel() {
    return label().get();
  }
}
