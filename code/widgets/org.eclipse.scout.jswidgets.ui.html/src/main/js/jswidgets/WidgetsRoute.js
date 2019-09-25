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
  var regex = /^jswidgets\.(\w*)(Form|PageWithTable|PageWithNodes)$/;
  var routes = [];
  scout.Tree.visitNodes(function(node) {
    var routeRef = null,
      objectType, result;
    if (node.detailForm) {
      objectType = node.detailForm.objectType;
    } else {
      objectType = node.objectType;
    }

    result = regex.exec(objectType);
    if (result !== null && result.length > 1) {
      routeRef = result[1].toLowerCase();
      routes.push([routeRef, objectType]);
    }
  }, desktop.outline.nodes);
  return routes;
};

jswidgets.WidgetsRoute.prototype.matches = function(location) {
  return !!this._getRouteData(location);
};

jswidgets.WidgetsRoute.prototype._getRouteData = function(location) {
  if (scout.strings.empty(location)) {
    return null;
  }
  return scout.arrays.find(this.routes, function(routeData) {
    return location.substring(1) === routeData[0];
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
  var foundNode = null;
  scout.Tree.visitNodes(function(node) {
    if ((node.detailForm && node.detailForm.objectType === objectType) || node.objectType === objectType) {
      foundNode = node;
      return false;
    }
  }, this.desktop.outline.nodes);
  this.desktop.outline.selectNode(foundNode);
};

jswidgets.WidgetsRoute.prototype._onPageChanged = function(event) {
  var page = event.source.selectedNode();
  if (page) {
    var objectType;
    if (page.detailForm) {
      objectType = page.detailForm.objectType;
    } else {
      objectType = page.objectType;
    }
    var routeData = this._getRouteDataByObjectType(objectType);
    if (routeData) {
      scout.router.updateLocation(routeData[0]);
    }
  } else {
    scout.router.updateLocation('');
  }
};
