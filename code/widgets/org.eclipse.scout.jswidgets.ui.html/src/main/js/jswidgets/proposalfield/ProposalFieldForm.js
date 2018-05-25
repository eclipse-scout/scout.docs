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
jswidgets.ProposalFieldForm = function() {
  jswidgets.ProposalFieldForm.parent.call(this);
};
scout.inherits(jswidgets.ProposalFieldForm, scout.Form);

jswidgets.ProposalFieldForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.ProposalFieldForm');
};

jswidgets.ProposalFieldForm.prototype._init = function(model) {
  jswidgets.ProposalFieldForm.parent.prototype._init.call(this, model);

  this.proposalField = this.widget('ProposalField');

  this.widget('ProposalFieldPropertiesBox').setField(this.proposalField);
  this.widget('ValueFieldPropertiesBox').setField(this.proposalField);
  this.widget('FormFieldPropertiesBox').setField(this.proposalField);
  this.widget('GridDataBox').setField(this.proposalField);
  this.widget('EventsTab').setField(this.proposalField);
};
