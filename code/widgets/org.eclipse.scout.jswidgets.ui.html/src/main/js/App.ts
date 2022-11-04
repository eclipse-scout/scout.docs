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
import {App as ScoutApp, DesktopResponsiveHandler, models, ResponsiveManager, router, scout, Widget} from '@eclipse-scout/core';
import {Desktop, WidgetsRoute} from './index';
import DesktopModel from './desktop/DesktopModel';

export class App extends ScoutApp {
  scoutVersion: string;

  constructor() {
    super();
    this.scoutVersion = '23.1';
  }

  // @ts-expect-error
  static get(): App {
    return ScoutApp.get() as App;
  }

  protected override _createDesktop(parent: Widget): Desktop {
    let desktop = scout.create(Desktop,
      models.get(DesktopModel, parent));

    router.register(new WidgetsRoute(desktop));
    router.activate();

    ResponsiveManager.get().registerHandler(desktop, scout.create(DesktopResponsiveHandler, {
      widget: desktop
    }));

    return desktop;
  }
}
