package org.eclipse.scout.widgets.client.ui.forms;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
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
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.ColorSchemeField;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.ConfigurationBox.TilesField;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.ConfigurationBox.TilesField.Tiles;
import org.eclipse.scout.widgets.client.ui.tiles.AbstractSimpleTile;

@ClassId("1c8091c8-6a6b-4860-9d12-e8bf6cab115a")
public class TilesFieldForm extends AbstractForm implements IAdvancedExampleForm {

  @Override
  public void startPageForm() {
    start();
  }

  public ColorSchemeField getColorSchemeField() {
    return getFieldByClass(ColorSchemeField.class);
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

        @Order(10)
        @ClassId("1f474645-120a-4386-b826-13a395cf2924")
        public class GridColumnCountField extends AbstractIntegerField {

          @Override
          protected String getConfiguredLabel() {
            return "Grid Column Count";
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

        @Order(30)
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
    }

    @Order(50)
    public class CloseButton extends AbstractCloseButton {
    }
  }

}
