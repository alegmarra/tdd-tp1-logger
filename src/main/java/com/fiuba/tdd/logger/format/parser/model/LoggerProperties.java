package com.fiuba.tdd.logger.format.parser.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class LoggerProperties {

    @XmlElement(name="config")
    public List<ConfigDto> configs;
}
