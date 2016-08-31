/*******************************************************************************
 * Copyright (c) 2010-2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the BSI CRM Software License v1.0
 * which accompanies this distribution as bsi-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
scout.ExampleBeanColumn = function() {
  scout.ExampleBeanColumn.parent.call(this);
};
scout.inherits(scout.ExampleBeanColumn, scout.BeanColumn);

scout.ExampleBeanColumn.prototype._renderValue = function($cell, value) {
  $cell.appendElement('<img>')
    .attr('src', value.image)
    .addClass('example-bean-column-image');
};
