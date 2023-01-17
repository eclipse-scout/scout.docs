/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.desktop.menu;

import java.util.Set;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TreeMenuType;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

@ClassId("18fea915-4cf6-4746-9212-9492b94aa981")
public class AbstractHierarchicalTreeMenu extends AbstractMenu {
  @Override
  protected Set<? extends IMenuType> getConfiguredMenuTypes() {
    return CollectionUtility.<IMenuType> hashSet(TreeMenuType.SingleSelection);
  }

  @Override
  protected String getConfiguredText() {
    return "HierarchicalMenu";
  }

  @Order(10)
  @ClassId("8b6bd62a-f103-444e-92f3-f455d88d6319")
  public class SubSingleMenu extends AbstractMenu {
    @Override
    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
      return CollectionUtility.<IMenuType> hashSet(TreeMenuType.SingleSelection);
    }

    @Override
    protected String getConfiguredText() {
      return "TreeSubSingle";
    }

  }

  @Order(20)
  @ClassId("23d94a6b-5337-4430-9e57-b22bb0fee37e")
  public class SubMultiMenu extends AbstractMenu {
    @Override
    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
      return CollectionUtility.<IMenuType> hashSet(TreeMenuType.MultiSelection);
    }

    @Override
    protected String getConfiguredText() {
      return "TreeSubMulti";
    }

  }

  @Order(30)
  @ClassId("4f57c4b4-bfc3-4082-865b-a561de393d91")
  public class SubEmptySpaceMenu extends AbstractMenu {
    @Override
    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
      return CollectionUtility.<IMenuType> hashSet(TreeMenuType.EmptySpace);
    }

    @Override
    protected String getConfiguredText() {
      return "TreeSubEmpty";
    }

  }

  @Order(40)
  @ClassId("1dde4072-0a5c-44eb-b234-b806642eced4")
  public class IntermediateMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return "Intermediate Menu";
    }

    @Order(10)
    @ClassId("ea803201-1fbc-4d7f-9635-7f213d9e52ff")
    public class SubSubSingleMenu extends AbstractMenu {
      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(TreeMenuType.SingleSelection);
      }

      @Override
      protected String getConfiguredText() {
        return "TreeSubSubSingle";
      }

    }

    @Order(20)
    @ClassId("508fbfa7-f5bd-4a09-8994-4f9818427b5d")
    public class SubSubMultiMenu extends AbstractMenu {
      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(TreeMenuType.MultiSelection);
      }

      @Override
      protected String getConfiguredText() {
        return "TreeSubSubMulti";
      }

    }

    @Order(30)
    @ClassId("c78b683b-6f2d-4379-8b6d-e0349b953ba3")
    public class SubSubEmptySpaceMenu extends AbstractMenu {
      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(TreeMenuType.EmptySpace);
      }

      @Override
      protected String getConfiguredText() {
        return "TreeSubSubEmpty";
      }

    }

  }

}
