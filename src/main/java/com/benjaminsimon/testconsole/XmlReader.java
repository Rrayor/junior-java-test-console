package com.benjaminsimon.testconsole;

import com.benjaminsimon.testconsole.config.XmlReaderConfig;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 *
 * @author simon
 */
public class XmlReader {
    
    public XmlReader() {
    }
    
    public TextList readXml(String fileName) {
        
        //If we read a new file, we want a new List
        TextList textList = new TextList();
        
        try {
            File file = checkFile(fileName);
            
            Document doc = createDocument(file);
            
            NodeList dataFields = doc.getElementsByTagName(XmlReaderConfig.DATA_FIELD_NAME);
            
            //Look through the datafields
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
                for(int j = 0; j < subFields.getLength(); j++) {
                    
                    Element currentSubField = (Element) subFields.item(j);

                    //If the current subfield has the correct attribute save it
                    if(XmlReaderConfig.SUBFIELD_ATTR_FILTER_VALUE.equals(currentSubField.getAttribute(XmlReaderConfig.SUBFIELD_ATTR_FILTER_NAME))) {

                        String textContext = currentSubField.getTextContent();
        
                        textList.addText(textContext);
                    }
                }
            }
            
            System.out.println("File read complete!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            
            //No further operations can be run if file read is unsuccessful
            System.exit(0);
        }
        
        return textList;
    }
    
    private File checkFile(String fileName) throws Exception {
        
        //Separate the extension from the file name
        final String[] parts = fileName.split("\\.");
        
        //There was no '.' in the file name, hence it has no valid extension
        if(parts.length < 2) {
            throw new Exception("File name is invalid");
        }
        
        //The file name may have a valid extension which is after the last '.' in it
        final String ext = parts[parts.length - 1];
        
        
        //Check if the extension is correct
        if(!ext.equals(XmlReaderConfig.SOURCE_EXT)) {
            throw new Exception("Only" + XmlReaderConfig.SOURCE_EXT + "files are accepted");
        }
        
        //If everythin is correct so far create a file instance based on the file name
        File file = new File(fileName);
        
        //Check if the file exists
        if(!file.exists()) {
            throw new Exception("File not found:\n" + fileName);
        }
        
        //No errors were found, return the file instance
        return file;
    }
    
    private Document createDocument(File file) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);
        
        return doc;
    }
}
