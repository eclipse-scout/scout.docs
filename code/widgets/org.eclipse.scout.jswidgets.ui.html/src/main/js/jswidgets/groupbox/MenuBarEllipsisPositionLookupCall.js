/*
 * Copyright (c) 2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
jswidgets.MenuBarEllipsisPositionLookupCall = function() {
  jswidgets.MenuBarEllipsisPositionLookupCall.parent.call(this);
};
scout.inherits(jswidgets.MenuBarEllipsisPositionLookupCall, scout.StaticLookupCall);

jswidgets.MenuBarEllipsisPositionLookupCall.prototype._data = function() {
  return jswidgets.MenuBarEllipsisPositionLookupCall.DATA;
};

jswidgets.MenuBarEllipsisPositionLookupCall.DATA = [
  [scout.MenuBar.EllipsisPosition.LEFT, 'Left'],
  [scout.MenuBar.EllipsisPosition.RIGHT, 'Right']
];
