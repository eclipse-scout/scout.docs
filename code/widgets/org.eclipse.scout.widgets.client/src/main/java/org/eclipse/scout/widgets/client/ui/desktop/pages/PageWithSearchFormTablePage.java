/*
 * Copyright (c) 2022 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.desktop.pages;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.ISearchForm;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.platform.util.date.DateUtility;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.widgets.client.ui.forms.SearchForm;
import org.eclipse.scout.widgets.client.ui.forms.SearchFormData;

@ClassId("7362d0ab-5d2e-412c-8c09-c6657c461592")
public class PageWithSearchFormTablePage extends PageWithTableTablePage {

  public PageWithSearchFormTablePage() {
    super();
  }

  @Override
  protected Class<? extends ISearchForm> getConfiguredSearchForm() {
    return SearchForm.class;
  }

  @Override
  protected String getConfiguredTitle() {
    return "Page with search form";
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    SearchFormData formData = (SearchFormData) filter.getFormData();
    importTableData(Arrays.stream(loadDemoData())
        .filter(row -> filterString((String) row[0], formData.getString().getValue()) || filterString((String) row[1], formData.getString().getValue()))
        .filter(row -> filterComparable((Comparable) row[2], formData.getLongFrom().getValue(), formData.getLongTo().getValue()))
        .filter(row -> filterComparable((Comparable) row[3], formData.getIntegerFrom().getValue(), formData.getIntegerTo().getValue()))
        .filter(row -> filterDate((Date) row[5], formData.getDateFrom().getValue(), formData.getDateTo().getValue()))
        .filter(row -> filterObject(row[6], formData.getBoolean().getValue()))
        .filter(row -> filterCollection(row[7], formData.getSmart().getValue()))
        .toArray(Object[][]::new));
  }

  protected boolean filterString(String value, String filter) {
    if (filter != null && value != null && !value.contains(filter)) {
      return false;
    }
    return true;
  }

  protected boolean filterObject(Object value, Object filter) {
    if (filter != null && !filter.equals(value)) {
      return false;
    }
    return true;
  }

  protected boolean filterCollection(Object value, Collection<?> filter) {
    if (filter != null && !filter.contains(value)) {
      return false;
    }
    return true;
  }

  @SuppressWarnings("unchecked")
  protected boolean filterComparable(Comparable value, Comparable filterFrom, Comparable filterTo) {
    if (filterFrom != null && ObjectUtility.compareTo(filterFrom, value) > 0) {
      return false;
    }
    if (filterTo != null && ObjectUtility.compareTo(filterTo, value) < 0) {
      return false;
    }
    return true;
  }

  protected boolean filterDate(Date value, Date filterFrom, Date filterTo) {
    value = DateUtility.truncDate(value);
    return filterComparable(value, filterFrom, filterTo);
  }

  @Order(10)
  @Replace
  @ClassId("d89e64bb-6ee8-4861-99cb-7f43cfb98b62")
  public class ViewSourceOnGitHubMenuEx extends ViewSourceOnGitHubMenu {

    @Override
    protected Class<?> provideSourceClass() {
      return PageWithSearchFormTablePage.class;
    }
  }
}
