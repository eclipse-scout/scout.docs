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
import {GroupBox, GroupBoxMenuBarPosition, StaticLookupCall} from '@eclipse-scout/core';

export class MenuBarPositionLookupCall extends StaticLookupCall<GroupBoxMenuBarPosition> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return MenuBarPositionLookupCall.DATA;
  }

  static DATA = [
    [GroupBox.MenuBarPosition.AUTO, 'Auto'],
    [GroupBox.MenuBarPosition.TOP, 'Top'],
    [GroupBox.MenuBarPosition.BOTTOM, 'Bottom'],
    [GroupBox.MenuBarPosition.TITLE, 'Title']
  ];
}
