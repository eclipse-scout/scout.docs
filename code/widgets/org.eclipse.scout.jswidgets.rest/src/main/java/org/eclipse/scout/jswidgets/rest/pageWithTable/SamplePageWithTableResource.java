/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.jswidgets.rest.pageWithTable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.scout.rt.api.data.table.MaxResultsHelper;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.rest.IRestResource;

@Path("samplePageWithTable")
public class SamplePageWithTableResource implements IRestResource {
  @POST
  @Path("list")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public SamplePageWithTableResponse list(SamplePageWithTableRestrictionDo restrictions) {
    MaxResultsHelper.ResultLimiter limiter = BEANS.get(MaxResultsHelper.class).limiter(restrictions);
    limiter.setEstimatedRowCount(Math.toIntExact(getRawData().count()));
    List<SamplePageWithTableRowDo> rows = listRows(restrictions, limiter.getQueryLimit()).collect(Collectors.toList());
    SamplePageWithTableResponse response = BEANS.get(SamplePageWithTableResponse.class);
    return response.withItems(limiter.limit(rows, response));
  }

  protected Stream<SamplePageWithTableRowDo> listRows(SamplePageWithTableRestrictionDo restrictions, int maxNumberOfRows) {
    String stringFilter = restrictions.getStringField();
    Stream<SamplePageWithTableRowDo> data = getRawData();
    if (StringUtility.hasText(stringFilter)) {
      data = data.filter(row -> StringUtility.containsString(row.getString(), stringFilter));
    }
    return data.limit(maxNumberOfRows);
  }

  protected Stream<SamplePageWithTableRowDo> getRawData() {
    return Stream.of(createSamplePageWithTableRowDo(1, "string 1", null, 103012, true),
        createSamplePageWithTableRowDo(2, "string 2", null, 9214575, false),
        createSamplePageWithTableRowDo(3, "string 3", "es_GT", 5685, true),
        createSamplePageWithTableRowDo(4, "string 4", "de_CH", 168461, false),
        createSamplePageWithTableRowDo(5, "string 5", "th_TH", 959161, true),
        createSamplePageWithTableRowDo(6, "string 6", "fr_FR", 597218, true));
  }

  protected SamplePageWithTableRowDo createSamplePageWithTableRowDo(long id, String string, String smartValue, long number, boolean bool) {
    return BEANS.get(SamplePageWithTableRowDo.class)
        .withId(id)
        .withString(string)
        .withSmartValue(smartValue)
        .withNumber(number)
        .withBool(bool);
  }
}
