package org.eclipse.scout.docs.snippets.dataobject;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.Generated;

import org.eclipse.scout.rt.platform.dataobject.DoEntity;
import org.eclipse.scout.rt.platform.dataobject.DoList;
import org.eclipse.scout.rt.platform.dataobject.DoValue;

//tag::class[]
public class ExampleDoEntityListDo extends DoEntity {
  public DoList<AbstractExampleEntityDo> listAttribute() {
    return doList("listAttribute");
  }

  public DoValue<AbstractExampleEntityDo> singleAttribute() {
    return doValue("singleAttribute");
  }

//end::class[]

  /* **************************************************************************
   * GENERATED CONVENIENCE METHODS
   * *************************************************************************/

  @Generated("DoConvenienceMethodsGenerator")
  public ExampleDoEntityListDo withListAttribute(Collection<AbstractExampleEntityDo> listAttribute) {
    listAttribute().clear();
    listAttribute().get().addAll(listAttribute);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public ExampleDoEntityListDo withListAttribute(AbstractExampleEntityDo... listAttribute) {
    return withListAttribute(Arrays.asList(listAttribute));
  }

  @Generated("DoConvenienceMethodsGenerator")
  public List<AbstractExampleEntityDo> getListAttribute() {
    return listAttribute().get();
  }

  @Generated("DoConvenienceMethodsGenerator")
  public ExampleDoEntityListDo withSingleAttribute(AbstractExampleEntityDo singleAttribute) {
    singleAttribute().set(singleAttribute);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public AbstractExampleEntityDo getSingleAttribute() {
    return singleAttribute().get();
  }
}
