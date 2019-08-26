package com.aszoke.assignment.issuesubmitter.csv;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class RegexBasedFilteringCsvReaderDecorator implements CsvReader {

    private final CsvReader csvReader;
    private final Pattern pattern;

    public RegexBasedFilteringCsvReaderDecorator(final CsvReader csvReader, final String regex) {
        requireNonNull(csvReader);
        requireNonNull(regex);

        this.csvReader = csvReader;
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public List<String> readLines() {
        return csvReader.readLines().stream()
                // java.util.regex.Pattern is final and cannot be mocked
                // Wrap in a Filter class to promote testability?
                .filter(line -> pattern.matcher(line).matches())
                .collect(Collectors.toList());
    }
}

