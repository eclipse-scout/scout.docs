package org.eclipse.scout.widgets.client.ui.forms;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.tilesfield.AbstractTilesField;
import org.eclipse.scout.rt.client.ui.tile.AbstractTiles;
import org.eclipse.scout.rt.client.ui.tile.ITile;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.data.tile.ITileColorScheme;
import org.eclipse.scout.rt.shared.data.tile.TileColorScheme;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.services.lookup.TileColorSchemeLookupCall;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.ConfigurationBox.ActionsBox.AddButton;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.ConfigurationBox.ActionsBox.AddManyButton;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.ConfigurationBox.ActionsBox.DeleteSelectedButton;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.ConfigurationBox.ActionsBox.SelectAllButton;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.ConfigurationBox.ActionsBox.SelectNextButton;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.ColorSchemeField;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.MultiSelectField;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.ScrollableField;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.SelectableField;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.ConfigurationBox.TilesField;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.ConfigurationBox.TilesField.Tiles;
import org.eclipse.scout.widgets.client.ui.tiles.AbstractSimpleTile;

@ClassId("1c8091c8-6a6b-4860-9d12-e8bf6cab115a")
public class TilesFieldForm extends AbstractForm implements IAdvancedExampleForm {

  private int m_tilesAddedCount = 0;

  @Override
  public void startPageForm() {
    start();
  }

  public ColorSchemeField getColorSchemeField() {
    return getFieldByClass(ColorSchemeField.class);
  }

  public AddButton getAddButton() {
    return getFieldByClass(AddButton.class);
  }

  public AddManyButton getAddManyButton() {
    return getFieldByClass(AddManyButton.class);
  }

  public DeleteSelectedButton getDeleteSelectedButton() {
    return getFieldByClass(DeleteSelectedButton.class);
  }

  public SelectableField getSelectableField() {
    return getFieldByClass(SelectableField.class);
  }

  public MultiSelectField getMultiSelectField() {
    return getFieldByClass(MultiSelectField.class);
  }

  public SelectAllButton getSelectAllButton() {
    return getFieldByClass(SelectAllButton.class);
  }

  public ScrollableField getScrollableField() {
    return getFieldByClass(ScrollableField.class);
  }

  public SelectNextButton getSelectNextButton() {
    return getFieldByClass(SelectNextButton.class);
  }

  public TilesField getTilesField() {
    return getFieldByClass(TilesField.class);
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
    public class ConfigurationBox extends AbstractGroupBox {

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

        @ClassId("269a1b03-5af1-4cf8-a063-20a9bbe85ae8")
        public class Tiles extends AbstractTiles {

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
      @ClassId("fc28a174-b683-4aae-8ec9-7f73dc2b45e2")
      public class PropertiesGroupBox extends AbstractGroupBox {

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

      @Order(3000)
      @ClassId("c5923038-bc5d-4248-b70c-c9dcf7dd7b6f")
      public class ActionsBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return "Actions";
        }

        @Order(1000)
        @ClassId("1635b251-77f5-4fc7-adc8-192aa165ade3")
        public class AddButton extends AbstractButton {
          @Override
          protected String getConfiguredLabel() {
            return "Add tile";
          }

          @Override
          protected void execClickAction() {
            SimpleTile tile = new SimpleTile();
            tile.setLabel("New tile " + m_tilesAddedCount++);
            getTilesField().getTiles().addTile(tile);
          }
        }

        @Order(1000)
        @ClassId("0b26759f-7d17-45ea-96ba-c85ee3dbc735")
        public class AddManyButton extends AbstractButton {
          @Override
          protected String getConfiguredLabel() {
            return "Add many tiles";
          }

          @Override
          protected void execClickAction() {
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
        public class SelectNextButton extends AbstractButton {
          @Override
          protected String getConfiguredLabel() {
            return "Select next tile";
          }

          @Override
          protected void execClickAction() {
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
        public class SelectAllButton extends AbstractButton {
          @Override
          protected String getConfiguredLabel() {
            return "Select all tiles";
          }

          @Override
          protected void execClickAction() {
            getTilesField().getTiles().selectAllTiles();
          }
        }

        @Order(1300)
        @ClassId("64f7eb9c-bedd-4680-8b94-555c9c64fd97")
        public class DeleteSelectedButton extends AbstractButton {
          @Override
          protected String getConfiguredLabel() {
            return "Delete selected tiles";
          }

          @Override
          protected void execClickAction() {
            getTilesField().getTiles().deleteTiles(getTilesField().getTiles().getSelectedTiles());
          }
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
