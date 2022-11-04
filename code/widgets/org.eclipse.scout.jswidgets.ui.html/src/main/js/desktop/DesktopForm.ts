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
import {Form, models} from '@eclipse-scout/core';
import DesktopFormModel from './DesktopFormModel';

export default class DesktopForm extends Form {

  constructor() {
    super();
  }

  _jsonModel() {
    return models.get(DesktopFormModel);
  }

  _init(model) {
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
