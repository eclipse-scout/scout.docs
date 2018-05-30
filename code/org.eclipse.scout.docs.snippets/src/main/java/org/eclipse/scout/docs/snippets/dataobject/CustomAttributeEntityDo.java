package org.eclipse.scout.docs.snippets.dataobject;

import java.util.Date;

import javax.annotation.Generated;

import org.eclipse.scout.rt.platform.dataobject.AttributeName;
import org.eclipse.scout.rt.platform.dataobject.DoEntity;
import org.eclipse.scout.rt.platform.dataobject.DoValue;
import org.eclipse.scout.rt.platform.dataobject.IValueFormatConstants;
import org.eclipse.scout.rt.platform.dataobject.TypeName;
import org.eclipse.scout.rt.platform.dataobject.ValueFormat;

@TypeName("CustomAttributeEntity")
public class CustomAttributeEntityDo extends DoEntity {

  //tag::customAttributeName[]
  @AttributeName("myCustomName")
  public DoValue<String> name() {
    return doValue("myCustomName"); // <1>
  }

  //end::customAttributeName[]

  //tag::customValueFormat[]
  @ValueFormat(pattern = IValueFormatConstants.DATE_PATTERN)
  public DoValue<Date> date() {
    return doValue("date");
  }

  //end::customValueFormat[]

  /* **************************************************************************
   * GENERATED CONVENIENCE METHODS
   * *************************************************************************/

  @Generated("DoConvenienceMethodsGenerator")
  public CustomAttributeEntityDo withName(String name) {
    name().set(name);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public String getName() {
    return name().get();
  }

  @Generated("DoConvenienceMethodsGenerator")
  public CustomAttributeEntityDo withDate(Date date) {
    date().set(date);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public Date getDate() {
    return date().get();
  }
}
