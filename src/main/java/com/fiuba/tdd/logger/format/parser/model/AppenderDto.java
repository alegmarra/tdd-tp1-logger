package com.fiuba.tdd.logger.format.parser.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.LinkedList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class AppenderDto {

    @XmlAttribute
    public String implementation;

    @XmlElement(name="param")
    public List<Parameter> params = new LinkedList<>();
}
