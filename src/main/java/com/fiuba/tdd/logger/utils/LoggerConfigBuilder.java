package com.fiuba.tdd.logger.utils;

import com.fiuba.tdd.logger.appenders.Appendable;
import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.exceptions.UnsuportedFormatException;
import com.fiuba.tdd.logger.filters.Filter;
import com.fiuba.tdd.logger.format.parser.ConfigParser;
import com.fiuba.tdd.logger.format.parser.PropertiesParser;
import com.fiuba.tdd.logger.format.parser.PropertiesParserFactory;
import com.fiuba.tdd.logger.utils.Configurable.Level;
import com.fiuba.tdd.logger.utils.LoggerConfig.ConfigKey;
import com.fiuba.tdd.logger.appenders.ConsoleAppender;
import org.apache.commons.io.FilenameUtils;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
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

    private LoggerConfig loggerConfig;
    private List<Appendable> outputs = new LinkedList<>();
    private List<Filter> filters = new LinkedList<>();

    public LoggerConfigBuilder() throws IOException, InvalidArgumentException {

        LoggerConfig defaultConfig = new LoggerConfig();

        URL properties = getPropertiesFile();
        if (properties == null){
            loggerConfig = defaultConfig;
            loggerConfig.addAppender(new ConsoleAppender());

        } else {
            try {
                String extension = FilenameUtils.getExtension(properties.toString());
                ConfigParser parser = PropertiesParserFactory.getParser(extension);
                loggerConfig = parser.parseConfigFile(properties.openStream());

            } catch (UnsuportedFormatException e) {
                e.printStackTrace();
                loggerConfig = defaultConfig;
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
        return loggerConfig;
    }

}
