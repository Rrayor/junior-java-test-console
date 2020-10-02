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
 *
 * @author simon
 */
public class MapUtils {
 
    //Source: StackOverflow xD
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, boolean reverse) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        
        Comparator<V> comparator = reverse ? Comparator.reverseOrder() : Comparator.naturalOrder();
        
        list.sort(Map.Entry.comparingByValue(comparator));

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
