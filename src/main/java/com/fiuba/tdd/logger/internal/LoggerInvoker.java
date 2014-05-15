package com.fiuba.tdd.logger.internal;


/**
 * Clase de uso interno, utilizada como abstracción por sobre los métodos
 * (un poco más complejos) que permiten obtener información sobre el momento
 * en que se llamó a una función de logging. Incluye, por enunciado,
 * <li> nombre del archivo </li>
 * <li> línea del archivo </li>
 * <li> nombre de la clase </li>
 * <li> nombre del método </li>
 */
public class LoggerInvoker {

    private StackTraceElement invoker;
    private String thread;

    public LoggerInvoker(StackTraceElement stackTraceElement){
        invoker = stackTraceElement;
        thread = Thread.currentThread().getName();
    }

    public String getLine(){
        return Integer.toString(invoker.getLineNumber());
    }

    public String getMethodName() {
        return invoker.getMethodName();
    }

    public String getFilename() {
        return invoker.getFileName();
    }

    public String getClassName(){
        return invoker.getClassName();
    }

    public String getThread(){
        return thread;
    }
}
