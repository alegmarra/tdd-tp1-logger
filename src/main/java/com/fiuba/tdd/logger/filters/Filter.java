package com.fiuba.tdd.logger.filters;

import com.fiuba.tdd.logger.utils.LoggerConfig;

public interface Filter {

    public Boolean allows(final String msg, LoggerConfig config);

}
