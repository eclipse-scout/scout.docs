package org.eclipse.scout.widgets.client.ui.forms;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.client.ui.accordion.AbstractAccordion;
import org.eclipse.scout.rt.client.ui.accordion.IAccordion;
import org.eclipse.scout.rt.client.ui.action.keystroke.IKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.GridData;
import org.eclipse.scout.rt.client.ui.form.fields.accordionfield.AbstractAccordionField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.group.AbstractGroup;
import org.eclipse.scout.rt.client.ui.group.IGroup;
import org.eclipse.scout.rt.client.ui.tile.AbstractTileGrid;
import org.eclipse.scout.rt.client.ui.tile.TileGridLayoutConfig;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.security.SecurityUtility;
import org.eclipse.scout.rt.shared.data.tile.TileColorScheme;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.AccordionFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.AccordionFieldForm.MainBox.DetailBox;
import org.eclipse.scout.widgets.client.ui.forms.AccordionFieldForm.MainBox.DetailBox.AccordionField;
import org.eclipse.scout.widgets.client.ui.forms.AccordionFieldForm.MainBox.DetailBox.AccordionField.Accordion;
import org.eclipse.scout.widgets.client.ui.forms.AccordionFieldForm.MainBox.PropertiesBox;
import org.eclipse.scout.widgets.client.ui.forms.AccordionFieldForm.MainBox.PropertiesBox.ExclusiveExpandField;
import org.eclipse.scout.widgets.client.ui.forms.AccordionFieldForm.MainBox.PropertiesBox.ScrollableField;
import org.eclipse.scout.widgets.client.ui.forms.TileFieldForm.SimpleTile;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFormFieldPropertiesBox;
import org.eclipse.scout.widgets.client.ui.tile.AbstractCustomTile;

@ClassId("59689d49-e6a5-4641-9d1a-a6a6ce98d9bf")
public class AccordionFieldForm extends AbstractForm implements IAdvancedExampleForm {

  private int m_groupsAddedCount = 0;

  @Override
  public void startPageForm() {
    start();
  }

  public ScrollableField getScrollableField() {
    return getFieldByClass(ScrollableField.class);
  }

  public AccordionField getAccordionField() {
    return getFieldByClass(AccordionField.class);
  }

  public DetailBox getDetailBox() {
    return getFieldByClass(DetailBox.class);
  }

  public ExclusiveExpandField getExclusiveExpandField() {
    return getFieldByClass(ExclusiveExpandField.class);
  }

  public PropertiesBox getPropertiesBox() {
    return getFieldByClass(PropertiesBox.class);
  }

  @Override
  public AbstractCloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  @Override
  protected String getConfiguredTitle() {
    return "AccordionField";
  }

  @ClassId("4d6137e0-7d67-491b-969f-c02c3161581b")
  public class MainBox extends AbstractGroupBox {

    @Order(100)
    @ClassId("41a25783-a1cb-4895-a417-73eebfb1b804")
    public class DetailBox extends AbstractGroupBox {

      @Order(1000)
      @ClassId("e4efa1c6-b6b0-4159-bf68-5fcb852bbaa5")
      public class AddMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Add group";
        }

        @Override
        protected String getConfiguredKeyStroke() {
          return IKeyStroke.INSERT;
        }

        @Override
        protected void execAction() {
          addGroupWithTiles();
        }
      }

