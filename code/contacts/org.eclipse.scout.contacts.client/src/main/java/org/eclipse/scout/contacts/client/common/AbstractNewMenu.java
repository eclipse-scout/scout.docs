/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.contacts.client.common;

import java.util.Set;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

@ClassId("e85bff77-d4be-4a4a-8525-370dc2e16fdb")
public class AbstractNewMenu extends AbstractMenu {

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("New");
  }

  @Override
  protected String getConfiguredIconId() {
    // get unicode from http://fontawesome.io/icon/magic/
    return "font:awesomeIcons \uf0d0";
  }

  @Override
  protected String getConfiguredKeyStroke() {
    return "alt-n";
  }

  @Override
  protected Set<? extends IMenuType> getConfiguredMenuTypes() {
    return CollectionUtility.<IMenuType> hashSet(
        TableMenuType.EmptySpace, TableMenuType.SingleSelection);
  }
}
