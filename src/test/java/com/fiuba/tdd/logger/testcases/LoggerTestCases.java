package com.fiuba.tdd.logger.testcases;

import com.fiuba.tdd.logger.appenders.Appendable;
import com.fiuba.tdd.logger.SimpleLogger;
import com.fiuba.tdd.logger.format.StringFormatter;
import com.fiuba.tdd.logger.slf4j.binding.SimpleLoggerAdapter;
import com.fiuba.tdd.logger.utils.Configurable.Level;
import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.format.LoggerInvoker;
import com.fiuba.tdd.logger.format.MessageFormatterBuilder;
import com.fiuba.tdd.logger.slf4j.binding.SimpleLoggerFactory;
import com.fiuba.tdd.logger.utils.LoggerConfig;
import com.fiuba.tdd.logger.appenders.ConsoleAppender;
import com.fiuba.tdd.logger.appenders.FileAppender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ MessageFormatterBuilder.class, LoggerConfig.class, SimpleLogger.class })
public class LoggerTestCases {

    public static final String TEST_NAME_DEFAULT = "default";
    public static final String TEST_NAME_ADAPTER = "adapter";
    public static final String TEST_NAME = LoggerTestCases.class.getSimpleName();
    private final String msg = "my simple message";
    private final String separator = "::";
    private final String format = "&p %n %m";
    private final Level level = Level.ERROR;
    private final String formattedMessage = "INFO :: my simple message ";

    protected LoggerConfig defaultConfig;
    protected LoggerConfig customConfig;

    protected Appendable consoleMock;
    protected Appendable fileMock;
    protected StringFormatter noFormatFormatterMock;
    protected StringFormatter formatterMock;
    protected LoggerInvoker invokerMock;
    protected Logger defaultLogger;
    protected SimpleLoggerAdapter loggerAdapter;
    protected SimpleLogger simpleLogger;
    protected SimpleLoggerFactory loggerFactory;

    @Before
    public void setupConfig() throws InvalidArgumentException {
        defaultConfig = new LoggerConfig();
        customConfig = new LoggerConfig(format, level, separator);
        loggerFactory = new SimpleLoggerFactory();
        defaultLogger = loggerFactory.getLogger(TEST_NAME_DEFAULT);
        loggerAdapter = new SimpleLoggerAdapter(TEST_NAME_ADAPTER, defaultConfig);
        simpleLogger = new SimpleLogger(TEST_NAME, defaultConfig);
    }
    @Before
    public void setupMocks() throws Exception {

        consoleMock = Mockito.mock(ConsoleAppender.class);
        fileMock = Mockito.mock(FileAppender.class);
        noFormatFormatterMock = Mockito.mock(StringFormatter.class);
        formatterMock = Mockito.mock(StringFormatter.class);
        invokerMock = Mockito.mock(LoggerInvoker.class);


        when(noFormatFormatterMock.formatMessage((LoggerInvoker) any(), anyString())).thenAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (String) args[1];
            }
        });

        PowerMockito.whenNew(ConsoleAppender.class).withNoArguments().thenReturn((ConsoleAppender) consoleMock);
        PowerMockito.whenNew(FileAppender.class).withArguments(anyString()).thenReturn((FileAppender) fileMock);

    }


    @Test
    public void testLoggerDefaultMessageFormatConfiguration() throws Exception {

        PowerMockito.whenNew(StringFormatter.class).withArguments(eq(defaultConfig)).thenReturn(formatterMock);
        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(formattedMessage);

        defaultLogger.info(msg);
        verify(consoleMock).append(eq(formattedMessage));
    }

    @Test
    public void testLoggerDefault_LevelIsInfo() throws Exception {
        assertTrue(defaultLogger.isInfoEnabled());
    }



    @Test
    public void testLoggerSetLevelTrace() throws Exception {

        PowerMockito.whenNew(StringFormatter.class).withArguments(any()).thenReturn(noFormatFormatterMock);

        loggerAdapter.setLevel(Level.TRACE);
        assertTrue(loggerAdapter.isTraceEnabled());

        loggerAdapter.trace(msg);
        verify(consoleMock).append(eq(msg));
    }

    @Test
    public void testLoggerSetLevelDebug() throws Exception {

        PowerMockito.whenNew(StringFormatter.class).withArguments(any()).thenReturn(noFormatFormatterMock);

        loggerAdapter.setLevel(Level.DEBUG);
        assertTrue(loggerAdapter.isDebugEnabled());

        loggerAdapter.debug(msg);
        verify(consoleMock).append(eq(msg));
    }

    @Test
    public void testLoggerSetLevelInfo() throws Exception {

        PowerMockito.whenNew(StringFormatter.class).withArguments(any()).thenReturn(noFormatFormatterMock);

        loggerAdapter.setLevel(Level.INFO);
        assertTrue(loggerAdapter.isInfoEnabled());

        loggerAdapter.info(msg);
        verify(consoleMock).append(eq(msg));
    }

    @Test
    public void testLoggerSetLevelWarn() throws Exception {

        PowerMockito.whenNew(StringFormatter.class).withArguments(any()).thenReturn(noFormatFormatterMock);

        loggerAdapter.setLevel(Level.WARN);
        assertTrue(loggerAdapter.isWarnEnabled());

        loggerAdapter.warn(msg);
        verify(consoleMock).append(eq(msg));
    }

    @Test
    public void testLoggerSetLevelError() throws Exception {

        PowerMockito.whenNew(StringFormatter.class).withArguments(any()).thenReturn(noFormatFormatterMock);

        loggerAdapter.setLevel(Level.ERROR);
        assertTrue(loggerAdapter.isErrorEnabled());

        loggerAdapter.error(msg);
        verify(consoleMock).append(eq(msg));
    }

    @Test
    public void testLoggerSetLevelFatal() throws Exception {

        PowerMockito.whenNew(StringFormatter.class).withArguments(any()).thenReturn(noFormatFormatterMock);

        simpleLogger.setLevel(Level.FATAL);

        simpleLogger.fatal(msg);
        verify(consoleMock).append(eq(msg));
    }

    @Test
    public void testLoggerSetLevelOff() throws Exception {

        PowerMockito.whenNew(StringFormatter.class).withArguments(any()).thenReturn(noFormatFormatterMock);

        loggerAdapter.setLevel(Level.OFF);

        loggerAdapter.trace(msg);
        loggerAdapter.debug(msg);
        loggerAdapter.info(msg);
        loggerAdapter.warn(msg);
        loggerAdapter.error(msg);
        verify(consoleMock, never()).append(eq(msg));
    }

