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

import java.util.Collection;
import java.util.List;

import javax.annotation.Generated;

import org.eclipse.scout.rt.dataobject.DoEntity;
import org.eclipse.scout.rt.dataobject.DoList;
import org.eclipse.scout.rt.dataobject.DoValue;
import org.eclipse.scout.rt.dataobject.TypeName;

@TypeName("widgets.ChartData")
public class ChartDataDo extends DoEntity {

  public DoValue<String[]> labels() {
    return doValue("labels");
  }

  public DoList<ChartDatasetDo> datasets() {
    return doList("datasets");
  }

  public DoValue<Boolean> updateDatasets() {
    return doValue("updateDatasets");
  }

  /* **************************************************************************
   * GENERATED CONVENIENCE METHODS
   * *************************************************************************/

  @Generated("DoConvenienceMethodsGenerator")
  public ChartDataDo withLabels(String[] labels) {
    labels().set(labels);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public String[] getLabels() {
    return labels().get();
  }

  @Generated("DoConvenienceMethodsGenerator")
  public ChartDataDo withDatasets(Collection<? extends ChartDatasetDo> datasets) {
    datasets().updateAll(datasets);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public ChartDataDo withDatasets(ChartDatasetDo... datasets) {
    datasets().updateAll(datasets);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public List<ChartDatasetDo> getDatasets() {
    return datasets().get();
  }

  @Generated("DoConvenienceMethodsGenerator")
  public ChartDataDo withUpdateDatasets(Boolean updateDatasets) {
    updateDatasets().set(updateDatasets);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public Boolean isUpdateDatasets() {
    return updateDatasets().get();
  }
}
