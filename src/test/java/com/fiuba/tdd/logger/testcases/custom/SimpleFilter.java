package com.fiuba.tdd.logger.testcases.custom;

import com.fiuba.tdd.logger.filters.Filter;

public class SimpleFilter implements Filter {

    public SimpleFilter(String format) {
        // Do something with format
    }

    @Override
    public Boolean allows(String msg) {
        return true;
    }
}
