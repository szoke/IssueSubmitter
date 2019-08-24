package com.aszoke.assignment.issuesubmitter.domain;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class DefaultIssueFactory implements IssueFactory {

    @Override
    public List<Issue> create(final List<String> csvLines) {
        requireNonNull(csvLines);

        return csvLines.stream()
                .map(Issue::fromCsv)
                .collect(Collectors.toList());
    }
}
