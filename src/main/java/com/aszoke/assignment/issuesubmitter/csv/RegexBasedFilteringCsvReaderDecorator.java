package com.aszoke.assignment.issuesubmitter.csv;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RegexBasedFilteringCsvReaderDecorator implements CsvReader {

    private final CsvReader csvReader;
    private final Pattern pattern;

    public RegexBasedFilteringCsvReaderDecorator(final CsvReader csvReader, final String regex) {
        this.csvReader = csvReader;
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public List<String> readLines() {
        return csvReader.readLines().stream()
                .filter(line -> pattern.matcher(line).matches())
                .collect(Collectors.toList());
    }
}

