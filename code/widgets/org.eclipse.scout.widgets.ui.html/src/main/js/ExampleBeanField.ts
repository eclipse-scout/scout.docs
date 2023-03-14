/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {BeanField} from '@eclipse-scout/core';
import {ExampleBean} from './index';

export class ExampleBeanField extends BeanField<ExampleBean> {

  protected override _render() {
    super._render();
    this.$container.addClass('example-bean-field');
  }

  protected override _renderValue() {
    this.$field.empty();
    if (!this.value) {
      return;
    }

    this.$field.appendDiv('example-bean-field-header')
      .text(this.value.header);
    let $content = this.$field.appendDiv('example-bean-field-content');
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
  }
}
