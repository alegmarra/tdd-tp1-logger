package com.fiuba.tdd.logger.format.parser;

import com.fiuba.tdd.logger.format.parser.model.LoggerProperties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

public class XmlPropertiesParser extends AbstractPropertiesParserTemplate {

    @Override
    protected LoggerProperties parseLoggerProperties(InputStream config) {
        LoggerProperties logger = new LoggerProperties();
        try {
            JAXBContext jc = JAXBContext.newInstance(LoggerProperties.class);

            Unmarshaller unmarshaller = jc.createUnmarshaller();
            logger = (LoggerProperties)unmarshaller.unmarshal(config);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return logger;
    }
}
