/**
 * Created by:  Tobias Mielke
 * Created on:  27.10.2015
 * Modified on: 27.10.2015
 */

package de.kayteem.lib.xmltools;


import org.w3c.dom.Element;

import java.io.File;
import java.util.List;

public interface XmlDocument {

    void parse(File file);

    String getRootElementName();

    List<Element> getElementsByName(String name, Element parent);
    List<Element> getElementsByName(String name);
    Element getElementByName(String name, Element parent, int idx);
    Element getElementByName(String name, int idx);
    Element getFirstElementByName(String name, Element parent);
    Element getFirstElementByName(String name);

    Element getElementWith(String attrName, String attrValue, List<Element> elements);

}
