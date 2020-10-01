package com.benjaminsimon.testconsole;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
/**
 *
 * @author simon
 */
public class Data {
    
    public static enum Order{
        NAME,
        FREQUENCY
    }
    
    public static List<String> texts = new ArrayList<>();
    private static Map<String, Integer> FREQUENCIES_MAP = new HashMap<>();
    
    private static final String SOURCE_EXT = "xml";
    private static final String DATA_FIELD_NAME = "datafield";
    private static final String SUBFIELD_NAME = "subfield";
    
    private static final String DATA_FIELD_ATTR_FILTER_NAME = "tag";
    private static final String SUBFIELD_ATTR_FILTER_NAME = "code";
    private static final String DATA_FIELD_ATTR_FILTER_VALUE = "100";
    private static final String SUBFIELD_ATTR_FILTER_VALUE = "a";

    public Data() {}
 
    public static List<String> filter(List<String> textList, String filterValue) {
        List<String> res = new ArrayList<>();
        for(String t : textList) {
            if(t.startsWith(filterValue))
                res.add(t);
        }
        
        return res;
    }
    
    public static List<String> sort(List<String> textList, Order order, boolean reverse) {
        List<String> res = new ArrayList<>();
        
        if(order == Order.NAME) {
            Collections.sort(textList);
            res = textList;
        }
        
        if(order == Order.FREQUENCY) {
            FREQUENCIES_MAP = sortByValue(FREQUENCIES_MAP);
            for(String k : FREQUENCIES_MAP.keySet()) {
                for(String t : textList) {
                    if(t.equals(k))
                        res.add(t);
                }
            }
        }
        
        if(reverse)
            Collections.reverse(res);
        
        return res;
    }
    
    //Source: StackOverflow xD
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
    
    public static void WriteTexts(List<String> textList) {
        for(String t : getFormattedText(textList)) {
            System.out.println(t);
        }
    }
    
    public static List<String> getFormattedText(List<String> textList) {
        List<String> res = new ArrayList<>();
        
        for(String t: textList) {
            if(!res.contains(t + ": " + FREQUENCIES_MAP.get(t)))
                res.add(t + ": " + FREQUENCIES_MAP.get(t));
        }
        
        return res;
    }
    
    public static void ReadXml(String fileName) {
        FREQUENCIES_MAP.clear();
        try {
            final String[] parts = fileName.split("\\.");
            if(parts.length <= 0) {
                throw new Exception("File name is invalid");
            }
            final String ext = parts[parts.length - 1];
            
            if(!ext.equals(SOURCE_EXT)) {
                throw new Exception("Only" + SOURCE_EXT + "files are accepted");
            }
            
            File file = new File(fileName);
            if(!file.exists()) {
                throw new Exception("File not found");
            }
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            
            NodeList dataFields = doc.getElementsByTagName(DATA_FIELD_NAME);
            for(int i = 0; i < dataFields.getLength(); i++) {
                
                Element df = (Element) dataFields.item(i);
                
                if(DATA_FIELD_ATTR_FILTER_VALUE.equals(df.getAttribute(DATA_FIELD_ATTR_FILTER_NAME))) {
                    NodeList subFields = df.getElementsByTagName(SUBFIELD_NAME);
                    
                    if(subFields != null && subFields.getLength() > 0) {
                        for(int j = 0; j < subFields.getLength(); j++) {
                        
                            Element sf = (Element) subFields.item(j);

                            if(SUBFIELD_ATTR_FILTER_VALUE.equals(sf.getAttribute(SUBFIELD_ATTR_FILTER_NAME))) {
                                
                                String name = sf.getTextContent();
                                
                                texts.add(name);
                                
                                int fr = (int)FREQUENCIES_MAP.getOrDefault(name, 0);
                                fr++;
                                FREQUENCIES_MAP.put(name, fr);
                            }
                        }
                    }
                }
            }
            System.out.println("File read complete!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
