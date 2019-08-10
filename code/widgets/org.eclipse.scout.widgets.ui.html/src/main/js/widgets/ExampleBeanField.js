/*
 * Copyright (c) 2010-2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the BSI CRM Software License v1.0
 * which accompanies this distribution as bsi-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
widgets.ExampleBeanField = function() {
  widgets.ExampleBeanField.parent.call(this);
};
scout.inherits(widgets.ExampleBeanField, scout.BeanField);

widgets.ExampleBeanField.prototype._render = function() {
  widgets.ExampleBeanField.parent.prototype._render.call(this);
  this.$container.addClass('example-bean-field');
};

/**
 * @override
 */
widgets.ExampleBeanField.prototype._renderValue = function() {
  this.$field.empty();
  if (!this.value) {
    return;
  }

  var $header = this.$field.appendDiv('example-bean-field-header')
    .text(this.value.header);
  var $content = this.$field.appendDiv('example-bean-field-content');
  $content.appendElement('<p>')
    .text(this.value.description);

  $content.appendElement('<p>')
    .text(this.session.text('ExampleBeanFieldUiText'));

  $content.appendElement('<p>')
    .text(this.session.text('ExampleBeanFieldAppLinkText') + ' ')
    .appendAppLink(this.value.appLink);

  $content.appendElement('<p>')
    .appLink(this.value.appLink.ref)
    .appendElement('<img>')
    .attr('src', this.value.image)
    .addClass('example-bean-field-image');
};
