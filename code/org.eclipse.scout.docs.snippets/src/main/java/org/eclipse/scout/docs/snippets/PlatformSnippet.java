/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.docs.snippets;

import java.util.List;
import java.util.Set;

import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.BeanMetaData;
import org.eclipse.scout.rt.platform.BeanProducer;
import org.eclipse.scout.rt.platform.IBean;
import org.eclipse.scout.rt.platform.IBeanInstanceProducer;
import org.eclipse.scout.rt.platform.IPlatform;
import org.eclipse.scout.rt.platform.IPlatformListener;
import org.eclipse.scout.rt.platform.Platform;
import org.eclipse.scout.rt.platform.PlatformEvent;
import org.eclipse.scout.rt.platform.config.AbstractStringConfigProperty;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.inventory.ClassInventory;
import org.eclipse.scout.rt.platform.inventory.IClassInfo;
import org.eclipse.scout.rt.platform.inventory.IClassInventory;
import org.eclipse.scout.rt.platform.service.IService;

public final class PlatformSnippet {
  //tag::PlatformListener[]
  public class MyListener implements IPlatformListener {
    @Override
    public void stateChanged(PlatformEvent event) {
      if (event.getState() == IPlatform.State.PlatformStarted) {
        // do some work as soon as the platform has been started completely
      }
    }
  }
  //end::PlatformListener[]

  //tag::RegisterBeansListener[]
  public class RegisterBeansListener implements IPlatformListener {
    @Override
    public void stateChanged(PlatformEvent event) {
      if (event.getState() == IPlatform.State.BeanManagerPrepared) {
        // register the class directly
        Platform.get().getBeanManager().registerClass(BeanSingletonClass.class);

        // Or register with meta information
        BeanMetaData beanData = new BeanMetaData(BeanClass.class).withApplicationScoped(true);
        Platform.get().getBeanManager().registerBean(beanData);
      }
    }
  }
  //end::RegisterBeansListener[]

  //tag::BeanSingletonClass[]
  @ApplicationScoped
  public class BeanSingletonClass {
  }
  //end::BeanSingletonClass[]

  //tag::BeanClass[]
  @Bean
  public class BeanClass {
  }
  //end::BeanClass[]

  //tag::BeanProducer[]
  @Bean
  @BeanProducer(MyCustomProducer.class)
  public class BeanWithCustomProducer {
  }

  public class MyCustomProducer<T> implements IBeanInstanceProducer<T> {
    @Override
    public T produce(IBean<T> bean) {
      // create instance of bean
      return null;
    }
  }
  //end::BeanProducer[]

  //tag::ConfigProperties[]
  /**
   * Defines a new property of data type {@link String} with key 'my.application.key' and default value 'defaultValue'.
   */
  public class MyCustomStringProperty extends AbstractStringConfigProperty {

    @Override
    public String getKey() {
      return "my.application.key";
    }

    @Override
    protected String getDefaultValue() {
      return "defaultValue";
    }
  }

  /**
   * @return The current value of the property 'my.application.key'.
   */
  private String getMyCustomPropertyValue() {
    return CONFIG.getPropertyValue(MyCustomStringProperty.class);
  }
  //end::ConfigProperties[]

  @SuppressWarnings("unused")
  private void snippets() {
    //tag::ClassInventory[]
    IClassInventory classInventory = ClassInventory.get();

    // get all classes below IService
    Set<IClassInfo> services = classInventory.getAllKnownSubClasses(IService.class);

    // get all classes having a Bean annotation (directly on them self).
    Set<IClassInfo> classesHavingBeanAnnot = classInventory.getKnownAnnotatedTypes(Bean.class);
    //end::ConfigProperties[]
    //tag::BeanRetrieval[]
    BeanSingletonClass bean = BEANS.get(BeanSingletonClass.class);
    BeanClass beanOrNull = BEANS.opt(BeanClass.class);
    List<BeanWithCustomProducer> all = BEANS.all(BeanWithCustomProducer.class);
    //end::BeanRetrieval[]
  }
}
