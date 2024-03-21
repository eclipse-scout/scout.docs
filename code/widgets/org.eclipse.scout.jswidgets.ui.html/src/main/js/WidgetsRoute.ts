/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {arrays, ObjectFactory, objects, Page, Route, router, Tree, TreeNodesSelectedEvent} from '@eclipse-scout/core';
import {Desktop} from './index';

export class WidgetsRoute extends Route {
  desktop: Desktop;
  routeDatas: WidgetsRouteData[];

  constructor(desktop: Desktop) {
    super();

    this.desktop = desktop;
    this.routeDatas = this._createRouteDatas();

    // This listener updates the URL in the browsers location field whenever a page has been activated
    desktop.outline.on('nodesSelected', this._onPageChanged.bind(this));
  }

  protected _createRouteDatas(): WidgetsRouteData[] {
    let routes = [];
    Tree.visitNodes((node: Page) => {
      let ref = this._getPageRef(node);
      if (ref) {
        routes.push({
          ref: ref,
          page: node
        });
      }
    }, this.desktop.outline.nodes);
    return routes;
  }

  override matches(location: string): boolean {
    let ref = this._locationToRef(location);
    let routeData = this._getRouteDataByRef(ref);
    return !!routeData;
  }

  override activate(location: string) {
    super.activate(location);
    let ref = this._locationToRef(location);
    let routeData = this._getRouteDataByRef(ref);
    if (routeData) {
      this.desktop.outline.selectNode(routeData.page);
    }
  }

  protected _getRouteDataByRef(ref: string): WidgetsRouteData {
    return arrays.find(this.routeDatas, route => route.ref === ref);
  }

  protected _getRouteDataByPage(page: Page): WidgetsRouteData {
    return arrays.find(this.routeDatas, route => route.page === page);
  }

  protected _locationToRef(location: string): string {
    let match = /#(.+)$/.exec(location);
    return match ? match[1] : null;
  }

  protected _getPageRef(page: Page): string {
    if (typeof page['routeRef'] === 'string') {
      return page['routeRef'];
    }

    let objectType;
    if (page.detailForm) {
      objectType = page.detailForm.objectType;
    } else if (page._detailFormModel) {
      objectType = page._detailFormModel.objectType;
    } else {
      objectType = page.objectType;
    }
    if (objects.isFunction(objectType)) {
      objectType = ObjectFactory.get().getObjectType(objectType);
    }
    return this._objectTypeToRef(objectType);
  }

  protected _objectTypeToRef(objectType: string): string {
    let regex = /^jswidgets\.(\w+)(Form|PageWithTable|PageWithNodes)$/;
    let match = regex.exec(objectType);
    if (match) {
      return match[1].replace(/(.)([A-Z]+)/g, '$1-$2').toLowerCase();
    }
    return null;
  }

  protected _onPageChanged(event: TreeNodesSelectedEvent) {
    let page = event.source.selectedNode() as Page;
    if (page) {
      let routeData = this._getRouteDataByPage(page);
      if (routeData) {
        router.updateLocation(routeData.ref);
      }
    } else {
      router.updateLocation('');
    }
  }
}

export interface WidgetsRouteData {
  ref: string;
  page: Page;
}
