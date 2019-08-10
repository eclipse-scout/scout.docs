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
jswidgets.FormPropertiesBox = function() {
  jswidgets.FormPropertiesBox.parent.call(this);
  this.field = null;
  this.titleField = null;
  this.subTitleField = null;
  this.iconIdField = null;
  this.askIfNeedSaveField = null;
  this.cacheBoundsField = null;
  this.closableField = null;
  this.resizableField = null;
  this.modalField = null;
  this.displayHintField = null;
  this.displayParentField = null;
};
scout.inherits(jswidgets.FormPropertiesBox, scout.GroupBox);

jswidgets.FormPropertiesBox.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.FormPropertiesBox');
};

jswidgets.FormPropertiesBox.prototype._init = function(model) {
  jswidgets.FormPropertiesBox.parent.prototype._init.call(this, model);
  this.titleField = this.widget('titleField');
  this.subTitleField = this.widget('subTitleField');
  this.iconIdField = this.widget('iconIdField');
  this.displayHintField = this.widget('displayHintField');
  this.displayParentField = this.widget('displayParentField');
  this.askIfNeedSaveField = this.widget('askIfNeedSaveField');
  this.cacheBoundsField = this.widget('cacheBoundsField');
  this.closableField = this.widget('closableField');
  this.resizableField = this.widget('resizableField');
  this.modalField = this.widget('modalField');
  this._setField(this.field);
};

jswidgets.FormPropertiesBox.prototype.setField = function(field) {
  this.setProperty('field', field);
};

jswidgets.FormPropertiesBox.prototype._setField = function(field) {
  this._setProperty('field', field);
  if (!this.field) {
    return;
  }
  this.titleField.setValue(field.title);
  this.titleField.on('propertyChange', this._onPropertyChange.bind(this));

  this.subTitleField.setValue(field.subTitle);
  this.subTitleField.on('propertyChange', this._onPropertyChange.bind(this));

  this.iconIdField.setValue(field.iconId);
  this.iconIdField.on('propertyChange', this._onPropertyChange.bind(this));

  this.askIfNeedSaveField.setValue(field.askIfNeedSave);
  this.askIfNeedSaveField.on('propertyChange', this._onPropertyChange.bind(this));

  this.cacheBoundsField.setValue(field.cacheBounds);
  this.cacheBoundsField.on('propertyChange', this._onPropertyChange.bind(this));

  this.closableField.setValue(field.closable);
  this.closableField.on('propertyChange', this._onPropertyChange.bind(this));

  this.resizableField.setValue(field.resizable);
  this.resizableField.on('propertyChange', this._onPropertyChange.bind(this));

  this.modalField.setValue(field.modal);
  this.modalField.on('propertyChange', this._onPropertyChange.bind(this));

  this.displayHintField.setValue(field.displayHint);
  this.displayParentField.setValue(jswidgets.DisplayParentLookupCall.resolveDisplayParentType(field.displayParent));
};

jswidgets.FormPropertiesBox.prototype._onPropertyChange = function(event) {
  if (event.propertyName === 'value' && event.source.id === 'titleField') {
    this.field.setTitle(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'subTitleField') {
    this.field.setSubTitle(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'iconIdField') {
    this.field.setIconId(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'askIfNeedSaveField') {
    this.field.setAskIfNeedSave(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'cacheBoundsField') {
    this.field.setCacheBounds(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'closableField') {
    this.field.setClosable(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'resizableField') {
    this.field.setResizable(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'modalField') {
    this.field.setModal(event.newValue);
  }
};
