package com.fiuba.tdd.logger.format.parser.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.LinkedList;
import java.util.List;

@XmlSeeAlso({AppenderDto.class, FilterDto.class})
public class ConfigDto {

    @XmlAttribute
    public String name;

    @XmlElement
    public String format;

    @XmlElement
    public String level;

    @XmlElement
    public String separator;

    @XmlElement(name="appender")
    public  List<AppenderDto> appenders = new LinkedList<>();

    @XmlElement(name="filter")
    public List<FilterDto> filters = new LinkedList<>();

}
