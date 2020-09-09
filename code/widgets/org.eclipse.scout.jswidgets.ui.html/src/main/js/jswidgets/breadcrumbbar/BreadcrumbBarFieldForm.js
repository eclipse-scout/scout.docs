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
jswidgets.BreadcrumbBarFieldForm = function() {
  jswidgets.BreadcrumbBarFieldForm.parent.call(this);

  this.breadcrumbBarField = null;
  this._breacrumbActionListener = this._onBreadcrumbAction.bind(this);
};
scout.inherits(jswidgets.BreadcrumbBarFieldForm, scout.Form);

jswidgets.BreadcrumbBarFieldForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.BreadcrumbBarFieldForm');
};

jswidgets.BreadcrumbBarFieldForm.prototype._onBreadcrumbAction = function(event) {
  scout.MessageBoxes.openOk(this, this.session.text('BreadcrumbClickedX', event.source.ref), scout.Status.Severity.INFO);
};

jswidgets.BreadcrumbBarFieldForm.prototype._init = function(model) {
  jswidgets.BreadcrumbBarFieldForm.parent.prototype._init.call(this, model);

  this.breadcrumbBarField = this.widget('BreadcrumbBarField');

  this.widget('FormFieldPropertiesBox').setField(this.breadcrumbBarField);
  this.widget('GridDataBox').setField(this.breadcrumbBarField);
  this.widget('WidgetActionsBox').setField(this.breadcrumbBarField);
  this.widget('EventsTab').setField(this.breadcrumbBarField);

  this.widget('BreadcrumbItemsField').on('propertyChange', this._onBreadcrumbItemsPropertyChange.bind(this));
  this.widget('BreadcrumbItemsField').setValue('Storyboard\nFolder\nChild Folder');
};

jswidgets.BreadcrumbBarFieldForm.prototype._onBreadcrumbItemsPropertyChange = function(event) {
  if (event.propertyName !== 'value') {
    return;
  }

  this.breadcrumbBarField.breadcrumbBar.breadcrumbItems.forEach(function(item) {
    item.off('action', this._breacrumbActionListener);
  }.bind(this));

  this.breadcrumbBarField.setBreadcrumbItems(scout.arrays.ensure(event.newValue.split("\n")).map(function(text) {
    return {
      objectType: 'BreadcrumbItem',
      text: text,
      ref: text
    };
  }.bind(this)));

  this.breadcrumbBarField.breadcrumbBar.breadcrumbItems.forEach(function(item) {
    item.on('action', this._breacrumbActionListener);
  }.bind(this));
};
