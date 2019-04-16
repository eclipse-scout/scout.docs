package org.eclipse.scout.docs.snippets.dataobject;

import org.eclipse.scout.rt.platform.dataobject.AttributeName;
import org.eclipse.scout.rt.platform.dataobject.DoValue;
import org.eclipse.scout.rt.platform.dataobject.TypeName;

//tag::class[]
@TypeName("ExampleEntityEx")
public class ExampleEntityExDo extends ExampleEntityDo {

  @Override
  @AttributeName("nameEx")
  public DoValue<String> name() { // <1>
    return doValue("nameEx");
  }

//end::class[]
}
