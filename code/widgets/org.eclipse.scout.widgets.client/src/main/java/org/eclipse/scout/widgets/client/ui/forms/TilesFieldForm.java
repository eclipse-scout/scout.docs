package org.eclipse.scout.widgets.client.ui.forms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.MenuMediator;
import org.eclipse.scout.rt.client.ui.action.menu.MenuUtility;
import org.eclipse.scout.rt.client.ui.action.menu.TilesMenuType;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tilesfield.AbstractTilesField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.client.ui.tile.AbstractTiles;
import org.eclipse.scout.rt.client.ui.tile.ITile;
import org.eclipse.scout.rt.client.ui.tile.ITiles;
import org.eclipse.scout.rt.client.ui.tile.TilesLayoutConfig;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.data.tile.ITileColorScheme;
import org.eclipse.scout.rt.shared.data.tile.TileColorScheme;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.services.lookup.TileColorSchemeLookupCall;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.DetailBox;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.DetailBox.FilterField;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.DetailBox.StatusField;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.DetailBox.TilesField;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.DetailBox.TilesField.Tiles;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.PropertiesBox;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.PropertiesBox.ColorSchemeField;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.PropertiesBox.MaxContentWidthField;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.PropertiesBox.MultiSelectField;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.PropertiesBox.ScrollableField;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.PropertiesBox.SelectableField;
import org.eclipse.scout.widgets.client.ui.tiles.AbstractSimpleTile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClassId("1c8091c8-6a6b-4860-9d12-e8bf6cab115a")
public class TilesFieldForm extends AbstractForm implements IAdvancedExampleForm {
  private static final Logger LOG = LoggerFactory.getLogger(TilesFieldForm.class);

  private int m_tilesAddedCount = 0;
  private SimpleTileFilter m_tileFilter;

  @Override
  public void startPageForm() {
    start();
  }

  public ColorSchemeField getColorSchemeField() {
    return getFieldByClass(ColorSchemeField.class);
  }

  public SelectableField getSelectableField() {
    return getFieldByClass(SelectableField.class);
  }

  public MultiSelectField getMultiSelectField() {
    return getFieldByClass(MultiSelectField.class);
  }

  public ScrollableField getScrollableField() {
    return getFieldByClass(ScrollableField.class);
  }

  public TilesField getTilesField() {
    return getFieldByClass(TilesField.class);
  }

  public DetailBox getDetailBox() {
    return getFieldByClass(DetailBox.class);
  }

  public MaxContentWidthField getMaxContentWidthField() {
    return getFieldByClass(MaxContentWidthField.class);
  }

  public FilterField getFilterField() {
    return getFieldByClass(FilterField.class);
  }

  public StatusField getStatusField() {
    return getFieldByClass(StatusField.class);
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
    return "TilesField";
  }

  @ClassId("fcf36822-2d2d-4a23-a32d-74f7385370ea")
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    public class DetailBox extends AbstractGroupBox {
      private MenuMediator m_menuMediator;

      @Override
      protected void execInitField() {
        m_menuMediator = new MenuMediator(getTilesField().getTiles(), getDetailBox());
        m_menuMediator.install();
      }

      @Override
      protected void execDisposeField() {
        m_menuMediator.uninstall();
      }

      @Order(1000)
      @ClassId("e06efe30-25db-446c-848f-22935dcff376")
      public class TilesField extends AbstractTilesField<TilesField.Tiles> {

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return FULL_WIDTH;
        }

        @Override
        protected void execInitField() {
          updateStatus();
        }

        @ClassId("269a1b03-5af1-4cf8-a063-20a9bbe85ae8")
        public class Tiles extends AbstractTiles {

          @Override
          protected void initConfig() {
            super.initConfig();
            MenuUtility.updateMenuVisibilitiesForTiles(this);

            addPropertyChangeListener((event) -> {
              if (event.getPropertyName().equals(ITiles.PROP_TILES) || event.getPropertyName().equals(ITiles.PROP_FILTERED_TILES)) {
                updateStatus();
              }
            });
          }

          @Override
          protected void execTilesSelected(List<? extends ITile> tiles) {
            super.execTilesSelected(tiles);
            MenuUtility.updateMenuVisibilitiesForTiles(this);
            updateStatus();
          }

          @Order(1000)
          @ClassId("1635b251-77f5-4fc7-adc8-192aa165ade3")
          public class AddMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
              return "Add tile";
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TilesMenuType.EmptySpace);
            }

