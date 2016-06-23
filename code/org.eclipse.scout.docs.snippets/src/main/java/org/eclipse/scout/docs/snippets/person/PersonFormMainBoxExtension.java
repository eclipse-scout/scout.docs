package org.eclipse.scout.docs.snippets.person;

import org.eclipse.scout.docs.snippets.person.PersonForm.MainBox;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.extension.ui.form.fields.groupbox.AbstractGroupBoxExtension;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.platform.Order;

//tag::PersonFormMainBoxExtension[]
/**
 * Extension for the MainBox of the PersonForm
 */
@Data(PersonFormMainBoxExtensionData.class)
public class PersonFormMainBoxExtension extends AbstractGroupBoxExtension<PersonForm.MainBox> {

  public PersonFormMainBoxExtension(MainBox ownerBox) {
    super(ownerBox);
  }

  @Order(2000)
  public class SalaryField extends AbstractBigDecimalField {
  }

  @Order(3000)
  public class BirthdayField extends AbstractDateField {
  }
}
//end::PersonFormMainBoxExtension[]
