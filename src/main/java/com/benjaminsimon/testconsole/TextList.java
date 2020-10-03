package com.benjaminsimon.testconsole;

import com.benjaminsimon.testconsole.utils.MapUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores the values appropriate for the project.
 * It is also responsible for filtering and sorting the data, it stores.
 * @author simon
 */
public class TextList {
 
    /**
     * An enum for the possible Order values.
     */
    public enum Order{
        NAME,
        FREQUENCY
    }
    
    /**
     * Stores the data of the instance with the data as key and its frequency
     * as value.
     */
    private LinkedHashMap<String, Integer> textMap;
    
    /**
     * Constructor
     */
    public TextList(){
        this.textMap = new LinkedHashMap<>();
    }
    
    /**
     * Clears the instances data.
     */
    public void clear() {
        this.textMap.clear();
    }
    
    /**
     * Add a single value to textMap and/or increase its frequency.
     * @param text The String to add.
     * @see textMap
     */
    public void addText(String text) {
        //If the given text have already appeared, get its value from the map.
        //Else start from 0
        int frequency = (int)this.textMap.getOrDefault(text, 0);
        
        //Increment the frequency
        //It will be at least 1
        frequency++;
        
        this.textMap.put(text, frequency);
    }
    
    /**
     * Runs the filtering and sorting functions according to the parameters
     * provided.
     * @param filterAndOrder FilterAndOrder instance containing the parameters.
     * @return Filtered and sorted map.
     * @see textMap
     * @see filter
     * @see sort
     */
    public Map<String, Integer> filterAndSort(FilterAndOrder filterAndOrder) {
        
        //Only filter if a value is present
        if(filterAndOrder.getFilter() != null && !filterAndOrder.getFilter().isBlank())
            this.filter(filterAndOrder.getFilter());
        
        this.sort(filterAndOrder.getOrder(), filterAndOrder.isReverse());
        
        return this.textMap;
    }
    
    /**
     * Filter textMap by the parameter provided
     * @param filterValue
     * @see textMap
     */
    private void filter(String filterValue) {
        LinkedHashMap<String, Integer> filteredMap = new LinkedHashMap<>();
        
        this.textMap
            .entrySet()
            .stream()
            .filter(item -> (item.getKey().startsWith(filterValue)))
            .forEachOrdered(item -> filteredMap.put(item.getKey(), item.getValue()));
        
        this.textMap = filteredMap;
    }
    
    /**
     * Sorts textMap according to the parameters provided.
     * @param order Order value
     * @param reverse boolean that specifies if it should sort in reverse order.
     * @see textMap
     * @see sortFrequency
     * @see sortAlphabetic
     */
    private void sort(Order order, boolean reverse) {
        
        switch(order) {
            case FREQUENCY:
                sortFrequency(reverse);
                break;
            case NAME:
            default:
                sortAlphabetic(reverse);
                break;
        }
    }

    
    /**
     * Sorts textMap by frequency.
     * @param reverse boolean that specifies if it should sort in reverse order.
     * @see textMap
     * @see MapUtils.sortByValue
     */
    private void sortFrequency(boolean reverse) {
        this.textMap = (LinkedHashMap<String, Integer>)MapUtils.sortByValue(this.textMap, reverse);
    }

    
    /**
     * Sorts textMap in alphabetic order.
     * @param reverse boolean that specifies if it should sort in reverse order.
     * @see textMap
     * @see MapUtils.getReverseComparator
     */
    private void sortAlphabetic(boolean reverse) {
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        
        Comparator<String> comparator = MapUtils.getReverseComparator(reverse);
        
        this.textMap
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(comparator))
                .forEachOrdered(item -> sortedMap.put(item.getKey(), item.getValue()));
        
        this.textMap = sortedMap;
    }
    
    /**
     * Get the size of textMap
     * @return The size of textMap
     * @see textMap
     */
    public int getNumberOfTexts() {
        return this.textMap.size();
    }
    
    /**
     * Prints the keys and values from textMap to the console formatted.
     * @see textMap
     * @see getFormattedText
     */
    public void writeTexts() {
        getFormattedText().forEach(item -> {
            System.out.println(item);
        });
    }
    
    /**
     * Creates formatted Strings from textMap keys and values.
     * @return A List of formatted Strings.
     * @see textMap
     */
    public List<String> getFormattedText() {
        List<String> formattedTexts = new ArrayList<>();
        
        this.textMap
                .entrySet()
                .stream()
                .map(item -> item.getKey() + ": " + item.getValue())
                .forEachOrdered(line -> {
                    formattedTexts.add(line);
                });
        
        return formattedTexts;
    }
}
