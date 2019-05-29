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
jswidgets.ChartTypeLookupCall = function() {
  jswidgets.ChartTypeLookupCall.parent.call(this);
};
scout.inherits(jswidgets.ChartTypeLookupCall, scout.StaticLookupCall);

jswidgets.ChartTypeLookupCall.prototype._data = function() {
  return jswidgets.ChartTypeLookupCall.DATA;
};

jswidgets.ChartTypeLookupCall.DATA = [
  [jswidgets.Chart.type.PIE, 'Pie'],
  [jswidgets.Chart.type.LINE, 'Line'],
  [jswidgets.Chart.type.DOUGHNUT, 'Doughnut'],
  [jswidgets.Chart.type.POLAR_AREA, 'Polar Area']


];
