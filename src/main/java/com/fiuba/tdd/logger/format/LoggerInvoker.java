package com.fiuba.tdd.logger.format;


/**
 * Clase de uso interno, utilizada como abstracción por sobre los métodos
 * (un poco más complejos) que permiten obtener información sobre el momento
 * en que se llamó a una función de logging. Incluye, por enunciado,
 * <ul>
 * <li> nombre del archivo </li>
 * <li> línea del archivo </li>
 * <li> nombre de la clase </li>
 * <li> nombre del método </li>
 * <li> nombre del logger </li>
 * </ul>
 */
public class LoggerInvoker {

    private StackTraceElement invoker;
    private String name;
    private String thread;

    public LoggerInvoker(StackTraceElement stackTraceElement, String invokerName){
        invoker = stackTraceElement;
        thread = Thread.currentThread().getName();
        name = invokerName;
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

    public String getName(){
        return name;
    }

    public String getThread(){
        return thread;
    }
}
