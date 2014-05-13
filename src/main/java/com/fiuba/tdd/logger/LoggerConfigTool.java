package com.fiuba.tdd.logger;

import java.util.List;

public class LoggerConfigTool {

    // TODO code smell: this four attributes appear altogether many times
    final static private Logger.Level defaultLevel = Logger.Level.DEBUG;
    final static private String defaultFormat = "%d{HH:mm:ss} %n %p %n %t %n %m ";
    final static private String defaultSeparator = "-";
    final static private Appendable defaultOutput = System.out;

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

    public LoggerConfigTool(String configFileName) {
        // TODO load file and set config values
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
