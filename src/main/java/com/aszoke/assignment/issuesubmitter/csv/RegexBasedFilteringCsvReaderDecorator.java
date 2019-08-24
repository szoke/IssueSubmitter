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
                .filter(line -> pattern.matcher(line).matches())
                .collect(Collectors.toList());
    }
}

