/*
 * Copyright (c) 2019 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import { DummyApp } from '@eclipse-scout/core';
import * as $ from 'jquery';

export default class WidgetsDummyApp extends DummyApp {
  constructor() {
    super();
    console.log('Constructing app...'); // jshint ignore:line
    $('<div>Hello World</div>').appendTo($('body'));
  }

  init() {
    console.log('Initializing app...'); // jshint ignore:line
  }
}