      @Order(1100)
      @ClassId("64f7eb9c-bedd-4680-8b94-565c9c64fd97")
      public class DeleteFirstMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Delete first group";
        }

        @Override
        protected String getConfiguredKeyStroke() {
          return IKeyStroke.DELETE;
        }

        @Override
        protected void execAction() {
          Accordion accordion = getAccordionField().getAccordion();
          if (accordion.getGroupCount() == 0) {
            return;
          }
          accordion.deleteGroup(accordion.getGroups().get(0));
          if (accordion.getGroupCount() == 0) {
            m_groupsAddedCount = 0;
          }
        }

      }

      @Order(1200)
      @ClassId("b0bea1fd-0de8-4695-a026-d9c7d290d997")
      public class ExpandCollapseFirstMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Expand/Collapse first group";
        }

        @Override
        protected void execAction() {
          Accordion accordion = getAccordionField().getAccordion();
          if (accordion.getGroupCount() == 0) {
            return;
          }
          accordion.getGroups().get(0).toggleCollapse();
        }
      }

      @Order(1300)
      @ClassId("bbea0547-f999-408a-a15f-a1342843a31d")
      public class CollapseAllMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Collapse all groups";
        }

        @Override
        protected void execAction() {
          for (IGroup group : getAccordionField().getAccordion().getGroups()) {
            group.setCollapsed(true);
          }
        }
      }

      @Order(1000)
      @ClassId("0b9d8c2e-b58b-4d3b-8f37-bb3f4ac075fb")
      public class AccordionField extends AbstractAccordionField<AccordionField.Accordion> {

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return FULL_WIDTH;
        }

        @ClassId("8221d7df-6803-434b-ada8-ae1bdc4f0723")
        public class Accordion extends AbstractAccordion {

        }
      }

    }

    @Order(200)
    @ClassId("d556cf3b-699f-4c9f-a6a0-7eac28398cd1")
    public class PropertiesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return "Properties";
      }

      @Order(20)
      @ClassId("f82d22e3-944e-4f25-afdc-bb1736d530c8")
      public class ExclusiveExpandField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return "Exclusive Expand";
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execChangedValue() {
          getAccordionField().getAccordion().setExclusiveExpand(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getAccordionField().getAccordion().isExclusiveExpand());
        }
      }

      @Order(38)
      @ClassId("e3725a0d-f6ea-4115-b892-703890962114")
      public class ScrollableField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return "Scrollable";
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execChangedValue() {
          getAccordionField().getAccordion().setScrollable(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getAccordionField().getAccordion().isScrollable());
        }
      }

    }

    @Order(300)
    @ClassId("28a04cd7-8be9-4136-9b14-097cdf67260b")
    public class FormFieldPropertiesBox extends AbstractFormFieldPropertiesBox {

      @Override
      protected void execInitField() {
        setField(getAccordionField());
      }

      @Override
      protected boolean getConfiguredExpanded() {
        return false;
      }
    }

    @Order(500)
    @ClassId("e6b4210d-2cf7-477e-87c1-617e00a334df")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  protected void addGroupWithTiles() {
    IAccordion accordion = getAccordionField().getAccordion();
    List<AbstractCustomTile> tiles = new ArrayList<>();
    int maxTiles = SecurityUtility.createSecureRandom().nextInt(30);
    for (int i = 0; i < maxTiles; i++) {
      SimpleTile tile = new SimpleTile();
      tile.setLabel("Tile " + i);
      GridData gridDataHints = tile.getGridDataHints();
      gridDataHints.weightX = 0;
      tile.setGridDataHints(gridDataHints);
      tile.setColorScheme(accordion.getGroupCount() * 2 == 0 ? TileColorScheme.DEFAULT : TileColorScheme.ALTERNATIVE);
      tiles.add(tile);
    }
    TileGroup group = new TileGroup();
    group.setTitle("Group " + m_groupsAddedCount++);
    group.getBody().setTiles(tiles);
    accordion.addGroup(group);
  }

  @Override
  protected void execInitForm() {
    super.execInitForm();
    addGroupWithTiles();
    addGroupWithTiles();
    addGroupWithTiles();
  }

  @ClassId("16cefe7d-1488-4dd3-aeb7-f19bf905e36f")
  public static class TileGroup extends AbstractGroup {

    @Override
    public TileGrid getBody() {
      return (TileGrid) super.getBody();
    }

    @ClassId("1af3bcc9-5cb0-486a-bb5a-6ef5dfc63230")
    public class TileGrid extends AbstractTileGrid<AbstractCustomTile> {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 6;
      }

      @Override
      protected TileGridLayoutConfig getConfiguredLayoutConfig() {
        return super.getConfiguredLayoutConfig()
            .withColumnWidth(100)
            .withRowHeight(100);
      }

      @Override
      protected boolean getConfiguredScrollable() {
        return false;
      }
    }

  }

}
