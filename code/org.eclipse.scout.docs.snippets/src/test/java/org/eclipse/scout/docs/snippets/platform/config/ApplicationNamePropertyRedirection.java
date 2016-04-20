package org.eclipse.scout.docs.snippets.platform.config;

//tag::code[]
import org.eclipse.scout.rt.platform.IgnoreBean;
import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.platform.config.PlatformConfigProperties.ApplicationNameProperty;

//end::code[]
@IgnoreBean
//tag::code[]
@Replace
public class ApplicationNamePropertyRedirection extends ApplicationNameProperty {

  @Override
  public String getKey() {
    return "myproject.application.name";
  }
}
//end::code[]