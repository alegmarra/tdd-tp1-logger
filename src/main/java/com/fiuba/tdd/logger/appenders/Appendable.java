package com.fiuba.tdd.logger.appenders;

import java.io.IOException;

/**
 * Interfaz similar en concepto a java.lang.Appendable, pero más simple.
 * Para abstraerse del destino de los logs.
 */
public interface Appendable {

    public void append(String message) throws IOException;

}