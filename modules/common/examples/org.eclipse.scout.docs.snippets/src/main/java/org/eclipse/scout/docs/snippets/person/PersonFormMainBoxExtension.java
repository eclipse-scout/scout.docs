package org.eclipse.scout.docs.snippets.person;

import org.eclipse.scout.docs.snippets.person.PersonForm.MainBox;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.extension.ui.form.fields.groupbox.AbstractGroupBoxExtension;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;

//tag::PersonFormMainBoxExtension[]
/**
 * Extension for the MainBox of the PersonForm
 */
@Data(PersonFormMainBoxExtensionData.class)
public class PersonFormMainBoxExtension extends AbstractGroupBoxExtension<MainBox> {

  public PersonFormMainBoxExtension(MainBox ownerBox) {
    super(ownerBox);
  }

  @Order(2000)
  @ClassId("fda7cd67-0df1-4194-9d70-22a9b3ce890d")
  public class SalaryField extends AbstractBigDecimalField {
  }

  @Order(3000)
  @ClassId("478037fb-759f-4fa1-b737-c77f903c6881")
  public class BirthdayField extends AbstractDateField {
  }
}
//end::PersonFormMainBoxExtension[]
