package com.fiuba.tdd.logger;


import com.fiuba.tdd.logger.internal.InvalidArgumentException;
import com.fiuba.tdd.logger.SimpleLogger.Level;

public interface Logger {

    public void setLevel(Level level);
    public Level getLevel();

    public void setFormat(final String format);
    public void setSeparator(final String separator);

    public void registerAppender(Appendable appender) throws InvalidArgumentException;

    public void debug(String msg);
    public void info(String msg);
    public void warn(String msg);

    public void error(String msg);
    public void fatal(String msg);
}
