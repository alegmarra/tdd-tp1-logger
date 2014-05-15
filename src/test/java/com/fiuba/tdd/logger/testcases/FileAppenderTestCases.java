package com.fiuba.tdd.logger.testcases;

import com.fiuba.tdd.logger.Appendable;
import com.fiuba.tdd.logger.internal.InvalidArgumentException;
import com.fiuba.tdd.logger.writers.FileAppender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;


public class FileAppenderTestCases {

    private BufferedReader br = null;

    private Appendable appender = null;
    private String filename = "";

    @Before
    public void setUpFile() {

    }

    @After
    public void cleanUpFile() throws IOException {

        if (br!= null) br.close();

        File temp = new File(filename);
        if (temp.exists() && !temp.delete())
            throw new RuntimeException("Unable to delete the test file " + temp.getAbsolutePath());

        filename = "";
    }


    @Test
    public void testCreateNewFile() {

        try {
            filename = "./test_fileLogger.log";
            appender = new FileAppender(filename);

            File logFile = new File(filename);

            assertTrue(logFile.exists());
            assertTrue(logFile.canWrite());
            assertEquals(logFile.length(), 0);

        } catch (InvalidArgumentException e) {
            e.printStackTrace();
            fail("An InvalidArgument exception was thrown when passing a valid argument. Cause: " + e.getCause());

        } catch (IOException e) {
            e.printStackTrace();
            fail("An exception was thrown when trying to append an empty String. Cause: " + e.getCause());
        }
    }

    @Test
    public void testCreateNewFile_ThrowsException() {

        try {
            filename = "";
            appender = new FileAppender(filename);

            fail("An InvalidArgumentException was expected because of an empty filename, but none was thrown");

        } catch (InvalidArgumentException e) {
            assertTrue(true);

        } catch (IOException e) {
            e.printStackTrace();
            fail("An InvalidArgumentException was expected because of an empty filename, but none was thrown");
        }
    }

    @Test
    public void testAppendToNewFile() {

        try {
            filename = "./test_fileLogger.log";
            appender = new FileAppender(filename);

            String message_1 = "my first message";
            String message_2 = "my second message";

            appender.append(message_1);
            appender.append(message_2);

            assertFileContainsMessages(message_1, message_2);

        } catch (InvalidArgumentException e) {
            e.printStackTrace();
            fail("An InvalidArgument exception was thrown when passing a valid argument. Cause: " + e.getCause());

        } catch (IOException e) {
            e.printStackTrace();
            fail("An exception was thrown when trying to write to a new file. Cause: " + e.getCause());
        }
    }

    @Test
    public void testAppendToExistingFile() throws IOException {

        File temp = File.createTempFile("test_", "appendToExistingFile");
        filename = temp.getCanonicalPath();

        try {
            appender = new FileAppender(filename);

            String message_1 = "my first message";
            String message_2 = "my second message";

            appender.append(message_1);
            appender.append(message_2);

            assertFileContainsMessages(message_1, message_2);

        } catch (InvalidArgumentException e) {
            e.printStackTrace();
            fail("An InvalidArgument exception was thrown when passing a valid argument. Cause: " + e.getCause());

        } catch (IOException e) {
            e.printStackTrace();
            fail("An exception was thrown when trying to write to a new file. Cause: " + e.getCause());
        }
    }


    @Test
    public void testMultipleAppenders() {
        try {
            filename = "./test_fileLogger.log";
            Appendable appender_1 = new FileAppender(filename);
            Appendable appender_2 = new FileAppender(filename);

            String message_1 = "my first message";
            appender_1.append(message_1);

            String message_2 = "my second message";
            appender_2.append(message_2);

            assertFileContainsMessages(message_1, message_2);

        } catch (InvalidArgumentException e) {
            e.printStackTrace();
            fail("An InvalidArgument exception was thrown when passing a valid argument. Cause: " + e.getCause());

        } catch (IOException e) {
            e.printStackTrace();
            fail("An exception was thrown when trying to write to a new file. Cause: " + e.getCause());
        }
    }


    /**
     *  Custom assertion.
     *  Checks that all the messages declared as required exists as lines of the test file.
     * */
    private void assertFileContainsMessages(String... messages) throws IOException {

        try {
            br = new BufferedReader(new FileReader(filename));

            for (String message: messages){
                assertEquals(message, br.readLine());
            }

            assertEquals(null, br.readLine());

        } catch (IOException e) {
            e.printStackTrace();
            fail("An exception was thrown when trying to read the log file. Cause: " + e.getCause());

        } finally {
            if (br != null) br.close();
        }
    }

}
