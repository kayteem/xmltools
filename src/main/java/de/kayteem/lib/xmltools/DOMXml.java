/**
 * Created by:  Tobias Mielke
 * Created on:  27.10.2015
 * Modified on: 27.10.2015
 */

package de.kayteem.lib.xmltools;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DOMXml implements XmlDocument {

    // FIELDS
    private Document dom;


    // CONSTRUCTION
    public DOMXml() {
        this.dom = null;
    }


    // IMPLEMENTATION (XmlDocument)
    public void parse(File file) {

        // [1] - If file does not exist -> throw exception.
        if (!file.exists()) {
            throw new IllegalArgumentException("Invalid file!");
        }

        // [2] - Create a DOM builder factory.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();


        try {
            // [3] - Create a DOM builder.
            DocumentBuilder domBuilder = factory.newDocumentBuilder();

            // [4] - Parse to get the DOM representation of the XML file.
            dom = domBuilder.parse(file);

        } catch(ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch(SAXException se) {
            se.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }


    public String getRootElementName() {
        String rootElementName = null;

        if (dom != null) {
            rootElementName = dom.getDocumentElement().getTagName();
        }

        return rootElementName;
    }


    public List<Element> getElementsByName(String name, Element parent) {
        List<Element> elements = new ArrayList<Element>();

        try {
            NodeList nodes = parent.getElementsByTagName(name);

            if (nodes != null && nodes.getLength() > 0) {
                for (int i=0; i<nodes.getLength(); i++) {
                    Element element = (Element) nodes.item(i);
                    if (element != null) {
                        elements.add(element);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return elements;
    }

    public List<Element> getElementsByName(String name) {
        return getElementsByName(name, dom.getDocumentElement());
    }

    public Element getElementByName(String name, Element parent, int idx) {
        return getElementsByName(name, parent).get(idx);
    }

    public Element getElementByName(String name, int idx) {
        return getElementsByName(name).get(idx);
    }

    public Element getFirstElementByName(String name, Element parent) {
        return getElementByName(name, parent, 0);
    }

    public Element getFirstElementByName(String name) {
        return getElementByName(name, 0);
    }


    public Element getElementWith(String attrName, String attrValue, List<Element> elements) {

        // [1] - Iterate the given elements.
        for (Element element : elements) {

            // [2] - Check existence of attribute name.
            boolean hasAttribute = element.hasAttribute(attrName);
            if (!hasAttribute) {
                continue;
            }

            // [3] - Check correctness of attribute value.
            boolean hasCorrectValue = element.getAttributeNode(attrName).getValue().equals(attrValue);
            if (hasCorrectValue) {
                return element;
            }
        }

        return null;
    }


    public String getAttributeValue(String attrName, Element element) {
        return element.getAttribute(attrName);
    }

    public String getContent(Element element) {
        return element.getChildNodes().item(0).getNodeValue();
    }

}
