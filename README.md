# junior-java-test-console
Created for a job application. Used in my junior-java-test project

# The purpose
The application is a command line program, which can read one xml file at a time, scrape through it and save data that is acceptable by the specification. It can also sort the data alphabetically or by frequency. It can filter and reverse order as well.

# Usage
To run the program, you have to use command line. After the name of the file you can specify four arguments separated by spaces.

  * File Path (Required)
    The file path to the XML file you'd like to read. It is important that it needs to have .xml extension.
  * Filter Value (Optional)
     
     If you are only interested in results starting with a specific character chain, you can give that as the second argument.
     
     If you don't want to filter, just type in an '_' (underscore) character as the second argument.
  * Order (Optional)
    
    If you would like to order your list by frequency type 'f' as the third argument.
    
    If you use any other character or word, the program will fall back to ordering alphabetically.
  * Reverse (Optional)
  
    If you would like to get your list in reverse order, type 'rev' as the fourth argument.
    
    If you use any other character or word, the program will fall back to ordering naturally.
    
## Important
The default placeholder character is '_' (underscore). If you want to make sure that your argument does not modify your results, use it. Future changes may add other arguments but the project will reserve the placeholder character.

# For Developers and Reviewers
I will now further introduce the inner workings of the code.

## Entry

The entry point for console usage is the Main.java file. It handles the console arguments. Checks them for validity and calls the necessarry methods from other classes to be able to print the results to the console.

It sets up the logger, creates an XmlReader and a textList instance, calls filterAndSort on textList, provides feedback and prints the results to the console.
