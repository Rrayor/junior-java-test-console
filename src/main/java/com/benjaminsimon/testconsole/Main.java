package com.benjaminsimon.testconsole;

import com.benjaminsimon.testconsole.Data.Order;

/**
 *
 * @author simon
 */
public class Main {
    
    private static Order OrderVal = Order.NAME;
    
    private static boolean reverse = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //WelcomeMessage(); For running in console
        
        if(args.length <= 0) {
            System.out.println("Please enter a file path for the source");
            System.exit(0);
        }
        
        Data.ReadXml(args[0]);
        
        String filter = null;
        if(args.length > 1 && args[1] != null && args[1].length() > 0)
            if(!"_".equals(args[1]))
                filter = args[1];
        
        if(args.length > 2 && args[2] != null && args[2].length() > 0)
            if("f".equals(args[2]))
                OrderVal = Order.FREQUENCY;
        
        if(args.length > 3 && args[3] != null && args[3].length() > 0)
            if("rev".equals(args[3]))
                reverse = true;
        
        
        Data.texts = Data.filter(Data.texts, filter);
        Data.texts = Data.sort(Data.texts, OrderVal, reverse);
        Data.WriteTexts(Data.texts);
    }
    
    private void WelcomeMessage() {
        System.out.println("Welcome! Please enter a file path to begin!");
        System.out.println("To filter by name - SECOND argument");
        System.out.println("To set ordering by frequency - Enter 'f' for the THIRD argument");
        System.out.println("To reverse the order - Enter 'rev' for the FOURTH argument");
        System.out.println("To skip any argument (expect for the file name - that is required)- Enter '_' in place of it");
    }
}
