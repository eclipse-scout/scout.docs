/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

import {ValueFieldPropertiesBox, ValueFieldPropertiesBoxWidgetMap} from '../index';
import {scout} from '@eclipse-scout/core';

export class TreeBoxValueFieldPropertiesBox extends ValueFieldPropertiesBox {
  declare widgetMap: ValueFieldPropertiesBoxWidgetMap; // Necessary to make widget map generator happy

  override parseValue(newValue: string): any {
    return scout.nvl(newValue, '').split(',');
  }
}
