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
jswidgets.PopupHorizontalAlignLookupCall = function() {
  jswidgets.PopupHorizontalAlignLookupCall.parent.call(this);
};
scout.inherits(jswidgets.PopupHorizontalAlignLookupCall, scout.StaticLookupCall);

jswidgets.PopupHorizontalAlignLookupCall.prototype._data = function() {
  return jswidgets.PopupHorizontalAlignLookupCall.DATA;
};

jswidgets.PopupHorizontalAlignLookupCall.DATA = [
  [scout.Popup.Alignment.LEFT, 'left'],
  [scout.Popup.Alignment.LEFTEDGE, 'leftedge'],
  [scout.Popup.Alignment.CENTER, 'center'],
  [scout.Popup.Alignment.RIGHT, 'right'],
  [scout.Popup.Alignment.RIGHTEDGE, 'rightedge'],
];
