/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 * 
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipsescout.demo.widgets.client.ui.forms;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.bigintegerfield.AbstractBigIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.doublefield.AbstractDoubleField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.placeholder.AbstractPlaceholderField;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.demo.widgets.client.ui.forms.NumberFieldsDecimalFieldsForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.NumberFieldsDecimalFieldsForm.MainBox.HighestValueButton;
import org.eclipsescout.demo.widgets.client.ui.forms.NumberFieldsDecimalFieldsForm.MainBox.SmallestValueButton;
import org.eclipsescout.demo.widgets.client.ui.forms.NumberFieldsDecimalFieldsForm.MainBox.ValueFieldTypeBox;
import org.eclipsescout.demo.widgets.client.ui.forms.NumberFieldsDecimalFieldsForm.MainBox.ValueFieldTypeBox.DecimalFieldsBox;
import org.eclipsescout.demo.widgets.client.ui.forms.NumberFieldsDecimalFieldsForm.MainBox.ValueFieldTypeBox.DecimalFieldsBox.BigDecimalField;
import org.eclipsescout.demo.widgets.client.ui.forms.NumberFieldsDecimalFieldsForm.MainBox.ValueFieldTypeBox.DecimalFieldsBox.Calculator0Box;
import org.eclipsescout.demo.widgets.client.ui.forms.NumberFieldsDecimalFieldsForm.MainBox.ValueFieldTypeBox.DecimalFieldsBox.Calculator0Box.Equals0Field;
import org.eclipsescout.demo.widgets.client.ui.forms.NumberFieldsDecimalFieldsForm.MainBox.ValueFieldTypeBox.DecimalFieldsBox.Calculator0Box.FirstValue0Field;
import org.eclipsescout.demo.widgets.client.ui.forms.NumberFieldsDecimalFieldsForm.MainBox.ValueFieldTypeBox.DecimalFieldsBox.Calculator0Box.SecondValue0Field;
import org.eclipsescout.demo.widgets.client.ui.forms.NumberFieldsDecimalFieldsForm.MainBox.ValueFieldTypeBox.DecimalFieldsBox.DoubleField;
import org.eclipsescout.demo.widgets.client.ui.forms.NumberFieldsDecimalFieldsForm.MainBox.ValueFieldTypeBox.DecimalFieldsBox.Placeholder0Field;
import org.eclipsescout.demo.widgets.client.ui.forms.NumberFieldsDecimalFieldsForm.MainBox.ValueFieldTypeBox.NumberFieldsBox;
import org.eclipsescout.demo.widgets.client.ui.forms.NumberFieldsDecimalFieldsForm.MainBox.ValueFieldTypeBox.NumberFieldsBox.BigIntegerField;
import org.eclipsescout.demo.widgets.client.ui.forms.NumberFieldsDecimalFieldsForm.MainBox.ValueFieldTypeBox.NumberFieldsBox.CalculatorBox;
import org.eclipsescout.demo.widgets.client.ui.forms.NumberFieldsDecimalFieldsForm.MainBox.ValueFieldTypeBox.NumberFieldsBox.CalculatorBox.EqualsField;
import org.eclipsescout.demo.widgets.client.ui.forms.NumberFieldsDecimalFieldsForm.MainBox.ValueFieldTypeBox.NumberFieldsBox.CalculatorBox.FirstValueField;
import org.eclipsescout.demo.widgets.client.ui.forms.NumberFieldsDecimalFieldsForm.MainBox.ValueFieldTypeBox.NumberFieldsBox.CalculatorBox.SecondValueField;
import org.eclipsescout.demo.widgets.client.ui.forms.NumberFieldsDecimalFieldsForm.MainBox.ValueFieldTypeBox.NumberFieldsBox.IntegerField;
import org.eclipsescout.demo.widgets.client.ui.forms.NumberFieldsDecimalFieldsForm.MainBox.ValueFieldTypeBox.NumberFieldsBox.LongField;
import org.eclipsescout.demo.widgets.client.ui.forms.NumberFieldsDecimalFieldsForm.MainBox.ValueFieldTypeBox.NumberFieldsBox.PlaceholderField;

