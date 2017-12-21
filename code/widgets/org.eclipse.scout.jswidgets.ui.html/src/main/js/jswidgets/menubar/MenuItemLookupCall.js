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
jswidgets.MenuItemLookupCall = function() {
  jswidgets.MenuItemLookupCall.parent.call(this);
};
scout.inherits(jswidgets.MenuItemLookupCall, scout.StaticLookupCall);

jswidgets.MenuItemLookupCall.prototype._data = function() {
  return jswidgets.MenuItemLookupCall.DATA;
};

jswidgets.MenuItemLookupCall.DATA = [
  ['Menu1', 'Menu 1'],
  ['Menu2', 'Menu 2'],
  ['Menu3', 'Menu 3']
];
