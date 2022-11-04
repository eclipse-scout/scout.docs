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
import {NullLayout} from '@eclipse-scout/core';
import {WatchField} from './WatchField';

export class WatchFieldLayout extends NullLayout {
  watchField: WatchField;

  constructor(watchField: WatchField) {
    super();
    this.watchField = watchField;
  }

  override layout($container: JQuery) {
    let containerHeight = $container.height(),
      containerWidth = $container.width(),
      size = Math.min(containerHeight, containerWidth),
      $canvas = this.watchField.$canvas;
    // set width and height
    $canvas.prop('width', size);
    $canvas.prop('height', size);
    // set css size and position
    $canvas
      .cssTop((containerHeight - size) / 2)
      .cssRight((containerWidth - size) / 2)
      .cssHeight(size)
      .cssWidth(size);

    this.watchField._paintWatch();
    super.layout($container);
  }
}
