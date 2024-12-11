/*
 * Copyright (c) 2010, 2025 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Form, FormModel, models, scout} from '@eclipse-scout/core';
import PersonJsFormModel, {PersonJsFormWidgetMap} from './PersonJsFormModel';
import {PersonDo} from '../../index';

export class PersonJsForm extends Form {
  declare widgetMap: PersonJsFormWidgetMap;
  declare data: PersonDo;

  protected override _jsonModel(): FormModel {
    return models.get(PersonJsFormModel);
  }

  override importData() {
    if (!this.data) {
      return;
    }

    const personDo = this.data;
    this.widget('NameField').setValue(personDo.name);
    this.widget('FirstNameField').setValue(personDo.firstName);
    this.widget('DateOfBirthField').setValue(personDo.dateOfBirth);
    this.widget('ActiveField').setValue(personDo.active);
  }

  override exportData(): PersonDo {
    return scout.create(PersonDo, {
      name: this.widget('NameField').value,
      firstName: this.widget('FirstNameField').value,
      dateOfBirth: this.widget('DateOfBirthField').value,
      active: this.widget('ActiveField').value
    });
  }
}
