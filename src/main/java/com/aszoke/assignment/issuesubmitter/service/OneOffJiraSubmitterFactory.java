package com.aszoke.assignment.issuesubmitter.service;

import com.aszoke.assignment.issuesubmitter.domain.Issue;
import com.aszoke.assignment.issuesubmitter.server.Jira;

import static java.util.Objects.requireNonNull;

public class OneOffJiraSubmitterFactory implements JiraSubmitterFactory {

    private final Jira jira;

    public OneOffJiraSubmitterFactory(final Jira jira) {
        this.jira = jira;
    }

    @Override
    public OneOffJiraSubmitter create(final Issue issue) {
        requireNonNull(issue);

        return new OneOffJiraSubmitter(jira, issue);
    }
}
