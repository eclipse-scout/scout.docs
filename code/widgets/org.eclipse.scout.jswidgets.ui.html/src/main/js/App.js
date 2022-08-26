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
import {App as ScoutApp, models, ResponsiveManager, router, scout} from '@eclipse-scout/core';
import {WidgetsRoute} from './index';
import DesktopModel from './desktop/DesktopModel';

export default class App extends ScoutApp {

  constructor() {
    super();
    this.scoutVersion = '23.1';
  }

  _createDesktop(parent) {
    let desktop = scout.create('Desktop',
      models.get(DesktopModel, parent));

    router.register(new WidgetsRoute(desktop));
    router.activate();

    ResponsiveManager.get().registerHandler(desktop, scout.create('DesktopResponsiveHandler', {
      widget: desktop
    }));

    return desktop;
  }
}
