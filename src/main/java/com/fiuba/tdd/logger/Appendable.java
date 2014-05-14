package com.fiuba.tdd.logger;

import java.io.IOException;

public interface Appendable {

    public void append(String message) throws IOException;

}