/*******************************************************************************
 * Copyright (c) 2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
jswidgets.FormForm = function() {
  jswidgets.FormForm.parent.call(this);
  this.openedByButton = false;
  this.LifecycleData = {};
};
scout.inherits(jswidgets.FormForm, scout.Form);

jswidgets.FormForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.FormForm');
};

jswidgets.FormForm.prototype._init = function(model) {
  jswidgets.FormForm.parent.prototype._init.call(this, model);

  this.widget('OpenFormButton').on('click', this._onOpenFormButtonClick.bind(this));
  this.widget('OpenLifecycleFormButton').on('click', this._onOpenLifecycleFormButtonClick.bind(this));

  var titleField = this.widget('TitleField');
  titleField.setValue(this.openedByButton ? this.title : 'Title');
  titleField.on('propertyChange', this._onTitleChange.bind(this));

  var subTitleField = this.widget('SubTitleField');
  subTitleField.setValue(this.subTitle);
  subTitleField.on('propertyChange', this._onSubTitleChange.bind(this));

  var iconIdField = this.widget('IconIdField');
  iconIdField.setValue(this.iconId);
  iconIdField.on('propertyChange', this._onIconIdChange.bind(this));

  var statusField = this.widget('StatusField');
  statusField.setValue(this.status ? this.status.severity: null);
  statusField.on('propertyChange', this._onStatusChange.bind(this));

  var askIfNeedSaveField = this.widget('AskIfNeedSaveField');
  askIfNeedSaveField.setValue(this.askIfNeedSave);
  askIfNeedSaveField.on('propertyChange', this._onAskIfNeedSaveChange.bind(this));

  var cacheBoundsField = this.widget('CacheBoundsField');
  cacheBoundsField.setValue(this.cacheBounds);

  var closableField = this.widget('ClosableField');
  closableField.setValue(this.closable);
  if (this.openedByButton) {
    closableField.on('propertyChange', this._onClosableChange.bind(this));
  }

  var resizableField = this.widget('ResizableField');
  resizableField.setValue(this.resizable);

  var modalField = this.widget('ModalField');
  modalField.setValue(this.modal);
  if (this.openedByButton) {
    modalField.on('propertyChange', this._onModalChange.bind(this));
  }

  var displayHintField = this.widget('DisplayHintField');
  displayHintField.setValue(this.openedByButton ? this.displayHint : scout.Form.DisplayHint.DIALOG);

  var displayParentField = this.widget('DisplayParentField');
  displayParentField.setValue(this.openedByButton ? jswidgets.DisplayParentLookupCall.resolveDisplayParentType(this.displayParent) : null);

  if (this.openedByButton) {
    this.widget('EventsTab').setField(this);
    this.widget('WidgetActionsBox').setField(this);
  }
};

jswidgets.FormForm.prototype._onOpenFormButtonClick = function(model) {
  var form = scout.create('jswidgets.FormForm', {
    parent: this,
    title: this.widget('TitleField').value,
    subTitle: this.widget('SubTitleField').value,
    iconId: this.widget('IconIdField').value,
    displayHint: this.widget('DisplayHintField').value,
    displayParent: jswidgets.DisplayParentLookupCall.displayParentForType(this, this.widget('DisplayParentField').value),
    modal: this.widget('ModalField').value,
    askIfNeedSave: this.widget('AskIfNeedSaveField').value,
    cacheBounds: this.widget('CacheBoundsField').value,
    closable: this.widget('ClosableField').value,
    resizable: this.widget('ResizableField').value,
    status: this._createStatus(this.widget('StatusField').value),
    openedByButton: true
  });
  this.widget('EventsTab').setField(form);
  this.widget('WidgetActionsBox').setField(form);
  form.open();
};

jswidgets.FormForm.prototype._onOpenLifecycleFormButtonClick = function(model) {
  var form = scout.create('jswidgets.LifecycleForm', {
    parent: this,
    title: this.widget('TitleField').value,
    subTitle: this.widget('SubTitleField').value,
    iconId: this.widget('IconIdField').value,
    displayHint: this.widget('DisplayHintField').value,
    displayParent: jswidgets.DisplayParentLookupCall.displayParentForType(this, this.widget('DisplayParentField').value),
    modal: this.widget('ModalField').value,
    closable: this.widget('ClosableField').value,
    resizable: this.widget('ResizableField').value,
    status: this._createStatus(this.widget('StatusField').value),
    data: this.LifecycleData
  });
  this.widget('EventsTab').setField(form);

  var lifecycleDataField = this.widget('LifecycleDataField');
  form.on('load', function(event) {
    var data = form.data;
    var text = 'Form loaded (' + this.lifecycleDataToString(data) + ')';
    lifecycleDataField.setValue(text);
  }.bind(this));
  form.on('save', function(event) {
    var data = form.data;
    var text = lifecycleDataField.value;
    text += '\n' + 'Form saved (' + this.lifecycleDataToString(data) + ')';
    lifecycleDataField.setValue(text);
  }.bind(this));
  form.on('reset', function(event) {
    var data = form.data;
    var text = lifecycleDataField.value;
    text += '\n' + 'Form reset (' + this.lifecycleDataToString(data) + ')';
    lifecycleDataField.setValue(text);
  }.bind(this));
  form.on('close', function(event) {
    var data = form.data;
    var text = lifecycleDataField.value;
    text += '\n' + 'Form closed (' + this.lifecycleDataToString(data) + ')';
    lifecycleDataField.setValue(text);
    this.lifecycleData = data;
  }.bind(this));

  lifecycleDataField.setVisible(true);
  form.open();
};

jswidgets.FormForm.prototype.lifecycleDataToString = function(data) {
  return 'Name: ' + data.name + ', Birthday: ' + data.birthday;
};

jswidgets.FormForm.prototype._onTitleChange = function(event) {
  if (event.propertyName === 'value') {
    this.setTitle(event.newValue);
  }
};

jswidgets.FormForm.prototype._onSubTitleChange = function(event) {
  if (event.propertyName === 'value') {
    this.setSubTitle(event.newValue);
  }
};

jswidgets.FormForm.prototype._onIconIdChange = function(event) {
  if (event.propertyName === 'value') {
    this.setIconId(event.newValue);
  }
};

jswidgets.FormForm.prototype._onStatusChange = function(event) {
  if (event.propertyName === 'value') {
    this.setStatus(this._createStatus(event.newValue));
  }
};

jswidgets.FormForm.prototype._onAskIfNeedSaveChange = function(event) {
  if (event.propertyName === 'value') {
    this.setAskIfNeedSave(event.newValue);
  }
};

jswidgets.FormForm.prototype._onClosableChange = function(event) {
  if (event.propertyName === 'value') {
    this.setClosable(event.newValue);
  }
};

jswidgets.FormForm.prototype._onModalChange = function(event) {
  if (event.propertyName === 'value') {
    this.setModal(event.newValue);
  }
};

jswidgets.FormForm.prototype._createStatus = function(severity) {
  if (!severity ) {
    return null;
  }
  return scout.create('Status', {
    parent: this,
    severity: severity,
    message: this.session.text('StatusMessage', scout.objects.keyByValue(scout.Status.Severity, severity))
  });
};
