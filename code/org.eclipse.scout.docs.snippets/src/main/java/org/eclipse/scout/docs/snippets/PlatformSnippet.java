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

import org.eclipse.scout.rt.client.ClientBeanDecorationFactory;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.BeanMetaData;
import org.eclipse.scout.rt.platform.BeanProducer;
import org.eclipse.scout.rt.platform.IBean;
import org.eclipse.scout.rt.platform.IBeanInstanceProducer;
import org.eclipse.scout.rt.platform.IPlatform;
import org.eclipse.scout.rt.platform.IPlatformListener;
import org.eclipse.scout.rt.platform.PlatformEvent;
import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.platform.config.AbstractStringConfigProperty;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.interceptor.IBeanDecorator;
import org.eclipse.scout.rt.platform.interceptor.IBeanInvocationContext;
import org.eclipse.scout.rt.platform.inventory.ClassInventory;
import org.eclipse.scout.rt.platform.inventory.IClassInfo;
import org.eclipse.scout.rt.platform.inventory.IClassInventory;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.platform.util.TuningUtility;

@SuppressWarnings("unused")
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
        BEANS.getBeanManager().registerClass(BeanSingletonClass.class);

        // Or register with meta information
        BeanMetaData beanData = new BeanMetaData(BeanClass.class).withApplicationScoped(true);
        BEANS.getBeanManager().registerBean(beanData);
      }
    }
  }
  //end::RegisterBeansListener[]

  //tag::BeanDecorationFactory[]
  @Replace
  public class ProfilerDecorationFactory extends ClientBeanDecorationFactory {
    @Override
    public <T> IBeanDecorator<T> decorate(IBean<T> bean, Class<? extends T> queryType) {
      return new BackendCallProfilerDecorator<>(super.decorate(bean, queryType));
    }
  }

  public class BackendCallProfilerDecorator<T> implements IBeanDecorator<T> {

    private final IBeanDecorator<T> m_inner;

    public BackendCallProfilerDecorator(IBeanDecorator<T> inner) {
      m_inner = inner;
    }

    @Override
    public Object invoke(IBeanInvocationContext<T> context) {
      final String className;
      if (context.getTargetObject() == null) {
        className = context.getTargetMethod().getDeclaringClass().getSimpleName();
      }
      else {
        className = context.getTargetObject().getClass().getSimpleName();
      }

      String timerName = className + '.' + context.getTargetMethod().getName();
      TuningUtility.startTimer();
      try {
        if (m_inner != null) {
          // delegate to the next decorator in the chain
          return m_inner.invoke(context);
        }
        // forward to real bean
        return context.proceed();
      }
      finally {
        TuningUtility.stopTimer(timerName);
      }
    }
  }

  //end::BeanDecorationFactory[]

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

  private void snippets() {
    //tag::ClassInventory[]
    IClassInventory classInventory = ClassInventory.get();

    // get all classes below IService
    Set<IClassInfo> services = classInventory.getAllKnownSubClasses(IService.class);

    // get all classes having a Bean annotation (directly on them self).
    Set<IClassInfo> classesHavingBeanAnnot = classInventory.getKnownAnnotatedTypes(Bean.class);
    //end::ClassInventory[]
    //tag::BeanRetrieval[]
    BeanSingletonClass bean = BEANS.get(BeanSingletonClass.class);
    BeanClass beanOrNull = BEANS.opt(BeanClass.class);
    List<BeanWithCustomProducer> all = BEANS.all(BeanWithCustomProducer.class);
    //end::BeanRetrieval[]
  }
}
