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
jswidgets.WidgetsRoute = function(desktop, includeOutlineInRef) {
  jswidgets.WidgetsRoute.parent.call(this);

  this.desktop = desktop;
  this.includeOutlineInRef = !!includeOutlineInRef;
  this.routes = scout.objects.createMap();

  this._initRoutes(desktop.outline);
  // This listener updates the URL in the browsers location field whenever a page has been activated
  desktop.outline.on('nodesSelected', this._onPageChanged.bind(this));
};
scout.inherits(jswidgets.WidgetsRoute, scout.Route);

jswidgets.WidgetsRoute.prototype._initRoutes = function(outline) {
  this._registerRoute(outline);
  scout.Tree.visitNodes(function(node) {
    this._registerRoute(node);
  }.bind(this), outline.nodes);
};

jswidgets.WidgetsRoute.prototype._getRouteRef = function(obj) {
  if (!obj) {
    return null;
  }
  return scout.objects.keyByValue(this.routes, obj);
};

jswidgets.WidgetsRoute.prototype._getOrCreateRouteRef = function(obj) {
  if (!obj) {
    return null;
  }
  return this._getRouteRef(obj) || this._registerRoute(obj);
};

jswidgets.WidgetsRoute.prototype._registerRoute = function(obj) {
  if (!obj) {
    return null;
  }
  var ref = this._computeRouteRef(obj);
  this.routes[ref] = obj;
  return ref;
};

jswidgets.WidgetsRoute.prototype._computeRouteRef = function(nodeOrOutline) {
  if (!nodeOrOutline) {
    return null;
  }

  var node = null;
  var outline = null;
  if (nodeOrOutline instanceof scout.Outline) {
    outline = nodeOrOutline;
  } else {
    node = nodeOrOutline;
    outline = node.getOutline();
  }
  var path = [];
  if (node) {
    path.push(node.text);
    var p = node.parentNode;
    while (p) {
      path.push(p.text);
      p = p.parentNode;
    }
  }
  if (outline && this.includeOutlineInRef) {
    path.push(outline.title);
  }

  return path
    .map(function(p) {
      return this._normalizePathSegment(p);
    }, this)
    .filter(function(p) {
      return !!p;
    }, this)
    .reverse()
    .join('/');
};

jswidgets.WidgetsRoute.prototype._normalizePathSegment = function(s) {
  return (s || '')
    .trim()
    .toLowerCase()
    .replace(/\s+/g, ' ')
    .replace(/ /g, '-')
    .replace(/[^a-z0-9._\-]/g, '');
};

/**
 * @override Route.js
 */
jswidgets.WidgetsRoute.prototype.matches = function(location) {
  var ref = (location || '').replace(/^#/, '');
  return !!this.routes[ref];
};

/**
 * @override Route.js
 */
jswidgets.WidgetsRoute.prototype.activate = function(location) {
  jswidgets.WidgetsRoute.parent.prototype.activate.call(this, location);

  var ref = (location || '').replace(/^#/, '');
  var obj = this.routes[ref];
  if (!obj) {
    return;
  }

  var node = null;
  var outline = null;
  if (obj instanceof scout.Outline) {
    outline = obj;
  } else {
    node = obj;
    outline = node.getOutline();
  }

  if (outline) {
    this.desktop.setOutline(outline);
      outline.selectNode(node);
      outline.revealSelection();
  }
};

jswidgets.WidgetsRoute.prototype._onPageChanged = function(event) {
  var nodeOrOutline = event.source.selectedNode() || event.source;
  var ref = this._getRouteRef(nodeOrOutline);
  if (ref !== null && ref !== undefined) {
    scout.router.updateLocation(ref, {
      replace: false
    });
  }
};
