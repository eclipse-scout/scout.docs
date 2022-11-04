/*
 * Copyright (c) 2022 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {arrays, Form, MessageBoxes, models, Status} from '@eclipse-scout/core';
import BreadcrumbBarFieldFormModel from './BreadcrumbBarFieldFormModel';

export default class BreadcrumbBarFieldForm extends Form {

  constructor() {
    super();

    this.breadcrumbBarField = null;
    this._breacrumbActionListener = this._onBreadcrumbAction.bind(this);
  }

  _jsonModel() {
    return models.get(BreadcrumbBarFieldFormModel);
  }

  _init(model) {
    super._init(model);

    this.breadcrumbBarField = this.widget('BreadcrumbBarField');

    this.widget('FormFieldPropertiesBox').setField(this.breadcrumbBarField);
    this.widget('GridDataBox').setField(this.breadcrumbBarField);
    this.widget('WidgetActionsBox').setField(this.breadcrumbBarField);
    this.widget('EventsTab').setField(this.breadcrumbBarField);

    this.widget('BreadcrumbItemsField').on('propertyChange:value', event => {
      this.breadcrumbBarField.breadcrumbBar.breadcrumbItems.forEach(item => {
        item.off('action', this._breacrumbActionListener);
      });

      this.breadcrumbBarField.setBreadcrumbItems(arrays.ensure(event.newValue.split('\n')).map(text => {
        return {
          objectType: 'BreadcrumbItem',
          text: text,
          ref: text
        };
      }));

      this.breadcrumbBarField.breadcrumbBar.breadcrumbItems.forEach(item => {
        item.on('action', this._breacrumbActionListener);
      });
    });
    this.widget('BreadcrumbItemsField').setValue('Storyboard\nFolder\nChild Folder');
  }

  _onBreadcrumbAction(event) {
    MessageBoxes.openOk(this, this.session.text('BreadcrumbClickedX', event.source.ref), Status.Severity.INFO);
  }
}