public class NumberFieldsDecimalFieldsForm extends AbstractForm implements IPageForm {

  public NumberFieldsDecimalFieldsForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("NumberFieldsDecimalFields");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  public BigDecimalField getBigDecimalField() {
    return getFieldByClass(BigDecimalField.class);
  }

  public BigIntegerField getBigIntegerField() {
    return getFieldByClass(BigIntegerField.class);
  }

  public Calculator0Box getCalculator0Box() {
    return getFieldByClass(Calculator0Box.class);
  }

  public CalculatorBox getCalculatorBox() {
    return getFieldByClass(CalculatorBox.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public DecimalFieldsBox getDecimalFieldsBox() {
    return getFieldByClass(DecimalFieldsBox.class);
  }

  public DoubleField getDoubleField() {
    return getFieldByClass(DoubleField.class);
  }

  public Equals0Field getEquals0Field() {
    return getFieldByClass(Equals0Field.class);
  }

  public EqualsField getEqualsField() {
    return getFieldByClass(EqualsField.class);
  }

  public FirstValue0Field getFirstValue0Field() {
    return getFieldByClass(FirstValue0Field.class);
  }

  public FirstValueField getFirstValueField() {
    return getFieldByClass(FirstValueField.class);
  }

  public HighestValueButton getHighestValueButton() {
    return getFieldByClass(HighestValueButton.class);
  }

  public IntegerField getIntegerField() {
    return getFieldByClass(IntegerField.class);
  }

  public LongField getLongField() {
    return getFieldByClass(LongField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public NumberFieldsBox getNumberFieldsBox() {
    return getFieldByClass(NumberFieldsBox.class);
  }

  public Placeholder0Field getPlaceholder0Field() {
    return getFieldByClass(Placeholder0Field.class);
  }

  public PlaceholderField getPlaceholderField() {
    return getFieldByClass(PlaceholderField.class);
  }

  public SecondValue0Field getSecondValue0Field() {
    return getFieldByClass(SecondValue0Field.class);
  }

  public SecondValueField getSecondValueField() {
    return getFieldByClass(SecondValueField.class);
  }

  public SmallestValueButton getSmallestValueButton() {
    return getFieldByClass(SmallestValueButton.class);
  }

  public ValueFieldTypeBox getValueFieldTypeBox() {
    return getFieldByClass(ValueFieldTypeBox.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class ValueFieldTypeBox extends AbstractTabBox {

      @Order(10.0)
      public class NumberFieldsBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("NumberFields");
        }

        @Order(10.0)
        public class IntegerField extends AbstractIntegerField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("IntegerField");
          }
        }

        @Order(20.0)
        public class BigIntegerField extends AbstractBigIntegerField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("BigIntegerField");
          }
        }

        @Order(30.0)
        public class LongField extends AbstractLongField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("LongField");
          }
        }

        @Order(40.0)
        public class PlaceholderField extends AbstractPlaceholderField {

          @Override
          protected int getConfiguredGridW() {
            return 2;
          }

          @Override
          protected int getConfiguredHeightInPixel() {
            return 20;
          }
        }

        @Order(50.0)
        public class CalculatorBox extends AbstractSequenceBox {

          @Override
          protected int getConfiguredGridW() {
            return 2;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Calculator");
          }

          @Order(10.0)
          public class FirstValueField extends AbstractBigIntegerField {

            @Override
            protected void execChangedValue() throws ProcessingException {
              if (getFirstValueField().getValue() != null && getSecondValueField().getValue() != null) {
                getEqualsField().setValue(getFirstValueField().getValue().add(getSecondValueField().getValue()));
              }
            }

            @Override
            protected void execInitField() throws ProcessingException {
              setValue(new BigInteger("2455781"));
            }
          }

