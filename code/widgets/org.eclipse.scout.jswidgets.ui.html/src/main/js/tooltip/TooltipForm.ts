/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Form, FormModel, InitModelOf, models, scout, Tooltip} from '@eclipse-scout/core';
import TooltipFormModel from './TooltipFormModel';
import {TooltipFormWidgetMap} from '../index';

export class TooltipForm extends Form {
  declare widgetMap: TooltipFormWidgetMap;

  tooltip: Tooltip;

  constructor() {
    super();
    this.tooltip = null;
  }

  protected override _jsonModel(): FormModel {
    return models.get(TooltipFormModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    let dummyTooltip = scout.create(Tooltip, {
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

  protected override _render() {
    super._render();
    if (this.tooltip) {
      this.tooltip.$anchor = this.widget('OpenTooltipButton').$field;
      this.tooltip.render();
      this.session.layoutValidator.schedulePostValidateFunction(() => this.tooltip.position());
    }
  }

  protected _onOpenTooltipButtonClick() {
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
