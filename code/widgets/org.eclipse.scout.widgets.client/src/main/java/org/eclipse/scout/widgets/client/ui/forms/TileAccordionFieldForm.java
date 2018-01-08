package org.eclipse.scout.widgets.client.ui.forms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.eclipse.scout.rt.client.ui.action.keystroke.IKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TileGridMenuType;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.accordionfield.AbstractAccordionField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.group.AbstractGroup;
import org.eclipse.scout.rt.client.ui.tile.AbstractTileAccordion;
import org.eclipse.scout.rt.client.ui.tile.AbstractTileGrid;
import org.eclipse.scout.rt.client.ui.tile.DefaultGroupManager;
import org.eclipse.scout.rt.client.ui.tile.TileGridLayoutConfig;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.data.tile.TileColorScheme;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.TileAccordionFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.TileAccordionFieldForm.MainBox.DetailBox;
import org.eclipse.scout.widgets.client.ui.forms.TileAccordionFieldForm.MainBox.DetailBox.AccordionField;
import org.eclipse.scout.widgets.client.ui.forms.TileAccordionFieldForm.MainBox.DetailBox.AccordionField.Accordion;
import org.eclipse.scout.widgets.client.ui.forms.TileAccordionFieldForm.MainBox.PropertiesBox;
import org.eclipse.scout.widgets.client.ui.forms.TileAccordionFieldForm.MainBox.PropertiesBox.ExclusiveExpandField;
import org.eclipse.scout.widgets.client.ui.forms.TileAccordionFieldForm.MainBox.PropertiesBox.ScrollableField;
import org.eclipse.scout.widgets.client.ui.forms.TileFieldForm.SimpleTile;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFormFieldPropertiesBox;
import org.eclipse.scout.widgets.client.ui.tile.ISimpleTile;
import org.eclipse.scout.widgets.client.ui.tile.SimpleTileGroupManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClassId("a3b0a11c-8be7-424b-9a97-5902af85809a")
public class TileAccordionFieldForm extends AbstractForm implements IAdvancedExampleForm {
  private static final Logger LOG = LoggerFactory.getLogger(TileAccordionFieldForm.class);

  private int m_tilesAddedCount = 0;

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

  @ClassId("eb0180d2-0c32-4198-9771-3dfef2a3662b")
  public class MainBox extends AbstractGroupBox {

    @Order(100)
    @ClassId("eab5ced8-eceb-47d3-92f0-affda2c1547d")
    public class DetailBox extends AbstractGroupBox {

      @Order(1000)
      @ClassId("65daec6c-698e-4f7e-aa92-a80d82ed05f8")
      public class AddMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Add tile to group A";
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet(TileGridMenuType.EmptySpace);
        }

        @Override
        protected String getConfiguredKeyStroke() {
          return IKeyStroke.INSERT;
        }

        @Override
        protected void execAction() {
          SimpleTile tile = new SimpleTile();
          tile.setLabel("New tile " + m_tilesAddedCount++);
          tile.setGroup("Group A");
          tile.setColorScheme(TileColorScheme.DEFAULT);
          getAccordionField().getAccordion().addTile(tile);
        }
      }

