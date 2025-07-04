/*
 * Copyright (c) 2010, 2025 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.jswidgets.rest.pageWithTable;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.scout.rt.api.data.table.MaxResultsHelper;
import org.eclipse.scout.rt.dataobject.value.BooleanValueDo;
import org.eclipse.scout.rt.dataobject.value.DateValueDo;
import org.eclipse.scout.rt.dataobject.value.IValueDo;
import org.eclipse.scout.rt.dataobject.value.IntegerValueDo;
import org.eclipse.scout.rt.dataobject.value.StringValueDo;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.util.NumberUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.rest.IRestResource;

@Path("samplePageWithTable")
public class SamplePageWithTableResource implements IRestResource {

  @POST
  @Path("list")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public SamplePageWithTableResponse list(SamplePageWithTableRestrictionDo restriction) {
    MaxResultsHelper.ResultLimiter limiter = BEANS.get(MaxResultsHelper.class).limiter(restriction);
    limiter.setEstimatedRowCount(Math.toIntExact(getRawData(null).count()));
    List<SamplePageWithTableRowDo> rows = listRows(restriction, limiter.getQueryLimit()).collect(Collectors.toList());
    SamplePageWithTableResponse response = BEANS.get(SamplePageWithTableResponse.class);
    return response.withItems(limiter.limit(rows, response));
  }

  protected Stream<SamplePageWithTableRowDo> listRows(SamplePageWithTableRestrictionDo restriction, int maxNumberOfRows) {
    SampleCustomColumnRestrictionContributionDo customizerData = restriction.getContribution(SampleCustomColumnRestrictionContributionDo.class);
    Stream<SamplePageWithTableRowDo> data = getRawData(customizerData);

    String stringFilter = restriction.getStringField();
    if (StringUtility.hasText(stringFilter)) {
      data = data.filter(row -> StringUtility.containsString(row.getString(), stringFilter));
    }

    return data.limit(maxNumberOfRows);
  }

  protected Stream<SamplePageWithTableRowDo> getRawData(SampleCustomColumnRestrictionContributionDo customizerData) {
    return Stream.of(
        createSamplePageWithTableRowDo(1, "string 1", null, 103012, true, customizerData),
        createSamplePageWithTableRowDo(2, "string 2", null, 9214575, false, customizerData),
        createSamplePageWithTableRowDo(3, "string 3", "es_GT", 5685, true, customizerData),
        createSamplePageWithTableRowDo(4, "string 4", "de_CH", 168461, false, customizerData),
        createSamplePageWithTableRowDo(5, "string 5", "th_TH", 959161, true, customizerData),
        createSamplePageWithTableRowDo(6, "string 6", "fr_FR", 597218, true, customizerData));
  }

  protected SamplePageWithTableRowDo createSamplePageWithTableRowDo(long id, String string, String smartValue, long number, boolean bool, SampleCustomColumnRestrictionContributionDo customizerData) {
    SamplePageWithTableRowDo row = BEANS.get(SamplePageWithTableRowDo.class)
        .withId(id)
        .withString(string)
        .withSmartValue(smartValue)
        .withNumber(number)
        .withBool(bool);
    if (customizerData != null && customizerData.getColumnTypes() != null) {
      Map<String, IValueDo> cells = new LinkedHashMap<>();
      customizerData.getColumnTypes().forEach((columnId, columnType) -> cells.put(columnId, getRandomValue(columnType)));
      row.putContribution(BEANS.get(SampleCustomColumnTableRowContributionDo.class).withCells(cells));
    }
    return row;
  }

  protected IValueDo getRandomValue(String type) {
    if ("number".equals(type)) {
      return IntegerValueDo.of(NumberUtility.randomInt(999_999));
    }
    if ("boolean".equals(type)) {
      return BooleanValueDo.of(Math.random() > 0.5);
    }
    if ("date".equals(type)) {
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.DAY_OF_YEAR, (Math.random() < 0.5 ? -1 : 1) * NumberUtility.randomInt(365 * 10));
      cal.add(Calendar.SECOND, (Math.random() < 0.5 ? -1 : 1) * NumberUtility.randomInt(60 * 60 * 12));
      cal.add(Calendar.MILLISECOND, (Math.random() < 0.5 ? -1 : 1) * NumberUtility.randomInt(500));
      return DateValueDo.of(cal.getTime());
    }
    String[] s1 = {"Red", "Green", "Blue", "Yellow", "Purple", "Black", "White"};
    String[] s2 = {"Rabbit", "Dog", "Cat", "Elephant", "Fish", "Chicken", "Spider"};
    return StringValueDo.of(StringUtility.join(" ",
        s1[NumberUtility.randomInt(s1.length)],
        s2[NumberUtility.randomInt(s2.length)]));
  }
}
