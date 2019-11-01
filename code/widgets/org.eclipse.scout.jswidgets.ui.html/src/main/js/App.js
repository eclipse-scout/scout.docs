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
import {App as App_1, ResponsiveManager, models, router, scout} from '@eclipse-scout/core';
import {WidgetsRoute} from './index';
import DesktopModel from './desktop/DesktopModel';

export default class App extends App_1 {

  constructor() {
    super();
    this.scoutVersion = '10.0';
  }


  _createDesktop(parent) {
    var desktop = scout.create('Desktop',
      models.get(DesktopModel, parent));

    router.register(new WidgetsRoute(desktop));
    router.activate();

    ResponsiveManager.get().registerHandler(desktop, new scout.create('DesktopResponsiveHandler', {
      widget: desktop
    }));

    return desktop;
  }
}
