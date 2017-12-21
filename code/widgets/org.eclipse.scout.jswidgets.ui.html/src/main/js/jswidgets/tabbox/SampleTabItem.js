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
jswidgets.SampleTabItem = function() {
  jswidgets.SampleTabItem.parent.call(this);
};
scout.inherits(jswidgets.SampleTabItem, scout.TabItem);

jswidgets.SampleTabItem.prototype._init = function(model) {
  jswidgets.SampleTabItem.parent.prototype._init.call(this, model);

  this.widget('CurrentTab.Label').setValue(this.label);

};

jswidgets.SampleTabItem.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.SampleTabItem');
};


