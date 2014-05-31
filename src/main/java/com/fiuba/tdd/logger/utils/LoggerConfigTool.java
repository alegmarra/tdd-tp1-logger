package com.fiuba.tdd.logger.utils;

import com.fiuba.tdd.logger.appenders.Appendable;
import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.utils.Configurable.Level;
import com.fiuba.tdd.logger.format.Properties;
import com.fiuba.tdd.logger.utils.LoggerConfig.ConfigKey;
import com.fiuba.tdd.logger.appenders.ConsoleAppender;

import java.io.IOException;
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
public class LoggerConfigTool {

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
        Properties properties = new Properties(configFileName);

        Level level = Level.valueOf(properties.getProperty(ConfigKey.LEVEL, defaultConfig.level.name()));
        String format = properties.getProperty(ConfigKey.FORMAT, defaultConfig.format);
        String separator = properties.getProperty(ConfigKey.SEPARATOR, defaultConfig.separator);

        config = new LoggerConfig(format, level, separator);
    }



    public LoggerConfig getConfig() {
        return config;
    }

    public List<Appendable> getRegisteredAppenders(){
        return Collections.unmodifiableList(outputs);
    }


    public void config(Configurable logger) {

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
