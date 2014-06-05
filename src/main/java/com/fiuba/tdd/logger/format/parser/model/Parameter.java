package com.fiuba.tdd.logger.format.parser.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Parameter {

    @XmlAttribute
    public String type;

    @XmlValue
    public String value;
}
