/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.benjaminsimon.testconsole;

import com.benjaminsimon.testconsole.utils.MapUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author simon
 */
public class TextList {
 
    public static enum Order{
        NAME,
        FREQUENCY
    }
    
    private List<String> texts;
    private Map<String, Integer> frequencyMap;
    
    public TextList(){
        this.texts = new ArrayList<>();
        this.frequencyMap = new HashMap<>();
    }
    
    public boolean contains(String text) {
        return this.texts.contains(text);
    }
    
    public void addText(String text) {
        this.texts.add(text);
        this.addOccurence(text);
    }
    
    private void addOccurence(String text) {
         //If the given text have already appeared, get its value from the frequencies map.
        //Else start from 0
        int frequency = (int)this.frequencyMap.getOrDefault(text, 0);
        
        //Increment the frequency
        //It will be at least 1
        frequency++;
        
        frequencyMap.put(text, frequency);
    }
    
    public void filter(String filterValue) {
        this.texts = this.texts
                .stream()
                .filter(t -> (t.startsWith(filterValue)))
                .collect(Collectors.toList());
    }
    
    public List<String> sort(Order order, boolean reverse) {
        
        switch(order) {
            case FREQUENCY:
                sortFrequency();
                break;
            case NAME:
            default:
                sortAlphabetic();
                break;
        }
        
        if(reverse)
            Collections.reverse(this.texts);
        
        return this.texts;
    }

    private void sortFrequency() {
        this.frequencyMap = MapUtils.sortByValue(this.frequencyMap);
        
        Set<String> orderedList = this.frequencyMap.keySet();
        
        orderedList.retainAll(texts);
        
        this.texts = new ArrayList<>(orderedList);
    }

    private void sortAlphabetic() {
        Collections.sort(this.texts);
    }
    
    public int getNumberOfTexts() {
        return this.texts.size();
    }
    
    public void writeTexts() {
        getFormattedText().forEach(item -> {
            System.out.println(item);
        });
    }
    
    public List<String> getFormattedText() {
        List<String> formattedTexts = new ArrayList<>();
        
        this.texts.stream()
                .map(item -> item + ": " + this.frequencyMap
                        .get(item))
                .filter(line -> (!formattedTexts.contains(line)))
                .forEachOrdered(line -> {
                    formattedTexts.add(line);
                });
        
        return formattedTexts;
    }
}
