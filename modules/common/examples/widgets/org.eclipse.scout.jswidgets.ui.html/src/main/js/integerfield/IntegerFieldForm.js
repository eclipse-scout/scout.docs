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
import {models} from '@eclipse-scout/core';
import {NumberFieldForm} from '../index';
import IntegerFieldFormModel from './IntegerFieldFormModel';

export default class IntegerFieldForm extends NumberFieldForm {

  _jsonModel() {
    return models.extend(IntegerFieldFormModel, super._jsonModel());
  }

}
