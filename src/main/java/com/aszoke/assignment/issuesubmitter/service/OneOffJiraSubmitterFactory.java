package com.aszoke.assignment.issuesubmitter.service;

import com.aszoke.assignment.issuesubmitter.domain.Issue;
import com.aszoke.assignment.issuesubmitter.domain.SubmissionResult;
import com.aszoke.assignment.issuesubmitter.domain.SubmissionResultFactory;
import com.aszoke.assignment.issuesubmitter.server.Jira;

import static java.util.Objects.requireNonNull;

public class OneOffJiraSubmitterFactory implements JiraSubmitterFactory {

    private final Jira jira;
    private final SubmissionResultFactory submissionResultFactory;

    public OneOffJiraSubmitterFactory(final Jira jira, final SubmissionResultFactory submissionResultFactory) {
        requireNonNull(jira);
        requireNonNull(submissionResultFactory);

        this.jira = jira;
        this.submissionResultFactory = submissionResultFactory;
    }

    @Override
    public OneOffJiraSubmitter create(final Issue issue) {
        requireNonNull(issue);

        return new OneOffJiraSubmitter(jira, submissionResultFactory, issue);
    }
}
