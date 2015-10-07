/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.client.template;

import java.net.URL;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.util.Locale;

import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.ProcessingStatus;
import org.eclipse.scout.contacts.client.template.MapForm.MainBox.MapField;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.imagebox.AbstractImageField;
import org.eclipse.scout.rt.shared.TEXTS;

public class MapForm extends AbstractForm {

  private String m_street;
  private String m_city;
  private String m_country;

  public MapForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Map");
  }

  public void startModify() throws ProcessingException {
    startInternal(new ModifyHandler());
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public MapField getMapField() {
    return getFieldByClass(MapField.class);
  }

  @FormData
  public String getStreet() {
    return m_street;
  }

  @FormData
  public void setStreet(String street) {
    m_street = street;
  }

  @FormData
  public String getCity() {
    return m_city;
  }

  @FormData
  public void setCity(String city) {
    m_city = city;
  }

  @FormData
  public String getCountry() {
    return m_country;
  }

  @FormData
  public void setCountry(String country) {
    m_country = country;
  }

  @Order(1000.0)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(1000.0)
    public class MapField extends AbstractImageField {

      @Override
      protected int getConfiguredGridH() {
        return 6;
      }

      @Override
      protected int getConfiguredHeightInPixel() {
        return 400;
      }

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      @Override
      protected int getConfiguredWidthInPixel() {
        return 400;
      }

      @Override
      protected void execInitField() throws ProcessingException {
        String size = "" + (getConfiguredHeightInPixel() - 20) + "x" + (getConfiguredWidthInPixel() - 20);
        String address = (new Locale("", getCountry())).getDisplayCountry();
        String zoom = "7";
        String url = null;

        if (StringUtility.hasText(getCity())) {
          address += "," + getCity();
          zoom = "14";

          if (StringUtility.hasText(getStreet())) {
            address += "," + getStreet();
            zoom = "16";
          }
        }

        address = normalize(address);

        try {
          url = "http://maps.googleapis.com/maps/api/staticmap?center=" + URLEncoder.encode(address, "ISO-8859-1") + "&zoom=" + zoom + "&size=" + size + "&maptype=roadmap&sensor=false";
          setImage(IOUtility.getContent((new URL(url)).openStream()));
        }
        catch (Exception e) {
          addErrorStatus(new ProcessingStatus("Bad Link: " + url + ", please check", ProcessingStatus.ERROR));
          setImage(null);
        }
      }

      private String normalize(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
      }
    }

    @Order(2000.0)
    public class OkButton extends AbstractOkButton {
      @Override
      protected boolean getConfiguredFocusable() {
        return false;
      }
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
  }
}
