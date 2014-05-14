package com.fiuba.tdd.logger.writers;

import com.fiuba.tdd.logger.Appendable;

import java.io.PrintStream;

public class ConsoleAppender implements Appendable {

    private PrintStream output = System.out;

    public ConsoleAppender(){}

    @Override
    public void append(String message) {

        output.println(message);
    }
}
