package com.fiuba.tdd.logger.testcases;

import com.fiuba.tdd.logger.Appendable;
import com.fiuba.tdd.logger.Logger;
import com.fiuba.tdd.logger.SimpleLogger;
import com.fiuba.tdd.logger.SimpleLogger.Level;
import com.fiuba.tdd.logger.internal.InvalidArgumentException;
import com.fiuba.tdd.logger.internal.LoggerInvoker;
import com.fiuba.tdd.logger.internal.MessageFormatter;
import com.fiuba.tdd.logger.internal.MessageFormatterBuilder;
import com.fiuba.tdd.logger.utils.LoggerConfig;
import com.fiuba.tdd.logger.writers.ConsoleAppender;
import com.fiuba.tdd.logger.writers.FileAppender;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MessageFormatterBuilder.class)
public class LoggerTestCases {


    final String msg = "my simple message";
    final String separator = "::";
    final String format = "&p %n %m";
    final Level level = Level.ERROR;
    final String formattedMessage = "INFO :: my simple message ";

    protected final LoggerConfig defaultConfig = new LoggerConfig();
    protected final LoggerConfig customConfig = new LoggerConfig(format, level, separator);

    protected Appendable consoleMock;
    protected Appendable fileMock;
    protected MessageFormatter formatterMock;
    protected LoggerInvoker invokerMock;
    protected Logger logger;

    @Before
    public void setupMocks(){
        consoleMock = Mockito.mock(ConsoleAppender.class);
        fileMock = Mockito.mock(FileAppender.class);
        formatterMock = Mockito.mock(MessageFormatter.class);
        invokerMock = Mockito.mock(LoggerInvoker.class);
    }


    @Test
    public void testLoggerDefaultLevelConfiguration() throws Exception {

        logger = new SimpleLogger();
        logger.registerAppender(consoleMock);

        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(msg);
        PowerMockito.whenNew(MessageFormatter.class).withAnyArguments().thenReturn(formatterMock);

        logger.debug(msg);
        verify(consoleMock, never()).append(eq(msg));

        logger.info(msg);
        verify(consoleMock).append(eq(msg));

        logger.error(msg);
        logger.warn(msg);
        logger.fatal(msg);
        verify(consoleMock, times(4)).append(eq(msg));
    }

    @Test
    public void testLoggerDefaultMessageFormatConfiguration() throws Exception {
        //String formattedMessage = "10:10:10 - INFO - CustomThread - my simple message ";

        PowerMockito.whenNew(MessageFormatter.class).withArguments(eq(defaultConfig)).thenReturn(formatterMock);
        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(formattedMessage);

        logger = new SimpleLogger();
        logger.registerAppender(consoleMock);

        logger.info(msg);
        verify(consoleMock).append(eq(formattedMessage));
    }


    @Test
    public void testLoggerGetLevelDefaultInfo() throws Exception {

        logger = new SimpleLogger();
        assertEquals(Level.INFO, logger.getLevel());
    }


    @Test
    public void testLoggerChangeLevelDebug() throws Exception {

        logger = new SimpleLogger();
        logger.registerAppender(consoleMock);

        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(msg);
        PowerMockito.whenNew(MessageFormatter.class).withArguments(any()).thenReturn(formatterMock);

        logger.setLevel(Level.OFF);
        logger.debug(msg);
        verify(consoleMock, never()).append(eq(msg));

        logger.setLevel(Level.DEBUG);
        logger.debug(msg);
        verify(consoleMock).append(eq(msg));
    }

    @Test
    public void testLoggerChangeLevelInfo() throws Exception {

        logger = new SimpleLogger();
        logger.registerAppender(consoleMock);

        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(msg);
        PowerMockito.whenNew(MessageFormatter.class).withArguments(any()).thenReturn(formatterMock);

        logger.setLevel(Level.OFF);
        logger.info(msg);
        verify(consoleMock, never()).append(eq(msg));

        logger.setLevel(Level.INFO);
        logger.info(msg);
        verify(consoleMock).append(eq(msg));
    }

    @Test
    public void testLoggerChangeLevelError() throws Exception {

        logger = new SimpleLogger();
        logger.registerAppender(consoleMock);

        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(msg);
        PowerMockito.whenNew(MessageFormatter.class).withArguments(any()).thenReturn(formatterMock);

        logger.setLevel(Level.OFF);
        logger.error(msg);
        verify(consoleMock, never()).append(eq(msg));

        logger.setLevel(Level.ERROR);
        logger.error(msg);
        verify(consoleMock).append(eq(msg));
    }

