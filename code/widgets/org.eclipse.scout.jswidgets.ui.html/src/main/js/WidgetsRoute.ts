/*
 * Copyright (c) 2022 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {arrays, ObjectFactory, objects, Page, Route, router, strings, Tree, TreeNodesSelectedEvent} from '@eclipse-scout/core';
import {Desktop} from './index';

export class WidgetsRoute extends Route {
  desktop: Desktop;
  routes: [string, string][];

  constructor(desktop: Desktop) {
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
  protected _createRoutes(desktop: Desktop): [string, string][] {
    let regex = /^jswidgets\.(\w*)(Form|PageWithTable|PageWithNodes)$/;
    let routes = [];
    Tree.visitNodes((node: Page) => {
      let routeRef = null;
      let objectType = this._objectTypeForNode(node);

      let result = regex.exec(objectType);
      if (result !== null && result.length > 1) {
        routeRef = result[1].toLowerCase();
        routes.push([routeRef, objectType]);
      }
    }, desktop.outline.nodes);
    return routes;
  }

  override matches(location: string): boolean {
    return !!this._getRouteData(location);
  }

  protected _getRouteData(location: string): [string, string] {
    if (strings.empty(location)) {
      return null;
    }
    return arrays.find(this.routes, routeData => {
      return location.substring(1) === routeData[0];
    });
  }

  protected _getRouteDataByObjectType(objectType: string): [string, string] {
    return arrays.find(this.routes, routeData => {
      return routeData[1] === objectType;
    });
  }

  override activate(location: string) {
    super.activate(location);

    let objectType = this._getRouteData(location)[1];
    let foundNode = null;
    Tree.visitNodes((node: Page) => {
      if (this._objectTypeForNode(node) === objectType) {
        foundNode = node;
        return false;
      }
    }, this.desktop.outline.nodes);
    this.desktop.outline.selectNode(foundNode);
  }

  protected _objectTypeForNode(node: Page): string {
    let objectType;
    if (node.detailForm) {
      objectType = node.detailForm.objectType;
    } else if (node._detailFormModel) {
      objectType = node._detailFormModel.objectType;
    } else {
      objectType = node.objectType;
    }

    if (objects.isFunction(objectType)) {
      objectType = ObjectFactory.get().getObjectType(objectType);
    }

    return objectType;
  }

  protected _onPageChanged(event: TreeNodesSelectedEvent) {
    let page = event.source.selectedNode() as Page;
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
