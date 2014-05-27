package com.fiuba.tdd.logger.utils;

import com.fiuba.tdd.logger.Appendable;
import com.fiuba.tdd.logger.SimpleLogger;
import com.fiuba.tdd.logger.SimpleLogger.Level;
import com.fiuba.tdd.logger.internal.InvalidArgumentException;
import com.fiuba.tdd.logger.utils.LoggerConfig.ConfigKey;
import com.fiuba.tdd.logger.writers.ConsoleAppender;


import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoggerConfigTool {

    public static final String configRegexPattern = "(?i)(%s|%s|%s)=(.+)";

    private static LoggerConfig config;

    final static private Appendable defaultOutput = new ConsoleAppender();

    private Level level;
    private String format;
    private String separator;
    private List<Appendable> outputs = new LinkedList<>();

    public LoggerConfigTool() {
        config = new LoggerConfig();
        outputs.add(defaultOutput);
    }

    public LoggerConfigTool(String configFileName) throws IOException, InvalidArgumentException {

        if (configFileName == null || configFileName.isEmpty())
            throw new InvalidArgumentException("The given filename was null or the string was empty");

        LoggerConfig defaultConfig = new LoggerConfig();

        Map<ConfigKey, String> fileConfig = parseConfigFile(configFileName);

        Level level = fileConfig.containsKey(ConfigKey.LEVEL) ? Level.valueOf(fileConfig.get(ConfigKey.LEVEL)) : defaultConfig.level;
        String format = fileConfig.containsKey(ConfigKey.FORMAT) ? fileConfig.get(ConfigKey.FORMAT) : defaultConfig.format;
        String separator = fileConfig.containsKey(ConfigKey.SEPARATOR) ? fileConfig.get(ConfigKey.SEPARATOR) : defaultConfig.separator;

        config = new LoggerConfig(format, level, separator);
    }


    private Map<ConfigKey, String> parseConfigFile(String configFileName)
            throws IOException
    {
        Map<ConfigKey, String> configValues = new HashMap<>();
        BufferedReader br = null;
        try {

            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFileName);
            br = (is != null) ? new BufferedReader(new InputStreamReader(is)) : new BufferedReader(new FileReader(configFileName));

            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                Map<ConfigKey, String> entry = parseConfigElement(currentLine);

                if (entry != null){
                    configValues.putAll(entry);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw e;

        } finally {
            if (br != null) br.close();
        }

        return configValues;
    }

    private Map<ConfigKey, String> parseConfigElement(String line) {
        final String configFormat = String.format(configRegexPattern, ConfigKey.FORMAT.name(), ConfigKey.LEVEL, ConfigKey.SEPARATOR);

        final Matcher matcher = Pattern.compile(configFormat).matcher(line);
        if (matcher.matches()){

            return new HashMap<ConfigKey, String>(){{
                put(ConfigKey.valueOf(matcher.group(1).toUpperCase()), matcher.group(2).trim());
            }};
        }

        return null;
    }


    public LoggerConfig getConfig() {
        return config;
    }

    public List<Appendable> getRegisteredAppenders(){
        return Collections.unmodifiableList(outputs);
    }


    public void config(SimpleLogger logger) {

        logger.setLevel(level);
        logger.setFormat(format);
        logger.setSeparator(separator);

        for ( Appendable output : outputs ) {
            try {
                logger.registerAppender(output);
            } catch (InvalidArgumentException e) {
                e.printStackTrace();
            }
        }
    }
}
