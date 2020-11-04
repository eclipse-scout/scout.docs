/*
 * Copyright (c) 2014-2019 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.action.keystroke.IKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.MenuMediator;
import org.eclipse.scout.rt.client.ui.action.menu.TileGridMenuType;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tilefield.AbstractTileField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.client.ui.tile.AbstractTileGrid;
import org.eclipse.scout.rt.client.ui.tile.ITile;
import org.eclipse.scout.rt.client.ui.tile.ITileGrid;
import org.eclipse.scout.rt.client.ui.tile.TileGridLayoutConfig;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.data.colorscheme.IColorScheme;
import org.eclipse.scout.rt.shared.data.colorscheme.ColorScheme;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.services.lookup.TileColorSchemeLookupCall;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.TileFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.TileFieldForm.MainBox.DetailBox;
import org.eclipse.scout.widgets.client.ui.forms.TileFieldForm.MainBox.DetailBox.FilterField;
import org.eclipse.scout.widgets.client.ui.forms.TileFieldForm.MainBox.DetailBox.StatusField;
import org.eclipse.scout.widgets.client.ui.forms.TileFieldForm.MainBox.DetailBox.TileField;
import org.eclipse.scout.widgets.client.ui.forms.TileFieldForm.MainBox.DetailBox.TileField.TileGrid;
import org.eclipse.scout.widgets.client.ui.forms.TileFieldForm.MainBox.FormFieldPropertiesBox;
import org.eclipse.scout.widgets.client.ui.forms.TileFieldForm.MainBox.PropertiesBox;
import org.eclipse.scout.widgets.client.ui.forms.TileFieldForm.MainBox.PropertiesBox.ColorSchemeField;
import org.eclipse.scout.widgets.client.ui.forms.TileFieldForm.MainBox.PropertiesBox.MaxContentWidthField;
import org.eclipse.scout.widgets.client.ui.forms.TileFieldForm.MainBox.PropertiesBox.MultiSelectField;
import org.eclipse.scout.widgets.client.ui.forms.TileFieldForm.MainBox.PropertiesBox.ScrollableField;
import org.eclipse.scout.widgets.client.ui.forms.TileFieldForm.MainBox.PropertiesBox.SelectableField;
import org.eclipse.scout.widgets.client.ui.forms.TileFieldForm.MainBox.PropertiesBox.VirtualField;
import org.eclipse.scout.widgets.client.ui.forms.fields.formfield.AbstractFormFieldPropertiesBox;
import org.eclipse.scout.widgets.client.ui.tile.AbstractCustomTile;
import org.eclipse.scout.widgets.client.ui.tile.CustomTileFilter;
import org.eclipse.scout.widgets.client.ui.tile.ICustomTile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClassId("1c8091c8-6a6b-4860-9d12-e8bf6cab115a")
public class TileFieldForm extends AbstractForm implements IAdvancedExampleForm {
  private static final Logger LOG = LoggerFactory.getLogger(TileFieldForm.class);

  private int m_tilesAddedCount = 0;
  private CustomTileFilter m_tileFilter;

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

  public TileField getTileField() {
    return getFieldByClass(TileField.class);
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

  public FormFieldPropertiesBox getFormFieldPropertiesBox() {
    return getFieldByClass(FormFieldPropertiesBox.class);
  }

  public VirtualField getVirtualField() {
    return getFieldByClass(VirtualField.class);
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
    return "TileField";
  }

  @ClassId("fcf36822-2d2d-4a23-a32d-74f7385370ea")
  public class MainBox extends AbstractGroupBox {

    @Order(100)
    @ClassId("db483b87-eec5-4f50-8534-0a870ff3d55a")
    public class DetailBox extends AbstractGroupBox {
      private MenuMediator m_menuMediator;

      @Override
      protected void execInitField() {
        m_menuMediator = new MenuMediator(getTileField().getTileGrid(), getDetailBox());
        m_menuMediator.install();
      }

      @Override
      protected void execDisposeField() {
        m_menuMediator.uninstall();
      }

      @Order(1000)
      @ClassId("e06efe30-25db-446c-848f-22935dcff376")
      public class TileField extends AbstractTileField<TileGrid> {

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
          updateHasCustomTiles();
        }

        @ClassId("0cd4de91-68d0-4a1d-b123-5006b566481d")
        public class TileGrid extends AbstractTileGrid<ICustomTile> {

          @Override
          protected void initConfig() {
            super.initConfig();
            TileGridMenuType.updateMenuVisibilitiesForTiles(this);

            addPropertyChangeListener((event) -> {
              if (event.getPropertyName().equals(ITileGrid.PROP_TILES) || event.getPropertyName().equals(ITileGrid.PROP_FILTERED_TILES)) {
                updateStatus();
              }
              if (event.getPropertyName().equals(ITileGrid.PROP_FILTERED_TILES)) {
                updateHasCustomTiles();
              }
            });
          }

          @Override
          protected void execTilesSelected(List<ICustomTile> tiles) {
            super.execTilesSelected(tiles);
            TileGridMenuType.updateMenuVisibilitiesForTiles(this);
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
              getTileField().getTileGrid().addTile(tile);
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
              return CollectionUtility.<IMenuType> hashSet(TileGridMenuType.EmptySpace);
            }

            @Override
            protected void execAction() {
              List<ICustomTile> tiles = new ArrayList<>();
              for (int i = 0; i < 50; i++) {
                SimpleTile tile = new SimpleTile();
                tile.setLabel("New tile " + m_tilesAddedCount++);
                tiles.add(tile);
              }
              getTileField().getTileGrid().addTiles(tiles);
            }
          }

          @Order(1100)
          @ClassId("0ddffa11-4ab7-4815-b6fa-e3cc814df30c")
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
              TileGrid tileGrid = getTileField().getTileGrid();
              if (tileGrid.getTiles().size() == 0) {
                return;
              }
              ICustomTile selectedTile = tileGrid.getSelectedTile();
              ICustomTile tileToSelect = null;
              if (selectedTile != null) {
                int selectedTileIndex = tileGrid.getTiles().indexOf(selectedTile);
                if (selectedTileIndex < tileGrid.getTiles().size() - 1) {
                  tileToSelect = tileGrid.getTiles().get(selectedTileIndex + 1);
                }
              }
              if (tileToSelect == null) {
                tileToSelect = tileGrid.getTiles().get(0);
              }
              tileGrid.selectTile(tileToSelect);
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
              return CollectionUtility.<IMenuType> hashSet(TileGridMenuType.EmptySpace);
            }

            @Override
            protected void execAction() {
              getTileField().getTileGrid().selectAllTiles();
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
              return CollectionUtility.<IMenuType> hashSet(TileGridMenuType.SingleSelection, TileGridMenuType.MultiSelection);
            }

            @Override
            protected String getConfiguredKeyStroke() {
              return IKeyStroke.DELETE;
            }

            @Override
            protected void execAction() {
              getTileField().getTileGrid().deleteTiles(getTileField().getTileGrid().getSelectedTiles());
              if (getTileField().getTileGrid().getTiles().size() == 0) {
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
              return CollectionUtility.hashSet(TileGridMenuType.SingleSelection);
            }

            @Order(1000)
            @ClassId("b9c094bf-5e01-495a-aa97-f64507f16a50")
            public class SingleSelectionSub1Menu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return "Sub Menu 1";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TileGridMenuType.SingleSelection);
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
                return CollectionUtility.hashSet(TileGridMenuType.SingleSelection);
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
              return CollectionUtility.hashSet(TileGridMenuType.MultiSelection);
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
                return CollectionUtility.hashSet(TileGridMenuType.MultiSelection);
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
                return CollectionUtility.hashSet(TileGridMenuType.MultiSelection);
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
              return CollectionUtility.hashSet(TileGridMenuType.EmptySpace);
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
                return CollectionUtility.hashSet(TileGridMenuType.EmptySpace);
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
                return CollectionUtility.hashSet(TileGridMenuType.EmptySpace);
              }

              @Override
              protected void execAction() {
                sortTiles(false);
              }
            }

          }

          @ClassId("ef2f2d9a-c0cf-4f52-b057-cb408a82b4d0")
          public class SimpleTile1 extends AbstractCustomTile {
            @Override
            protected String getConfiguredLabel() {
              return "Tile 1";
            }
          }

          @ClassId("35c830af-96dd-47ad-a494-eed258318938")
          public class SimpleTile2 extends AbstractCustomTile {
            @Override
            protected String getConfiguredLabel() {
              return "Tile 2";
            }
          }

          @ClassId("27e2d10a-9a1c-4ed9-bc18-4b5467da8e98")
          public class SimpleTile3 extends AbstractCustomTile {
            @Override
            protected String getConfiguredLabel() {
              return "Tile 3";
            }
          }

          @ClassId("79db80d5-173e-48b3-9445-1db48dd41f12")
          public class SimpleTile4 extends AbstractCustomTile {
            @Override
            protected String getConfiguredLabel() {
              return "Tile 4";
            }
          }

          @ClassId("074b1675-16ef-481e-8f65-174341ed4b5c")
          public class SimpleTile5 extends AbstractCustomTile {
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
      @FormData(sdkCommand = SdkCommand.IGNORE)
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
          m_tileFilter = new CustomTileFilter();
          getTileField().getTileGrid().addFilter(m_tileFilter);
        }
        m_tileFilter.setText(text);
      }
      else {
        getTileField().getTileGrid().removeFilter(m_tileFilter);
        m_tileFilter = null;
      }
      getTileField().getTileGrid().filter();
    }

    protected void updateStatus() {
      TileGrid tileGrid = getTileField().getTileGrid();
      getStatusField().setValue(TEXTS.get("TilesStatus", tileGrid.getTileCount() + "", tileGrid.getFilteredTileCount() + "", tileGrid.getSelectedTileCount() + ""));
    }

    protected void updateHasCustomTiles() {
      TileGrid tileGrid = getTileField().getTileGrid();
      tileGrid.toggleCssClass("has-custom-tiles", tileGrid.getFilteredTiles().stream().anyMatch(t -> t instanceof ICustomTile));
    }

    protected void sortTiles(boolean asc) {
      getTileField().getTileGrid().setComparator((tile1, tile2) -> {
        int result = StringUtility.ALPHANUMERIC_COMPARATOR.compare(tile1.getLabel(), tile2.getLabel());
        if (!asc) {
          result = -result;
        }
        return result;
      });
    }

    @Order(200)
    @ClassId("fc28a174-b683-4aae-8ec9-7f73dc2b45e2")
    public class PropertiesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return "Properties";
      }

      @Override
      protected boolean getConfiguredExpandable() {
        return true;
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
        protected Integer execValidateValue(Integer rawValue) {
          if (rawValue == null) {
            throw new VetoException("Value must not be null");
          }
          return super.execValidateValue(rawValue);
        }

        @Override
        protected void execChangedValue() {
          getTileField().getTileGrid().setGridColumnCount(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getTileField().getTileGrid().getGridColumnCount());
        }
      }

      @Order(20)
      @ClassId("a79b08d7-7a19-44fb-843f-455928d71861")
      public class ColorSchemeField extends AbstractSmartField<IColorScheme> {
        @Override
        protected String getConfiguredLabel() {
          return "Color Scheme";
        }

        @Override
        protected Class<? extends ILookupCall<IColorScheme>> getConfiguredLookupCall() {
          return TileColorSchemeLookupCall.class;
        }

        @Override
        protected String getConfiguredDisplayStyle() {
          return DISPLAY_STYLE_DROPDOWN;
        }

        @Override
        protected void execInitField() {
          TileGrid tileGrid = getTileField().getTileGrid();
          IColorScheme colorScheme = ColorScheme.DEFAULT;
          if (tileGrid.getTiles().size() > 0) {
            colorScheme = getTileField().getTileGrid().getTiles().get(0).getColorScheme();
          }
          setValue(colorScheme);
        }

        @Override
        protected void execChangedValue() {
          for (ITile tile : getTileField().getTileGrid().getTiles()) {
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
        protected Integer execValidateValue(Integer rawValue) {
          if (rawValue == null) {
            throw new VetoException("Value must not be null");
          }
          return super.execValidateValue(rawValue);
        }

        @Override
        protected void execChangedValue() {
          TileGridLayoutConfig layoutConfig = getTileField().getTileGrid().getLayoutConfig().copy()
              .withMaxWidth(getValue());
          getTileField().getTileGrid().setLayoutConfig(layoutConfig);
        }

        @Override
        protected void execInitField() {
          setValue(getTileField().getTileGrid().getLayoutConfig().getMaxWidth());
        }
      }

      @Order(25)
      @ClassId("e3e5e1bc-ce2e-44d2-9669-d3c950fe4d88")
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
        protected void execChangedValue() {
          getTileField().getTileGrid().setSelectable(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getTileField().getTileGrid().isSelectable());
        }
      }

      @Order(27)
      @ClassId("2a421c5e-674f-4652-a0c5-a77d291c9c57")
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
        protected void execChangedValue() {
          getTileField().getTileGrid().setMultiSelect(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getTileField().getTileGrid().isMultiSelect());
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
        protected void execChangedValue() {
          getTileField().getTileGrid().setScrollable(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getTileField().getTileGrid().isScrollable());
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
        protected void execChangedValue() {
          getTileField().getTileGrid().setWithPlaceholders(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getTileField().getTileGrid().isWithPlaceholders());
        }
      }

      @Order(2000)
      @ClassId("59dcd5a4-b308-4910-b558-e6e9cbb6b9d6")
      public class VirtualField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return "Virtual";
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected String getConfiguredTooltipText() {
          return TEXTS.get("TileGridVirtualTooltip");
        }

        @Override
        protected void execChangedValue() {
          getTileField().getTileGrid().setVirtual(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getTileField().getTileGrid().isVirtual());
        }
      }
    }

    @Order(300)
    @ClassId("36584055-ed0a-464d-88d8-30c37cec1112")
    public class FormFieldPropertiesBox extends AbstractFormFieldPropertiesBox {

      @Override
      protected void execInitField() {
        setField(getTileField());
      }

      @Override
      protected boolean getConfiguredExpanded() {
        return false;
      }
    }

    @Order(500)
    @ClassId("e5de5758-cff2-4c19-b76c-a80456cbf75f")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  @ClassId("a7c26df1-d941-44bf-8533-8b2855a97923")
  public static class SimpleTile extends AbstractCustomTile {
  }

}
