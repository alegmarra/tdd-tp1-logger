package com.fiuba.tdd.logger.testcases;

import com.fiuba.tdd.logger.Logger;
import com.fiuba.tdd.logger.internal.LoggerInvoker;
import com.fiuba.tdd.logger.internal.MessageFormatter;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MessageFormatterTestCases extends TestCase{

    private final String noFormat = "None format string";
    private final String withLevel = "show level :: %p";
    private final String withThread = "show thread :: %t";
    private final String withMessage = "show message :: %m";
    private final String withPercent = "show percent :: %%";
    private final String withSeparator = "show separator :: %n";
    private final String withFileName = "show invoker filename :: %F";
    private final String withLineNumber = "show invoker line :: %L";
    private final String withMethodName = "show invoker method :: %M";
    private final String withDate = "show invoker method :: %d{dd/M/yyyy}";


    @Test
    public void testFormatMessage_NoFormat(){

        LoggerInvoker invoker = new LoggerInvoker(Thread.currentThread().getStackTrace()[0]);

        MessageFormatter mf = new MessageFormatter(noFormat, ",", Logger.Level.INFO);

        assertEquals("Formatter modified an unformatted string", noFormat, mf.formatMessage(invoker, ""));

    }


    @Test
    public void testFormatMessage_WithLine(){

        MessageFormatter mf = new MessageFormatter(withLineNumber, ",", Logger.Level.INFO);

        Integer currentLine = Thread.currentThread().getStackTrace()[1].getLineNumber();
        String expected = "show invoker line :: " + (currentLine + 2 );
        String formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1]), "");

        assertEquals(expected, formatted);
    }

    @Test
    public void testFormatMessage_WithFileName(){

        MessageFormatter mf = new MessageFormatter(withFileName, ",", Logger.Level.INFO);

        String fileName = Thread.currentThread().getStackTrace()[1].getFileName();
        String expected = "show invoker filename :: " + fileName;
        String formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1]), "");

        assertEquals(expected, formatted);
    }

    @Test
    public void testFormatMessage_WithLevel(){

        MessageFormatter mf = new MessageFormatter(withLevel, ",", Logger.Level.INFO);

        String expected = "show level :: INFO";
        String formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1]), "");

        assertEquals(expected, formatted);
    }

    @Test
    public void testFormatMessage_WithMethodName(){

        MessageFormatter mf = new MessageFormatter(withMethodName, ",", Logger.Level.INFO);

        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String expected = "show invoker method :: " + methodName;
        String formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1]), "");

        assertEquals(expected, formatted);
    }

    @Test
    public void testFormatMessage_WithMessage(){

        MessageFormatter mf = new MessageFormatter(withMessage, ",", Logger.Level.INFO);

        String message = "My_Custom Message";
        String expected = "show message :: " + message;
        String formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1]), message);

        assertEquals(expected, formatted);
    }

    @Test
    public void testFormatMessage_WithPercent(){

        MessageFormatter mf = new MessageFormatter(withPercent, ",", Logger.Level.INFO);

        String expected = "show percent :: %";
        String formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1]), "");

        assertEquals(expected, formatted);
    }

    @Test
    public void testFormatMessage_WithSeparator(){

        MessageFormatter mf = new MessageFormatter(withSeparator, ",", Logger.Level.INFO);

        String expected = "show separator :: ,";
        String formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1]), "");

        assertEquals(expected, formatted);
    }

    @Test
    public void testFormatMessage_WithThread(){

        MessageFormatter mf = new MessageFormatter(withThread, ",", Logger.Level.INFO);

        String thread = Thread.currentThread().getName();
        String expected = "show thread :: " + thread;
        String formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1]), "");

        assertEquals(expected, formatted);
    }

    @Test
    public void testFormatMessage_WithDate(){

        MessageFormatter mf = new MessageFormatter(withDate, ",", Logger.Level.INFO);

        Calendar calendar = new GregorianCalendar();
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String month = String.valueOf(calendar.get(Calendar.MONTH) +1 );
        String year = String.valueOf(calendar.get(Calendar.YEAR));

        String expected = "show invoker method :: " + day +"/"+ month +"/"+ year ;
        String formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1]), "");

        assertEquals("Formatter modified an unformatted string", expected, formatted);
    }

}
