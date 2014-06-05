package com.fiuba.tdd.logger.format.parser.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoggerProperties {

    @XmlElement
    public Config config;
}
