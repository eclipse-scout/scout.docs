package org.eclipse.scout.contacts.server.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.contacts.server.ServerSession;
import org.eclipse.scout.contacts.shared.dtos.HelloWorldFormData;
import org.eclipse.scout.contacts.shared.services.IHelloWorldFormService;
import org.eclipse.scout.rt.server.Server;

@Server
public class HelloWorldFormService implements IHelloWorldFormService {

  @Override
  public HelloWorldFormData load(HelloWorldFormData input) throws ProcessingException {
    StringBuilder msg = new StringBuilder();
    msg.append("Hello ").append(ServerSession.get().getUserId()).append("!");
    input.getMessage().setValue(msg.toString());
    return input;
  }

}
