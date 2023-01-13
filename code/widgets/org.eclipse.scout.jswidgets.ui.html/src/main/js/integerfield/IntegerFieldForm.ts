/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {FormModel, models} from '@eclipse-scout/core';
import {NumberFieldForm} from '../index';
import IntegerFieldFormModel from './IntegerFieldFormModel';

export class IntegerFieldForm extends NumberFieldForm {

  protected override _jsonModel(): FormModel {
    return models.extend(IntegerFieldFormModel, super._jsonModel());
  }
}
