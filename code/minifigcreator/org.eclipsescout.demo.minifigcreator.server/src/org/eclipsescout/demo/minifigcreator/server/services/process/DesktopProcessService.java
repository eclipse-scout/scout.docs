/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 * 
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipsescout.demo.minifigcreator.server.services.process;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.demo.minifigcreator.server.data.IMinifigDataStoreService;
import org.eclipsescout.demo.minifigcreator.shared.minifig.part.Part;
import org.eclipsescout.demo.minifigcreator.shared.services.lookup.HeadLookupCall;
import org.eclipsescout.demo.minifigcreator.shared.services.lookup.LegsLookupCall;
import org.eclipsescout.demo.minifigcreator.shared.services.lookup.TorsoLookupCall;
import org.eclipsescout.demo.minifigcreator.shared.services.process.DesktopFormData;
import org.eclipsescout.demo.minifigcreator.shared.services.process.FormState;
import org.eclipsescout.demo.minifigcreator.shared.services.process.IDesktopProcessService;

public class DesktopProcessService extends AbstractService implements IDesktopProcessService {

  @Override
  public DesktopFormData load(DesktopFormData formData) throws ProcessingException {
    FormState fs = new FormState();
    fs.setHeadEnabled(computePartState(new HeadLookupCall(), formData.getHead()));
    fs.setTorsoEnabled(computePartState(new TorsoLookupCall(), formData.getTorso()));
    fs.setLegsEnabled(computePartState(new LegsLookupCall(), formData.getLegs()));
    formData.setState(fs);

    computePartValue(formData.getHead());
    computePartValue(formData.getTorso());
    computePartValue(formData.getLegs());
    return formData;
  }

  private boolean computePartState(LookupCall lookupCall, AbstractValueFieldData<Part> fieldData) throws ProcessingException {
    LookupRow[] rows = lookupCall.getDataByAll();
    if (rows == null || rows.length == 0) {
      return false;
    }
    else if (rows.length == 1) {
      fieldData.setValue((Part) rows[0].getKey());
      return false;
    }
    else {
      return true;
    }
  }

  private void computePartValue(AbstractValueFieldData<Part> fieldData) {
    Part part = fieldData.getValue();
    if (part != null) {
      if (!SERVICES.getService(IMinifigDataStoreService.class).isAvailable(part)) {
        fieldData.setValue(null);
      }
    }
  }

  @Override
  public DesktopFormData store(DesktopFormData formData) throws ProcessingException {
    storePart(formData.getHead());
    storePart(formData.getTorso());
    storePart(formData.getLegs());

    return formData;
  }

  private void storePart(AbstractValueFieldData<Part> fieldData) throws ProcessingException {
    Part part = fieldData.getValue();
    if (part != null) {
      IMinifigDataStoreService service = SERVICES.getService(IMinifigDataStoreService.class);
      if (!service.isAvailable(part)) {
        throw new VetoException(TEXTS.get("WarningPartNotAvailable", part.getName()));
      }
      service.decreaseQuantity(part);
    }
  }
}
