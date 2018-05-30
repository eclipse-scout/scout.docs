package org.eclipse.scout.docs.snippets.dataobject;

import javax.annotation.Generated;

import org.eclipse.scout.rt.platform.dataobject.DoValue;
import org.eclipse.scout.rt.platform.dataobject.TypeName;

//tag::class[]
@TypeName("ExampleEntity2")
public class ExampleEntity2Do extends AbstractExampleEntityDo {
  public DoValue<String> name2Ex() {
    return doValue("name2Ex");
  }

//end::class[]

  /* **************************************************************************
   * GENERATED CONVENIENCE METHODS
   * *************************************************************************/

  @Generated("DoConvenienceMethodsGenerator")
  public ExampleEntity2Do withName2Ex(String name2Ex) {
    name2Ex().set(name2Ex);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public String getName2Ex() {
    return name2Ex().get();
  }
}
