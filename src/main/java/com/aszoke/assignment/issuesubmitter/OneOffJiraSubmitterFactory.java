package com.aszoke.assignment.issuesubmitter;

public class OneOffJiraSubmitterFactory {

    private final Jira jira;

    public OneOffJiraSubmitterFactory(Jira jira) {
        this.jira = jira;
    }

    public OneOffJiraSubmitter create(Issue issue) {
        return new OneOffJiraSubmitter(jira, issue);
    }
}
