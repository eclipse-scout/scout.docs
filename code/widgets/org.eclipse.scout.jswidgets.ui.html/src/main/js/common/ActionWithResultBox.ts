/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Button, InitModelOf, LabelField, SequenceBox, SequenceBoxModel, WidgetModel} from '@eclipse-scout/core';
import model, {ActionWithResultBoxWidgetMap} from './ActionWithResultBoxModel';

export class ActionWithResultBox extends SequenceBox implements ActionWithResultBoxModel {
  declare model: ActionWithResultBoxModel;
  declare widgetMap: ActionWithResultBoxWidgetMap;

  declare actionLabel;
  declare actionTooltipText;

  protected override _jsonModel(): WidgetModel {
    return model();
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);
    this.resolveTextKeys(['actionLabel', 'actionTooltipText']);
    this.widget('ActionButton').setLabel(this.actionLabel);
    this.widget('ActionButton').setTooltipText(this.actionTooltipText);
  }

  get actionButton(): Button {
    return this.widget('ActionButton');
  }

  get resultField(): LabelField {
    return this.widget('ResultField');
  }

  updateBooleanReturnValue(returned: boolean) {
    if (returned) {
      this.resultField.setValue('-> returned true');
    } else {
      this.resultField.setValue('-> returned false');
    }
    this.resultField.toggleCssClass('action-return-success', returned);
    this.resultField.toggleCssClass('action-return-fail', !returned);
  }
}

export interface ActionWithResultBoxModel extends SequenceBoxModel {
  actionLabel?: string;
  actionTooltipText?: string;
}
