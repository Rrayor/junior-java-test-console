/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.benjaminsimon.testconsole.config;

/**
 * Stores constants for XmlReader configuration
 * @author simon
 */
public class XmlReaderConfig {
    
        /**
         * The extension that the file should have
         */
        public static final String SOURCE_EXT = "xml";
        
        /**
         * The tag name of datafield
         */
        public static final String DATA_FIELD_NAME = "datafield";
        
        /**
         * The tag name of subfield
         */
        public static final String SUBFIELD_NAME = "subfield";

        /**
         * The attribute name that we should check on datafields
         */
        public static final String DATA_FIELD_ATTR_FILTER_NAME = "tag";
        
        /**
         * The attribute name that we should check on subfields
         */
        public static final String SUBFIELD_ATTR_FILTER_NAME = "code";
        
        /**
         * The attribute value that we should check on datafields
         */
        public static final String DATA_FIELD_ATTR_FILTER_VALUE = "100";
        
        
        /**
         * The attribute value that we should check on subfields
         */
        public static final String SUBFIELD_ATTR_FILTER_VALUE = "a";
}
