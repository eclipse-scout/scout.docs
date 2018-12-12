/*******************************************************************************
 * Copyright (c) 2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
jswidgets.CollapseStyleLookupCall = function() {
  jswidgets.CollapseStyleLookupCall.parent.call(this);
};
scout.inherits(jswidgets.CollapseStyleLookupCall, scout.StaticLookupCall);

jswidgets.CollapseStyleLookupCall.prototype._data = function() {
  return jswidgets.CollapseStyleLookupCall.DATA;
};

jswidgets.CollapseStyleLookupCall.DATA = [
  [scout.Group.CollapseStyle.LEFT, 'left'],
  [scout.Group.CollapseStyle.RIGHT, 'right'],
  [scout.Group.CollapseStyle.BOTTOM, 'bottom']
];
