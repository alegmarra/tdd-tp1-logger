package com.fiuba.tdd.logger.utils;

import com.fiuba.tdd.logger.appenders.Appendable;
import com.fiuba.tdd.logger.appenders.ConsoleAppender;
import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.exceptions.UnsuportedFormatException;
import com.fiuba.tdd.logger.filters.Filter;
import com.fiuba.tdd.logger.format.parser.ConfigParser;
import com.fiuba.tdd.logger.format.parser.PropertiesParserFactory;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;


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

    private List<LoggerConfig> loggerConfigs;
    private List<Appendable> outputs = new LinkedList<>();
    private List<Filter> filters = new LinkedList<>();

    public LoggerConfigBuilder() throws IOException, InvalidArgumentException {

        LoggerConfig defaultConfig = new LoggerConfig();
        defaultConfig.addAppender(new ConsoleAppender());

        URL properties = getPropertiesFile();
        if (properties == null){
            loggerConfigs.add(defaultConfig);

        } else {
            try {
                String extension = FilenameUtils.getExtension(properties.toString());
                ConfigParser parser = PropertiesParserFactory.getParser(extension);
                loggerConfigs = parser.parseConfigFile(properties.openStream());

            } catch (UnsuportedFormatException e) {
                e.printStackTrace();
                loggerConfigs.add(defaultConfig);
            }
        }
    }

    private URL getPropertiesFile() {
        URL properties = null;
        String configFileName = "";
        int retries = 0;
        do {
            configFileName = configPrefix.concat(supportedExtensions[retries++]);
            properties = Thread.currentThread().getContextClassLoader().getResource(configFileName);
        } while (properties == null && retries< supportedExtensions.length);
        return properties;
    }


    public LoggerConfig getConfig() {
        return loggerConfigs.get(0);
    }
    
    public LoggerConfig getConfig(String name) {
        return loggerConfigs.get(0);
    }

}
