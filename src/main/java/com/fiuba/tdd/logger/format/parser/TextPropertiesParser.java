package com.fiuba.tdd.logger.format.parser;

import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.format.parser.model.*;
import com.fiuba.tdd.logger.utils.Configurable;
import com.fiuba.tdd.logger.utils.LoggerConfig;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Clase que replica, limitadamente, a java.util.TextPropertiesParser, pero con las mejoras
 * de:
 * <ol>
 *     <li> Soportar UTF-8 en los archivos (java.util.TextPropertiesParser utiliza
 *     ISO 8859-1)</li>
 *     <li> Soportar obtener las propiedades a partir del enum definido en
 *     LoggerConfitTool </li>
 * </ol>
 */
public class TextPropertiesParser extends AbstractPropertiesParserTemplate{

    private static final Pattern keyValPattern = Pattern.compile("(.+\\..+)=(.*)");

    public TextPropertiesParser(){}

    @Override
    protected LoggerProperties parseLoggerProperties(InputStream config) throws InvalidArgumentException, IOException {
        Matcher matcher;

        LoggerProperties logger = new LoggerProperties();
        BufferedReader br = null;
        try {
            if (config == null)
                throw new InvalidArgumentException("Config input file was null");

            String currentLine;
            ConfigDto currentConfig = new ConfigDto();
            br = new BufferedReader(new InputStreamReader(config));
            while ((currentLine = br.readLine()) != null) {
                if (!StringUtils.isEmpty(currentLine)){
                    matcher = keyValPattern.matcher(currentLine);
                    if (matcher.matches()){
                        String key = matcher.group(1);
                        String value = matcher.group(2);
                        currentConfig.name = StringUtils.isEmpty(currentConfig.name) ? parseName(key): currentConfig.name;
                        if (key.contains("level")){
                            currentConfig.level = value;
                        } else if (key.contains("format")){
                            currentConfig.format = value;
                        } else if (key.contains("separator")){
                            currentConfig.separator = value;
                        } else if (key.contains("appender")){
                            currentConfig.appenders.add(parseAppender(currentLine, br));
                        } else if (key.contains("filter")){
                            currentConfig.filters.add(parseFilter(currentLine, br));
                        }
                    }
                } else {
                    logger.configs.add(currentConfig);
                    currentConfig = new ConfigDto();
                }
            }
            logger.configs.add(currentConfig);

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (br != null) br.close();
        }

        return logger;
    }

    private String parseName(String property) {
        Pattern pattern = Pattern.compile(".(level|format|separator|appender|filter)");
        Matcher matcher = pattern.matcher(property);
        int nameEnd = matcher.find() ? matcher.start() : -1;

        return (nameEnd > 0 ? StringUtils.substring(property, 0, nameEnd) : "");
    }

    private FilterDto parseFilter(String initLine, BufferedReader br) throws IOException {
        FilterDto filterDto = new FilterDto();
        Parameter param = new Parameter();

        Matcher matcher;
        String currentLine = initLine;
        while ((currentLine = br.readLine()) != null &&
                currentLine.contains("filter") && !currentLine.contains(".id="))
        {
            matcher = keyValPattern.matcher(currentLine);
            if (matcher.matches()){
                String key = matcher.group(1);
                String value = matcher.group(2);
                if (key.contains("class")){
                    filterDto.implementation = value;
                } else if (key.contains("param.type")){
                    param.type = value;
                } else if (key.contains("param.value")){
                    param.value = value;
                    filterDto.params.add(param);
                    param = new Parameter();
                }
            }
        }

        return filterDto;
    }

    private AppenderDto parseAppender(String initLine, BufferedReader br) throws IOException {
        AppenderDto appenderDto = new AppenderDto();
        Parameter param = new Parameter();

        Matcher matcher;
        String currentLine = initLine;
        while ((currentLine = br.readLine()) != null &&
                currentLine.contains("appender")  && !currentLine.contains(".id="))
        {
            matcher = keyValPattern.matcher(currentLine);
            if (matcher.matches()){
                String key = matcher.group(1);
                String value = matcher.group(2);
                if (key.contains("class")){
                    appenderDto.implementation = value;
                } else if (key.contains("param.type")){
                    param.type = value;
                } else if (key.contains("param.value")){
                    param.value = value;
                    appenderDto.params.add(param);
                    param = new Parameter();
                }
            }
        }

        return appenderDto;
    }
}
