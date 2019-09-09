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
jswidgets.ChartTypeLookupCall = function() {
  jswidgets.ChartTypeLookupCall.parent.call(this);
};
scout.inherits(jswidgets.ChartTypeLookupCall, scout.StaticLookupCall);

jswidgets.ChartTypeLookupCall.prototype._data = function() {
  return jswidgets.ChartTypeLookupCall.DATA;
};

jswidgets.ChartTypeLookupCall.DATA = [
  [jswidgets.Chart.Type.PIE, 'Pie'],
  [jswidgets.Chart.Type.LINE, 'Line'],
  [jswidgets.Chart.Type.DOUGHNUT, 'Doughnut'],
  [jswidgets.Chart.Type.POLAR_AREA, 'Polar Area']


];
