/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {arrays, BreadcrumbBarField, BreadcrumbItem, Event, EventHandler, Form, FormModel, InitModelOf, MessageBoxes, models, Status} from '@eclipse-scout/core';
import BreadcrumbBarFieldFormModel from './BreadcrumbBarFieldFormModel';
import {BreadcrumbBarFieldFormWidgetMap} from '../index';

export class BreadcrumbBarFieldForm extends Form {
  declare widgetMap: BreadcrumbBarFieldFormWidgetMap;

  breadcrumbBarField: BreadcrumbBarField;
  protected _breadcrumbActionListener: EventHandler<Event<BreadcrumbItem>>;

  constructor() {
    super();

    this.breadcrumbBarField = null;
    this._breadcrumbActionListener = this._onBreadcrumbAction.bind(this);
  }

  protected override _jsonModel(): FormModel {
    return models.get(BreadcrumbBarFieldFormModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.breadcrumbBarField = this.widget('BreadcrumbBarField');

    this.widget('FormFieldPropertiesBox').setField(this.breadcrumbBarField);
    this.widget('GridDataBox').setField(this.breadcrumbBarField);
    this.widget('WidgetActionsBox').setField(this.breadcrumbBarField);
    this.widget('EventsTab').setField(this.breadcrumbBarField);

    this.widget('BreadcrumbItemsField').on('propertyChange:value', event => {
      this.breadcrumbBarField.breadcrumbBar.breadcrumbItems.forEach(item => {
        item.off('action', this._breadcrumbActionListener);
      });

      this.breadcrumbBarField.setBreadcrumbItems(arrays.ensure(event.newValue.split('\n')).map(text => {
        return {
          objectType: BreadcrumbItem,
          text: text,
          ref: text
        };
      }));

      this.breadcrumbBarField.breadcrumbBar.breadcrumbItems.forEach(item => {
        item.on('action', this._breadcrumbActionListener);
      });
    });
    this.widget('BreadcrumbItemsField').setValue('Storyboard\nFolder\nChild Folder');
  }

  protected _onBreadcrumbAction(event: Event<BreadcrumbItem>) {
    MessageBoxes.openOk(this, this.session.text('BreadcrumbClickedX', event.source.ref), Status.Severity.INFO);
  }
}
