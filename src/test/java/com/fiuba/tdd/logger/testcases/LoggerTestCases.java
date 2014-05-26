package com.fiuba.tdd.logger.testcases;

import com.fiuba.tdd.logger.Appendable;
import com.fiuba.tdd.logger.Logger;
import com.fiuba.tdd.logger.Logger.Level;
import com.fiuba.tdd.logger.internal.InvalidArgumentException;
import com.fiuba.tdd.logger.internal.LoggerInvoker;
import com.fiuba.tdd.logger.internal.MessageFormatter;
import com.fiuba.tdd.logger.utils.LoggerConfig;
import com.fiuba.tdd.logger.writers.ConsoleAppender;
import com.fiuba.tdd.logger.writers.FileAppender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.Assert.fail;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Logger.class)
public class LoggerTestCases {

    protected final LoggerConfig defaultConfig = new LoggerConfig();
    protected Appendable consoleMock;
    protected MessageFormatter formatterMock;
    protected LoggerInvoker invokerMock;

    @Before
    public void setupMocks(){
        consoleMock = Mockito.mock(ConsoleAppender.class);
        formatterMock = Mockito.mock(MessageFormatter.class);
        invokerMock = Mockito.mock(LoggerInvoker.class);
    }


    @Test
    public void testLoggerDefaultLevelConfiguration() throws Exception {
        final String msg = "my simple message";

        Logger logger = new Logger();
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
        final String msg = "my simple message";
        final String formattedMessage = "10:10:10 - INFO - CustomThread - my simple message ";

        PowerMockito.whenNew(MessageFormatter.class).withArguments(eq(defaultConfig)).thenReturn(formatterMock);
        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(formattedMessage);

        Logger logger = new Logger();
        logger.registerAppender(consoleMock);

        logger.info(msg);
        verify(consoleMock).append(eq(formattedMessage));
    }


    @Test
    public void testLoggerChangeLevel() throws Exception {
        final String msg = "my simple message";

        Logger logger = new Logger();
        logger.registerAppender(consoleMock);

        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(msg);
        PowerMockito.whenNew(MessageFormatter.class).withArguments(any()).thenReturn(formatterMock);

        logger.debug(msg);
        verify(consoleMock, never()).append(eq(msg));

        logger.setLevel(Level.DEBUG);
        logger.debug(msg);
        verify(consoleMock, times(1)).append(eq(msg));

        logger.setLevel(Level.ERROR);
        logger.debug(msg);
        verify(consoleMock, times(1)).append(eq(msg));

        logger.error(msg);
        verify(consoleMock, times(2)).append(eq(msg));
    }


    @Test
    public void testLoggerChangeFormat() throws Exception {
        final String msg = "my simple message";
        final String newformat = "%p %n %m";
        final String formattedMessage = "INFO - my simple message ";

        LoggerConfig formattedConfig = new LoggerConfig(newformat, defaultConfig.level, defaultConfig.separator);

        PowerMockito.whenNew(MessageFormatter.class).withArguments(eq(formattedConfig)).thenReturn(formatterMock);
        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(formattedMessage);

        Logger logger = new Logger();
        logger.registerAppender(consoleMock);

        logger.setFormat(newformat);

        logger.info(msg);
        verify(consoleMock).append(eq(formattedMessage));
    }

    @Test
    public void testLoggerChangeSeparator() throws Exception {
        final String msg = "my simple message";
        final String newSeparator = "::";
        final String formattedMessage = "INFO :: my simple message ";

        LoggerConfig formattedConfig = new LoggerConfig(defaultConfig.format, defaultConfig.level, newSeparator);

        PowerMockito.whenNew(MessageFormatter.class).withArguments(eq(formattedConfig)).thenReturn(formatterMock);
        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(formattedMessage);

        Logger logger = new Logger();
        logger.registerAppender(consoleMock);

        logger.setSeparator(newSeparator);

        logger.info(msg);
        verify(consoleMock).append(eq(formattedMessage));
    }


