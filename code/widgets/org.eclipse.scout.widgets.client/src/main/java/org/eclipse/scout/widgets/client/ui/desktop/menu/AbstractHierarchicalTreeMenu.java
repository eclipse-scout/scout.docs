package org.eclipse.scout.widgets.client.ui.desktop.menu;

import java.util.Set;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TreeMenuType;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

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
  public class IntermediateMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return "Intermediate Menu";
    }

    @Order(10)
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
