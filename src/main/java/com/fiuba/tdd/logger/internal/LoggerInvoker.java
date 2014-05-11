package com.fiuba.tdd.logger.internal;

public class LoggerInvoker {

    private StackTraceElement invoker;

    public LoggerInvoker(StackTraceElement stackTraceElement){
        invoker = stackTraceElement;
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
}
