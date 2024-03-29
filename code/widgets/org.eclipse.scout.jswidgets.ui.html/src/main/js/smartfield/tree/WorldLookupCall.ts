/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {icons, LookupRow, StaticLookupCall} from '@eclipse-scout/core';

export class WorldLookupCall extends StaticLookupCall<string> {

  constructor() {
    super();

    this.setLoadIncremental(false);
    this.setHierarchical(true);
  }

  protected override _data(): any[] {
    return WorldLookupCall.DATA;
  }

  protected override _dataToLookupRow(data: any[]): LookupRow<string> {
    let lookupRow = super._dataToLookupRow(data);
    if (lookupRow.parentKey) {
      lookupRow.iconId = icons.WORLD;
    } else {
      lookupRow.iconId = icons.FOLDER;
    }
    return lookupRow;
  }

  // 0: key
  // 1: text
  // 2: [parentKey]
  static DATA = [
    ['AF', 'Africa', null],
    ['EAF', 'Eastern Africa', 'AF'],
    ['MAF', 'Middle Africa', 'AF'],
    ['NAF', 'Northern Africa', 'AF'],
    ['SAF', 'Southern Africa', 'AF'],
    ['WAF', 'Western Africa', 'AF'],
    ['AM', 'Americas', null],
    ['LAM', 'Latin America', 'AM'],
    ['SAM', 'South America', 'LAM'],
    ['CARAM', 'Caribbean', 'LAM'],
    ['CAM', 'Central America', 'LAM'],
    ['NAM', 'Northern America', 'AM'],
    ['AN', 'Antarctica', null],
    ['AS', 'Asia', null],
    ['CAS', 'Central Asia', 'AS'],
    ['EAS', 'Eastern Asia', 'AS'],
    ['SAS', 'Southern Asia', 'AS'],
    ['SEAS', 'South-Eastern Asia', 'AS'],
    ['WAS', 'Western Asia', 'AS'],
    ['ER', 'Europe', null],
    ['EER', 'Eastern Europe', 'ER'],
    ['NER', 'Northern Europe', 'ER'],
    ['SER', 'Southern Europe', 'ER'],
    ['WER', 'Western Europe', 'ER'],
    ['OC', 'Oceania', null]
  ];
}
