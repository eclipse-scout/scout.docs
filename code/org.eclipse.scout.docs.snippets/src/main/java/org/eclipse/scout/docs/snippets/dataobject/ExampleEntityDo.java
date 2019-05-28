package org.eclipse.scout.docs.snippets.dataobject;

import java.util.Collection;
import java.util.List;

import javax.annotation.Generated;

import org.eclipse.scout.rt.dataobject.DoEntity;
import org.eclipse.scout.rt.dataobject.DoList;
import org.eclipse.scout.rt.dataobject.DoValue;
import org.eclipse.scout.rt.dataobject.TypeName;
import org.eclipse.scout.rt.dataobject.TypeVersion;

//tag::class[]
@TypeName("ExampleEntity")
@TypeVersion("scout-8.0.0.027")
public class ExampleEntityDo extends DoEntity {

  public DoValue<String> name() { // <1>
    return doValue("name");
  }

  public DoList<Integer> values() { // <2>
    return doList("values");
  }

//end::class[]

  /* **************************************************************************
   * GENERATED CONVENIENCE METHODS
   * *************************************************************************/

  @Generated("DoConvenienceMethodsGenerator")
  public ExampleEntityDo withName(String name) {
    name().set(name);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public String getName() {
    return name().get();
  }

  @Generated("DoConvenienceMethodsGenerator")
  public ExampleEntityDo withValues(Collection<? extends Integer> values) {
    values().updateAll(values);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public ExampleEntityDo withValues(Integer... values) {
    values().updateAll(values);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public List<Integer> getValues() {
    return values().get();
  }
}
