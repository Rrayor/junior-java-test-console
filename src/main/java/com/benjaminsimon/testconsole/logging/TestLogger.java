package com.benjaminsimon.testconsole.logging;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Logger for the project
 * @author simon
 */
public class TestLogger {

    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;
    
    private static final String LOGFILE_PATH = "logs.log";

    /**
     * Override of setup function
     * @throws java.io.IOException
     */
    public static void setup() throws IOException, InvalidPathException, IllegalArgumentException {
        setup(true);
    }
    
    /**
     * Sets up the logger to log to logs.log file and console if parameter is true.
     * @param useConsole boolean that specifies if the logger should log to the console.
     * @throws java.io.IOException
     */
    public static void setup(boolean useConsole) throws IOException, InvalidPathException, IllegalArgumentException {

        // get the global logger to configure it
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        if(!useConsole) {
            suppressConsoleLogging();
        }

        logger.setLevel(Level.INFO);
        fileTxt = new FileHandler(LOGFILE_PATH);

        // create a TXT formatter
        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);
    }

    /**
     * Prevents logging to the console.
     */
    private static void suppressConsoleLogging() throws SecurityException {
        // suppress the logging output to the console
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }
    }
}
