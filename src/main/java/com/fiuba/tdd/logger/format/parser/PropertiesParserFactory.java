package com.fiuba.tdd.logger.format.parser;

import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.exceptions.UnsuportedFormatException;

import java.io.IOException;

public class PropertiesParserFactory {

    private static final String XML = "xml";
    private static final String dotXML = ".xml";
    private static final String PROPERTIES = "properties";
    private static final String dotPROPERTIES = ".properties";

    public static ConfigParser getParser(String extension)
            throws UnsuportedFormatException, IOException, InvalidArgumentException
    {
        switch (extension){
            case XML:
            case dotXML: return new XmlPropertiesParser();
            case PROPERTIES:
            case dotPROPERTIES: return new PropertiesParser();
            default: throw new UnsuportedFormatException("Format " + extension + "not supported");
        }

    }
}
