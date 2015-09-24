package org.eclipse.scout.contacts.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.contacts.shared.dtos.HelloWorldFormData;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IHelloWorldFormService extends IService {
  HelloWorldFormData load(HelloWorldFormData input) throws ProcessingException;
}
