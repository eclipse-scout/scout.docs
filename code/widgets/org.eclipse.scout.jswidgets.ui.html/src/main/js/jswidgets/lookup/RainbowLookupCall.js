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
jswidgets.RainbowLookupCall = function () {
  jswidgets.RainbowLookupCall.parent.call(this);
};
scout.inherits(jswidgets.RainbowLookupCall, scout.StaticLookupCall);

jswidgets.RainbowLookupCall.prototype._data = function () {
  return jswidgets.RainbowLookupCall.DATA;
};

jswidgets.RainbowLookupCall.DATA = [
  {
    key: "FEE0E0",
    text: "FEE0E0",
    backgroundColor: "FEE0E0",
    foregroundColor: "black",
    iconId: scout.icons.MINUS,
    active: true,
    enabled: true
  },
  {
    key: "FECAC8",
    text: "FECAC8",
    backgroundColor: "FECAC8",
    foregroundColor: "black",
    font: "Times New Roman",
    iconId: scout.icons.GROUP,
    active: true,
    enabled: false
  },
  {
    key: "FEB1AB",
    text: "FEB1AB",
    backgroundColor: "FEB1AB",
    foregroundColor: "black",
    font: "Courier New",
    iconId: scout.icons.PLUS,
    active: true,
    enabled: false
  },
  {
    key: "ECF3F7",
    text: "ECF3F7",
    tooltipText: "ECF3F7",
    backgroundColor: "ECF3F7",
    foregroundColor: "black",
    font: "sans-serif",
    iconId: scout.icons.INFO,
    active: true,
    enabled: true
  },
  {
    key: "C8E4F2",
    text: "C8E4F2",
    tooltipText: "C8E4F2",
    backgroundColor: "C8E4F2",
    foregroundColor: "black",
    font: "Open Sans",
    iconId: scout.icons.CALENDAR,
    active: true,
    enabled: false
  },
  {
    key: "B0DAEE",
    text: "B0DAEE",
    tooltipText: "B0DAEE",
    backgroundColor: "B0DAEE",
    foregroundColor: "black",
    font: "Bold-Roboto",
    iconId: scout.icons.FOLDER,
    active: true,
    enabled: true
  },
  {
    key: "DDF4EA",
    text: "DDF4EA",
    tooltipText: "DDF4EA",
    backgroundColor: "DDF4EA",
    foregroundColor: "black",
    font: "Impact",
    iconId: scout.icons.WORLD,
    active: true,
    enabled: true
  },
  {
    key: "B6ECD3",
    text: "B6ECD3",
    tooltipText: "B6ECD3",
    backgroundColor: "B6ECD3",
    foregroundColor: "black",
    font: "Comic Sans MS",
    iconId: scout.icons.STAR,
    active: true,
    enabled: true
  },
  {
    key: "86D9B2",
    text: "86D9B2",
    backgroundColor: "86D9B2",
    foregroundColor: "black",
    font: "20 Arial",
    iconId: scout.icons.STAR_MARKED,
    active: true,
    enabled: false
  },
  {
    key: "3EE1C3",
    text: "3EE1C3",
    backgroundColor: "3EE1C3",
    foregroundColor: "black",
    font: "Calibri-italic",
    iconId: scout.icons.AVG,
    active: true,
    enabled: false
  },
  {
    key: "93EDDC",
    text: "93EDDC",
    backgroundColor: "93EDDC",
    foregroundColor: "black",
    font: "Comic Sans",
    iconId: scout.icons.GEAR,
    active: true,
    enabled: false
  },
  {
    key: "BAF3E8",
    text: "BAF3E8",
    backgroundColor: "BAF3E8",
    foregroundColor: "black",
    font: "Comic Sans",
    iconId: scout.icons.COLLAPSE,
    active: true,
    enabled: false
  },
  {
    key: "DDFFF9",
    text: "DDFFF9",
    tooltipText: "DDFFF9",
    backgroundColor: "DDFFF9",
    foregroundColor: "black",
    iconId: scout.icons.LONG_ARROW_DOWN,
    active: true,
    enabled: true
  },
  {
    key: "FCF0E5",
    text: "FCF0E5",
    backgroundColor: "FCF0E5",
    foregroundColor: "black",
    font: "Arial-Bold-12",
    iconId: scout.icons.EXCLAMATION_MARK,
    active: true,
    enabled: false
  },
  {
    key: "FEE6C0",
    text: "FEE6C0",
    backgroundColor: "FEE6C0",
    foregroundColor: "black",
    font: "10 Courier",
    iconId: scout.icons.SQUARE_BOLD,
    active: true,
    enabled: true
  },
  {
    key: "FFDB9D",
    text: "FFDB9D",
    backgroundColor: "FFDB9D",
    foregroundColor: "black",
    font: "10 Courier",
    iconId: scout.icons.SQUARE_BOLD,
    active: true,
    enabled: true
  },
];

jswidgets.RainbowLookupCall.prototype._dataToLookupRow = function (data) {
  return scout.create('LookupRow', data);
};
