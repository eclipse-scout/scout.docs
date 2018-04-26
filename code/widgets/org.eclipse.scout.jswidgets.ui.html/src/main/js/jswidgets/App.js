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
jswidgets.App = function() {
  jswidgets.App.parent.call(this);
  this.scoutVersion = '8.0';
};
scout.inherits(jswidgets.App, scout.App);

jswidgets.App.prototype._createDesktop = function(parent) {
  var desktop = scout.create('Desktop',
    scout.models.getModel('jswidgets.Desktop', parent));

  scout.router.register(new jswidgets.WidgetsRoute(desktop));
  scout.router.activate();

  return desktop;
};
