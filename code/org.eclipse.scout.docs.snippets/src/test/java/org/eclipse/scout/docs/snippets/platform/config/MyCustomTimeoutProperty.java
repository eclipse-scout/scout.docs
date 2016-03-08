package org.eclipse.scout.docs.snippets.platform.config;

//tag::ConfigProperties[]
import org.eclipse.scout.rt.platform.config.AbstractLongConfigProperty;

/**
 * Property of data type {@link Long} with key 'my.custom.timeout' and default value '3600L'.
 */
public class MyCustomTimeoutProperty extends AbstractLongConfigProperty {

  @Override
  public String getKey() {
    return "my.custom.timeout"; //<1>
  }

  @Override
  protected Long getDefaultValue() {
    return 3600L; //<2>
  }
}
//end::ConfigProperties[]
