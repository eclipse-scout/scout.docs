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
jswidgets.FormDisplayHintLookupCall = function() {
  jswidgets.FormDisplayHintLookupCall.parent.call(this);
};
scout.inherits(jswidgets.FormDisplayHintLookupCall, scout.StaticLookupCall);

jswidgets.FormDisplayHintLookupCall.prototype._data = function() {
  return jswidgets.FormDisplayHintLookupCall.DATA;
};

jswidgets.FormDisplayHintLookupCall.DATA = [
  [scout.Form.DisplayHint.DIALOG, 'dialog'],
  [scout.Form.DisplayHint.VIEW, 'view'],
  [scout.Form.DisplayHint.POPUP_WINDOW, 'popupWindow']
];
