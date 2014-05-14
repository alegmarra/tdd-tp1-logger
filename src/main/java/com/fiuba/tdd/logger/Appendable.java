package com.fiuba.tdd.logger;

import java.io.IOException;

public interface Appendable {

    public void write(String message) throws IOException;

}