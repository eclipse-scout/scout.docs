package org.eclipse.scout.docs.snippets;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.annotation.Generated;
import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.MessageContext.Scope;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.http.HTTPException;
import javax.xml.ws.soap.MTOM;

import org.eclipse.scout.docs.ws.pingwebservice.PingWebService;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.config.AbstractPositiveIntegerConfigProperty;
import org.eclipse.scout.rt.platform.config.AbstractStringConfigProperty;
import org.eclipse.scout.rt.platform.config.IConfigProperty;
import org.eclipse.scout.rt.platform.context.RunContext;
import org.eclipse.scout.rt.platform.context.RunWithRunContext;
import org.eclipse.scout.rt.platform.exception.DefaultExceptionTranslator;
import org.eclipse.scout.rt.platform.security.ConfigFileCredentialVerifier;
import org.eclipse.scout.rt.server.jaxws.MessageContexts;
import org.eclipse.scout.rt.server.jaxws.consumer.AbstractWebServiceClient;
import org.eclipse.scout.rt.server.jaxws.consumer.InvocationContext;
import org.eclipse.scout.rt.server.jaxws.consumer.auth.handler.BasicAuthenticationHandler;
import org.eclipse.scout.rt.server.jaxws.handler.LogHandler;
import org.eclipse.scout.rt.server.jaxws.handler.WsConsumerCorrelationIdHandler;
import org.eclipse.scout.rt.server.jaxws.handler.WsProviderCorrelationIdHandler;
import org.eclipse.scout.rt.server.jaxws.provider.annotation.Authentication;
import org.eclipse.scout.rt.server.jaxws.provider.annotation.Clazz;
import org.eclipse.scout.rt.server.jaxws.provider.annotation.Handler;
import org.eclipse.scout.rt.server.jaxws.provider.annotation.IgnoreWebServiceEntryPoint;
import org.eclipse.scout.rt.server.jaxws.provider.annotation.InitParam;
import org.eclipse.scout.rt.server.jaxws.provider.annotation.WebServiceEntryPoint;
import org.eclipse.scout.rt.server.jaxws.provider.auth.method.BasicAuthenticationMethod;
import org.eclipse.scout.rt.server.jaxws.provider.context.IWebServiceContext;
import org.eclipse.scout.rt.server.jaxws.provider.context.JaxWsServletRunContexts;

@SuppressWarnings("unused")
public final class JaxWsSnippet {

  @IgnoreWebServiceEntryPoint
  // tag::jaxws.example.entrypoint.definition[]
  @WebServiceEntryPoint(endpointInterface = PingWebServicePortType.class) // <2>
  interface PingWebServiceEntryPointDefinition { // <1>
  }
  // end::jaxws.example.entrypoint.definition[]

  // tag::jaxws.example.pingws.entrypoint.definition[]
  @WebServiceEntryPoint(
      endpointInterface = PingWebServicePortType.class, // <1>
      entryPointName = "PingWebServiceEntryPoint",
      entryPointPackage = "org.eclipse.scout.docs.ws.ping",
      serviceName = "PingWebService",
      portName = "PingWebServicePort",
      handlerChain = {// <2>
          @Handler(@Clazz(CorrelationIdHandler.class)), // <3>
          @Handler(value = @Clazz(IPAddressFilter.class) , initParams = { // <4>
              @InitParam(key = "rangeFrom", value = "192.200.0.0"),
              @InitParam(key = "rangeTo", value = "192.255.0.0")}),
          @Handler(@Clazz(LogHandler.class)), // <5>
      },
      authentication = @Authentication( // <6>
          order = 2, // <7>
          method = @Clazz(BasicAuthenticationMethod.class) , // <8>
          verifier = @Clazz(ConfigFileCredentialVerifier.class) ) ) // <9>
  @MTOM // <10>
  // end::jaxws.example.pingws.entrypoint.definition[]
  public interface PingWebServiceEntryPointDefinition1 {
  }

