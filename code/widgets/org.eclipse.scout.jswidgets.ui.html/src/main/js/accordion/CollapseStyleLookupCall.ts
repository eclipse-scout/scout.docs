/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Group, GroupCollapseStyle, StaticLookupCall} from '@eclipse-scout/core';

export class CollapseStyleLookupCall extends StaticLookupCall<GroupCollapseStyle> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return CollapseStyleLookupCall.DATA;
  }

  static DATA = [
    [Group.CollapseStyle.LEFT, 'left'],
    [Group.CollapseStyle.RIGHT, 'right'],
    [Group.CollapseStyle.BOTTOM, 'bottom']
  ];
}