/*
    @Test
    public void testLoggerChangeFormat() throws Exception {

        LoggerConfig formattedConfig = new LoggerConfig(formattedMessage, defaultConfig.level, defaultConfig.separator);

        PowerMockito.whenNew(StringFormatter.class).withArguments(eq(formattedConfig)).thenReturn(formatterMock);
        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(formattedMessage);

        logger = new SimpleLoggerFactory().getLogger(LoggerTestCases.class.getName());

        logger.info(msg);
        verify(consoleMock).append(eq(formattedMessage));
    }

    @Test
    public void testLoggerChangeSeparator() throws Exception {

        LoggerConfig formattedConfig = new LoggerConfig(defaultConfig.format, defaultConfig.level, separator);

        PowerMockito.whenNew(StringFormatter.class).withArguments(eq(formattedConfig)).thenReturn(formatterMock);
        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(formattedMessage);

        logger = new SimpleLoggerFactory().getLogger(LoggerTestCases.class.getName());

        logger.info(msg);
        verify(consoleMock).append(eq(formattedMessage));
    }


    @Test
    public void testMultipleAppenders() throws Exception {

        Appendable consoleMock_2 = Mockito.mock(ConsoleAppender.class);
        Appendable fileMock_2 = Mockito.mock(FileAppender.class);

        PowerMockito.whenNew(StringFormatter.class).withAnyArguments().thenReturn(formatterMock);
        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(formattedMessage);

        logger = new SimpleLoggerFactory().getLogger(LoggerTestCases.class.getName());

        logger.info(msg);
        verify(consoleMock).append(eq(formattedMessage));
        verify(consoleMock_2).append(eq(formattedMessage));
        verify(fileMock).append(eq(formattedMessage));
        verify(fileMock_2).append(eq(formattedMessage));
    }

    @Test
    public void testCustomConstructor_NoAppenders() throws Exception {

        PowerMockito.whenNew(StringFormatter.class).withArguments(eq(customConfig)).thenReturn(formatterMock);
        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(formattedMessage);

        logger = new SimpleLoggerAdapter(TEST_NAME);

        logger.info(msg);
        verify(consoleMock, never()).append(eq(formattedMessage));

        logger.error(msg);
        verify(consoleMock).append(eq(formattedMessage));
    }

    @Test
    public void testCustomConstructor_WithOneAppender() throws Exception {

        PowerMockito.whenNew(StringFormatter.class).withArguments(eq(customConfig)).thenReturn(formatterMock);
        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(formattedMessage);

        logger = new SimpleLoggerAdapter(TEST_NAME);

        logger.info(msg);
        verify(consoleMock, never()).append(eq(formattedMessage));

        logger.error(msg);
        verify(consoleMock).append(eq(formattedMessage));
    }

    @Test
    public void testCustomConstructor_WithMultipleAppenders() throws Exception {

        PowerMockito.whenNew(StringFormatter.class).withArguments(eq(customConfig)).thenReturn(formatterMock);
        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(formattedMessage);

        logger = new SimpleLoggerFactory().getLogger(TEST_NAME);

        logger.info(msg);
        verify(consoleMock, never()).append(eq(formattedMessage));

        logger.error(msg);
        verify(consoleMock).append(eq(formattedMessage));
        verify(fileMock).append(eq(formattedMessage));
    }

    @Test(expected = InvalidArgumentException.class)
    public void testCustomConstructor_WithNullValues() throws InvalidArgumentException {
        new SimpleLogger(LoggerTestCases.class.getName(), null, null, null);
    }

    @Test(expected = InvalidArgumentException.class)
    public void testAddNullAppender() throws InvalidArgumentException {
        new SimpleLogger(LoggerTestCases.class.getName()).registerAppender(null);
    }
/**/
}
