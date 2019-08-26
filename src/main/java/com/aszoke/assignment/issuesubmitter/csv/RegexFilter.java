package com.aszoke.assignment.issuesubmitter.csv;

public class RegexFilter implements Filter {

    private final String regex;

    public RegexFilter(final String regEx) {
        this.regex = regEx;
    }

    public boolean matches(String s) {
        return s.matches(regex);
    }
}
