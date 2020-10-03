/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.benjaminsimon.testconsole.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility methods for Maps.
 * @author simon
 */
public class MapUtils {
 
    /**
     * Sorts a Map by value.
     * @param map The map that should be sorted.
     * @param reverse boolean that specifies if it should sort in reverse order.
     * @return the sorted map.
     * @see getReverseComparator
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, boolean reverse) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        
        Comparator<V> comparator = getReverseComparator(reverse);
        
        list.sort(Map.Entry.comparingByValue(comparator));

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
    
    /**
     * Gets the comparator required by the value of reverse ordering.
     * @param reverse boolean true: reverseOrder, false: naturalOrder
     * @return The comparator that fits reverse parameter value
     */
    public static Comparator getReverseComparator(boolean reverse) {
        return reverse ? Comparator.reverseOrder() : Comparator.naturalOrder();
    }
}
