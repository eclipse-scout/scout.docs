package org.eclipse.scout.docs.snippets.dataobject;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.Generated;

import org.eclipse.scout.rt.platform.dataobject.DoEntity;
import org.eclipse.scout.rt.platform.dataobject.DoList;
import org.eclipse.scout.rt.platform.dataobject.DoValue;
import org.eclipse.scout.rt.platform.dataobject.TypeName;

//tag::class[]
@TypeName("ExampleEntity")
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
  public ExampleEntityDo withValues(Collection<Integer> values) {
    values().clear();
    values().get().addAll(values);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public ExampleEntityDo withValues(Integer... values) {
    return withValues(Arrays.asList(values));
  }

  @Generated("DoConvenienceMethodsGenerator")
  public List<Integer> getValues() {
    return values().get();
  }
}
