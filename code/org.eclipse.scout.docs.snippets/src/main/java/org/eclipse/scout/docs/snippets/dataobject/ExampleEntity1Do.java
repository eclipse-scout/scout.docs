package org.eclipse.scout.docs.snippets.dataobject;

import javax.annotation.Generated;

import org.eclipse.scout.rt.dataobject.DoValue;
import org.eclipse.scout.rt.dataobject.TypeName;

//tag::class[]
@TypeName("ExampleEntity1")
public class ExampleEntity1Do extends AbstractExampleEntityDo {
  public DoValue<String> name1Ex() {
    return doValue("name1Ex");
  }

//end::class[]

  /* **************************************************************************
   * GENERATED CONVENIENCE METHODS
   * *************************************************************************/

  @Generated("DoConvenienceMethodsGenerator")
  public ExampleEntity1Do withName1Ex(String name1Ex) {
    name1Ex().set(name1Ex);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public String getName1Ex() {
    return name1Ex().get();
  }
}
