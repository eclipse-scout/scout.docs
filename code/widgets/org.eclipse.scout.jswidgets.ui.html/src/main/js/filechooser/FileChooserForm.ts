/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import FileChooserFormModel from './FileChooserFormModel';
import {AppLinkActionEvent, Button, Event, FileChooser, FileInput, Form, FormModel, HtmlField, InitModelOf, models, scout, strings} from '@eclipse-scout/core';
import {DisplayParentLookupCall, FileChooserFormWidgetMap} from '../index';

export class FileChooserForm extends Form {
  declare widgetMap: FileChooserFormWidgetMap;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return models.get(FileChooserFormModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    let button = this.widget('Button');
    button.on('click', this._onButtonClick.bind(this));
    this.widget('MaximumUploadSizeField').setValue(FileInput.DEFAULT_MAXIMUM_UPLOAD_SIZE);
    this.widget('ChosenFilesField').on('appLinkAction', this._onChosenFilesAppLinkAction.bind(this));
    this._updateChosenFiles([]);
  }

  protected _onButtonClick(event: Event<Button>) {
    let fileChooser = scout.create(FileChooser, {
      parent: this.session.desktop,
      acceptTypes: this.widget('AcceptTypesField').value,
      displayParent: DisplayParentLookupCall.displayParentForType(this, this.widget('DisplayParentField').value),
      maximumUploadSize: this.widget('MaximumUploadSizeField').value,
      multiSelect: this.widget('MultiSelectField').value
    });
    this.widget('EventsTab').setField(fileChooser);
    fileChooser.open();
    fileChooser.on('upload', () => {
      this._updateChosenFiles(fileChooser.files);
      fileChooser.close();
    });
  }

  protected _updateChosenFiles(files: File[]) {
    let chosenFilesText = '';
    if (files.length === 0) {
      chosenFilesText = this.session.text('FileChooserNoFilesChosen');
    } else if (files.length === 1) {
      chosenFilesText = this.session.text('FileChooserOneFileChosen');
    } else {
      chosenFilesText = this.session.text('FileChooserNFilesChosen', files.length);
    }

    let fileDescriptions = [];
    for (let i = 0; i < files.length; i++) {
      let file = files[i];
      let html = '<span class="app-link unfocusable" data-ref="' + i + '">' + strings.encode(file.name) + ' (' + file.size + ' bytes)</span>';
      fileDescriptions.push(html);
    }

    let chosenFilesField: HtmlFieldWithFiles = this.widget('ChosenFilesField');
    chosenFilesField.files = files; // remember files to handle app link action
    chosenFilesField.setValue(chosenFilesText + ' ' + fileDescriptions.join(', '));
  }

  protected _onChosenFilesAppLinkAction(event: AppLinkActionEvent) {
    let chosenFilesField: HtmlFieldWithFiles = this.widget('ChosenFilesField');
    let file = chosenFilesField.files[event.ref];
    let url = URL.createObjectURL(file);
    this.session.desktop.openUri(url);
  }
}

type HtmlFieldWithFiles = HtmlField & {
  files?: File[];
};
