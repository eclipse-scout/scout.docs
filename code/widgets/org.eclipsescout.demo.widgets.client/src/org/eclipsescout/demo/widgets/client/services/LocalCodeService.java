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
package org.eclipsescout.demo.widgets.client.services;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.core.runtime.Platform;
import org.eclipse.scout.commons.annotations.Priority;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.Holder;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.commons.osgi.BundleClassDescriptor;
import org.eclipse.scout.commons.runtime.BundleBrowser;
import org.eclipse.scout.rt.shared.Activator;
import org.eclipse.scout.rt.shared.services.common.code.ICode;
import org.eclipse.scout.rt.shared.services.common.code.ICodeService;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.common.code.ICodeVisitor;
import org.eclipse.scout.rt.shared.services.common.exceptionhandler.IExceptionHandlerService;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;
import org.osgi.framework.Bundle;

/**
 * delegates to {@link CodeTypeStore}
 */
@Priority(1)
public class LocalCodeService extends AbstractService implements ICodeService {
  private static final IScoutLogger LOG = ScoutLogManager.getLogger(LocalCodeService.class);
  private HashMap<String, BundleClassDescriptor[]> m_codeTypeClassDescriptorMap = new HashMap<String, BundleClassDescriptor[]>();

  @Override
  public <T extends ICodeType> T getCodeType(Class<T> type) {
    T instance = null;
    try {
      instance = type.newInstance();
    }
    catch (Throwable t) {
      SERVICES.getService(IExceptionHandlerService.class).handleException(new ProcessingException("create " + type.getName(), t));
    }
    return instance;
  }

  @Override
  public <T extends ICodeType> T getCodeType(Long partitionId, Class<T> type) {
    return getCodeType(type);
  }

  @Override
  public ICodeType findCodeTypeById(Object id) {
    if (id == null) {
      return null;
    }
    for (ICodeType ct : getAllCodeTypes("")) {
      if (id.equals(ct.getId())) {
        return ct;
      }
    }
    return null;
  }

  @Override
  public ICodeType findCodeTypeById(Long partitionId, Object id) {
    return findCodeTypeById(id);
  }

  @SuppressWarnings("unchecked")
  @Override
  public ICodeType[] getCodeTypes(Class... types) {
    ICodeType[] instances = new ICodeType[types.length];
    for (int i = 0; i < instances.length; i++) {
      instances[i] = getCodeType(types[i]);
    }
    return instances;
  }

  @Override
  public ICodeType[] getCodeTypes(Long partitionId, Class... types) {
    return getCodeTypes(types);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T extends ICode> T getCode(final Class<T> type) {
    if (type == null) {
      return null;
    }
    Class declaringCodeTypeClass = null;
    if (type.getDeclaringClass() != null) {
      // code is inner type of code type or another code
      Class c = type.getDeclaringClass();
      while (c != null && !(ICodeType.class.isAssignableFrom(c))) {
        c = c.getDeclaringClass();
      }
      declaringCodeTypeClass = c;
    }
    if (declaringCodeTypeClass == null) {
      try {
        declaringCodeTypeClass = type.newInstance().getCodeType().getClass();
      }
      catch (Throwable t) {
        LOG.error("find code " + type, t);
      }
    }
    ICodeType codeType = getCodeType(declaringCodeTypeClass);
    final Holder<ICode> codeHolder = new Holder<ICode>(ICode.class);
    ICodeVisitor v = new ICodeVisitor() {
      @Override
      public boolean visit(ICode code, int treeLevel) {
        if (code.getClass() == type) {
          codeHolder.setValue(code);
          return false;
        }
        return true;
      }
    };
    codeType.visit(v);
    return (T) codeHolder.getValue();
  }

  @Override
  public <T extends ICode> T getCode(Long partitionId, Class<T> type) {
    return getCode(type);
  }

  @Override
  public <T extends ICodeType> T reloadCodeType(Class<T> type) {
    if (type == null) {
      return null;
    }
    return getCodeType(type);
  }

  @Override
  public ICodeType[] reloadCodeTypes(Class... types) {
    if (types == null) {
      return null;
    }
    return getCodeTypes(types);
  }

  @Override
  public BundleClassDescriptor[] getAllCodeTypeClasses(String classPrefix) {
    // There is no classPrefix integration  
    if (classPrefix == null) {
      return new BundleClassDescriptor[0];
    }
    BundleClassDescriptor[] a = m_codeTypeClassDescriptorMap.get(classPrefix);
    if (a != null) {
      return a;
    }
    //
    HashSet<BundleClassDescriptor> discoveredCodeTypes = new HashSet<BundleClassDescriptor>();
    for (Bundle bundle : Activator.getDefault().getBundle().getBundleContext().getBundles()) {
      if (bundle.getSymbolicName().startsWith(classPrefix)) {
        // ok
      }
      else if (classPrefix.startsWith(bundle.getSymbolicName() + ".")) {
        // ok
      }
      else {
        continue;
      }
      String[] classNames;
      try {
        BundleBrowser bundleBrowser = new BundleBrowser(bundle.getSymbolicName(), bundle.getSymbolicName());
        classNames = bundleBrowser.getClasses(false, true);
      }
      catch (Exception e1) {
        LOG.warn(null, e1);
        continue;
      }
      // filter
      for (String className : classNames) {
        // fast pre-check
        if (className.indexOf("CodeType") >= 0) {
          try {
            Class c = null;
            c = bundle.loadClass(className);
            if (ICodeType.class.isAssignableFrom(c)) {
              if (!c.isInterface()) {
                int flags = c.getModifiers();
                if (Modifier.isPublic(flags) && (!Modifier.isAbstract(flags)) && (!c.getSimpleName().startsWith("Abstract"))) {
                  if (ICodeType.class.isAssignableFrom(c)) {
                    discoveredCodeTypes.add(new BundleClassDescriptor(bundle.getSymbolicName(), c.getName()));
                  }
                }
              }
            }
          }
          catch (Throwable t) {
            // nop
          }
        }
      }
    }
    a = discoveredCodeTypes.toArray(new BundleClassDescriptor[discoveredCodeTypes.size()]);
    m_codeTypeClassDescriptorMap.put(classPrefix, a);
    return a;
  }

  @Override
  public ICodeType[] getAllCodeTypes(String classPrefix) {
    ArrayList<Class> list = new ArrayList<Class>();
    for (BundleClassDescriptor d : getAllCodeTypeClasses(classPrefix)) {
      try {
        list.add(Platform.getBundle(d.getBundleSymbolicName()).loadClass(d.getClassName()));
      }
      catch (Throwable t) {
        LOG.warn("Loading " + d.getClassName() + " of bundle " + d.getBundleSymbolicName(), t);
        continue;
      }
    }
    return getCodeTypes(list.toArray(new Class[list.size()]));
  }

  @Override
  public ICodeType[] getAllCodeTypes(String classPrefix, Long partitionId) {
    return getAllCodeTypes(classPrefix);
  }

}
