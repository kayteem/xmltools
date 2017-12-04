/**
 * Created by:  Tobias Mielke
 * Created on:  07.12.2015
 * Modified on: 22.08.2016
 */

package de.kayteem.lib.xmltools;

import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Element;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;


public interface XmlWriter {

    void create() throws ParserConfigurationException;

    Element addRootElement(String tagName);
    Element addRootElement(String namespace, String tagName);
    Element addElement(String tagName, Element parent);
    Element addElement(String tagName, String content, Element parent);
    Attr addAttribute(String name, String value, Element parent);
    Comment insertCommentLineBefore(String strComment, Element element);
    Comment insertCommentLineAfter(String strComment, Element element);
    void commentOut(Element element) throws TransformerException;

    void saveToXML(File file) throws ParserConfigurationException, TransformerException;
    void saveToXML(File file, int indent) throws ParserConfigurationException, TransformerException;

}