  // tag::jaxws.example.endpointinterface[]
  @WebService(name = "PingWebServicePortType", targetNamespace = "http://scout.eclipse.org/docs/ws/PingWebService/")
  @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
  public interface PingWebServicePortType {

    @WebMethod(action = "http://scout.eclipse.org/docs/ws/PingWebService/ping")
    @WebResult(name = "pingResponse", targetNamespace = "http://scout.eclipse.org/docs/ws/PingWebService/", partName = "parameters")
    String ping(@WebParam(name = "pingRequest", targetNamespace = "http://scout.eclipse.org/docs/ws/PingWebService/", partName = "ping") String ping);
  }
  // end::jaxws.example.endpointinterface[]

  // tag::jaxws.example.pingws.porttype[]
  @WebService(name = "PingWebServicePortType",
      targetNamespace = "http://scout.eclipse.org/docs/ws/PingWebService/",
      endpointInterface = "org.eclipse.scout.docs.snippets.JaxWsSnippet.PingWebServicePortType",
      serviceName = "PingWebService",
      portName = "PingWebServicePort")
  @MTOM
  @HandlerChain(file = "PingWebServiceEntryPoint_handler-chain.xml")
  public class PingWebServiceEntryPoint implements org.eclipse.scout.docs.snippets.JaxWsSnippet.PingWebServicePortType {

    // end::jaxws.example.pingws.porttype[]
    @Override
    public String ping(String ping) {
      return null;
    }
  }

  // tag::jaxws.example.entrypoint[]
  @Generated(value = "org.eclipse.scout.jaxws.apt.JaxWsAnnotationProcessor", date = "2016-01-25T14:22:58:583+0100", comments = "EntryPoint to run webservice requests on behalf of a RunContext")
  @WebService(name = "PingWebServicePortType", targetNamespace = "http://scout.eclipse.org/docs/ws/PingWebService/", endpointInterface = "org.eclipse.scout.docs.ws.pingwebservice.PingWebServicePortType")
  public class PingWebServicePortTypeEntryPoint implements org.eclipse.scout.docs.ws.pingwebservice.PingWebServicePortType {

    @Resource
    protected WebServiceContext m_webServiceContext;

    @Override
    public String ping(final String ping) {
      final RunContext servletRunContext = JaxWsServletRunContexts.copyCurrent().withWebServiceContext(m_webServiceContext);
      final RunContext requestRunContext = MessageContexts.getRunContext(m_webServiceContext.getMessageContext());
      try {
        return servletRunContext.call(new Callable<String>() {

          @Override
          public final String call() throws Exception {
            if (requestRunContext == null) {
              return BEANS.get(org.eclipse.scout.docs.ws.pingwebservice.PingWebServicePortType.class).ping(ping);
            }
            else {
              return requestRunContext.call(new Callable<String>() {

                @Override
                public final String call() throws Exception {
                  return BEANS.get(org.eclipse.scout.docs.ws.pingwebservice.PingWebServicePortType.class).ping(ping);
                }
              }, DefaultExceptionTranslator.class);
            }
          }
        }, DefaultExceptionTranslator.class);
      }
      catch (Exception e) {
        throw handleUndeclaredFault(e);
      }
    }

    protected RuntimeException handleUndeclaredFault(final Exception e) {
      if (e instanceof RuntimeException) {
        throw (RuntimeException) e;
      }
      else {
        throw new HTTPException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      }
    }
  }
  // end::jaxws.example.entrypoint[]

  // tag::jaxws.example.porttype.bean[]
  @ApplicationScoped
  public class PingWebServicePortTypeBean implements PingWebServicePortType {

    @Override
    public String ping(String ping) {
      return "ping: " + ping;
    }
  }
  // end::jaxws.example.porttype.bean[]

  // tag::jaxws.example.handler.ipaddressfilter[]
  @ApplicationScoped // <1>
  @RunWithRunContext // <2>
  public class IPAddressFilter implements SOAPHandler<SOAPMessageContext> {

