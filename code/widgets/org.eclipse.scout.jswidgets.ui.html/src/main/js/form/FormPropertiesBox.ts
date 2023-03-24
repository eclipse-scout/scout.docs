/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Form, GroupBox, GroupBoxModel, InitModelOf, models} from '@eclipse-scout/core';
import FormPropertiesBoxModel from './FormPropertiesBoxModel';
import {DisplayParentLookupCall, FormPropertiesBoxWidgetMap} from '../index';

export class FormPropertiesBox extends GroupBox {
  declare widgetMap: FormPropertiesBoxWidgetMap;

  form: Form;

  // noinspection DuplicatedCode
  constructor() {
    super();
    this.form = null;
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.get(FormPropertiesBoxModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);
    this._setForm(this.form);
  }

  setForm(form: Form) {
    this.setProperty('form', form);
  }

  // noinspection DuplicatedCode
  protected _setForm(form: Form) {
    this._setProperty('form', form);
    if (!this.form) {
      return;
    }

    let titleField = this.widget('TitleField');
    titleField.setValue(form.title);
    titleField.on('propertyChange:value', event => this.form.setTitle(event.newValue));

    let subTitleField = this.widget('SubTitleField');
    subTitleField.setValue(form.subTitle);
    subTitleField.on('propertyChange:value', event => this.form.setSubTitle(event.newValue));

    let notificationBadgeTextField = this.widget('NotificationBadgeTextField');
    notificationBadgeTextField.setValue(form.getNotificationBadgeText());
    notificationBadgeTextField.on('propertyChange:value', event => this.form.setNotificationBadgeText(event.newValue));

    let iconIdField = this.widget('IconIdField');
    iconIdField.setValue(form.iconId);
    iconIdField.on('propertyChange:value', event => this.form.setIconId(event.newValue));

    let askIfNeedSaveField = this.widget('AskIfNeedSaveField');
    askIfNeedSaveField.setValue(form.askIfNeedSave);
    askIfNeedSaveField.on('propertyChange:value', event => this.form.setAskIfNeedSave(event.newValue));

    this.widget('CacheBoundsField').setValue(form.cacheBounds);

    let closableField = this.widget('ClosableField');
    closableField.setValue(form.closable);
    closableField.on('propertyChange:value', event => this.form.setClosable(event.newValue));

    let movableField = this.widget('MovableField');
    movableField.setValue(form.movable);
    movableField.on('propertyChange:value', event => this.form.setMovable(event.newValue));

    let resizableField = this.widget('ResizableField');
    resizableField.setValue(form.resizable);
    resizableField.on('propertyChange:value', event => this.form.setResizable(event.newValue));

    let modalField = this.widget('ModalField');
    modalField.setValue(form.modal);
    modalField.on('propertyChange:value', event => this.form.setModal(event.newValue));

    let headerVisibleField = this.widget('HeaderVisibleField');
    headerVisibleField.setValue(form.headerVisible);
    headerVisibleField.on('propertyChange:value', event => this.form.setHeaderVisible(event.newValue));

    let maximizedField = this.widget('MaximizedField');
    maximizedField.setValue(form.maximized);
    maximizedField.on('propertyChange:value', event => this.form.setMaximized(event.newValue));

    let saveNeededVisibleField = this.widget('SaveNeededVisibleField');
    saveNeededVisibleField.setValue(form.saveNeededVisible);
    saveNeededVisibleField.on('propertyChange:value', event => this.form.setSaveNeededVisible(event.newValue));

    this.widget('DisplayHintField').setValue(form.displayHint);
    this.widget('DisplayViewIdField').setValue(form.displayViewId);
    this.widget('DisplayParentField').setValue(DisplayParentLookupCall.resolveDisplayParentType(form.displayParent));
  }
}
