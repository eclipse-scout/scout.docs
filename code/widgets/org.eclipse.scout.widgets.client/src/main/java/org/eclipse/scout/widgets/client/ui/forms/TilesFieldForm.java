package org.eclipse.scout.widgets.client.ui.forms;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tilesfield.AbstractTilesField;
import org.eclipse.scout.rt.client.ui.tile.AbstractTiles;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.TilesFieldForm.MainBox.ConfigurationBox.TilesField;
import org.eclipse.scout.widgets.client.ui.tiles.AbstractSimpleTile;

@ClassId("1c8091c8-6a6b-4860-9d12-e8bf6cab115a")
public class TilesFieldForm extends AbstractForm implements IAdvancedExampleForm {

  @Override
  public void startPageForm() {
    start();
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
      public class PropertiesGroupBox extends AbstractGroupBox {

        @Order(10)
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
