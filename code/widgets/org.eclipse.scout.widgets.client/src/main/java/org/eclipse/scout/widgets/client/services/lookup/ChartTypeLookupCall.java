/*
 * Copyright (c) 2021 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
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
    rows.add(new LookupRow<>(IChartType.SPEEDO, "Speedo"));
    rows.add(new LookupRow<>(IChartType.VENN, "Venn"));
    return rows;
  }
}
