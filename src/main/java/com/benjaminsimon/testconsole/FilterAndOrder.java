/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.benjaminsimon.testconsole;

/**
 *
 * @author simon
 */
public class FilterAndOrder {
    
    private TextList.Order order = TextList.Order.NAME;
    private String filter = null;
    private boolean reverse = false;
    
    public FilterAndOrder() {}

    public TextList.Order getOrder() {
        return order;
    }

    public void setOrder(TextList.Order order) {
        this.order = order;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public boolean isReverse() {
        return reverse;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }
    
    
    
}