      @Order(1010)
      @ClassId("1fd54668-d9e7-4afd-accd-0eaac0c3b191")
      public class AddToBMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Add tile to group B";
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet(TileGridMenuType.EmptySpace);
        }

        @Override
        protected void execAction() {
          SimpleTile tile = new SimpleTile();
          tile.setLabel("New tile " + m_tilesAddedCount++);
          tile.setGroup("Group B");
          tile.setColorScheme(TileColorScheme.ALTERNATIVE);
          getAccordionField().getAccordion().addTile(tile);
        }
      }

      @Order(1050)
      @ClassId("52bae9c0-c38c-4033-b8d8-c7f6a9a154a4")
      public class AddManyMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Add many tiles";
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet(TileGridMenuType.EmptySpace);
        }

        @Override
        protected void execAction() {
          List<ISimpleTile> tiles = new ArrayList<>();
          for (int i = 0; i < 25; i++) {
            SimpleTile tile = new SimpleTile();
            tile.setLabel("New tile " + m_tilesAddedCount++);
            tile.setGroup("Group A");
            tile.setColorScheme(TileColorScheme.DEFAULT);
            tiles.add(tile);
          }
          for (int i = 0; i < 25; i++) {
            SimpleTile tile = new SimpleTile();
            tile.setLabel("New tile " + m_tilesAddedCount++);
            tile.setGroup("Group B");
            tile.setColorScheme(TileColorScheme.ALTERNATIVE);
            tiles.add(tile);
          }
          getAccordionField().getAccordion().addTiles(tiles);
        }
      }

      @Order(1100)
      @ClassId("7cbd6273-77f6-4560-ab2e-7110a43298dc")
      public class SelectNextMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Select next tile";
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet(TileGridMenuType.EmptySpace);
        }

        @Override
        protected void execAction() {
          Accordion accordion = getAccordionField().getAccordion();
          if (accordion.getTiles().size() == 0) {
            return;
          }
          ISimpleTile selectedTile = accordion.getSelectedTile();
          ISimpleTile tileToSelect = null;
          if (selectedTile != null) {
            int selectedTileIndex = accordion.getTiles().indexOf(selectedTile);
            if (selectedTileIndex < accordion.getTiles().size() - 1) {
              tileToSelect = accordion.getTiles().get(selectedTileIndex + 1);
            }
          }
          if (tileToSelect == null) {
            tileToSelect = accordion.getTiles().get(0);
          }
          accordion.selectTile(tileToSelect);
        }
      }

      @Order(1200)
      @ClassId("5f6a9267-bdc2-427c-95fb-779c42cca9f2")
      public class SelectAllMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Select all tiles";
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet(TileGridMenuType.EmptySpace);
        }

        @Override
        protected void execAction() {
          getAccordionField().getAccordion().selectAllTiles();
        }
      }

      @Order(1300)
      @ClassId("6fd723b6-e00b-4c09-9fcb-8f9e8d4ded12")
      public class DeleteSelectedMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Delete selected tiles";
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet(TileGridMenuType.SingleSelection, TileGridMenuType.MultiSelection);
        }

        @Override
        protected String getConfiguredKeyStroke() {
          return IKeyStroke.DELETE;
        }

        @Override
        protected void execAction() {
          getAccordionField().getAccordion().deleteTiles(getAccordionField().getAccordion().getSelectedTiles());
          if (getAccordionField().getAccordion().getTileCount() == 0) {
            m_tilesAddedCount = 0;
          }
        }

        @Override
        protected void execOwnerValueChanged(Object newOwnerValue) {
          LOG.debug("ownerValueChanged" + newOwnerValue);
        }
      }

      @Order(1400)
      @ClassId("e77d6698-8e91-4a22-a17c-19e2a6da9017")
      public class SortMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Sort";
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.hashSet(TileGridMenuType.EmptySpace);
        }

        @Order(1000)
        @ClassId("5846d3dd-ad65-4ff2-b217-136e2ea20fdc")
        public class AscMenu extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return "Ascending";
          }

          @Override
          protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.hashSet(TileGridMenuType.EmptySpace);
          }

          @Override
          protected void execAction() {
            sortTiles(true);
          }
        }

        @Order(2000)
        @ClassId("dc737ecb-4d31-4a3b-8533-d133456b9282")
        public class DescMenu extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return "Descending";
          }

          @Override
          protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.hashSet(TileGridMenuType.EmptySpace);
          }

          @Override
          protected void execAction() {
            sortTiles(false);
          }
        }

      }

      @Order(1500)
      @ClassId("9c3a2421-82a4-42a4-b455-87549a15173b")
      public class GroupMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Ungroup";
        }

        @Override
        protected void execAction() {
          if (getText().equals("Group")) {
            group();
            setText("Ungroup");
          }
          else {
            ungroup();
            setText("Group");
          }
        }

        @Override
        protected void execInitAction() {
          getAccordionField().getAccordion().addGroupManager(new SimpleTileGroupManager());
          group();
        }
      }

      @Order(1000)
      @ClassId("37999d63-7407-44f5-b776-bb1f97344bcd")
      public class AccordionField extends AbstractAccordionField<AccordionField.Accordion> {

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return FULL_WIDTH;
        }

        @ClassId("f59eaed0-afeb-48f8-a99d-cc4a15aa4253")
        public class Accordion extends AbstractTileAccordion<ISimpleTile> {

          @ClassId("0663a479-ff8f-4a72-b337-ff9759643955")
          public class TileGroup extends AbstractGroup {

            @Override
            public TileGrid getBody() {
              return (TileGrid) super.getBody();
            }

            @ClassId("3d7e78f9-75b1-48f5-aefe-2fb2118b7577")
            public class TileGrid extends AbstractTileGrid<ISimpleTile> {

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
      }

    }

    @Order(200)
    @ClassId("68892606-268e-4a13-a58c-f00ae6af47c4")
    public class PropertiesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return "Properties";
      }

      @Order(20)
      @ClassId("0c0f6c44-c7b5-483c-ad96-7026d3e30c24")
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
        protected String getConfiguredFont() {
          return "ITALIC";
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
      @ClassId("78fc1ed3-b3b4-4834-96fc-5a0241e32ca3")
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
        protected String getConfiguredFont() {
          return "ITALIC";
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

      @Order(40)
      @ClassId("eb20300a-a46d-4449-8613-75b6991b648f")
      public class GridColumnCountField extends AbstractIntegerField {

        @Override
        protected String getConfiguredLabel() {
          return "Grid Column Count";
        }

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return -1;
        }

        @Override
        protected void execChangedValue() {
          getAccordionField().getAccordion().setGridColumnCount(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getAccordionField().getAccordion().getGridColumnCount());
        }
      }

      @Order(50)
      @ClassId("9dd5d66b-8626-42c2-8c45-e75f1ae8e81f")
      public class MaxContentWidthField extends AbstractIntegerField {
        @Override
        protected String getConfiguredLabel() {
          return "Max Content Width";
        }

        @Override
        protected void execChangedValue() {
          TileGridLayoutConfig layoutConfig = getAccordionField().getAccordion().getTileGridLayoutConfig().copy()
              .withMaxWidth(getValue());
          getAccordionField().getAccordion().setTileGridLayoutConfig(layoutConfig);
        }

        @Override
        protected void execInitField() {
          setValue(getAccordionField().getAccordion().getTileGridLayoutConfig().getMaxWidth());
        }
      }

      @Order(25)
      @ClassId("e63c284c-1bc6-4e73-bd69-4c3bc8f321d5")
      public class SelectableField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return "Selectable";
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected void execChangedValue() {
          getAccordionField().getAccordion().setSelectable(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getAccordionField().getAccordion().isSelectable());
        }
      }

      @Order(27)
      @ClassId("b4148e8c-a081-4807-beb7-bd0fc4264809")
      public class MultiSelectField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return "Multi Select";
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected void execChangedValue() {
          getAccordionField().getAccordion().setMultiSelect(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getAccordionField().getAccordion().isMultiSelect());
        }
      }

    }

    @Order(300)
    @ClassId("a2286e1b-0a57-48ec-8720-558f5a8b9583")
    public class FormFieldPropertiesBox extends AbstractFormFieldPropertiesBox {
      @Override
      protected String getConfiguredLabel() {
        return "Form Field Properties";
      }

      @Override
      protected void execInitField() {
        setFormField(getAccordionField());
      }

      @Override
      protected boolean getConfiguredExpanded() {
        return false;
      }
    }

    @Order(500)
    public class CloseButton extends AbstractCloseButton {
    }

  }

  @Override
  protected void execInitForm() {
    int tileCount = 0;
    List<ISimpleTile> tiles = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      SimpleTile tile = new SimpleTile();
      tile.setLabel("Tile " + tileCount++);
      tile.setGroup("Group A");
      tile.setColorScheme(TileColorScheme.DEFAULT);
      tiles.add(tile);
    }
    for (int i = 0; i < 10; i++) {
      SimpleTile tile = new SimpleTile();
      tile.setLabel("Tile " + tileCount++);
      tile.setGroup("Group B");
      tile.setColorScheme(TileColorScheme.ALTERNATIVE);
      tiles.add(tile);
    }
    getAccordionField().getAccordion().addTiles(tiles);
  }

  protected void group() {
    getAccordionField().getAccordion().activateGroupManager(SimpleTileGroupManager.ID);
  }

  protected void ungroup() {
    getAccordionField().getAccordion().activateGroupManager(DefaultGroupManager.ID);
  }

  protected void sortTiles(boolean asc) {
    Accordion accordion = getAccordionField().getAccordion();
    accordion.setTileComparator(new Comparator<ISimpleTile>() {
      @Override
      public int compare(ISimpleTile tile1, ISimpleTile tile2) {
        int result = StringUtility.ALPHANUMERIC_COMPARATOR.compare((tile1).getLabel(), (tile2).getLabel());
        if (!asc) {
          result = -result;
        }
        return result;
      }
    });
  }

}
