/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.services.lookup;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.chart.shared.data.basic.chart.IChartType;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

@ClassId("10f36728-ff9f-4695-a37a-50d6add6a687")
public class ChartTypeLookupCall extends LocalLookupCall<String> {

  private static final long serialVersionUID = 1L;

  @Override
  protected List<? extends ILookupRow<String>> execCreateLookupRows() {
    List<ILookupRow<String>> rows = new ArrayList<>();
    rows.add(new LookupRow<>(IChartType.BAR, "Bar"));
    rows.add(new LookupRow<>(IChartType.BAR_HORIZONTAL, "Bar Horizontal"));
    rows.add(new LookupRow<>(IChartType.BUBBLE, "Bubble"));
    rows.add(new LookupRow<>(IChartType.COMBO_BAR_LINE, "Combo Bar Line"));
    rows.add(new LookupRow<>(IChartType.DOUGHNUT, "Doughnut"));
    rows.add(new LookupRow<>(IChartType.FULFILLMENT, "Fulfillment"));
    rows.add(new LookupRow<>(IChartType.LINE, "Line"));
    rows.add(new LookupRow<>(IChartType.PIE, "Pie"));
    rows.add(new LookupRow<>(IChartType.POLAR_AREA, "Polar area"));
    rows.add(new LookupRow<>(IChartType.RADAR, "Radar"));
    rows.add(new LookupRow<>(IChartType.SALESFUNNEL, "Salesfunnel"));
    rows.add(new LookupRow<>(IChartType.SCATTER, "Scatter"));
    rows.add(new LookupRow<>(IChartType.SPEEDO, "Speedo"));
    rows.add(new LookupRow<>(IChartType.VENN, "Venn"));
    return rows;
  }
}
