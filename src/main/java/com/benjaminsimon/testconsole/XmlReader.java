package com.benjaminsimon.testconsole;

import com.benjaminsimon.testconsole.config.XmlReaderConfig;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 * Responsible for reading XML files for the project.
 * Puts the appropriate results in a TextList instance.
 * @see TextList
 * @author simon
 */
public class XmlReader {
    
    /**
     * Reference to TextList instance that will store the appropriate values
     * after reading the XML file
     */
    private TextList textList;
    
    /**
     * Constructor
     */
    public XmlReader() {
        this.textList = new TextList();
    }
    
    /**
     * Reads an XML file according to the needs of the project, if the path provided is valid.If an exception is thrown, it exits the program.
     * @param filePath The path to the XML file we want to read.
     * @return TextList
     * @throws java.io.FileNotFoundException
     * @see TextList
     * @see checkFile
     * @see createDocument
     * @see traverseDataFields
     * @see traverseSubFields
     */
    public TextList readXml(String filePath) throws FileNotFoundException, IOException, ParserConfigurationException, SAXException {
        
        //If we read a new file, we want a new List
        this.textList.clear();
        
        File file = checkFile(filePath);

        Document doc = createDocument(file);

        NodeList dataFields = doc.getElementsByTagName(XmlReaderConfig.DATA_FIELD_NAME);

        //Look through the datafields
        traverseDataFields(dataFields);

        System.out.println("File read complete!");
        
        return this.textList;
    }
    
    /**
     * Checks the validity of a file path according to the needs of the project.
     * @param filePath the file path that needs checking.
     * @return File
     */
    private File checkFile(String filePath) throws FileNotFoundException, IOException {
        
        //Separate the extension from the file name
        final String[] parts = filePath.split("\\.");
        
        //There was no '.' in the file name, hence it has no valid extension
        if(parts.length < 2) {
            throw new IOException("File name is invalid");
        }
        
        //The file name may have a valid extension which is after the last '.' in it
        final String ext = parts[parts.length - 1];
        
        
        //Check if the extension is correct
        if(!ext.equals(XmlReaderConfig.SOURCE_EXT)) {
            throw new IOException("Only" + XmlReaderConfig.SOURCE_EXT + "files are accepted");
        }
        
        //If everything is correct so far create a file instance based on the file name
        File file = new File(filePath);
        
        //Check if the file exists
        if(!file.exists()) {
            throw new FileNotFoundException("File not found:\n" + filePath);
        }
        
        //No errors were found, return the file instance
        return file;
    }
    
    
    /**
     * Creates a Document instance from the File instance provided.
     * @param file The file we need to create a Document from.
     * @return Document
     */
    private Document createDocument(File file) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);
        
        return doc;
    }

    /**
     * Loops through the datafields provided.
     * Checks them according to the needs of the project and if the current
     * datafield is in accordance with what we need, calls the traverseSubFields
     * method with its subfield tags as parameter.
     * @param dataFields A NodeList of datafields to traverse
     * @see traverseSubFields
     */
    private void traverseDataFields(NodeList dataFields) throws DOMException {
        for(int i = 0; i < dataFields.getLength(); i++) {
            
            Element currentDataField = (Element) dataFields.item(i);
            
            //Does this datafield have the attribute, we are looking for?
            if(!XmlReaderConfig.DATA_FIELD_ATTR_FILTER_VALUE.equals(currentDataField.getAttribute(XmlReaderConfig.DATA_FIELD_ATTR_FILTER_NAME))) {
                continue;
            }
            
            //If the datafield has the correct attribute, get its subfields
            NodeList subFields = currentDataField.getElementsByTagName(XmlReaderConfig.SUBFIELD_NAME);
            
            //If no subfields were found, go on to the next datafield
            if(subFields == null || subFields.getLength() <= 0) {
                continue;
            }
            
            //Look through the subfields
            traverseSubFields(subFields);
        }
    }

    /**
     * Loops through the subfields provided. If they are in accordance with what
     * we need, adds their textContect to textList.
     * @param subFields NodeList of subfields to traverse.
     * @see traverseDataFields
     * @see textList
     */
    private void traverseSubFields(NodeList subFields) throws DOMException {
        for(int i = 0; i < subFields.getLength(); i++) {
            
            Element currentSubField = (Element) subFields.item(i);
            
            //If the current subfield has the correct attribute save it
            if(XmlReaderConfig.SUBFIELD_ATTR_FILTER_VALUE.equals(currentSubField.getAttribute(XmlReaderConfig.SUBFIELD_ATTR_FILTER_NAME))) {
                
                String textContext = currentSubField.getTextContent();
                
                this.textList.addText(textContext);
            }
        }
    }
}
