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
package org.eclipse.scout.contacts.client.common;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.util.Locale;

import org.eclipse.scout.contacts.client.common.MapForm.MainBox.MapField;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.imagefield.AbstractImageField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.exception.ProcessingStatus;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapForm extends AbstractForm {
  private static final Logger LOG = LoggerFactory.getLogger(MapForm.class);

  private String street;
  private String city;
  private String country;

  public MapForm() {
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

  public void startModify() {
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
    return street;
  }

  @FormData
  public void setStreet(String street) {
    this.street = street;
  }

  @FormData
  public String getCity() {
    return city;
  }

  @FormData
  public void setCity(String city) {
    this.city = city;
  }

  @FormData
  public String getCountry() {
    return country;
  }

  @FormData
  public void setCountry(String country) {
    this.country = country;
  }

  @Order(1000)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(1000)
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
      protected void execInitField() {
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
        address = encode(address);

        url = "http://maps.googleapis.com/maps/api/staticmap?center=" + address + "&zoom=" + zoom + "&size=" + size + "&maptype=roadmap&sensor=false";
        LOG.info("Map image URL: {}", url);

        try (InputStream in = new URL(url).openStream()) {
          setImage(IOUtility.readBytes(in));
        }
        catch (Exception e) {
          addErrorStatus(new ProcessingStatus("Bad Link: " + url + ", please check", ProcessingStatus.ERROR));
          LOG.error("Bad URL? URL is {}", url, e);
          setImage(null);
        }
      }

      private String normalize(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
      }

      private String encode(String s) {

        try {
          return URLEncoder.encode(s, "ISO-8859-1");
        }
        catch (Exception e) {
          LOG.error("Failed to encode string '{}'", s, e);
        }

        return s;
      }
    }

    @Order(2000)
    public class OkButton extends AbstractOkButton {

      @Override
      protected void execClickAction() {
        getMapField().clearErrorStatus();
        super.execClickAction();
      }

      @Override
      protected boolean getConfiguredFocusable() {
        return false;
      }
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
  }
}
