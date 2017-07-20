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
  this.routes = this._createRoutes(desktop);

  // This listener updates the URL in the browsers location field whenever a page has been activated
  desktop.outline.on('nodesSelected', this._onPageChanged.bind(this));
};
scout.inherits(jswidgets.WidgetsRoute, scout.Route);

/**
 * Creates an array which maps a routeRef to an objectType.
 * 0: routeRef
 * 1: objectType of the detail form of a node in FormFieldOutline
 */
jswidgets.WidgetsRoute.prototype._createRoutes = function(desktop) {
  var regex = /^jswidgets\.(\w*)Form$/;
  return desktop.outline.nodes.map(function(node) {
    var routeRef = null,
      objectType = node.detailForm.objectType,
      result = regex.exec(objectType);

    if (result === null || result.length === 0) {
      $.log.error('Failed to create route for objectType=' + objectType);
    }
    routeRef = result[1].toLowerCase();
    return [routeRef, objectType];
  });
};

jswidgets.WidgetsRoute.prototype.matches = function(location) {
  return !!this._getRouteData(location);
};

jswidgets.WidgetsRoute.prototype._getRouteData = function(location) {
  if (scout.strings.empty(location)) {
    return null;
  }
  return scout.arrays.find(this.routes, function(routeData) {
    return location.indexOf(routeData[0]) > -1;
  });
};

jswidgets.WidgetsRoute.prototype._getRouteDataByObjectType = function(objectType) {
  return scout.arrays.find(this.routes, function(routeData) {
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

