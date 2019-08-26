package com.aszoke.assignment.issuesubmitter.csv;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class FilteringCsvReaderDecorator implements CsvReader {

    private final CsvReader csvReader;
    private final Collection<Filter> filters;

    public FilteringCsvReaderDecorator(final CsvReader csvReader, final Collection<Filter> filters) {
        requireNonNull(csvReader);
        requireNonNull(filters);

        this.csvReader = csvReader;
        this.filters = filters;
    }

    @Override
    public List<String> readLines() {
        return csvReader.readLines().stream()
                .filter(this::allFiltersMatch)
                .collect(Collectors.toList());
    }

    private boolean allFiltersMatch(String line) {
        return filters.stream().allMatch(filter -> filter.matches(line));
    }
}

