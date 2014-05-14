package com.fiuba.tdd.logger.utils;

import com.fiuba.tdd.logger.Logger;
import com.fiuba.tdd.logger.Logger.Level;
import com.fiuba.tdd.logger.Appendable;
import com.fiuba.tdd.logger.writers.ConsoleAppender;
import com.fiuba.tdd.logger.utils.LoggerConfig.ConfigKey;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoggerConfigTool {

    public static final String configRegexPattern = "(?i)(%s|%s|%s)=(.+)";

    private static LoggerConfig config;

    final static private com.fiuba.tdd.logger.Appendable defaultOutput = new ConsoleAppender();

    private Level level;
    private String format;
    private String separator;
    private List<Appendable> outputs;

    public LoggerConfigTool() {
        config = new LoggerConfig();
        outputs.add(defaultOutput);
    }

    public LoggerConfigTool(String configFileName) throws IOException {

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
            br = new BufferedReader(new FileReader(configFileName));
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

        if (line.matches(configFormat)){
            final Matcher matcher = Pattern.compile(configFormat).matcher(line);

            return new HashMap<ConfigKey, String>(){{
                put(ConfigKey.valueOf(matcher.group(1)), matcher.group(2));
            }};
        }

        return null;
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
