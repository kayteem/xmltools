package de.kayteem.lib.xmltools;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;


/**
 * Created by Tobias Mielke
 * Created on 27.10.2015
 * Changed on 06.09.2020
 */
public interface XmlReader {

    void parse(Path path) throws ParserConfigurationException, IOException, SAXException;
    void parse(File file) throws ParserConfigurationException, IOException, SAXException;

    List<Element> xpath(String expression) throws XPathExpressionException;

    Element getRootElement();
    String getRootElementName();

    List<Element> getElementsByName(String name, Element parent);
    List<Element> getElementsByName(String name);
    Element getElementByName(String name, Element parent, int idx);
    Element getElementByName(String name, int idx);
    Element getFirstElementByName(String name, Element parent);
    Element getFirstElementByName(String name);

    Element getElementWith(String attrName, String attrValue, List<Element> elements);

    String getAttributeValue(String attrName, Element element);
    String getContent(Element element);

}