          @Order(30.0)
          public class SecondValueField extends AbstractBigIntegerField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("plus");
            }

            @Override
            protected void execChangedValue() throws ProcessingException {
              if (getFirstValueField().getValue() != null && getSecondValueField().getValue() != null) {
                getEqualsField().setValue(getFirstValueField().getValue().add(getSecondValueField().getValue()));
              }
            }

            @Override
            protected void execInitField() throws ProcessingException {
              setValue(new BigInteger("478873"));
            }
          }

          @Order(40.0)
          public class EqualsField extends AbstractBigIntegerField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("LogicEQ");
            }

            @Override
            protected void execInitField() throws ProcessingException {
              setValue(new BigInteger("6334654"));
            }
          }
        }
      }

      @Order(20.0)
      public class DecimalFieldsBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DecimalFields");
        }

        @Order(10.0)
        public class DoubleField extends AbstractDoubleField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("DoubleField");
          }

          @Override
          protected int getConfiguredMaxFractionDigits() {
            return 10;
          }

          @Override
          protected int getConfiguredMinFractionDigits() {
            return 0;
          }
        }

        @Order(20.0)
        public class BigDecimalField extends AbstractBigDecimalField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("BigDecimalField");
          }

          @Override
          protected int getConfiguredMaxFractionDigits() {
            return 10;
          }

          @Override
          protected int getConfiguredMinFractionDigits() {
            return 0;
          }
        }

        @Order(30.0)
        public class Placeholder0Field extends AbstractPlaceholderField {

          @Override
          protected int getConfiguredGridW() {
            return 2;
          }

          @Override
          protected int getConfiguredHeightInPixel() {
            return 49;
          }
        }

        @Order(40.0)
        public class Calculator0Box extends AbstractSequenceBox {

          @Override
          protected int getConfiguredGridW() {
            return 2;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Calculator");
          }

          @Order(10.0)
          public class FirstValue0Field extends AbstractBigDecimalField {

            @Override
            protected void execChangedValue() throws ProcessingException {
              if (getFirstValue0Field().getValue() != null && getSecondValue0Field().getValue() != null) {
                getEquals0Field().setValue(getFirstValue0Field().getValue().add(getSecondValue0Field().getValue()));
              }
            }

            @Override
            protected void execInitField() throws ProcessingException {
              setValue(new BigDecimal(55.534));
            }
          }

          @Order(30.0)
          public class SecondValue0Field extends AbstractBigDecimalField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("plus");
            }

            @Override
            protected void execChangedValue() throws ProcessingException {
              if (getFirstValue0Field().getValue() != null && getSecondValue0Field().getValue() != null) {
                getEquals0Field().setValue(getFirstValue0Field().getValue().add(getSecondValue0Field().getValue()));
              }
            }

            @Override
            protected void execInitField() throws ProcessingException {
              setValue(new BigDecimal(64.267));
            }
          }

          @Order(40.0)
          public class Equals0Field extends AbstractBigDecimalField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("LogicEQ");
            }

            @Override
            protected void execInitField() throws ProcessingException {
              setValue(new BigDecimal(64.267 + 55.534));
            }
          }
        }
      }
    }

    @Order(20.0)
    public class HighestValueButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("HighestValue");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        getIntegerField().setValue(Integer.MAX_VALUE);
        getBigIntegerField().setDisplayText("can get as large as you want");
        getLongField().setValue(Long.MAX_VALUE);

        getDoubleField().setValue(Double.MAX_VALUE);
        getBigDecimalField().setDisplayText("can get as large as you want");
      }
    }

    @Order(30.0)
    public class SmallestValueButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SmallestValue");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        getIntegerField().setValue(Integer.MIN_VALUE);
        getBigIntegerField().setDisplayText("can get as small as you want");
        getLongField().setValue(Long.MIN_VALUE);

        getDoubleField().setValue(Double.MIN_VALUE);
        getBigDecimalField().setDisplayText("can get as small as you want");
      }
    }

    @Order(40.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
