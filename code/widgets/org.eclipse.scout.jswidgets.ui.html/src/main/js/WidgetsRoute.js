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
import {arrays, Route, router, strings, Tree} from '@eclipse-scout/core';

export default class WidgetsRoute extends Route {

  constructor(desktop) {
    super();

    this.desktop = desktop;
    this.routes = this._createRoutes(desktop);

    // This listener updates the URL in the browsers location field whenever a page has been activated
    desktop.outline.on('nodesSelected', this._onPageChanged.bind(this));
  }

  /**
   * Creates an array which maps a routeRef to an objectType.
   * 0: routeRef
   * 1: objectType of the detail form of a node in FormFieldOutline
   */
  _createRoutes(desktop) {
    let regex = /^jswidgets\.(\w*)(Form|PageWithTable|PageWithNodes)$/;
    let routes = [];
    Tree.visitNodes(node => {
      let routeRef = null,
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
  }

  matches(location) {
    return !!this._getRouteData(location);
  }

  _getRouteData(location) {
    if (strings.empty(location)) {
      return null;
    }
    return arrays.find(this.routes, routeData => {
      return location.substring(1) === routeData[0];
    });
  }

  _getRouteDataByObjectType(objectType) {
    return arrays.find(this.routes, routeData => {
      return routeData[1] === objectType;
    });
  }

  activate(location) {
    super.activate(location);

    let objectType = this._getRouteData(location)[1];
    let foundNode = null;
    Tree.visitNodes(node => {
      if ((node.detailForm && node.detailForm.objectType === objectType) || node.objectType === objectType) {
        foundNode = node;
        return false;
      }
    }, this.desktop.outline.nodes);
    this.desktop.outline.selectNode(foundNode);
  }

  _onPageChanged(event) {
    let page = event.source.selectedNode();
    if (page) {
      let objectType;
      if (page.detailForm) {
        objectType = page.detailForm.objectType;
      } else {
        objectType = page.objectType;
      }
      let routeData = this._getRouteDataByObjectType(objectType);
      if (routeData) {
        router.updateLocation(routeData[0]);
      }
    } else {
      router.updateLocation('');
    }
  }
}
