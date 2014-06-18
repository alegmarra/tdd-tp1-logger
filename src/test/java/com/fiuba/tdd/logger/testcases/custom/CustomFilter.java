package com.fiuba.tdd.logger.testcases.custom;

import com.fiuba.tdd.logger.filters.Filter;

public class CustomFilter implements Filter {

    public CustomFilter() {
        // Do something
    }

    public CustomFilter(String format) {
        // Do something with format
    }

    @Override
    public Boolean allows(String msg) {
        return true;
    }
}