            @Override
            protected void execAction() {
              SimpleTile tile = new SimpleTile();
              tile.setLabel("New tile " + m_tilesAddedCount++);
              getTilesField().getTiles().addTile(tile);
            }
          }

          @Order(1000)
          @ClassId("0b26759f-7d17-45ea-96ba-c85ee3dbc735")
          public class AddManyMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
              return "Add many tiles";
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TilesMenuType.EmptySpace);
            }

            @Override
            protected void execAction() {
              List<ITile> tiles = new ArrayList<>();
              for (int i = 0; i < 50; i++) {
                SimpleTile tile = new SimpleTile();
                tile.setLabel("New tile " + m_tilesAddedCount++);
                tiles.add(tile);
              }
              getTilesField().getTiles().addTiles(tiles);
            }
          }

          @Order(1100)
          @ClassId("09d44b29-d1b9-427b-af2a-89825f3f31f3")
          public class SelectNextMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
              return "Select next tile";
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TilesMenuType.EmptySpace);
            }

            @Override
            protected void execAction() {
              Tiles tiles = getTilesField().getTiles();
              if (tiles.getTiles().size() == 0) {
                return;
              }
              ITile selectedTile = tiles.getSelectedTile();
              ITile tileToSelect = null;
              if (selectedTile != null) {
                int selectedTileIndex = tiles.getTiles().indexOf(selectedTile);
                if (selectedTileIndex < tiles.getTiles().size() - 1) {
                  tileToSelect = tiles.getTiles().get(selectedTileIndex + 1);
                }
              }
              if (tileToSelect == null) {
                tileToSelect = tiles.getTiles().get(0);
              }
              tiles.selectTile(tileToSelect);
            }
          }

          @Order(1200)
          @ClassId("02c99aae-ae40-46c8-8a7e-cb938630001b")
          public class SelectAllMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
              return "Select all tiles";
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TilesMenuType.EmptySpace);
            }

            @Override
            protected void execAction() {
              getTilesField().getTiles().selectAllTiles();
            }
          }

          @Order(1300)
          @ClassId("64f7eb9c-bedd-4680-8b94-555c9c64fd97")
          public class DeleteSelectedMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
              return "Delete selected tiles";
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TilesMenuType.SingleSelection, TilesMenuType.MultiSelection);
            }

            @Override
            protected void execAction() {
              getTilesField().getTiles().deleteTiles(getTilesField().getTiles().getSelectedTiles());
              if (getTilesField().getTiles().getTiles().size() == 0) {
                m_tilesAddedCount = 0;
              }
            }

            @Override
            protected void execOwnerValueChanged(Object newOwnerValue) {
              LOG.debug("ownerValueChanged" + newOwnerValue);
            }
          }

          @Order(3000)
          @ClassId("9527bc36-f9dd-45b2-8d8e-f4382879cf21")
          public class SingleSelectionMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
              return "Single Selection Menu";
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.hashSet(TilesMenuType.SingleSelection);
            }

            @Order(1000)
            @ClassId("a41e1caa-aeaa-44cf-b7e5-224718b40788")
            public class SingleSelectionSub1Menu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return "Sub Menu 1";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TilesMenuType.SingleSelection);
              }

              @Override
              protected void execAction() {
                MessageBoxes.createOk().withHeader(getText() + " activated").show();
              }
            }

            @Order(2000)
            @ClassId("839c3a5e-8c0d-4487-ba5e-9a10c4759e62")
            public class SingleSelectionSub2Menu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return "Sub Menu 2";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TilesMenuType.SingleSelection);
              }

              @Override
              protected void execAction() {
                MessageBoxes.createOk().withHeader(getText() + " activated").show();
              }
            }

          }

          @Order(4000)
          @ClassId("53129ea4-a192-4f65-bbeb-c480028bd81b")
          public class MultiSelectionMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
              return "Multi Selection Menu";
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.hashSet(TilesMenuType.MultiSelection);
            }

            @Order(1000)
            @ClassId("6d512204-8eaa-4c69-9c2f-0368dad922c9")
            public class MultiSelectionSub1Menu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return "Sub Menu 1";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TilesMenuType.MultiSelection);
              }

              @Override
              protected void execAction() {
                MessageBoxes.createOk().withHeader(getText() + " activated").show();
              }
            }

            @Order(2000)
            @ClassId("28094982-95f0-488e-83cf-0440b78e74dd")
            public class MultiSelectionSub2Menu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return "Sub Menu 2";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TilesMenuType.MultiSelection);
              }

              @Override
              protected void execAction() {
                MessageBoxes.createOk().withHeader(getText() + " activated").show();
              }
            }

          }

          @ClassId("7728b4be-e5b9-4682-ac32-1fcde1c13bea")
          public class SortMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
              return "Sort";
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.hashSet(TilesMenuType.EmptySpace);
            }

            @Order(1000)
            @ClassId("a41e1caa-aeaa-44cf-b7e5-224718b40788")
            public class AscMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return "Ascending";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TilesMenuType.EmptySpace);
              }

              @Override
              protected void execAction() {
                sortTiles(true);
              }
            }

            @Order(2000)
            @ClassId("c85a8ee3-0ecf-4024-b451-1a0bf8709a84")
            public class DescMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return "Descending";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TilesMenuType.EmptySpace);
              }

              @Override
              protected void execAction() {
                sortTiles(false);
              }
            }

          }

          public class SimpleTile1 extends AbstractSimpleTile {
            @Override
            protected String getConfiguredLabel() {
              return "Tile 1";
            }
          }

          public class SimpleTile2 extends AbstractSimpleTile {
            @Override
            protected String getConfiguredLabel() {
              return "Tile 2";
            }
          }

          public class SimpleTile3 extends AbstractSimpleTile {
            @Override
            protected String getConfiguredLabel() {
              return "Tile 3";
            }
          }

          public class SimpleTile4 extends AbstractSimpleTile {
            @Override
            protected String getConfiguredLabel() {
              return "Tile 4";
            }
          }

          public class SimpleTile5 extends AbstractSimpleTile {
            @Override
            protected String getConfiguredLabel() {
              return "Tile 5";
            }
          }
        }
      }

      @Order(2000)
      @ClassId("64397690-7521-4fad-b149-b83ca0007af5")
      public class FilterField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FilterBy");
        }

        @Override
        protected boolean getConfiguredUpdateDisplayTextOnModify() {
          return true;
        }

        @Override
        protected byte getConfiguredLabelPosition() {
          return LABEL_POSITION_ON_FIELD;
        }

        @Override
        protected String getConfiguredClearable() {
          return CLEARABLE_ALWAYS;
        }

        @Override
        protected int getConfiguredWidthInPixel() {
          return 300;
        }

        @Override
        protected boolean getConfiguredFillHorizontal() {
          return false;
        }

        @Override
        protected void execChangedDisplayText() {
          filterTilesByText(getDisplayText());
        }
      }

      @Order(3000)
      @ClassId("16a80da8-a168-4b87-bf86-0ceff2823d36")
      @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
      public class StatusField extends AbstractLabelField {
        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return 1;
        }

      }
    }

    protected void filterTilesByText(String text) {
      if (!StringUtility.isNullOrEmpty(text)) {
        if (m_tileFilter == null) {
          m_tileFilter = new SimpleTileFilter();
          getTilesField().getTiles().addFilter(m_tileFilter);
        }
        m_tileFilter.setText(text);
      }
      else {
        getTilesField().getTiles().removeFilter(m_tileFilter);
        m_tileFilter = null;
      }
      getTilesField().getTiles().filter();
    }

    protected void updateStatus() {
      Tiles tiles = getTilesField().getTiles();
      getStatusField().setValue(TEXTS.get("TilesStatus", tiles.getTileCount() + "", tiles.getFilteredTileCount() + "", tiles.getSelectedTileCount() + ""));
    }

    protected void sortTiles(boolean asc) {
      List<? extends ITile> tiles = getTilesField().getTiles().getTiles();
      tiles.sort(new Comparator<ITile>() {
        @Override
        public int compare(ITile tile1, ITile tile2) {
          if (tile1 instanceof AbstractSimpleTile && tile2 instanceof AbstractSimpleTile) {
            int result = StringUtility.ALPHANUMERIC_COMPARATOR.compare(((AbstractSimpleTile) tile1).getLabel(), ((AbstractSimpleTile) tile2).getLabel());
            if (!asc) {
              result = -result;
            }
            return result;
          }
          return 0;
        }
      });
      getTilesField().getTiles().setTiles(tiles);
    }

    @Order(2000)
    @ClassId("fc28a174-b683-4aae-8ec9-7f73dc2b45e2")
    public class PropertiesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return "Properties";
      }

      @Order(10)
      @ClassId("1f474645-120a-4386-b826-13a395cf2924")
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
          getTilesField().getTiles().setGridColumnCount(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getTilesField().getTiles().getGridColumnCount());
        }
      }

      @Order(20)
      @ClassId("a79b08d7-7a19-44fb-843f-455928d71861")
      public class ColorSchemeField extends AbstractSmartField<ITileColorScheme> {
        @Override
        protected String getConfiguredLabel() {
          return "Color Scheme";
        }

        @Override
        protected Class<? extends ILookupCall<ITileColorScheme>> getConfiguredLookupCall() {
          return TileColorSchemeLookupCall.class;
        }

        @Override
        protected String getConfiguredDisplayStyle() {
          return DISPLAY_STYLE_DROPDOWN;
        }

        @Override
        protected void execInitField() {
          Tiles tiles = getTilesField().getTiles();
          ITileColorScheme colorScheme = TileColorScheme.DEFAULT;
          if (tiles.getTiles().size() > 0) {
            colorScheme = getTilesField().getTiles().getTiles().get(0).getColorScheme();
          }
          setValue(colorScheme);
        }

        @Override
        protected void execChangedValue() {
          for (ITile tile : getTilesField().getTiles().getTiles()) {
            tile.setColorScheme(getValue());
          }
        }
      }

      @Order(22)
      @ClassId("24e33599-8612-4015-aad7-37e8b77c4815")
      public class MaxContentWidthField extends AbstractIntegerField {
        @Override
        protected String getConfiguredLabel() {
          return "Max Content Width";
        }

        @Override
        protected void execChangedValue() {
          TilesLayoutConfig layoutConfig = getTilesField().getTiles().getLayoutConfig().copy()
              .withMaxWidth(getValue());
          getTilesField().getTiles().setLayoutConfig(layoutConfig);
        }

        @Override
        protected void execInitField() {
          setValue(getTilesField().getTiles().getLayoutConfig().getMaxWidth());
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
          getTilesField().getTiles().setSelectable(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getTilesField().getTiles().isSelectable());
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
          getTilesField().getTiles().setMultiSelect(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getTilesField().getTiles().isMultiSelect());
        }
      }

      @Order(38)
      @ClassId("ce9aec66-0617-472a-804f-157565b7b5d7")
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
          getTilesField().getTiles().setScrollable(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getTilesField().getTiles().isScrollable());
        }
      }

      @Order(50)
      @ClassId("ac5a4cf5-61c1-4254-82b0-c4daa81d92bf")
      public class WithPlaceholdersField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "With Placeholders";
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
          getTilesField().getTiles().setWithPlaceholders(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getTilesField().getTiles().isWithPlaceholders());
        }
      }
    }

    @Order(50)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public static class SimpleTile extends AbstractSimpleTile {
  }

}
