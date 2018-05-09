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
jswidgets.FileChooserForm = function() {
  jswidgets.FileChooserForm.parent.call(this);
};
scout.inherits(jswidgets.FileChooserForm, scout.Form);

jswidgets.FileChooserForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.FileChooserForm');
};

jswidgets.FileChooserForm.prototype._init = function(model) {
  jswidgets.FileChooserForm.parent.prototype._init.call(this, model);

  var button = this.widget('Button');
  button.on('click', this._onButtonClick.bind(this));
  this.widget('MaximumUploadSizeField').setValue(scout.FileInput.DEFAULT_MAXIMUM_UPLOAD_SIZE);
  this.widget('ChosenFilesField').on('appLinkAction', this._onChosenFilesAppLinkAction.bind(this));
  this._updateChosenFiles([]);
};

jswidgets.FileChooserForm.prototype._onButtonClick = function(event) {

  var fileChooser = scout.create('FileChooser', {
    parent: this.session.desktop,
    acceptTypes: this.widget('AcceptTypesField').value,
    maximumUploadSize: this.widget('MaximumUploadSizeField').value,
    multiSelect: this.widget('MultiSelectField').value
  });
  fileChooser.open();
  fileChooser.on('upload', function() {
    this._updateChosenFiles(fileChooser.files);
    fileChooser.close();
  }.bind(this));
};

jswidgets.FileChooserForm.prototype._updateChosenFiles = function(files) {
  var chosenFilesText = '';
  if (files.length === 0) {
    chosenFilesText = this.session.text('FileChooserNoFilesChosen');
  } else if (files.length == 1) {
    chosenFilesText = this.session.text('FileChooserOneFileChosen');
  } else {
    chosenFilesText = this.session.text('FileChooserNFilesChosen', files.length);
  }

  var fileDescriptions = [];
  for (var i = 0; i < files.length; i++) {
    var file = files[i];
    var html = '<span class="app-link unfocusable" data-ref="' + i + '">' + scout.strings.encode(file.name) + ' (' + file.size + ' bytes)</span>';
    fileDescriptions.push(html);
  }

  var chosenFilesField = this.widget('ChosenFilesField');
  chosenFilesField.files = files; // remember files to handle app link action
  chosenFilesField.setValue(chosenFilesText + ' ' + fileDescriptions.join(', '));
};

jswidgets.FileChooserForm.prototype._onChosenFilesAppLinkAction = function(event) {
  var file = this.widget('ChosenFilesField').files[event.ref];
  var url = URL.createObjectURL(file);
  this.session.desktop.openUri(url);
};
