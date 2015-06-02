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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.Platform;
import org.eclipse.scout.commons.CollectionUtility;
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

  private final Object m_codeTypeClassDescriptorMapLock;
  private final HashMap<String, Set<BundleClassDescriptor>> m_codeTypeClassDescriptorMap;

  public LocalCodeService() {
    m_codeTypeClassDescriptorMapLock = new Object();
    m_codeTypeClassDescriptorMap = new HashMap<String, Set<BundleClassDescriptor>>();
  }

  @Override
  public <T extends ICodeType<?, ?>> T getCodeType(Class<T> type) {
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
  public <T extends ICodeType<?, ?>> T getCodeType(Long partitionId, Class<T> type) {
    return getCodeType(type);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> ICodeType<T, ?> findCodeTypeById(T id) {
    if (id == null) {
      return null;
    }
    for (ICodeType<?, ?> ct : getAllCodeTypes("")) {
      if (id.equals(ct.getId())) {
        return (ICodeType<T, ?>) ct;
      }
    }
    return null;
  }

  @Override
  public <T> ICodeType<T, ?> findCodeTypeById(Long partitionId, T id) {
    return findCodeTypeById(id);
  }

  @Override
  public List<ICodeType<?, ?>> getCodeTypes(List<Class<? extends ICodeType<?, ?>>> types) {
    List<ICodeType<?, ?>> result = new ArrayList<ICodeType<?, ?>>();
    for (Class<? extends ICodeType<?, ?>> codeTypeClazz : types) {
      result.add(getCodeType(codeTypeClazz));
    }
    return Collections.unmodifiableList(result);
  }

  @Override
  public List<ICodeType<?, ?>> getCodeTypes(Long partitionId, List<Class<? extends ICodeType<?, ?>>> types) {
    return getCodeTypes(types);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <CODE_ID_TYPE, CODE extends ICode<CODE_ID_TYPE>> CODE getCode(final Class<CODE> type) {
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
    ICodeType<?, CODE_ID_TYPE> codeType = getCodeType(declaringCodeTypeClass);
    final Holder<ICode<CODE_ID_TYPE>> codeHolder = new Holder<ICode<CODE_ID_TYPE>>();
    ICodeVisitor<ICode<CODE_ID_TYPE>> v = new ICodeVisitor<ICode<CODE_ID_TYPE>>() {
      @Override
      public boolean visit(ICode<CODE_ID_TYPE> code, int treeLevel) {
        if (code.getClass() == type) {
          codeHolder.setValue(code);
          return false;
        }
        return true;
      }
    };
    codeType.visit(v);
    return (CODE) codeHolder.getValue();
  }

  @Override
  public <CODE_ID_TYPE, CODE extends ICode<CODE_ID_TYPE>> CODE getCode(Long partitionId, Class<CODE> type) {
    return getCode(type);
  }

  @Override
  public <T extends ICodeType<?, ?>> T reloadCodeType(Class<T> type) throws ProcessingException {
    if (type == null) {
      return null;
    }
    return getCodeType(type);
  }

  @Override
  public List<ICodeType<?, ?>> reloadCodeTypes(List<Class<? extends ICodeType<?, ?>>> types) throws ProcessingException {
    if (types == null) {
      return null;
    }
    return getCodeTypes(types);
  }

  @Override
  public Set<BundleClassDescriptor> getAllCodeTypeClasses(String classPrefix) {
    if (classPrefix == null) {
      return CollectionUtility.hashSet();
    }
    synchronized (m_codeTypeClassDescriptorMapLock) {
      Set<BundleClassDescriptor> a = m_codeTypeClassDescriptorMap.get(classPrefix);
      if (a != null) {
        return CollectionUtility.hashSet(a);
      }
      //
      Set<BundleClassDescriptor> discoveredCodeTypes = new HashSet<BundleClassDescriptor>();
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
        // Skip uninteresting bundles
        if (!acceptBundle(bundle, classPrefix)) {
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
          if (acceptClassName(bundle, className)) {
            try {
              Class c = null;
              c = bundle.loadClass(className);
              if (acceptClass(bundle, c)) {
                discoveredCodeTypes.add(new BundleClassDescriptor(bundle.getSymbolicName(), c.getName()));
              }
            }
            catch (Throwable t) {
              // nop
            }
          }
        }
      }
      m_codeTypeClassDescriptorMap.put(classPrefix, discoveredCodeTypes);
      return CollectionUtility.hashSet(discoveredCodeTypes);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ICodeType<?, ?>> getAllCodeTypes(String classPrefix) {
    List<Class<? extends ICodeType<?, ?>>> list = new ArrayList<Class<? extends ICodeType<?, ?>>>();
    for (BundleClassDescriptor d : getAllCodeTypeClasses(classPrefix)) {
      try {
        list.add((Class<? extends ICodeType<?, ?>>) Platform.getBundle(d.getBundleSymbolicName()).loadClass(d.getClassName()));
      }
      catch (Throwable t) {
        LOG.warn("Loading " + d.getClassName() + " of bundle " + d.getBundleSymbolicName(), t);
        continue;
      }
    }
    return getCodeTypes(list);
  }

  @Override
  public List<ICodeType<?, ?>> getAllCodeTypes(String classPrefix, Long partitionId) {
    return getAllCodeTypes(classPrefix);
  }

  /**
   * Checks whether the given bundle should be scanned for code type classes. The default implementations accepts
   * all bundles that are not fragments (because classes from fragments are automatically read when browsing the host
   * bundle).
   * 
   * @return Returns <code>true</code> if the given bundle meets the requirements to be scanned for code type classes.
   *         <code>false</code> otherwise.
   */
  protected boolean acceptBundle(Bundle bundle, String classPrefix) {
    return !Platform.isFragment(bundle);
  }

  /**
   * Checks whether the given class name is a potential code type class. Class names that do not meet the
   * requirements of this method are not considered further, i.e. the "expensive" class instantiation is skipped.
   * The default implementation checks whether the class name contains <code>"CodeType"</code>.
   * 
   * @param bundle
   *          The class's hosting bundle
   * @param className
   *          the class name to be checked
   * @return Returns <code>true</code> if the given class name meets the requirements to be considered as a code type
   *         class. <code>false</code> otherwise.
   */
  protected boolean acceptClassName(Bundle bundle, String className) {
    return (className.indexOf("CodeType") >= 0);
  }

  /**
   * Checks whether the given class is a CodeType class that should be visible to this service. The default
   * implementation checks if the class meets the following conditions:
   * <ul>
   * <li>subclass of {@link ICodeType}
   * <li><code>public</code>
   * <li>not an <code>interface</code>
   * <li>not <code>abstract</code>
   * <li>the class's simple name does not start with <code>"Abstract"</code> (convenience check)
   * </ul>
   * 
   * @param bundle
   *          The class's hosting bundle
   * @param c
   *          the class to be checked
   * @return Returns <code>true</code> if the class is a code type class. <code>false</code> otherwise.
   */
  protected boolean acceptClass(Bundle bundle, Class<?> c) {
    if (ICodeType.class.isAssignableFrom(c)) {
      if (!c.isInterface()) {
        int flags = c.getModifiers();
        if (Modifier.isPublic(flags) && (!Modifier.isAbstract(flags)) && (!c.getSimpleName().startsWith("Abstract"))) {
          return true;
        }
      }
    }
    return false;
  }
}
