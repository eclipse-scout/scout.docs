/*
 * Copyright (c) 2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {icons, scout, StaticLookupCall} from '@eclipse-scout/core';

export default class RainbowLookupCall extends StaticLookupCall {

  constructor() {
    super();
  }

  _data() {
    return RainbowLookupCall.DATA;
  }

  static DATA = [
    ['FEE0E0', icons.MINUS],
    ['FFBFBF', icons.GROUP, 'Times New Roman'],
    ['FF9292', icons.PLUS, 'Courier New'],
    ['ECF3F7', icons.INFO, 'sans-serif'],
    ['C8E4F2', icons.CALENDAR, 'Open Sans'],
    ['B0DAEE', icons.FOLDER, 'Bold-Roboto'],
    ['DCFBF5', icons.WORLD, 'Impact'],
    ['BDF2E8', icons.STAR, 'Comic Sans MS'],
    ['67E9D1', icons.STAR_MARKED, '20 Arial'],
    ['3EE1C3', icons.AVG, 'Calibri-italic'],
    ['93EDDC', icons.GEAR, 'Comic Sans'],
    ['BAF3E8', icons.COLLAPSE, 'Comic Sans'],
    ['DDFFF9', icons.LONG_ARROW_DOWN],
    ['FCF0E5', icons.EXCLAMATION_MARK_CIRCLE, 'Arial-Bold-12'],
    ['FDE1B1', icons.SQUARE_BOLD, '10 Courier'],
    ['FFDB9D', icons.ANGLE_RIGHT]
  ];

  _dataToLookupRow(data, index) {
    let model = {
      key: data[0],
      text: data[0],
      backgroundColor: data[0],
      iconId: data[1]
    };
    if (data[2]) {
      model.font = data[2];
    }
    if (index % 2 === 0) {
      model.foregroundColor = 'blue';
    }
    return scout.create('LookupRow', model);
  }
}
