package com.fiuba.tdd.logger.utils;

import com.fiuba.tdd.logger.*;
import com.fiuba.tdd.logger.Appendable;
import com.fiuba.tdd.logger.writers.ConsoleAppender;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class LoggerConfigTool {

    // TODO code smell: this four attributes appear altogether many times
    final static private Logger.Level defaultLevel = Logger.Level.DEBUG;
    final static private String defaultFormat = "%d{HH:mm:ss} %n %p %n %t %n %m ";
    final static private String defaultSeparator = "-";
    final static private com.fiuba.tdd.logger.Appendable defaultOutput = new ConsoleAppender();

    private Logger.Level level;
    private String format;
    private String separator;
    private List<Appendable> outputs;

    public LoggerConfigTool() {
        level = defaultLevel;
        format = defaultFormat;
        separator = defaultSeparator;
        outputs.add(defaultOutput);
    }

    public LoggerConfigTool(String configFileName) throws IOException {

        final String configFormat = ".+=.+";

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(configFileName));
            String currentLine;

            while ((currentLine = br.readLine()) != null) {

                if (currentLine.matches(configFormat))
                    parseConfigElement(currentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw e;

        } finally {
            if (br != null) br.close();
        }

    }

    private void parseConfigElement(String currentLine) {

    }

    public void config(Logger logger) {
        logger.setLevel(level);
        logger.setFormat(format);
        logger.setSeparator(separator);

        for ( Appendable output : outputs ) {
            logger.registerAppender(output);
        }
    }
}
