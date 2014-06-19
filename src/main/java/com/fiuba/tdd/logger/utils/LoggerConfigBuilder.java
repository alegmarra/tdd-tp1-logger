package com.fiuba.tdd.logger.utils;

import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.exceptions.UnsuportedFormatException;
import com.fiuba.tdd.logger.format.parser.ConfigParser;
import com.fiuba.tdd.logger.format.parser.PropertiesParserFactory;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * Clase destinada a la configuración del logger. Puede tomar un archivo
 * de properties con la configuración deseada y rellenar aquellos campos
 * faltantes con valores por defecto. También puede ser construido sólo con
 * valores por defecto.
 *
 * Es parte de la interfaz pública.
 */
public class LoggerConfigBuilder {

    private static final String configPrefix = "logger­config";
    private static final String[] supportedExtensions = {".properties", ".xml"};

    private Map<String, LoggerConfig> loggerConfigs;
    private final static LoggerConfig defaultConfig = new LoggerConfig();

    public static LoggerConfigBuilder getInstance(String configFileName)
            throws IOException, InvalidArgumentException
    {
        return new LoggerConfigBuilder(configFileName);
    }

    public LoggerConfigBuilder() throws IOException, InvalidArgumentException { this(""); }

    public LoggerConfigBuilder(String configFileName) throws IOException, InvalidArgumentException {

        URL properties = getPropertiesFile(configFileName);
        if (properties != null){
            try {
                String extension = FilenameUtils.getExtension(properties.toString());
                ConfigParser parser = PropertiesParserFactory.getParser(extension);
                loggerConfigs = parser.parseConfigFile(properties.openStream());

            } catch (UnsuportedFormatException e) {
                e.printStackTrace();
            }
        } else {
            loggerConfigs = new HashMap<>();
        }
    }

    private URL getPropertiesFile(String filename) {
        URL properties = null;
        if (filename == null || filename.equals("")) {
            String configFileName = "";
            int retries = 0;
            do {
                configFileName = configPrefix.concat(supportedExtensions[retries++]);
                properties = Thread.currentThread().getContextClassLoader().getResource(configFileName);
            } while (properties == null && retries< supportedExtensions.length);
        } else {
            properties = Thread.currentThread().getContextClassLoader().getResource(filename);
        }
        return properties;
    }

    public static LoggerConfig getConfig() {
        return defaultConfig;
    }
    
    public LoggerConfig getConfig(String name) {
        return loggerConfigs.get(name);
    }

}