    @Test
    public void testLoggerChangeLevelWarn() throws Exception {

        logger = new SimpleLogger();
        logger.registerAppender(consoleMock);

        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(msg);
        PowerMockito.whenNew(MessageFormatter.class).withArguments(any()).thenReturn(formatterMock);

        logger.setLevel(Level.OFF);
        logger.warn(msg);
        verify(consoleMock, never()).append(eq(msg));

        logger.setLevel(Level.WARN);
        logger.warn(msg);
        verify(consoleMock).append(eq(msg));
    }


    @Test
    public void testLoggerChangeLevelFatal() throws Exception {

        logger = new SimpleLogger();
        logger.registerAppender(consoleMock);

        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(msg);
        PowerMockito.whenNew(MessageFormatter.class).withArguments(any()).thenReturn(formatterMock);

        logger.setLevel(Level.OFF);
        logger.fatal(msg);
        verify(consoleMock, never()).append(eq(msg));

        logger.setLevel(Level.FATAL);
        logger.fatal(msg);
        verify(consoleMock).append(eq(msg));
    }

    @Test
    public void testLoggerChangeFormat() throws Exception {

        LoggerConfig formattedConfig = new LoggerConfig(formattedMessage, defaultConfig.level, defaultConfig.separator);

        PowerMockito.whenNew(MessageFormatter.class).withArguments(eq(formattedConfig)).thenReturn(formatterMock);
        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(formattedMessage);

        logger = new SimpleLogger();
        logger.registerAppender(consoleMock);

        logger.setFormat(formattedMessage);

        logger.info(msg);
        verify(consoleMock).append(eq(formattedMessage));
    }

    @Test
    public void testLoggerChangeSeparator() throws Exception {

        LoggerConfig formattedConfig = new LoggerConfig(defaultConfig.format, defaultConfig.level, separator);

        PowerMockito.whenNew(MessageFormatter.class).withArguments(eq(formattedConfig)).thenReturn(formatterMock);
        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(formattedMessage);

        logger = new SimpleLogger();
        logger.registerAppender(consoleMock);

        logger.setSeparator(separator);

        logger.info(msg);
        verify(consoleMock).append(eq(formattedMessage));
    }


    @Test
    public void testMultipleAppenders() throws Exception {

        Appendable consoleMock_2 = Mockito.mock(ConsoleAppender.class);
        Appendable fileMock_2 = Mockito.mock(FileAppender.class);

        PowerMockito.whenNew(MessageFormatter.class).withAnyArguments().thenReturn(formatterMock);
        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(formattedMessage);

        logger = new SimpleLogger();
        logger.registerAppender(consoleMock);
        logger.registerAppender(consoleMock_2);
        logger.registerAppender(fileMock);
        logger.registerAppender(fileMock_2);

        logger.info(msg);
        verify(consoleMock).append(eq(formattedMessage));
        verify(consoleMock_2).append(eq(formattedMessage));
        verify(fileMock).append(eq(formattedMessage));
        verify(fileMock_2).append(eq(formattedMessage));
    }

    @Test
    public void testCustomConstructor_NoAppenders() throws Exception {

        PowerMockito.whenNew(MessageFormatter.class).withArguments(eq(customConfig)).thenReturn(formatterMock);
        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(formattedMessage);

        logger = new SimpleLogger(format, level, separator);
        logger.registerAppender(consoleMock);

        logger.info(msg);
        verify(consoleMock, never()).append(eq(formattedMessage));

        logger.error(msg);
        verify(consoleMock).append(eq(formattedMessage));
    }

    @Test
    public void testCustomConstructor_WithOneAppender() throws Exception {

        PowerMockito.whenNew(MessageFormatter.class).withArguments(eq(customConfig)).thenReturn(formatterMock);
        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(formattedMessage);

        logger = new SimpleLogger(format, level, separator, consoleMock);

        logger.info(msg);
        verify(consoleMock, never()).append(eq(formattedMessage));

        logger.error(msg);
        verify(consoleMock).append(eq(formattedMessage));
    }

    @Test
    public void testCustomConstructor_WithMultipleAppenders() throws Exception {

        PowerMockito.whenNew(MessageFormatter.class).withArguments(eq(customConfig)).thenReturn(formatterMock);
        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(formattedMessage);

        logger = new SimpleLogger(format, level, separator, consoleMock, fileMock);

        logger.info(msg);
        verify(consoleMock, never()).append(eq(formattedMessage));

        logger.error(msg);
        verify(consoleMock).append(eq(formattedMessage));
        verify(fileMock).append(eq(formattedMessage));
    }


    @Test(expected = InvalidArgumentException.class)
    public void testCustomConstructor_WithNullValues() throws InvalidArgumentException {
            new SimpleLogger(null, null, null);
    }

    @Test(expected = InvalidArgumentException.class)
    public void testAddNullAppender() throws InvalidArgumentException {

        new SimpleLogger().registerAppender(null);

    }
}
