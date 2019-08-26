package com.aszoke.assignment.issuesubmitter.domain;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class DefaultIssueFactory implements IssueFactory {

    @Override
    public List<Issue> create(final List<String> csvLines) {
        requireNonNull(csvLines);

        return csvLines.stream()
                .map(this::fromCsv)
                .collect(Collectors.toList());
    }

    private Issue fromCsv(final String csv) {
        // DEMO-1000,11905,Backlog,07/Jun/18 3:13 PM,07/Jun/18 3:13 PM,,jiraadmin,Descript,MikeDemo2
        String[] fields = csv.split(",");

        Issue issue = new Issue();

        // TODO CSV headers are hardcoded and not read from the file at all. Is this OK for demo purposes?
        issue.setKey(fields[0]);
        issue.setId(fields[1]);
        issue.setStatus(fields[2]);
        issue.setCreated(fields[3]);
        issue.setUpdated(fields[4]);
        issue.setAssignee(fields[5]);
        issue.setCreator(fields[6]);
        issue.setDescription(fields[7]);
        issue.setSummary(fields[8]);

        return issue;
    }
}
