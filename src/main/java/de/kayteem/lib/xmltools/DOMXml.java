/**
 * Created by:  Tobias Mielke
 * Created on:  27.10.2015
 * Modified on: 22.08.2016
 */

package de.kayteem.lib.xmltools;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DOMXml implements XmlReader, XmlWriter {

    // FIELDS
    private Document dom;


    // CONSTRUCTION
    public DOMXml() {
        this.dom = null;
    }


    // IMPLEMENTATION - READ (XmlReader)
    public void parse(File file) throws ParserConfigurationException, IOException, SAXException {

        // [1] - If file does not exist -> throw exception.
        if (!file.exists()) {
            throw new IllegalArgumentException("Invalid file!");
        }

        // [2] - Create a DOM builder factory.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // [3] - Create a document builder.
        DocumentBuilder docBuilder = factory.newDocumentBuilder();

        // [4] - Parse XML file to get the DOM representation.
        dom = docBuilder.parse(file);
    }


    public Element getRootElement() {
        Element rootElement = null;

        if (dom != null) {
            rootElement = dom.getDocumentElement();
        }

        return rootElement;
    }

    public String getRootElementName() {
        String rootElementName = null;

        Element rootElement = getRootElement();
        if (rootElement != null) {
            rootElementName = rootElement.getTagName();
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


    // IMPLEMENTATION - WRITE (XmlWriter)
    public void create() throws ParserConfigurationException {

        // [1] - Create a DOM builder factory.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // [2] - Create a document builder.
        DocumentBuilder docBuilder = factory.newDocumentBuilder();

        // [3] - Create new DOM.
        dom = docBuilder.newDocument();
    }


    public Element addRootElement(String tagName) {
        Element root = dom.createElement(tagName);
        dom.appendChild(root);

        return root;
    }

    public Element addRootElement(String namespace, String tagName) {
        Element root = (namespace == null) ? dom.createElement(tagName) : dom.createElementNS(namespace, tagName);
        dom.appendChild(root);

        return root;
    }

    public Element addElement(String tagName, Element parent) {
        Element element = dom.createElement(tagName);
        parent.appendChild(element);

        return element;
    }

    public Element addElement(String tagName, String content, Element parent) {
        Element element = dom.createElement(tagName);
        element.appendChild(dom.createTextNode(content));
        parent.appendChild(element);

        return element;
    }

    public Attr addAttribute(String name, String value, Element parent) {
        Attr attr = dom.createAttribute(name);
        attr.setValue(value);
        parent.setAttributeNode(attr);

        return attr;
    }


    public void saveToXML(File file, int indent) throws TransformerException {

        // [1] - Build transformer.
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // [2] - Pretty Print.
        if (indent > 0) {
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(indent));
        }

        // [3] - Build DOM source.
        DOMSource source = new DOMSource(dom);

        // [4] - Create Write to file.
        StreamResult target = new StreamResult(file);

        // [5] - Create Write to file.
        transformer.transform(source, target);
    }

    public void saveToXML(File file) throws TransformerException {
        saveToXML(file, 0);
    }

}
