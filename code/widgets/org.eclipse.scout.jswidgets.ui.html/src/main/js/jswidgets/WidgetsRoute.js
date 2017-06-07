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
jswidgets.WidgetsRoute = function(desktop) {
  jswidgets.WidgetsRoute.parent.call(this);

  this.desktop = desktop;

  // This listener updates the URL in the browsers location field whenever a page has been activated
  desktop.outline.on('nodesSelected', this._onPageChanged.bind(this));
};
scout.inherits(jswidgets.WidgetsRoute, scout.Route);

jswidgets.WidgetsRoute.prototype.matches = function(location) {
  return !!this._getRouteData(location);
};

jswidgets.WidgetsRoute.prototype._getRouteData = function(location) {
  if (scout.strings.empty(location)) {
    return null;
  }
  return scout.arrays.find(jswidgets.WidgetsRoute.ROUTES, function(routeData) {
    return location.indexOf(routeData[0]) > -1;
  });
};

jswidgets.WidgetsRoute.prototype._getRouteDataByObjectType = function(objectType) {
  return scout.arrays.find(jswidgets.WidgetsRoute.ROUTES, function(routeData) {
    return routeData[1] === objectType;
  });
};

jswidgets.WidgetsRoute.prototype.activate = function(location) {
  jswidgets.WidgetsRoute.parent.prototype.activate.call(this, location);

  var objectType = this._getRouteData(location)[1];
  var node = scout.arrays.find(this.desktop.outline.nodes, function(node) {
    return node.detailForm.objectType === objectType;
  });
  this.desktop.outline.selectNode(node);
};

jswidgets.WidgetsRoute.prototype._onPageChanged = function(event) {
  var page = event.source.selectedNode();
  if (page) {
    var routeData = this._getRouteDataByObjectType(page.detailForm.objectType);
    scout.router.updateLocation(routeData[0]);
  }
};

// 0: routeRef
// 1: objectType of the detail form of a node in FormFieldOutline
jswidgets.WidgetsRoute.ROUTES = [ // FIXME [awe] 7.0 - Extract routeRef dynamically from objectType, then delete this array
  ['carousel', 'jswidgets.CarouselForm'],
  ['datefield', 'jswidgets.DateFieldForm'],
  ['form', 'jswidgets.FormForm'],
  ['groupbox', 'jswidgets.GroupBoxForm'],
  ['numberfield', 'jswidgets.NumberFieldForm'],
  ['smartfield2', 'jswidgets.SmartField2Form'],
  ['stringfield', 'jswidgets.StringFieldForm'],
  ['tablefield', 'jswidgets.TableFieldForm']
];
