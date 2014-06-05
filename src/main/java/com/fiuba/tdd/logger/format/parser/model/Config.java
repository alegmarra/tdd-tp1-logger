package com.fiuba.tdd.logger.format.parser.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

@XmlSeeAlso({AppenderImp.class, FilterImp.class})
public class Config {

    @XmlElement
    public String format;

    @XmlElement
    public String level;

    @XmlElement
    public   String separator;

    @XmlElement(name="appender")
    public  List<AppenderImp> appenders;

    @XmlElement(name="filter")
    public List<FilterImp> filters;

}
