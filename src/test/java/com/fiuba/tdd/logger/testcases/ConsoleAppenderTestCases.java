package com.fiuba.tdd.logger.testcases;

import com.fiuba.tdd.logger.appenders.Appendable;
import com.fiuba.tdd.logger.appenders.ConsoleAppender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class ConsoleAppenderTestCases {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private Appendable appender;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        appender = new ConsoleAppender();
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void testSimpleMessageAppend() {
        String message = "My Custom Text message";
        try {
            appender.append(message);
            assertEquals(message, outContent.toString().trim());

        } catch (IOException e) {
            fail("An exception was thrown when trying to append a simple message. Cause: " + e.getCause());
        }
    }

    @Test
    public void testEmptyMessageAppend() {
        try {
            appender.append("");
            assertEquals("", outContent.toString().trim());

        } catch (IOException e) {
            fail("An exception was thrown when trying to append an empty String. Cause: " + e.getCause());
        }
    }

    @Test
    public void testMultipleAppenders() {

        Appendable secondaryAppender = new ConsoleAppender();

        try {

            String firstMessage = "myFirstMessage";
            String secondMessage = "my Second message";

            appender.append(firstMessage);
            secondaryAppender.append(secondMessage);

            assertEquals(firstMessage + "\n" + secondMessage + "\n", outContent.toString());

        } catch (IOException e) {
            fail("An exception was thrown when trying to append multiple messages to console. Cause: " + e.getCause());
        }
    }
}
