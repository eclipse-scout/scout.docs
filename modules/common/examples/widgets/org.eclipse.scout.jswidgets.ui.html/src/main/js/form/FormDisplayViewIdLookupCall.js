/*
 * Copyright (c) 2020 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {StaticLookupCall} from '@eclipse-scout/core';

export default class FormDisplayViewIdLookupCall extends StaticLookupCall {

  constructor() {
    super();
  }

  _data() {
    return FormDisplayViewIdLookupCall.DATA;
  }

  static DATA = [
    ['C', 'C'],
    ['N', 'N'],
    ['NE', 'NE'],
    ['E', 'E'],
    ['SE', 'SE'],
    ['S', 'S'],
    ['SW', 'SW'],
    ['W', 'W'],
    ['NW', 'NW']
  ];
}
