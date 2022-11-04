/*
 * Copyright (c) 2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {Form, models, scout, Tooltip} from '@eclipse-scout/core';
import TooltipFormModel from './TooltipFormModel';

export default class TooltipForm extends Form {

  constructor() {
    super();
    this.tooltip = null;
  }

  _jsonModel() {
    return models.get(TooltipFormModel);
  }

  _init(model) {
    super._init(model);

    let dummyTooltip = scout.create('Tooltip', {
      parent: this
    });
    this.widget('OpenTooltipButton').on('click', this._onOpenTooltipButtonClick.bind(this));

    this.widget('AutoRemoveField').setValue(dummyTooltip.autoRemove);
    this.widget('SeverityField').setValue(dummyTooltip.severity);
    this.widget('SeverityField').on('propertyChange:value', event => {
      if (this.tooltip) {
        this.tooltip.setSeverity(event.newValue);
      }
    });
    this.widget('TextField').on('propertyChange:value', event => {
      if (this.tooltip) {
        this.tooltip.setText(event.newValue);
      }
    });

    dummyTooltip.destroy();
  }

  _render() {
    super._render();
    if (this.tooltip) {
      this.tooltip.$anchor = this.widget('OpenTooltipButton').$field;
      this.tooltip.render();
      this.session.layoutValidator.schedulePostValidateFunction(() => this.tooltip.position());
    }
  }

  _onOpenTooltipButtonClick(model) {
    let $anchor = this.widget('OpenTooltipButton').$field;
    if (this.tooltip) {
      this.tooltip.destroy();
    }
    this.tooltip = scout.create(Tooltip, {
      parent: this,
      $anchor: $anchor,
      autoRemove: this.widget('AutoRemoveField').value,
      htmlEnabled: this.widget('HtmlEnabledField').value,
      text: this.widget('TextField').value,
      severity: this.widget('SeverityField').value
    });
    this.widget('EventsTab').setField(this.tooltip);
    this.widget('WidgetActionsBox').setField(this.tooltip);
    this.tooltip.render();
    this.tooltip.one('destroy', () => {
      this.tooltip = null;
    });
  }
}
