package com.fiuba.tdd.logger.writers;

import java.io.PrintStream;
import com.fiuba.tdd.logger.Appendable;

public class ConsoleAppender implements Appendable {

    private PrintStream output = System.out;

    public ConsoleAppender(){}

    @Override
    public void write(String message) {

        output.println(message);
    }
}
