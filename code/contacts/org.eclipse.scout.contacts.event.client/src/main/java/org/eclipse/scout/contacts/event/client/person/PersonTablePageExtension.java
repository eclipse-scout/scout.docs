/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.event.client.person;

import org.eclipse.scout.commons.annotations.Data;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.contacts.client.person.PersonTablePage;
import org.eclipse.scout.contacts.event.shared.person.PersonTablePageDataExtension;
import org.eclipse.scout.rt.client.extension.ui.basic.table.AbstractTableExtension;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.shared.TEXTS;

@Data(PersonTablePageDataExtension.class)
public class PersonTablePageExtension extends AbstractTableExtension<PersonTablePage.Table> {

  public PersonTablePageExtension(PersonTablePage.Table owner) {
    super(owner);
  }

  @Order(10_000.0)
  public class EventsColumn extends AbstractLongColumn {

    @Override
    protected String getConfiguredHeaderText() {
      return TEXTS.get("Events");
    }

    @Override
    protected int getConfiguredWidth() {
      return 100;
    }
  }
}
