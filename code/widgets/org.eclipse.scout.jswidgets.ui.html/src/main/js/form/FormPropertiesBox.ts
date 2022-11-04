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
import {CheckBoxField, DisplayHint, DisplayViewId, Form, GroupBox, GroupBoxModel, InitModelOf, models, SmartField, StringField} from '@eclipse-scout/core';
import FormPropertiesBoxModel from './FormPropertiesBoxModel';
import {DisplayParentLookupCall, FormPropertiesBoxWidgetMap} from '../index';

export class FormPropertiesBox extends GroupBox {
  declare widgetMap: FormPropertiesBoxWidgetMap;

  form: Form;
  titleField: StringField;
  subTitleField: StringField;
  iconIdField: SmartField<string>;
  askIfNeedSaveField: CheckBoxField;
  cacheBoundsField: CheckBoxField;
  closableField: CheckBoxField;
  movableField: CheckBoxField;
  resizableField: CheckBoxField;
  modalField: CheckBoxField;
  displayHintField: SmartField<DisplayHint>;
  displayViewIdField: SmartField<DisplayViewId>;
  displayParentField: SmartField<string>;
  headerVisibleField: CheckBoxField;
  maximizedField: CheckBoxField;

  // noinspection DuplicatedCode
  constructor() {
    super();
    this.form = null;
    this.titleField = null;
    this.subTitleField = null;
    this.iconIdField = null;
    this.askIfNeedSaveField = null;
    this.cacheBoundsField = null;
    this.closableField = null;
    this.movableField = null;
    this.resizableField = null;
    this.modalField = null;
    this.displayHintField = null;
    this.displayViewIdField = null;
    this.displayParentField = null;
    this.headerVisibleField = null;
    this.maximizedField = null;
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.get(FormPropertiesBoxModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);
    this.titleField = this.widget('TitleField');
    this.subTitleField = this.widget('SubTitleField');
    this.iconIdField = this.widget('IconIdField');
    this.displayHintField = this.widget('DisplayHintField');
    this.displayViewIdField = this.widget('DisplayViewIdField');
    this.displayParentField = this.widget('DisplayParentField');
    this.askIfNeedSaveField = this.widget('AskIfNeedSaveField');
    this.cacheBoundsField = this.widget('CacheBoundsField');
    this.closableField = this.widget('ClosableField');
    this.movableField = this.widget('MovableField');
    this.resizableField = this.widget('ResizableField');
    this.modalField = this.widget('ModalField');
    this.headerVisibleField = this.widget('HeaderVisibleField');
    this.maximizedField = this.widget('MaximizedField');
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
    this.titleField.setValue(form.title);
    this.titleField.on('propertyChange:value', event => this.form.setTitle(event.newValue));

    this.subTitleField.setValue(form.subTitle);
    this.subTitleField.on('propertyChange:value', event => this.form.setSubTitle(event.newValue));

    this.iconIdField.setValue(form.iconId);
    this.iconIdField.on('propertyChange:value', event => this.form.setIconId(event.newValue));

    this.askIfNeedSaveField.setValue(form.askIfNeedSave);
    this.askIfNeedSaveField.on('propertyChange:value', event => this.form.setAskIfNeedSave(event.newValue));

    this.cacheBoundsField.setValue(form.cacheBounds);

    this.closableField.setValue(form.closable);
    this.closableField.on('propertyChange:value', event => this.form.setClosable(event.newValue));

    this.movableField.setValue(form.movable);
    this.movableField.on('propertyChange:value', event => this.form.setMovable(event.newValue));

    this.resizableField.setValue(form.resizable);
    this.resizableField.on('propertyChange:value', event => this.form.setResizable(event.newValue));

    this.modalField.setValue(form.modal);
    this.modalField.on('propertyChange:value', event => this.form.setModal(event.newValue));

    this.headerVisibleField.setValue(form.headerVisible);
    this.headerVisibleField.on('propertyChange:value', event => this.form.setHeaderVisible(event.newValue));

    this.maximizedField.setValue(form.maximized);
    this.maximizedField.on('propertyChange:value', event => this.form.setMaximized(event.newValue));

    this.displayHintField.setValue(form.displayHint);
    this.displayViewIdField.setValue(form.displayViewId);
    this.displayParentField.setValue(DisplayParentLookupCall.resolveDisplayParentType(form.displayParent));
  }
}
