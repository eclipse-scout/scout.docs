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
package org.eclipsescout.demo.bahbah.shared.services.process;

import java.util.List;
import java.util.Set;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.code.ICode;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipsescout.demo.bahbah.shared.services.UserAdministrationTablePageData;

@InputValidation(IValidationStrategy.PROCESS.class)
@TunnelToServer
public interface IUserProcessService extends IService {

  String PERMISSION_KEY = "permission_id";

  void registerUser() throws ProcessingException;

  void unregisterUser() throws ProcessingException;

  Set<String> getUsersOnline() throws ProcessingException;

  ICode<Integer> getUserPermission() throws ProcessingException;

  void deleteUsers(List<Long> u_id) throws ProcessingException;

  void createUser(UserFormData formData) throws ProcessingException;

  void updateUser(UserFormData formData) throws ProcessingException;

  UserAdministrationTablePageData getUserAdministrationTableData(UserFormData formData) throws ProcessingException;

  void registerUserInternal(String userId) throws ProcessingException;

  void unregisterUserInternal(String userName) throws ProcessingException;
}
