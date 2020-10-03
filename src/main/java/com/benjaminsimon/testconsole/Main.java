package com.benjaminsimon.testconsole;

import com.benjaminsimon.testconsole.TextList.Order;
import com.benjaminsimon.testconsole.config.InputConfig;

/**
 *
 * @author simon
 */
public class Main {
    
    /**
     * Stores filtering and ordering options.
     * @see FilterAndOrder
     */
    private static final FilterAndOrder filterAndOrder = new FilterAndOrder();
    
    /**
     * Entry point for running in command line
     * @param args the command line arguments
     * @see welcomeMessage
     * @see XmlReader
     * @see TextList
     * @see checkOptionalArguments
     * @see printFeedback
     */
    public static void main(String[] args) {
        
        //Welcome message and instructions
        welcomeMessage();
        
        try {
            XmlReader xmlReader = new XmlReader();
            
            //No arguments were given. At least a file path is required as argument
            if(args.length <= 0) {
                System.out.println("Please enter a file path for the source");
                System.exit(0);
            }
            
            //Read the file
            TextList textList = xmlReader.readXml(args[0]);
            
            //Set the FilterAndOrder fields according to the arguments
            checkOptionalArguments(args);
            
            //Sorting
            textList.filterAndSort(filterAndOrder);
            
            //Help for testing and overall feedback
            printFeedback(textList.getNumberOfTexts());
            
            //Put found FORMATTED values to the output stream
            textList.writeTexts();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Checks if optional arguments are present and valid.
     * If so, sets them as filtering and ordering options.
     * @param args the command line arguments
     */
    private static void checkOptionalArguments(String[] args) {
        
        //Check the second argument. If it is valid It is assigned to the filter.
        if(args.length > 1 && args[1] != null && args[1].length() > 0)
            if(!InputConfig.PLACEHOLDER__STRING.equals(args[1]))
                filterAndOrder.setFilter(args[1]);
                
        //Check the third argument. Defaultly if it is an 'f', change the orderVal to FREQUENCY
        if(args.length > 2 && args[2] != null && args[2].length() > 0)
            if(InputConfig.ORDER_BY_FREQUENCY__STRING.equals(args[2]))
                filterAndOrder.setOrder(Order.FREQUENCY); //else it stays as default: Order.NAME
        
        //Check the fourth argument. Defaultly if it is 'rev', set reverse to true
        if(args.length > 3 && args[3] != null && args[3].length() > 0)
            if(InputConfig.REVERSE_ORDER__STRING.equals(args[3]))
                filterAndOrder.setReverse(true); //else it remains as default: false
    }
    
    /**
     * Greets the user and prints instructions.
     */
    private static void welcomeMessage() {
        System.out.println("Welcome! Please enter a file path to begin!");
        System.out.println("To filter by name - SECOND argument");
        System.out.println("To set ordering by frequency - Enter 'f' for the THIRD argument");
        System.out.println("To reverse the order - Enter 'rev' for the FOURTH argument");
        System.out.println("To skip any argument (expect for the file name - that is required)- Enter '_' in place of it");
        System.out.println("");
    }

    /**
     * Provides feedback for the user.
     * @param numberOfItems An integer to tell the user the number of items found.
     */
    private static void printFeedback(int numberOfItems) {
        System.out.println("You filtered by: " + (filterAndOrder.getFilter() == null ? "No filter value was given" : filterAndOrder.getFilter()));
        System.out.println("You ordered by: " + filterAndOrder.getOrder().name());
        System.out.println("Reverse: " + filterAndOrder.isReverse());
        System.out.println(numberOfItems + " items were found");
    }
}
