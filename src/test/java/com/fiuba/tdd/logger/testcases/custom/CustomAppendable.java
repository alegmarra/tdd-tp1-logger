package com.fiuba.tdd.logger.testcases.custom;

import com.fiuba.tdd.logger.appenders.Appendable;
import java.io.IOException;

public class CustomAppendable implements Appendable {

    public CustomAppendable() {
        // Do something
    }

    public CustomAppendable(String format) {
        // Do something with format
    }

    @Override
    public void append(String message) throws IOException {

    }
}
