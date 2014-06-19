package com.fiuba.tdd.logger.testcases;

import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.exceptions.UnsuportedFormatException;
import com.fiuba.tdd.logger.format.parser.ConfigParser;
import com.fiuba.tdd.logger.format.parser.PropertiesParserFactory;
import com.fiuba.tdd.logger.format.parser.XmlPropertiesParser;
import com.fiuba.tdd.logger.format.parser.model.AppenderDto;
import com.fiuba.tdd.logger.format.parser.model.FilterDto;
import com.fiuba.tdd.logger.format.parser.model.Parameter;
import com.fiuba.tdd.logger.utils.Configurable;
import com.fiuba.tdd.logger.utils.LoggerConfig;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PropertiesParserTestCases {

    protected String fileName;
    protected ConfigParser parser;
    protected InputStream inputFile;



    protected void assertEqualsAppenders(AppenderDto expected, AppenderDto resulting){

        assertEquals(expected.implementation, resulting.implementation);
        assertEquals(expected.params.size(), resulting.params.size());

        Iterator<Parameter> expectedIt = expected.params.iterator();
        Iterator<Parameter> resultingIt = resulting.params.iterator();
        while (expectedIt.hasNext()){
            Parameter exp = expectedIt.next();
            Parameter res = resultingIt.next();
            assertEquals(exp.type, res.type);
            assertEquals(exp.value, res.value);
        }
    }

    protected void assertEqualsFilters(FilterDto expected, FilterDto resulting){

        assertEquals(expected.implementation, resulting.implementation);
        assertEquals(expected.params.size(), resulting.params.size());

        Iterator<Parameter> expectedIt = expected.params.iterator();
        Iterator<Parameter> resultingIt = resulting.params.iterator();
        while (expectedIt.hasNext()){
            Parameter exp = expectedIt.next();
            Parameter res = resultingIt.next();
            assertEquals(exp.type, res.type);
            assertEquals(exp.value, res.value);
        }
    }
}
