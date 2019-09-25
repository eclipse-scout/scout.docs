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
jswidgets.RainbowLookupCall = function() {
  jswidgets.RainbowLookupCall.parent.call(this);
};
scout.inherits(jswidgets.RainbowLookupCall, scout.StaticLookupCall);

jswidgets.RainbowLookupCall.prototype._data = function() {
  return jswidgets.RainbowLookupCall.DATA;
};

jswidgets.RainbowLookupCall.DATA = [
  ['FEE0E0', scout.icons.MINUS],
  ['FECAC8', scout.icons.GROUP, 'Times New Roman'],
  ['FEB1AB', scout.icons.PLUS, 'Courier New'],
  ['ECF3F7', scout.icons.INFO, 'sans-serif'],
  ['C8E4F2', scout.icons.CALENDAR, 'Open Sans'],
  ['B0DAEE', scout.icons.FOLDER, 'Bold-Roboto'],
  ['DDF4EA', scout.icons.WORLD, 'Impact'],
  ['B6ECD3', scout.icons.STAR, 'Comic Sans MS'],
  ['86D9B2', scout.icons.STAR_MARKED, '20 Arial'],
  ['3EE1C3', scout.icons.AVG, 'Calibri-italic'],
  ['93EDDC', scout.icons.GEAR, 'Comic Sans'],
  ['BAF3E8', scout.icons.COLLAPSE, 'Comic Sans'],
  ['DDFFF9', scout.icons.LONG_ARROW_DOWN],
  ['FCF0E5', scout.icons.EXCLAMATION_MARK_CIRCLE, 'Arial-Bold-12'],
  ['FEE6C0', scout.icons.SQUARE_BOLD, '10 Courier'],
  ['FFDB9D', scout.icons.ANGLE_RIGHT]
];

jswidgets.RainbowLookupCall.prototype._dataToLookupRow = function(data, index) {
  var model = {
    key: data[0],
    text: data[0],
    backgroundColor: data[0],
    iconId: data[1],
  };
  if (data[2]) {
    model.font = data[2];
  }
  if (index % 2 === 0) {
    model.foregroundColor = 'blue';
  }
  return scout.create('LookupRow', model);
};
