/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.benjaminsimon.testconsole;

import com.benjaminsimon.testconsole.utils.MapUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author simon
 */
public class TextList {
 
    public enum Order{
        NAME,
        FREQUENCY
    }
    
    private LinkedHashMap<String, Integer> frequencyMap;
    
    public TextList(){
        this.frequencyMap = new LinkedHashMap<>();
    }
    
    public void clear() {
        this.frequencyMap.clear();
    }
    
    public void addText(String text) {
        //If the given text have already appeared, get its value from the frequencies map.
        //Else start from 0
        int frequency = (int)this.frequencyMap.getOrDefault(text, 0);
        
        //Increment the frequency
        //It will be at least 1
        frequency++;
        
        this.frequencyMap.put(text, frequency);
    }
    
    public Map<String, Integer> filterAndSort(FilterAndOrder filterAndOrder) {
        
        //Only filter if a value is present
        if(filterAndOrder.getFilter() != null && !filterAndOrder.getFilter().isBlank())
            this.filter(filterAndOrder.getFilter());
        
        return this.sort(filterAndOrder.getOrder(), filterAndOrder.isReverse());
    }
    
    private void filter(String filterValue) {
        LinkedHashMap<String, Integer> filteredMap = new LinkedHashMap<>();
        
        this.frequencyMap
            .entrySet()
            .stream()
            .filter(item -> (item.getKey().startsWith(filterValue)))
            .forEachOrdered(item -> filteredMap.put(item.getKey(), item.getValue()));
        
        this.frequencyMap = filteredMap;
    }
    
    private Map<String, Integer> sort(Order order, boolean reverse) {
        
        switch(order) {
            case FREQUENCY:
                sortFrequency(reverse);
                break;
            case NAME:
            default:
                sortAlphabetic(reverse);
                break;
        }
        
        return this.frequencyMap;
    }

    private void sortFrequency(boolean reverse) {
        this.frequencyMap = (LinkedHashMap<String, Integer>)MapUtils.sortByValue(this.frequencyMap, reverse);
    }

    private void sortAlphabetic(boolean reverse) {
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        
        Comparator<String> comparator = MapUtils.getReverseComparator(reverse);
        
        this.frequencyMap
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(comparator))
                .forEachOrdered(item -> sortedMap.put(item.getKey(), item.getValue()));
        
        this.frequencyMap = sortedMap;
    }
    
    public int getNumberOfTexts() {
        return this.frequencyMap.size();
    }
    
    public void writeTexts() {
        getFormattedText().forEach(item -> {
            System.out.println(item);
        });
    }
    
    public List<String> getFormattedText() {
        List<String> formattedTexts = new ArrayList<>();
        
        this.frequencyMap
                .entrySet()
                .stream()
                .map(item -> item.getKey() + ": " + item.getValue())
                .forEachOrdered(line -> {
                    formattedTexts.add(line);
                });
        
        return formattedTexts;
    }
}
