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
jswidgets.SampleDetailForm = function() {
  jswidgets.SampleDetailForm.parent.call(this);

  this.page = null;
};
scout.inherits(jswidgets.SampleDetailForm, scout.Form);

jswidgets.SampleDetailForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.SampleDetailForm');
};

jswidgets.SampleDetailForm.prototype._init = function(model) {
  jswidgets.SampleDetailForm.parent.prototype._init.call(this, model);
  var s = '';
  if (this.page.row) {
    s += '<ul>';
    for (var prop in this.page.row.data) {
      if (this.page.row.data.hasOwnProperty(prop)) {
        s += '<li><b>' + scout.strings.encode(prop) + '</b>: ' + scout.strings.encode(this.page.row.data[prop]) + '</li>';
      }
    }
    s += '</ul>';

  } else {
    s += '<div>&lt;No row&gt;</div>';
  }
  this.widget('HtmlField').setValue('<h1>Detail Page</h1>' + s);
};
