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
package org.eclipsescout.demo.minifigcreator.server.services.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.server.services.lookup.AbstractLookupService;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipsescout.demo.minifigcreator.shared.minifig.part.Part;

public abstract class AbstractPartLookupService extends AbstractLookupService implements ILookupService {

  protected abstract List<Part> createPartsList(LookupCall call);

  private LookupRow convertToLookupRow(Part part) {
    return new LookupRow(part, part.getName());
  }

  protected List<LookupRow> execCreateLookupRows(LookupCall call) throws ProcessingException {
    List<LookupRow> result = new ArrayList<LookupRow>();
    for (Part part : createPartsList(call)) {
      result.add(convertToLookupRow(part));
    }
    return result;
  }

  /**
   * Complete override using local data
   */
  @Override
  public LookupRow[] getDataByKey(LookupCall call) throws ProcessingException {
    if (call.getKey() == null) {
      return LookupRow.EMPTY_ARRAY;
    }
    Object key = call.getKey();
    ArrayList<LookupRow> list = new ArrayList<LookupRow>();
    for (LookupRow row : execCreateLookupRows(call)) {
      if (key.equals(row.getKey())) {
        list.add(row);
      }
    }
    return list.toArray(new LookupRow[list.size()]);
  }

  /**
   * Complete override using local data
   */
  @Override
  public LookupRow[] getDataByText(LookupCall call) throws ProcessingException {
    ArrayList<LookupRow> list = new ArrayList<LookupRow>();
    Pattern p = createSearchPattern(call.getText());
    for (LookupRow row : execCreateLookupRows(call)) {
      if (row.getText() != null && p.matcher(row.getText().toLowerCase()).matches()) {
        list.add(row);
      }
    }
    return list.toArray(new LookupRow[list.size()]);
  }

  /**
   * Complete override using local data
   */
  @Override
  public LookupRow[] getDataByAll(LookupCall call) throws ProcessingException {
    ArrayList<LookupRow> list = new ArrayList<LookupRow>();
    Pattern p = createSearchPattern(call.getAll());
    for (LookupRow row : execCreateLookupRows(call)) {
      if (row.getText() != null && p.matcher(row.getText().toLowerCase()).matches()) {
        list.add(row);
      }
    }
    return list.toArray(new LookupRow[list.size()]);
  }

  /**
   * Complete override using local data
   */
  @Override
  public LookupRow[] getDataByRec(LookupCall call) throws ProcessingException {
    ArrayList<LookupRow> list = new ArrayList<LookupRow>();
    Object parentKey = call.getRec();
    if (parentKey == null) {
      for (LookupRow row : execCreateLookupRows(call)) {
        if (row.getParentKey() == null) {
          list.add(row);
        }
      }
    }
    else {
      for (LookupRow row : execCreateLookupRows(call)) {
        if (parentKey.equals(row.getParentKey())) {
          list.add(row);
        }
      }
    }
    return list.toArray(new LookupRow[list.size()]);
  }

  /**
   * @param humanReadbleFilterPattern
   *          is not a regex and may contain *,%,? as wildcards for searching
   *          override this method for custom filter pattern creation
   */
  protected Pattern createSearchPattern(String humanReadbleFilterPattern) {
    return createLowerCaseSearchPattern(humanReadbleFilterPattern);
  }

  /**
   * alias for {@link StringUtility#toRegEx(String, int)}
   */
  public static Pattern createLowerCaseSearchPattern(String s) {
    return StringUtility.toRegEx(s, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
  }
}
