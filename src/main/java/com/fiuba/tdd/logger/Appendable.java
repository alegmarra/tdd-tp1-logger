package com.fiuba.tdd.logger;

import com.fiuba.tdd.logger.internal.LoggerInvoker;

public interface Appendable {

    public void write(LoggerInvoker invoker, String message);

}