    @Resource
    private Map<String, String> m_initParams; // <3>

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
      String rangeForm = m_initParams.get("rangeFrom"); // <4>
      String rangeTo = m_initParams.get("rangeTo");
      // ...
      return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
      return true;
    }

    @Override
    public Set<QName> getHeaders() {
      return Collections.emptySet();
    }

    @Override
    public void close(MessageContext context) {
    }
  }
  // end::jaxws.example.handler.ipaddressfilter[]

  // tag::jaxws.example.statepropagation.handler1[]
  @ApplicationScoped
  public class CorrelationIdHandler implements SOAPHandler<SOAPMessageContext> {

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
      context.put("cid", UUID.randomUUID().toString()); // <1>
      context.setScope("cid", Scope.APPLICATION); // <2>
      return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
      return true;
    }

    @Override
    public Set<QName> getHeaders() {
      return Collections.emptySet();
    }

    @Override
    public void close(MessageContext context) {
    }
  }
  // end::jaxws.example.statepropagation.handler1[]

  // tag::jaxws.example.statepropagation.handler2[]
  @ApplicationScoped
  public class CorrelationIdLogger implements SOAPHandler<SOAPMessageContext> {

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
      String correlationId = (String) context.get("cid"); // <1>
      // ...
      return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
      return true;
    }

    @Override
    public void close(MessageContext context) {
    }

    @Override
    public Set<QName> getHeaders() {
      return Collections.emptySet();
    }
  }
  // end::jaxws.example.statepropagation.handler2[]

  // tag::jaxws.example.statepropagation.porttype[]
  @ApplicationScoped
  public class CorrelationIdPortType implements PingWebServicePortType {

    @Override
    public String ping(String ping) {
      MessageContext currentMsgCtx = IWebServiceContext.CURRENT.get().getMessageContext(); // <1>
      String correlationId = (String) currentMsgCtx.get("cid"); // <2>
      // ...
      return ping;
    }
  }
  // end::jaxws.example.statepropagation.porttype[]

  //tag::jaxws.example.pingwsWithCorrelationId.entrypoint.definition[]
  @WebServiceEntryPoint(
      endpointInterface = PingWebServicePortType.class,
      entryPointName = "PingWebServiceEntryPoint",
      entryPointPackage = "org.eclipse.scout.docs.ws.ping2",
      serviceName = "PingWebService",
      portName = "PingWebServicePort",
      handlerChain = {
          @Handler(@Clazz(WsProviderCorrelationIdHandler.class)), // <1>
          @Handler(@Clazz(LogHandler.class)),
      },
      authentication = @Authentication(
          method = @Clazz(BasicAuthenticationMethod.class) ,
          verifier = @Clazz(ConfigFileCredentialVerifier.class) ) )
  // end::jaxws.example.pingwsWithCorrelationId.entrypoint.definition[]
  public interface PingWebServiceEntryPointDefinition2 {
  }

  // tag::jaxws.example.wsclient1[]
  public class PingWebServiceClient extends AbstractWebServiceClient<PingWebService, PingWebServicePortType> { // <1>
  }
  // end::jaxws.example.wsclient1[]

  // tag::jaxws.example.wsclient2[]
  public class PingWebServiceClient1 extends AbstractWebServiceClient<PingWebService, PingWebServicePortType> {

    @Override
    protected Class<? extends IConfigProperty<String>> getConfiguredEndpointUrlProperty() {
      return JaxWsPingEndpointUrlProperty.class; // <1>
    }

    @Override
    protected Class<? extends IConfigProperty<String>> getConfiguredUsernameProperty() {
      return JaxWsPingUsernameProperty.class; // <2>
    }

    @Override
    protected Class<? extends IConfigProperty<String>> getConfiguredPasswordProperty() {
      return JaxWsPingPasswordProperty.class; // <2>
    }

    @Override
    protected Class<? extends IConfigProperty<Integer>> getConfiguredConnectTimeoutProperty() {
      return JaxWsPingConnectTimeoutProperty.class; // <3>
    }

    @Override
    protected Class<? extends IConfigProperty<Integer>> getConfiguredReadTimeoutProperty() {
      return JaxWsPingReadTimeoutProperty.class; // <3>
    }
  }
  // end::jaxws.example.wsclient2[]

  // tag::jaxws.example.wsclient3[]
  public class PingWebServiceClient2 extends AbstractWebServiceClient<PingWebService, PingWebServicePortType> {

    @Override
    protected void execInstallHandlers(List<javax.xml.ws.handler.Handler<?>> handlerChain) {
      handlerChain.add(new BasicAuthenticationHandler());
      handlerChain.add(BEANS.get(LogHandler.class));
    }
  }
  // end::jaxws.example.wsclient3[]

  // tag::jaxws.example.wsclient4[]
  public class PingWebServiceClient3 extends AbstractWebServiceClient<PingWebService, PingWebServicePortType> {

    @Override
    protected void execInstallHandlers(List<javax.xml.ws.handler.Handler<?>> handlerChain) {
      handlerChain.add(new BasicAuthenticationHandler());
      handlerChain.add(BEANS.get(LogHandler.class));
      handlerChain.add(BEANS.get(WsConsumerCorrelationIdHandler.class)); // <1>
    }
  }
  // end::jaxws.example.wsclient4[]

  public static class JaxWsPingEndpointUrlProperty extends AbstractStringConfigProperty {
    @Override
    public String getKey() {
      return "jaxws.ping.url";
    }
  }

  public static class JaxWsPingUsernameProperty extends AbstractStringConfigProperty {
    @Override
    public String getKey() {
      return "jaxws.ping.username";
    }
  }

  public static class JaxWsPingPasswordProperty extends AbstractStringConfigProperty {
    @Override
    public String getKey() {
      return "jaxws.ping.password";
    }
  }

  public static class JaxWsPingConnectTimeoutProperty extends AbstractPositiveIntegerConfigProperty {

    @Override
    public String getKey() {
      return "jaxws.ping.timeout.connect";
    }
  }

  public static class JaxWsPingReadTimeoutProperty extends AbstractPositiveIntegerConfigProperty {

    @Override
    public String getKey() {
      return "jaxws.ping.timeout.read";
    }
  }
  // end::jaxws.example.wsclient.config[]

  {
    // tag::jaxws.example.wsclient.call_1[]
    PingWebServicePortType port = BEANS.get(PingWebServiceClient.class).newInvocationContext().getPort(); // <1>

    port.ping("Hello world"); // <2>
    // end::jaxws.example.wsclient.call_1[]
  }

  {
    // tag::jaxws.example.wsclient.call_2[]
    final InvocationContext<PingWebServicePortType> context = BEANS.get(PingWebServiceClient.class).newInvocationContext();

    PingWebServicePortType port = context
        .withUsername("test-user") // <1>
        .withPassword("secret")
        .withConnectTimeout(10, TimeUnit.SECONDS) // <2>
        .withoutReadTimeout() // <3>
        .withHttpRequestHeader("X-ENV", "integration") // <4>
        .getPort();

    port.ping("Hello world"); // <5>

    // end::jaxws.example.wsclient.call_2[]
  }

  {
    // tag::jaxws.example.wsclient.call_3[]
    final InvocationContext<PingWebServicePortType> context = BEANS.get(PingWebServiceClient.class).newInvocationContext();

    String pingResult = context.getPort().ping("Hello world");

    // Get HTTP status code
    int httpStatusCode = context.getHttpStatusCode();

    // Get HTTP response header
    List<String> httpResponseHeader = context.getHttpResponseHeader("X-CUSTOM-HEADER");
    // end::jaxws.example.wsclient.call_3[]
  }

  {
    // tag::jaxws.example.wsclient.call_4[]
    BEANS.get(PingWebServiceClient.class).newInvocationContext()
        .withRequestContextProperty("cid", UUID.randomUUID().toString()) // <1>
        .getPort().ping("Hello world"); // <2>

    // end::jaxws.example.wsclient.call_4[]
  }
}
