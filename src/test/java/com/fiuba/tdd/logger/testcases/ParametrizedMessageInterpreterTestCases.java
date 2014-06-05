package com.fiuba.tdd.logger.testcases;

import com.fiuba.tdd.logger.slf4j.binding.ParametrizedMessageInterpreter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParametrizedMessageInterpreterTestCases {
    @Test
    public void testParametrizeWithOneArgument() {
        String expected = "replace argument";
        String actual = ParametrizedMessageInterpreter.replaceMessageWithArgs("replace {}", "argument");
        assertEquals(expected, actual);
    }

    @Test
    public void testParametrizeWithNullArgumentDoesNothing() {
        String expected = "replace {}";
        String actual = ParametrizedMessageInterpreter.replaceMessageWithArgs("replace {}", null);
        assertEquals(expected, actual);
    }

    @Test
    public void testEscapeParametrization() {
        String expected = "replace {}";
        String actual = ParametrizedMessageInterpreter.replaceMessageWithArgs("replace \\{}", "argument");
        assertEquals(expected, actual);
    }

    @Test
    public void testParametrizeWithTwoArguments() {
        String expected = "replace arg1 and arg2";
        String actual = ParametrizedMessageInterpreter.replaceMessageWithArgs("replace {} and {}", "arg1", "arg2");
        assertEquals(expected, actual);
    }

    @Test
    public void testParametrizeWithTwoArguments_FirstNull() {
        String expected = "replace null and arg2";
        String actual = ParametrizedMessageInterpreter.replaceMessageWithArgs("replace {} and {}", null, "arg2");
        assertEquals(expected, actual);
    }

    @Test
    public void testParametrizeWithTwoArguments_SecondNull() {
        String expected = "replace arg1 and null";
        String actual = ParametrizedMessageInterpreter.replaceMessageWithArgs("replace {} and {}", "arg1", null);
        assertEquals(expected, actual);
    }

    @Test
    public void testParametrizeWithTwoArguments_FirstEscaped() {
        String expected = "replace {} and arg1 and arg2";
        String actual = ParametrizedMessageInterpreter.replaceMessageWithArgs("replace \\{} and {} and {}", "arg1", "arg2");
        assertEquals(expected, actual);
    }

    @Test
    public void testParametrizeWithTwoArguments_SecondEscaped() {
        String expected = "replace arg1 and {} and arg2";
        String actual = ParametrizedMessageInterpreter.replaceMessageWithArgs("replace {} and \\{} and {}", "arg1", "arg2");
        assertEquals(expected, actual);
    }

    @Test
    public void testParametrizeWithThreeArguments() {
        String expected = "replace arg1 and arg2 and arg3";
        String actual = ParametrizedMessageInterpreter.replaceMessageWithArgs("replace {} and {} and {}", "arg1", "arg2", "arg3");
        assertEquals(expected, actual);
    }

    /**
     * This is more of an integration test, provided that many combinations
     * have already been tested, here we test the second '{}' escaped, and
     * a null argument in the middle at the argument list
     */
    @Test
    public void testParametrizeWithThreeArguments_NullAndEscapedMix() {
        String expected = "replace arg1 and {} and arg2 and null and arg4";
        String actual = ParametrizedMessageInterpreter.replaceMessageWithArgs("replace {} and \\{} and {} and {} and {}", "arg1", "arg2", null, "arg4");
        assertEquals(expected, actual);
    }

}
