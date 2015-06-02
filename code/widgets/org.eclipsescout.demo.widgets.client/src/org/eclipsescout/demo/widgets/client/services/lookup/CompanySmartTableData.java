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
package org.eclipsescout.demo.widgets.client.services.lookup;

import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;

/**
 *
 */
public class CompanySmartTableData extends AbstractTableRowData {

  private static final long serialVersionUID = 1L;

  public static final String additionalInfo = "additionalInfo";
  public static final String companyType = "companyType";
  private String m_additionalInfo;
  private Long m_companyType;

  public CompanySmartTableData() {
  }

  public CompanySmartTableData(String additionalInfo, Long companyType) {
    m_additionalInfo = additionalInfo;
    m_companyType = companyType;
  }

  public String getAdditionalInfo() {
    return m_additionalInfo;
  }

  public void setAdditionalInfo(String additionalInfo) {
    m_additionalInfo = additionalInfo;
  }

  public Long getCompanyType() {
    return m_companyType;
  }

  public void setCompanyType(Long companyType) {
    m_companyType = companyType;
  }

}
