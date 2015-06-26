/**
 *
 */
package org.eclipsescout.contacts.server.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

/**
 * @author mzi
 */
public class DomUtility {
  private static Node getSubElement(Node parent, String name) {
    NodeList nodes = ((Element) parent).getElementsByTagName(name);
    if (nodes.getLength() == 0) {
      return null;
    }
    return nodes.item(0);
  }

  public static String getValue(Node node, String name) {
    Node n = getSubElement(node, name);
    if (n == null) {
      return "";
    }
    return n.getTextContent();
  }

  public static String getValue(Node node, String name, String subName) {
    Node n = getSubElement(node, name);
    if (n == null) {
      return "";
    }
    return getValue(n, subName);
  }

  public static String getString(Element element) {
    Document document = element.getOwnerDocument();
    DOMImplementationLS domImplLS = (DOMImplementationLS) document.getImplementation();
    LSSerializer serializer = domImplLS.createLSSerializer();
    return serializer.writeToString(element);
  }
}
