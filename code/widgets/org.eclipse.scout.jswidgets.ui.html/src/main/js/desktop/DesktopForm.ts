/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Form, FormModel, InitModelOf, models} from '@eclipse-scout/core';
import DesktopFormModel from './DesktopFormModel';
import {DesktopFormWidgetMap} from '../index';

export class DesktopForm extends Form {
  declare widgetMap: DesktopFormWidgetMap;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return models.get(DesktopFormModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    let desktop = this.session.desktop;
    let navigationVisibleField = this.widget('NavigationVisibleField');
    navigationVisibleField.setValue(desktop.navigationVisible);
    navigationVisibleField.on('propertyChange:value', event => this.session.desktop.setNavigationVisible(event.newValue));

    let headerVisibleField = this.widget('HeaderVisibleField');
    headerVisibleField.setValue(desktop.headerVisible);
    headerVisibleField.on('propertyChange:value', event => this.session.desktop.setHeaderVisible(event.newValue));

    let denseField = this.widget('DenseField');
    denseField.setValue(desktop.dense);
    denseField.on('propertyChange:value', event => this.session.desktop.setDense(event.newValue));

    let openUriButton = this.widget('OpenUriButton');
    let uriField = this.widget('UriField');
    let uriActionField = this.widget('UriActionField');
    openUriButton.on('click', event => this.session.desktop.openUri(uriField.value, uriActionField.value));

    this.widget('WidgetActionsBox').setField(desktop);
    this.widget('EventsTab').setField(desktop);
  }
}
