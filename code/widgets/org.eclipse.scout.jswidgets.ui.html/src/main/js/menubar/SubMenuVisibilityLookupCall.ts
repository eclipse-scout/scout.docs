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
import {Menu, objects, StaticLookupCall, SubMenuVisibility} from '@eclipse-scout/core';

export class SubMenuVisibilityLookupCall extends StaticLookupCall<SubMenuVisibility> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return SubMenuVisibilityLookupCall.DATA;
  }

  static DATA = objects.values(Menu.SubMenuVisibility).map(value => [value, value]);
}
