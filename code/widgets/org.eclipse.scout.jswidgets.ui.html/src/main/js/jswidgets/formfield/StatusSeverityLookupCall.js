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
jswidgets.StatusSeverityLookupCall = function() {
  jswidgets.StatusSeverityLookupCall.parent.call(this);
};
scout.inherits(jswidgets.StatusSeverityLookupCall, scout.StaticLookupCall);

jswidgets.StatusSeverityLookupCall.prototype._data = function() {
  return jswidgets.StatusSeverityLookupCall.DATA;
};

jswidgets.StatusSeverityLookupCall.DATA = [
  [scout.Status.Severity.INFO, 'info'],
  [scout.Status.Severity.WARNING, 'warning'],
  [scout.Status.Severity.ERROR, 'error']
];
