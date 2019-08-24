package com.aszoke.assignment.issuesubmitter.service;

import com.aszoke.assignment.issuesubmitter.domain.Issue;
import com.aszoke.assignment.issuesubmitter.server.Jira;

import static java.util.Objects.requireNonNull;

// TODO aszoke Extract interface
public class OneOffJiraSubmitterFactory {

    private final Jira jira;

    public OneOffJiraSubmitterFactory(final Jira jira) {
        this.jira = jira;
    }

    public OneOffJiraSubmitter create(final Issue issue) {
        requireNonNull(issue);

        return new OneOffJiraSubmitter(jira, issue);
    }
}
