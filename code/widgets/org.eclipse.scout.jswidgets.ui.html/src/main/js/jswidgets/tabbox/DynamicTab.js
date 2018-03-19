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
jswidgets.DynamicTab = function() {
  jswidgets.DynamicTab.parent.call(this);
};
scout.inherits(jswidgets.DynamicTab, scout.TabItem);

jswidgets.DynamicTab.prototype._init = function(model) {
  jswidgets.DynamicTab.parent.prototype._init.call(this, model);

  this.widget('label').setValue('This is the content area of the \'TabItem\'. The selected tab is \'' + this.label + '\'.');
};

jswidgets.DynamicTab.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.DynamicTab');
};
