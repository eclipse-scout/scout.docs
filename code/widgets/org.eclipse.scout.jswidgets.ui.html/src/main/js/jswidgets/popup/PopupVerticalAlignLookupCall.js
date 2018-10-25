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
jswidgets.PopupVerticalAlignLookupCall = function() {
  jswidgets.PopupVerticalAlignLookupCall.parent.call(this);
};
scout.inherits(jswidgets.PopupVerticalAlignLookupCall, scout.StaticLookupCall);

jswidgets.PopupVerticalAlignLookupCall.prototype._data = function() {
  return jswidgets.PopupVerticalAlignLookupCall.DATA;
};

jswidgets.PopupVerticalAlignLookupCall.DATA = [
  [scout.Popup.Alignment.TOP, 'top'],
  [scout.Popup.Alignment.TOPEDGE, 'topedge'],
  [scout.Popup.Alignment.CENTER, 'center'],
  [scout.Popup.Alignment.BOTTOM, 'bottom'],
  [scout.Popup.Alignment.BOTTOMEDGE, 'bottomedge'],
];
