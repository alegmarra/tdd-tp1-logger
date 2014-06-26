package com.fiuba.tdd.logger.testcases;

import com.fiuba.tdd.logger.filters.PatternFilter;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PatternFilterTestCases {

    private PatternFilter patternFilter;
    private final String simpleMessage = "Simple message";
    private final String matchesEverythingPatternString = ".*";
    private final Pattern matchesEverythingPattern = Pattern.compile(matchesEverythingPatternString);

    @Test
    public void testDefaultConstructor_AllowsEmptyMessage() {
        patternFilter = new PatternFilter();
        assertTrue(patternFilter.allows("", null));
    }

    @Test
    public void testDefaultConstructor_AllowsSimpleMessage() {
        patternFilter = new PatternFilter();
        assertTrue(patternFilter.allows(simpleMessage, null));
    }

    @Test
    public void testConstructorWithPattern_NotAllowsSimpleMessage() {
        patternFilter = new PatternFilter(matchesEverythingPattern);
        assertFalse(patternFilter.allows(simpleMessage, null));
    }

    @Test
    public void testConstructorWithPatternString_NotAllowsSimpleMessage() {
        patternFilter = new PatternFilter(matchesEverythingPatternString);
        assertFalse(patternFilter.allows(simpleMessage, null));
    }

    @Test
    public void testSetPatternWithPattern_NotAllowsSimpleMessage() {
        patternFilter = new PatternFilter();
        patternFilter.setPattern(matchesEverythingPattern);

        assertFalse(patternFilter.allows(simpleMessage, null));
    }

    @Test
    public void testSetPatternWithPatternString_NotAllowsSimpleMessage() {
        patternFilter = new PatternFilter();
        patternFilter.setPattern(matchesEverythingPatternString);

        assertFalse(patternFilter.allows(simpleMessage, null));
    }

    @Test
    public void testAllows_ComplexPattern() {
        Pattern ISO8601DatePattern = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2})\\:(\\d{2})\\:(\\d{2})[+-](\\d{2})\\:(\\d{2})");
        String ISO8601DateMessage = "2014-06-01T05:49:23-03:00";
        patternFilter = new PatternFilter(ISO8601DatePattern);

        assertFalse(patternFilter.allows(ISO8601DateMessage, null));
    }
}
