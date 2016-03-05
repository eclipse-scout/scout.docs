package org.eclipse.scout.docs.snippets.platform.config;

import static org.junit.Assert.assertEquals;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.config.PlatformConfigProperties.ApplicationNameProperty;
import org.junit.Test;

public class ScoutConfigTest {

  @Test
  public void testMyCustomTimeoutProperty() {
    //Beans defined in Test needs to be registered:
    BEANS.getBeanManager().registerClass(MyCustomTimeoutProperty.class);

    //tag::getPropertyValue[]
    Long value = CONFIG.getPropertyValue(MyCustomTimeoutProperty.class);
    //end::getPropertyValue[]

    Long expected = 3600L;
    assertEquals(expected, value);

    BEANS.getBeanManager().unregisterClass(MyCustomTimeoutProperty.class);
  }

  @Test
  public void testApplicationNamePropertyDefault() {
    String value = CONFIG.getPropertyValue(ApplicationNameProperty.class);
    assertEquals("My Scout Application", value);
  }

  @Test
  public void testApplicationNameConstant() {
    BEANS.getBeanManager().registerClass(ApplicationNameConstant.class);

    String value = CONFIG.getPropertyValue(ApplicationNameProperty.class);
    assertEquals("Contacts Application", value);

    BEANS.getBeanManager().unregisterClass(ApplicationNameConstant.class);
  }

  @Test
  public void testApplicationNamePropertyRedirection() {
    BEANS.getBeanManager().registerClass(ApplicationNamePropertyRedirection.class);

    String value = CONFIG.getPropertyValue(ApplicationNameProperty.class);
    assertEquals("My Project Application", value);

    BEANS.getBeanManager().unregisterClass(ApplicationNamePropertyRedirection.class);
  }

}
