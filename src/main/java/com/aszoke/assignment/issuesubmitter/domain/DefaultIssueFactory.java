package com.aszoke.assignment.issuesubmitter.domain;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultIssueFactory implements IssueFactory {

    @Override
    public List<Issue> create(List<String> csvLines) {
        return csvLines.stream()
                .map(Issue::fromCsv)
                .collect(Collectors.toList());
    }
}