    @Test
    public void testMultipleAppenders() throws Exception {
        final String msg = "my simple message";
        final String formattedMessage = "INFO :: my simple message ";

        Appendable consoleMock_2 = Mockito.mock(ConsoleAppender.class);
        Appendable fileMock_1 = Mockito.mock(FileAppender.class);
        Appendable fileMock_2 = Mockito.mock(FileAppender.class);

        PowerMockito.whenNew(MessageFormatter.class).withAnyArguments().thenReturn(formatterMock);
        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(formattedMessage);

        Logger logger = new Logger();
        logger.registerAppender(consoleMock);
        logger.registerAppender(consoleMock_2);
        logger.registerAppender(fileMock_1);
        logger.registerAppender(fileMock_2);

        logger.info(msg);
        verify(consoleMock).append(eq(formattedMessage));
        verify(consoleMock_2).append(eq(formattedMessage));
        verify(fileMock_1).append(eq(formattedMessage));
        verify(fileMock_2).append(eq(formattedMessage));
    }

    @Test
    public void testCustomConstructor_NoAppenders() throws Exception {
        final String msg = "my simple message";
        final String separator = "::";
        final String format = "&p %n %m";
        final Level level = Level.ERROR;
        final String formattedMessage = "INFO :: my simple message ";

        LoggerConfig customConfig = new LoggerConfig(format, level, separator);

        PowerMockito.whenNew(MessageFormatter.class).withArguments(eq(customConfig)).thenReturn(formatterMock);
        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(formattedMessage);

        Logger logger = new Logger(format, level, separator);
        logger.registerAppender(consoleMock);

        logger.info(msg);
        verify(consoleMock, never()).append(eq(formattedMessage));

        logger.error(msg);
        verify(consoleMock).append(eq(formattedMessage));
    }

    @Test
    public void testCustomConstructor_WithOneAppender() throws Exception {
        final String msg = "my simple message";
        final String separator = "::";
        final String format = "&p %n %m";
        final Level level = Level.ERROR;
        final String formattedMessage = "INFO :: my simple message ";

        LoggerConfig customConfig = new LoggerConfig(format, level, separator);

        PowerMockito.whenNew(MessageFormatter.class).withArguments(eq(customConfig)).thenReturn(formatterMock);
        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(formattedMessage);

        Logger logger = new Logger(format, level, separator, consoleMock);

        logger.info(msg);
        verify(consoleMock, never()).append(eq(formattedMessage));

        logger.error(msg);
        verify(consoleMock).append(eq(formattedMessage));
    }

    @Test
    public void testCustomConstructor_WithMultipleAppenders() throws Exception {
        final String msg = "my simple message";
        final String separator = "::";
        final String format = "&p %n %m";
        final Level level = Level.ERROR;
        final String formattedMessage = "INFO :: my simple message ";

        Appendable fileMock = Mockito.mock(FileAppender.class);

        LoggerConfig customConfig = new LoggerConfig(format, level, separator);

        PowerMockito.whenNew(MessageFormatter.class).withArguments(eq(customConfig)).thenReturn(formatterMock);
        when(formatterMock.formatMessage((LoggerInvoker) any(), eq(msg))).thenReturn(formattedMessage);

        Logger logger = new Logger(format, level, separator, consoleMock, fileMock);

        logger.info(msg);
        verify(consoleMock, never()).append(eq(formattedMessage));

        logger.error(msg);
        verify(consoleMock).append(eq(formattedMessage));
        verify(fileMock).append(eq(formattedMessage));
    }

    @Test(expected = InvalidArgumentException.class)
    public void testCustomConstructor_WithNullValues() throws InvalidArgumentException {
        new Logger(null, null, null);
    }

    @Test(expected = InvalidArgumentException.class)
    public void testAddNullAppender() throws InvalidArgumentException {
        Logger logger = new Logger();
        logger.registerAppender(null);
    }
}
