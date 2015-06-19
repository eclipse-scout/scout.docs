/**
 *
 */
package org.eclipsescout.contacts.client.ui.forms;

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
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.imagebox.AbstractImageField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.contacts.client.ui.forms.MapForm.MainBox.MapField;

/**
 * @author mzi
 */
public class MapForm extends AbstractForm {

  private String m_street;
  private String m_city;
  private String m_country;

  /**
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
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

  /**
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  public void startModify() throws ProcessingException {
    startInternal(new ModifyHandler());
  }

  /**
   * @return the MainBox
   */
  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  /**
   * @return the MapField
   */
  public MapField getMapField() {
    return getFieldByClass(MapField.class);
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
        String size = "" + getConfiguredHeightInPixel() + "x" + getConfiguredWidthInPixel();
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
          System.out.println(address);
          url = "http://maps.googleapis.com/maps/api/staticmap?center=" + URLEncoder.encode(address, "ISO-8859-1") + "&zoom=" + zoom + "&size=" + size + "&maptype=roadmap&sensor=false";
          setImage(IOUtility.getContent((new URL(url)).openStream()));
        }
        catch (Exception e) {
          setErrorStatus(new ProcessingStatus("Bad Link: " + url + ", please check", ProcessingStatus.ERROR));
          setImage(null);
          e.printStackTrace();
        }
      }

      private String normalize(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
      }
    }

    @Order(100000.0)
    public class OkButton extends AbstractOkButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
  }

  /**
   * @return the Street
   */
  @FormData
  public String getStreet() {
    return m_street;
  }

  /**
   * @param street
   *          the Street to set
   */
  @FormData
  public void setStreet(String street) {
    m_street = street;
  }

  /**
   * @return the City
   */
  @FormData
  public String getCity() {
    return m_city;
  }

  /**
   * @param city
   *          the City to set
   */
  @FormData
  public void setCity(String city) {
    m_city = city;
  }

  /**
   * @return the Country
   */
  @FormData
  public String getCountry() {
    return m_country;
  }

  /**
   * @param country
   *          the Country to set
   */
  @FormData
  public void setCountry(String country) {
    m_country = country;
  }
}
