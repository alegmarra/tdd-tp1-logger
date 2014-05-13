package com.fiuba.tdd.logger.writers;

import com.fiuba.tdd.logger.internal.LoggerInvoker;
import com.fiuba.tdd.logger.internal.MessageFormatter;

import java.io.PrintStream;

public class ConsoleAppender implements com.fiuba.tdd.logger.Appendable {

    private PrintStream output = System.out;
    private final MessageFormatter formatter;

    public ConsoleAppender(MessageFormatter formatter){

        this.formatter = formatter;
    }

    @Override
    public void write(LoggerInvoker invoker, String message) {

        output.println(formatter.formatMessage(invoker, message));
    }
}
