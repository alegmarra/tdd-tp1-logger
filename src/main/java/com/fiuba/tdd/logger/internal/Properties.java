package com.fiuba.tdd.logger.internal;

import com.fiuba.tdd.logger.utils.LoggerConfig;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Clase que replica, limitadamente, a java.util.Properties, pero con las mejoras
 * de:
 * <ol>
 *     <li> Soportar UTF-8 en los archivos (java.util.Properties utiliza
 *     ISO 8859-1)</li>
 *     <li> Soportar obtener las propiedades a partir del enum definido en
 *     LoggerConfitTool </li>
 * </ol>
 */
public class Properties {

    public static final String configRegexPattern = "(?i)(%s|%s|%s)=(.+)";

    private Map<LoggerConfig.ConfigKey, String> fileConfig;


    public Properties(String configFileName)
    throws IOException
    {
        fileConfig = parseConfigFile(configFileName);
    }

    private Map<LoggerConfig.ConfigKey, String> parseConfigFile(String configFileName)
            throws IOException
    {
        Map<LoggerConfig.ConfigKey, String> configValues = new HashMap<>();
        BufferedReader br = null;
        try {

            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFileName);
            br = (is != null) ? new BufferedReader(new InputStreamReader(is)) : new BufferedReader(new FileReader(configFileName));

            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                Map<LoggerConfig.ConfigKey, String> entry = parseConfigElement(currentLine);

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

    private Map<LoggerConfig.ConfigKey, String> parseConfigElement(String line) {
        final String configFormat = String.format(configRegexPattern, LoggerConfig.ConfigKey.FORMAT.name(), LoggerConfig.ConfigKey.LEVEL, LoggerConfig.ConfigKey.SEPARATOR);

        final Matcher matcher = Pattern.compile(configFormat).matcher(line);
        if (matcher.matches()){

            return new HashMap<LoggerConfig.ConfigKey, String>(){{
                put(LoggerConfig.ConfigKey.valueOf(matcher.group(1).toUpperCase()), matcher.group(2).trim());
            }};
        }

        return null;
    }

    public String getProperty(LoggerConfig.ConfigKey configKey, String defaultConfig) {
        return fileConfig.containsKey(configKey) ? fileConfig.get(configKey) : defaultConfig;
    }
}
