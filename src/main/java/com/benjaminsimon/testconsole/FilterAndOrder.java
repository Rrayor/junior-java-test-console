package com.benjaminsimon.testconsole;

/**
 * Stores filtering and ordering options.
 * @author simon
 */
public class FilterAndOrder {
    
    /**
     * Stores the order value for sorting.
     * @see TextList.Order
     */
    private TextList.Order order = TextList.Order.NAME;
    
    /**
     * Stores the filter value for filtering
     */
    private String filter = null;
    
    /**
     * Stores the reverse value for sorting
     */
    private boolean reverse = false;
    
    /**
     * Constructor
     */
    public FilterAndOrder() {}

    /**
     * Getter for order
     * @return TextList.Order
     * @see TextList.Order
     */
    public TextList.Order getOrder() {
        return order;
    }

    /**
     * Setter for order
     * @param order
     * @see TextList.Order
     */
    public void setOrder(TextList.Order order) {
        this.order = order;
    }

    /**
     * Getter for filter
     * @return String
     */
    public String getFilter() {
        return filter;
    }

    /**
     * Setter for order
     * @param filter
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     * Getter for reverse
     * @return boolean
     */
    public boolean isReverse() {
        return reverse;
    }

    /**
     * Setter for reverse
     * @param reverse
     */
    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }    
}
