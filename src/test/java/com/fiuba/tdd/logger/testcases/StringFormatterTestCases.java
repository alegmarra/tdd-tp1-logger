package com.fiuba.tdd.logger.testcases;

import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.format.MessageFormatter;
import com.fiuba.tdd.logger.utils.Configurable.Level;
import com.fiuba.tdd.logger.format.LoggerInvoker;
import com.fiuba.tdd.logger.format.StringFormatter;
import com.fiuba.tdd.logger.utils.LoggerConfig;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class StringFormatterTestCases extends TestCase{

    private final String testName = StringFormatterTestCases.class.getName();
    private final String noFormat = "None format string";
    private final String formatWithLevel = "show level :: %p";
    private final String formatWithThread = "show thread :: %t";
    private final String formatWithMessage = "show message :: %m";
    private final String formatWithPercent = "show percent :: %%";
    private final String formatWithSeparator = "show separator :: %n";
    private final String formatWithFileName = "show invoker filename :: %F";
    private final String formatWithLineNumber = "show invoker line :: %L";
    private final String formatWithMethodName = "show invoker method :: %M";
    private final String formatWithDate = "show invoker method :: %d{d/M/yyyy}";

    private final String integrationFormat =  "Simple text" +
                                            " and level %p" +
                                            " and thread %t" +
                                            " and message '%m'" +
                                            " and percent %%" +
                                            " and separator %n" +
                                            " and filename %F" +
                                            " and line %L" +
                                            " and method %M" +
                                            " and date %d{H:m:s}";

    protected String expected;
    protected String formatted;

    @Before
    public void setup(){
        expected = "";
        formatted = "";
    }

    @Test
    public void testFormatMessage_NoFormat() throws InvalidArgumentException {

        LoggerInvoker invoker = new LoggerInvoker(Thread.currentThread().getStackTrace()[0], testName);
        MessageFormatter mf = new StringFormatter(new LoggerConfig(noFormat, Level.INFO, ","));

        assertEquals("Formatter modified an unformatted string", noFormat, mf.formatMessage(invoker, ""));
    }


    @Test
    public void testFormatMessage_WithLine() throws InvalidArgumentException {

        MessageFormatter mf = new StringFormatter(new LoggerConfig(formatWithLineNumber, Level.INFO, ","));

        Integer currentLine = Thread.currentThread().getStackTrace()[1].getLineNumber();
        expected = "show invoker line :: " + (currentLine + 2 );
        formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1], testName), "");

        assertEquals(expected, formatted);
    }

    @Test
    public void testFormatMessage_WithFileName() throws InvalidArgumentException {

        MessageFormatter mf = new StringFormatter(new LoggerConfig(formatWithFileName, Level.INFO, ","));

        String fileName = Thread.currentThread().getStackTrace()[1].getFileName();
        expected = "show invoker filename :: " + fileName;
        formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1], testName), "");

        assertEquals(expected, formatted);
    }

    @Test
    public void testFormatMessage_WithLevel() throws InvalidArgumentException {

        MessageFormatter mf = new StringFormatter(new LoggerConfig(formatWithLevel, Level.INFO, ","));

        expected = "show level :: INFO";
        formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1], testName), "");

        assertEquals(expected, formatted);
    }

    @Test
    public void testFormatMessage_WithMethodName() throws InvalidArgumentException {

        MessageFormatter mf = new StringFormatter(new LoggerConfig(formatWithMethodName, Level.INFO, ","));

        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        expected = "show invoker method :: " + methodName;
        formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1], testName), "");

        assertEquals(expected, formatted);
    }

    @Test
    public void testFormatMessage_WithMessage() throws InvalidArgumentException {

        MessageFormatter mf = new StringFormatter(new LoggerConfig(formatWithMessage, Level.INFO, ","));

        String message = "My_Custom message";
        expected = "show message :: " + message;
        formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1], testName), message);

        assertEquals(expected, formatted);
    }

    @Test
    public void testFormatMessage_WithPercent() throws InvalidArgumentException {

        MessageFormatter mf = new StringFormatter(new LoggerConfig(formatWithPercent, Level.INFO, ","));

        expected = "show percent :: %";
        formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1], testName), "");

        assertEquals(expected, formatted);
    }

    @Test
    public void testFormatMessage_WithSeparator() throws InvalidArgumentException {

        MessageFormatter mf = new StringFormatter(new LoggerConfig(formatWithSeparator, Level.INFO, "-_-"));

        expected = "show separator :: -_-";
        formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1], testName), "");

        assertEquals(expected, formatted);
    }

    @Test
    public void testFormatMessage_WithThread() throws InvalidArgumentException {

        MessageFormatter mf = new StringFormatter(new LoggerConfig(formatWithThread, Level.INFO, ","));

        String thread = Thread.currentThread().getName();
        expected = "show thread :: " + thread;
        formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1], testName), "");

        assertEquals(expected, formatted);
    }

    @Test
    public void testFormatMessage_WithDate() throws InvalidArgumentException {

        MessageFormatter mf = new StringFormatter(new LoggerConfig(formatWithDate, Level.INFO, ","));

        Calendar calendar = new GregorianCalendar();
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String month = String.valueOf(calendar.get(Calendar.MONTH) +1 );
        String year = String.valueOf(calendar.get(Calendar.YEAR));

        expected = "show invoker method :: " + day +"/"+ month +"/"+ year ;
        formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1], testName), "");

        assertEquals("Formatter failed at formatting the date", expected, formatted);
    }


    @Test
    public void testFormatMessage_AllReplaced() throws InvalidArgumentException {

        String level = Level.DEBUG.name();
        String separator = ",";
        MessageFormatter mf = new StringFormatter(new LoggerConfig(integrationFormat, Level.DEBUG, separator));

        String message = "My_Custom message";
        String thread = Thread.currentThread().getName();
        String fileName = Thread.currentThread().getStackTrace()[1].getFileName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        int currentLine = Thread.currentThread().getStackTrace()[1].getLineNumber();

        Calendar calendar = new GregorianCalendar();
        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));
        String second = String.valueOf(calendar.get(Calendar.SECOND));

        expected = String.format("Simple text and level %s and thread %s and message '%s' and percent %% and separator %s and filename %s and line %s and method %s and date %s:%s:%s",
                                        level, thread, message, separator, fileName, Integer.toString(currentLine+10), methodName, hour, minute, second);

        formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1], testName), message);

        assertEquals("Formatter failed with multiple replacements", expected, formatted);
    }

}
