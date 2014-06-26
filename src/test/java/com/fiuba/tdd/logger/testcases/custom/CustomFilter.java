package com.fiuba.tdd.logger.testcases.custom;

import com.fiuba.tdd.logger.filters.Filter;
import com.fiuba.tdd.logger.utils.LoggerConfig;

public class CustomFilter implements Filter {

    public CustomFilter() {
        // Do something
    }

    public CustomFilter(String format) {
        // Do something with format
    }

    @Override
    public Boolean allows(String msg, LoggerConfig config) {
        return true;
    }
}
