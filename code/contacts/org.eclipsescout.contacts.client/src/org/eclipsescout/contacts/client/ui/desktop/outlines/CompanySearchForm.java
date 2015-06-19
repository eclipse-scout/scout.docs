/**
 *
 */
package org.eclipsescout.contacts.client.ui.desktop.outlines;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractSearchForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractResetButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractSearchButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipsescout.contacts.client.ui.desktop.outlines.CompanySearchForm.MainBox.ResetButton;
import org.eclipsescout.contacts.client.ui.desktop.outlines.CompanySearchForm.MainBox.SearchButton;
import org.eclipsescout.contacts.client.ui.desktop.outlines.CompanySearchForm.MainBox.TabBox;
import org.eclipsescout.contacts.client.ui.desktop.outlines.CompanySearchForm.MainBox.TabBox.FieldBox;
import org.eclipsescout.contacts.client.ui.desktop.outlines.CompanySearchForm.MainBox.TabBox.FieldBox.HomepageField;
import org.eclipsescout.contacts.client.ui.desktop.outlines.CompanySearchForm.MainBox.TabBox.FieldBox.Location;
import org.eclipsescout.contacts.client.ui.desktop.outlines.CompanySearchForm.MainBox.TabBox.FieldBox.NameField;
import org.eclipsescout.contacts.client.ui.template.formfield.AbstractLocationBox;
import org.eclipsescout.contacts.client.ui.template.formfield.AbstractLocationBox.CityField;
import org.eclipsescout.contacts.client.ui.template.formfield.AbstractLocationBox.CountryField;
import org.eclipsescout.contacts.shared.ui.desktop.outlines.CompanySearchFormData;

/**
 * @author mzi
 */
@FormData(value = CompanySearchFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class CompanySearchForm extends AbstractSearchForm {

  /**
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  public CompanySearchForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Company");
  }

  @Override
  protected void execResetSearchFilter(SearchFilter searchFilter) throws ProcessingException {
    super.execResetSearchFilter(searchFilter);
    CompanySearchFormData formData = new CompanySearchFormData();
    exportFormData(formData);
    searchFilter.setFormData(formData);
  }

  @Override
  public void startSearch() throws ProcessingException {
    startInternal(new SearchHandler());
  }

  /**
   * @return the CityField
   */
  public CityField getCityField() {
    return getFieldByClass(CityField.class);
  }

  /**
   * @return the CountryField
   */
  public CountryField getCountryField() {
    return getFieldByClass(CountryField.class);
  }

  /**
   * @return the FieldBox
   */
  public FieldBox getFieldBox() {
    return getFieldByClass(FieldBox.class);
  }

  /**
   * @return the HomepageField
   */
  public HomepageField getHomepageField() {
    return getFieldByClass(HomepageField.class);
  }

  /**
   * @return the LocationBox
   */
  public Location getLocationBox() {
    return getFieldByClass(Location.class);
  }

  /**
   * @return the MainBox
   */
  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  /**
   * @return the NameField
   */
  public NameField getNameField() {
    return getFieldByClass(NameField.class);
  }

  /**
   * @return the ResetButton
   */
  public ResetButton getResetButton() {
    return getFieldByClass(ResetButton.class);
  }

  /**
   * @return the SearchButton
   */
  public SearchButton getSearchButton() {
    return getFieldByClass(SearchButton.class);
  }

  /**
   * @return the TabBox
   */
  public TabBox getTabBox() {
    return getFieldByClass(TabBox.class);
  }

  @Order(1000.0)
  public class MainBox extends AbstractGroupBox {

    @Order(1000.0)
    public class TabBox extends AbstractTabBox {

      @Order(1000.0)
      public class FieldBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("searchCriteria");
        }

        @Order(1000.0)
        public class NameField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Name");
          }
        }

        @Order(2000.0)
        public class HomepageField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Homepage");
          }
        }

        @Order(4000.0)
        public class Location extends AbstractLocationBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Location");
          }
        }
      }
    }

    @Order(5000.0)
    public class ResetButton extends AbstractResetButton {
    }

    @Order(6000.0)
    public class SearchButton extends AbstractSearchButton {
    }
  }

  public class SearchHandler extends AbstractFormHandler {
  }
}
