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

## Reading XML

The class responsible for reading the xml file is XmlReader. It holds a TextList instance for readability purposes mostly. The most prominent method in this class is readXml() which requires a file path String as its parameter.

The Lifecycle of an XmlReader instance starts with its constructor, which instantiates its textList property. Then when readXml() is called, it checks file path validity and if the file exists or not. If everything is ok, it creates a Document in memory based on the XML files contents.

First it gets the datafield tags and traverses them. If a datafield meets certain conditions, it gets its subfields and traverses them. If the subfield matches certain conditions, then its textContext is saved to the xmlReader instances textList property.

At the end of scraping the document, the readXml() method returns the instances textList property.

## TextList class

The TextList class handles the data saved from the XML file. It has a LinkedHashMap<String, Integer> textMap property, where it stores its data. It executes all of its operations on this map.

It can:
 * clear the map
 * add to the map
 * filter the map
 * sort the map by keys (alphabetically)
 * sort the map by values (by frequency)
 
It also has some utility functions such as:
 * getting the number of items in its map
 * formatting the map to human readable String
 * write the human readable Strings to the console
 
For filtering and ordering the program uses the FilterAndOrder class. Every instance of FilterAndOrder holds 3 properties:
 * String filter
 * Order order
 * boolean reverse
 
It is useful mainly for readability as we only need to pass around these objects. In the future, it could handle its own validation as well.

The TextList class also holds the Order enum, which has two values:
 * NAME
 * FREQUENCY
 
I use this enum for security and compatibility purposes. Any application that uses this API has to convert its input to this enum, hence it's more secure and prevents too much overhead creating compatibility method for every type of client.

## MapUtils class

The Maputils class stores two utility functions:
 * sortByValue sorts a map by its values. if its reverse parameter is true, then in reverse order.
 * getReverseComparator returns Comparator.reverseOrder() or Comparator.naturalOrder() based on its boolean reverse parameter.
 
## Logging
 The TestLogger class is a simple logger class. It has a setup method and LOGFILE_PATH constant field. The setup method sets up the logger to use the provided filePath for logging in .txt format. If its boolean useConsole parameter is false, then it won't use console for logging at all. It has an overrided version, which doesn't require any parameters, instead calls it with true value.
 
 The default behaviour of the logger is to use the console as well as the file for logging.
