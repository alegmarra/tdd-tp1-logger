package com.fiuba.tdd.logger.exceptions;

/**
 * Excepción lanzada cuando los argumentos son inválidos.
 */
public class InvalidArgumentException extends Exception {

    public InvalidArgumentException(final String msg){
        super(msg);
    }
}
