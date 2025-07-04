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

import java.util.Map;

import jakarta.annotation.Generated;

import org.eclipse.scout.rt.dataobject.ContributesTo;
import org.eclipse.scout.rt.dataobject.DoEntity;
import org.eclipse.scout.rt.dataobject.DoValue;
import org.eclipse.scout.rt.dataobject.IDoEntityContribution;
import org.eclipse.scout.rt.dataobject.TypeName;

@ContributesTo(SamplePageWithTableRestrictionDo.class)
@TypeName("jswidgets.SampleCustomColumnRestrictionContribution")
public class SampleCustomColumnRestrictionContributionDo extends DoEntity implements IDoEntityContribution {

  /**
   * column id -> column type
   */
  public DoValue<Map<String, String>> columnTypes() {
    return doValue("columnTypes");
  }

  /* **************************************************************************
   * GENERATED CONVENIENCE METHODS
   * *************************************************************************/

  /**
   * See {@link #columnTypes()}.
   */
  @Generated("DoConvenienceMethodsGenerator")
  public SampleCustomColumnRestrictionContributionDo withColumnTypes(Map<String, String> columnTypes) {
    columnTypes().set(columnTypes);
    return this;
  }

  /**
   * See {@link #columnTypes()}.
   */
  @Generated("DoConvenienceMethodsGenerator")
  public Map<String, String> getColumnTypes() {
    return columnTypes().get();
  }
}
