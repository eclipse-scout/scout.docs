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

import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.common.code.ICode;
import org.eclipse.scout.rt.shared.services.lookup.CodeLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ICodeLookupCallVisitor;
import org.eclipse.scout.widgets.shared.services.code.EventTypeCodeType;

/**
 * @author mzi
 */
@ClassId("599f17a5-d42a-4751-a390-229a960bee32")
public class EventTypeLookupCall extends CodeLookupCall<Long> {

  private static final long serialVersionUID = 1L;

  /**
   * Visitor class that filters out inactive codes.
   */
  class LookupVisitor implements ICodeLookupCallVisitor<Long> {

    @Override
    public boolean visit(CodeLookupCall<Long> call, ICode<Long> code, int treeLevel) {
      return code.isActive();
    }
  }

  /**
   * Default constructor that wires this lookup call to the event type code type.
   */
  public EventTypeLookupCall() {
    super(EventTypeCodeType.class);
    setFilter(new LookupVisitor());
  }
}
