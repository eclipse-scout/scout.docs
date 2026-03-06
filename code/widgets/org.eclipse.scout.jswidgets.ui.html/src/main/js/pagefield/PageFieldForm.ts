/*
 * Copyright (c) 2010, 2026 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Form, FormModel, InitModelOf} from '@eclipse-scout/core';
import {PageFieldFormWidgetMap} from '../index';
import model from './PageFieldFormModel';

export class PageFieldForm extends Form {
  declare widgetMap: PageFieldFormWidgetMap;

  protected override _jsonModel(): FormModel {
    return model();
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    let field = this.widget('PageField');

    let innerPageField = this.widget('InnerPageField');
    innerPageField.setValue(field.page);
    innerPageField.on('propertyChange:value', () => field.setPage(innerPageField.value));
    field.on('propertyChange:page', () => innerPageField.setValue(field.page));

    this.widget('FormFieldPropertiesBox').setField(field);
    this.widget('GroupBoxPropertiesBox').setField(field);
    this.widget('GridDataBox').setField(field);
    this.widget('WidgetActionsBox').setField(field);
    this.widget('FormFieldActionsBox').setField(field);
    this.widget('EventsTab').setField(field);
  }
}
