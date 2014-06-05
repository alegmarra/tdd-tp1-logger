package com.fiuba.tdd.logger.format.parser;

import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.format.parser.model.ConfigDto;
import com.fiuba.tdd.logger.format.parser.model.LoggerProperties;
import com.fiuba.tdd.logger.utils.Configurable;
import com.fiuba.tdd.logger.utils.LoggerConfig;
import com.fiuba.tdd.logger.utils.LoggerConfig.ConfigKey;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Clase que replica, limitadamente, a java.util.PropertiesParser, pero con las mejoras
 * de:
 * <ol>
 *     <li> Soportar UTF-8 en los archivos (java.util.PropertiesParser utiliza
 *     ISO 8859-1)</li>
 *     <li> Soportar obtener las propiedades a partir del enum definido en
 *     LoggerConfitTool </li>
 * </ol>
 */
public class PropertiesParser implements ConfigParser{

    public static final String configRegexPattern = "(?i)(.+)\\.(%s|%s|%s)=(.+)";

    public PropertiesParser(){}

    public List<LoggerConfig> parseConfigFile(InputStream config)
            throws InvalidArgumentException, IOException
    {

        List<LoggerConfig> loggerConfigs = new ArrayList<>();

        LoggerProperties logger = new LoggerProperties();
        logger = parseLoggerProperties(config);

        for (ConfigDto configDto: logger.configs){
            LoggerConfig parsedConfig = new LoggerConfig(configDto.format,
                    Configurable.Level.valueOf(configDto.level),
                    configDto.separator);

            addAppenders(configDto, parsedConfig);
            addFilters(configDto, parsedConfig);


            loggerConfigs.add(parsedConfig);
        }

//        Map<LoggerConfig.ConfigKey, String> configValues = new HashMap<>();
//        BufferedReader br = null;
//        try {
//            if (config == null)
//                throw new InvalidArgumentException("Config input file was null");
//
//            br = new BufferedReader(new InputStreamReader(config));
//            String currentLine;
//            while ((currentLine = br.readLine()) != null) {
//                Map<ConfigKey, String> entry = parseConfigElement(currentLine);
//
//                if (entry != null){
//                    configValues.putAll(entry);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw e;
//
//        } finally {
//            if (br != null) br.close();
//        }
//
//        return createConfigFromMap(configValues);
        return loggerConfigs;
    }

    private void addFilters(ConfigDto configDto, LoggerConfig parsedConfig) {

    }

    private void addAppenders(ConfigDto configDto, LoggerConfig parsedConfig) {

    }

    private LoggerProperties parseLoggerProperties(InputStream config) {
        return null; // TODO
    }

    private LoggerConfig createConfigFromMap(Map<ConfigKey, String> configValues) throws InvalidArgumentException {
        return new LoggerConfig(
                                    configValues.get(ConfigKey.FORMAT),
                                    Configurable.Level.valueOf(configValues.get(ConfigKey.LEVEL)),
                                    configValues.get(ConfigKey.SEPARATOR)
                                );

    }

    private Map<LoggerConfig.ConfigKey, String> parseConfigElement(String line) {

        final String configFormat = String.format(configRegexPattern, LoggerConfig.ConfigKey.FORMAT.name(), LoggerConfig.ConfigKey.LEVEL, LoggerConfig.ConfigKey.SEPARATOR);

        // Somewhere over the rainbow...
        // Somewhere over here, calls to Instantiator should be done to create Filters and Appendables
        final Matcher matcher = Pattern.compile(configFormat).matcher(line);
        if (matcher.matches()){

            return new HashMap<LoggerConfig.ConfigKey, String>(){{
                put(LoggerConfig.ConfigKey.valueOf(matcher.group(2).toUpperCase()), matcher.group(3).trim());
            }};
        }

        return null;
    }
}
