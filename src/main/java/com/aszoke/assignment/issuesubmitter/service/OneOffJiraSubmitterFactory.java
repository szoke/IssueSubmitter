package com.aszoke.assignment.issuesubmitter.service;

import com.aszoke.assignment.issuesubmitter.domain.Issue;
import com.aszoke.assignment.issuesubmitter.server.Jira;

// TODO aszoke Extract interface
public class OneOffJiraSubmitterFactory {

    private final Jira jira;

    public OneOffJiraSubmitterFactory(Jira jira) {
        this.jira = jira;
    }

    public OneOffJiraSubmitter create(Issue issue) {
        return new OneOffJiraSubmitter(jira, issue);
    }
}
