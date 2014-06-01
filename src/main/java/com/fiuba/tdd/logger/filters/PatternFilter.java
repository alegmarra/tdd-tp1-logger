package com.fiuba.tdd.logger.filters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternFilter implements Filter {

    private Pattern pattern;
    private static final String allowsEverythingPatternString = "a^";

    public PatternFilter() {
        setPattern(allowsEverythingPatternString);
    }


    public PatternFilter(Pattern pattern) {
        setPattern(pattern);
    }

    public PatternFilter(String patternString) {
        setPattern(patternString);
    }


    public void setPattern(String patternString) {
        this.pattern = Pattern.compile(patternString);
    }


    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public Boolean allows(String msg) {
        Matcher matcher = pattern.matcher(msg);
        return !matcher.matches();
    }
}
