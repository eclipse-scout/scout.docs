package org.eclipse.scout.heatmap.demo.server.helloworld;

import org.eclipse.scout.heatmap.demo.server.ServerSession;
import org.eclipse.scout.heatmap.demo.shared.helloworld.HelloWorldFormData;
import org.eclipse.scout.heatmap.demo.shared.helloworld.IHelloWorldFormService;

/**
 * <h3>{@link HelloWorldFormService}</h3>
 *
 * @author asa
 */
public class HelloWorldFormService implements IHelloWorldFormService {

  @Override
  public HelloWorldFormData load(HelloWorldFormData input) {
    StringBuilder msg = new StringBuilder();
    msg.append("Hello ").append(ServerSession.get().getUserId()).append("!");
    input.getMessage().setValue(msg.toString());
    return input;
  }
}
