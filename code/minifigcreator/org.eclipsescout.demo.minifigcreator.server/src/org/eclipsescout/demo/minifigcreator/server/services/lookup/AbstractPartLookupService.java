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
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.server.services.lookup.AbstractLookupService;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipsescout.demo.minifigcreator.shared.minifig.part.Part;

public abstract class AbstractPartLookupService extends AbstractLookupService<Part> implements ILookupService<Part> {

  protected abstract List<Part> createPartsList(ILookupCall<Part> call);

  private ILookupRow<Part> convertToLookupRow(Part part) {
    return new LookupRow<Part>(part, part.getName());
  }

  protected List<ILookupRow<Part>> execCreateLookupRows(ILookupCall<Part> call) throws ProcessingException {
    List<ILookupRow<Part>> result = new ArrayList<ILookupRow<Part>>();
    for (Part part : createPartsList(call)) {
      result.add(convertToLookupRow(part));
    }
    return result;
  }

  /**
   * Complete override using local data
   */
  @Override
  public List<ILookupRow<Part>> getDataByKey(ILookupCall<Part> call) throws ProcessingException {
    if (call.getKey() == null) {
      return Collections.emptyList();
    }
    Object key = call.getKey();
    ArrayList<ILookupRow<Part>> list = new ArrayList<ILookupRow<Part>>();
    for (ILookupRow<Part> row : execCreateLookupRows(call)) {
      if (key.equals(row.getKey())) {
        list.add(row);
      }
    }
    return list;
  }

  /**
   * Complete override using local data
   */
  @Override
  public List<ILookupRow<Part>> getDataByText(ILookupCall<Part> call) throws ProcessingException {
    ArrayList<ILookupRow<Part>> list = new ArrayList<ILookupRow<Part>>();
    Pattern p = createSearchPattern(call.getText());
    for (ILookupRow<Part> row : execCreateLookupRows(call)) {
      if (row.getText() != null && p.matcher(row.getText().toLowerCase()).matches()) {
        list.add(row);
      }
    }
    return list;
  }

  /**
   * Complete override using local data
   */
  @Override
  public List<ILookupRow<Part>> getDataByAll(ILookupCall<Part> call) throws ProcessingException {
    ArrayList<ILookupRow<Part>> list = new ArrayList<ILookupRow<Part>>();
    Pattern p = createSearchPattern(call.getAll());
    for (ILookupRow<Part> row : execCreateLookupRows(call)) {
      if (row.getText() != null && p.matcher(row.getText().toLowerCase()).matches()) {
        list.add(row);
      }
    }
    return list;
  }

  /**
   * Complete override using local data
   */
  @Override
  public List<ILookupRow<Part>> getDataByRec(ILookupCall<Part> call) throws ProcessingException {
    List<ILookupRow<Part>> list = new ArrayList<ILookupRow<Part>>();
    Object parentKey = call.getRec();
    if (parentKey == null) {
      for (ILookupRow<Part> row : execCreateLookupRows(call)) {
        if (row.getParentKey() == null) {
          list.add(row);
        }
      }
    }
    else {
      for (ILookupRow<Part> row : execCreateLookupRows(call)) {
        if (parentKey.equals(row.getParentKey())) {
          list.add(row);
        }
      }
    }
    return list;
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
