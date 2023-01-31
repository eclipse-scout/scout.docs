/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {App as ScoutApp, DesktopResponsiveHandler, models, ResponsiveManager, router, scout, Widget} from '@eclipse-scout/core';
import {Desktop, WidgetsRoute} from './index';
import DesktopModel from './desktop/DesktopModel';

export class App extends ScoutApp {
  scoutVersion: string;

  constructor() {
    super();
    this.scoutVersion = '23.2';
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
