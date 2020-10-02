package com.benjaminsimon.testconsole;

import com.benjaminsimon.testconsole.TextList.Order;
import com.benjaminsimon.testconsole.config.InputConfig;

/**
 *
 * @author simon
 */
public class Main {
    /**
     * @param args the command line arguments
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

            //Only filter the list if filter has value
            if (FilterAndOrder.getFilter() != null)
                 textList.filter(FilterAndOrder.getFilter());

            //Help for testing and overall feedback
            printFeedback();
            
            //Sorting
            textList.sort(FilterAndOrder.getOrder(), FilterAndOrder.isReverse());
            
            //Additional feedback for user
            System.out.println(textList.getNumberOfTexts() + " items were found");
            
            //Put found FORMATTED values to the output stream
            textList.writeTexts();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void printFeedback() {
        System.out.println("You filtered by: " + (FilterAndOrder.getFilter() == null ? "No filter value was given" : FilterAndOrder.getFilter()));
        System.out.println("You ordered by: " + FilterAndOrder.getOrder().name());
        System.out.println("Reverse: " + FilterAndOrder.isReverse());
    }

    private static void checkOptionalArguments(String[] args) {
        
        //Check the second argument. If it is valid It is assigned to the filter.
        if(args.length > 1 && args[1] != null && args[1].length() > 0)
            if(!InputConfig.PLACEHOLDER__STRING.equals(args[1]))
                FilterAndOrder.setFilter(args[1]);
        
        
        //Check the third argument. Defaultly if it is an 'f', change the orderVal to FREQUENCY
        if(args.length > 2 && args[2] != null && args[2].length() > 0)
            if(InputConfig.ORDER_BY_FREQUENCY__STRING.equals(args[2]))
                FilterAndOrder.setOrder(Order.FREQUENCY); //else it stays as default: Order.NAME
        
        //Check the fourth argument. Defaultly if it is 'rev', set reverse to true
        if(args.length > 3 && args[3] != null && args[3].length() > 0)
            if(InputConfig.REVERSE_ORDER__STRING.equals(args[3]))
                FilterAndOrder.setReverse(true); //else it remains as default: false
    }
    
    private static void welcomeMessage() {
        System.out.println("Welcome! Please enter a file path to begin!");
        System.out.println("To filter by name - SECOND argument");
        System.out.println("To set ordering by frequency - Enter 'f' for the THIRD argument");
        System.out.println("To reverse the order - Enter 'rev' for the FOURTH argument");
        System.out.println("To skip any argument (expect for the file name - that is required)- Enter '_' in place of it");
        System.out.println("");
    }
    
    private static class FilterAndOrder {
        private static Order order = Order.NAME;
        private static String filter = null;
        private static boolean reverse = false;

        public static Order getOrder() {
            return order;
        }

        public static void setOrder(Order order) {
            FilterAndOrder.order = order;
        }

        public static String getFilter() {
            return filter;
        }

        public static void setFilter(String filter) {
            FilterAndOrder.filter = filter;
        }

        public static boolean isReverse() {
            return reverse;
        }

        public static void setReverse(boolean reverse) {
            FilterAndOrder.reverse = reverse;
        }        
    }
}
