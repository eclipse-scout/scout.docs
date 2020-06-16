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
import {GroupBox, models} from '@eclipse-scout/core';
import FormPropertiesBoxModel from './FormPropertiesBoxModel';
import {DisplayParentLookupCall} from '../index';

export default class FormPropertiesBox extends GroupBox {

  constructor() {
    super();
    this.form = null;
    this.titleField = null;
    this.subTitleField = null;
    this.iconIdField = null;
    this.askIfNeedSaveField = null;
    this.cacheBoundsField = null;
    this.closableField = null;
    this.resizableField = null;
    this.modalField = null;
    this.displayHintField = null;
    this.displayViewIdField = null;
    this.displayParentField = null;
  }

  _jsonModel() {
    return models.get(FormPropertiesBoxModel);
  }

  _init(model) {
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
    this.resizableField = this.widget('ResizableField');
    this.modalField = this.widget('ModalField');
    this.headerVisibleField = this.widget('HeaderVisibleField');
    this._setForm(this.form);
  }

  setForm(form) {
    this.setProperty('form', form);
  }

  // noinspection DuplicatedCode
  _setForm(form) {
    this._setProperty('form', form);
    if (!this.form) {
      return;
    }
    this.titleField.setValue(form.title);
    this.titleField.on('propertyChange', this._onPropertyChange.bind(this));

    this.subTitleField.setValue(form.subTitle);
    this.subTitleField.on('propertyChange', this._onPropertyChange.bind(this));

    this.iconIdField.setValue(form.iconId);
    this.iconIdField.on('propertyChange', this._onPropertyChange.bind(this));

    this.askIfNeedSaveField.setValue(form.askIfNeedSave);
    this.askIfNeedSaveField.on('propertyChange', this._onPropertyChange.bind(this));

    this.cacheBoundsField.setValue(form.cacheBounds);

    this.closableField.setValue(form.closable);
    this.closableField.on('propertyChange', this._onPropertyChange.bind(this));

    this.resizableField.setValue(form.resizable);

    this.modalField.setValue(form.modal);
    this.modalField.on('propertyChange', this._onPropertyChange.bind(this));

    this.headerVisibleField.setValue(form.headerVisible);
    this.headerVisibleField.on('propertyChange', this._onPropertyChange.bind(this));

    this.displayHintField.setValue(form.displayHint);
    this.displayViewIdField.setValue(form.displayViewId);
    this.displayParentField.setValue(DisplayParentLookupCall.resolveDisplayParentType(form.displayParent));
  }

  _onPropertyChange(event) {
    if (event.propertyName === 'value' && event.source.id === 'TitleField') {
      this.form.setTitle(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'SubTitleField') {
      this.form.setSubTitle(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'IconIdField') {
      this.form.setIconId(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'AskIfNeedSaveField') {
      this.form.setAskIfNeedSave(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'ClosableField') {
      this.form.setClosable(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'ModalField') {
      this.form.setModal(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'HeaderVisibleField') {
      this.form.setHeaderVisible(event.newValue);
    }
  }
}
