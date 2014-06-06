package com.fiuba.tdd.logger.filters;

public class CustomFilter implements Filter {

    @Override
    public Boolean allows(String msg) {
        return true;
    }
}
