/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {AbstractLayout, graphics, HtmlComponent} from '@eclipse-scout/core';

export class HeatmapFieldLayout extends AbstractLayout {

  constructor(field) {
    super();
    this.field = field;
  }

  /**
   * @override AbstractLayout.js
   */
  layout($container) {
    let htmlContainer = HtmlComponent.get($container);

    // Because of a bug (?) in Leaflet.js, the canvas size must not get smaller than
    // 1x1 pixels, otherwise an exception is thrown: "Failed to execute 'getImageData'
    // on 'CanvasRenderingContext2D': The source width is 0."
    let size = htmlContainer.size();
    if (size.width === 0 || size.height === 0) {
      size.width = Math.max(size.width, 1);
      size.height = Math.max(size.height, 1);
      graphics.setSize($container, size);
    }

    this.field.heatmap.invalidateSize();
  }
}
