/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {FormModel, GroupBox} from '@eclipse-scout/core';

export default (): FormModel => ({
  displayHint: 'view',
  title: 'All Combinations',
  rootGroupBox: {
    id: 'MainBox',
    objectType: GroupBox,
    fields: [
      {
        id: 'AllCombinationsBox',
        objectType: GroupBox,
        gridColumnCount: 8,
        bodyLayoutConfig: {
          hgap: 0
        }
      }
    ]
  }
})
;

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export type SwitchAllCombinationsJsFormWidgetMap = {
  'MainBox': GroupBox;
  'AllCombinationsBox': GroupBox;
};
