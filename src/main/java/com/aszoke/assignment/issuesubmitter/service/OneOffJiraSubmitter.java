package com.aszoke.assignment.issuesubmitter.service;

import com.aszoke.assignment.issuesubmitter.domain.Issue;
import com.aszoke.assignment.issuesubmitter.domain.SubmissionResult;
import com.aszoke.assignment.issuesubmitter.domain.SubmissionResultFactory;
import com.aszoke.assignment.issuesubmitter.server.Jira;

import static com.aszoke.assignment.issuesubmitter.util.Logger.logInfo;
import static java.util.Objects.requireNonNull;

public class OneOffJiraSubmitter implements JiraSubmitter {

    private final Jira jira;
    private final SubmissionResultFactory submissionResultFactory;
    private final Issue issue;
    private final long createdAt;

    public OneOffJiraSubmitter(final Jira jira, final SubmissionResultFactory submissionResultFactory, final Issue issue) {
        requireNonNull(jira);
        requireNonNull(submissionResultFactory);
        requireNonNull(issue);

        this.jira = jira;
        this.submissionResultFactory = submissionResultFactory;
        this.issue = issue;
        this.createdAt = System.currentTimeMillis();
    }

    @Override
    public SubmissionResult call() {
        long startedAt = System.currentTimeMillis();

        logInfo("Submitting " + issue + " to Jira...");
        int statusCode = jira.submit(issue);
        logInfo("Submitted " + issue.getKey() + ". Result: HTTP " + statusCode + ".");

        long finishedAt = System.currentTimeMillis();

        return submissionResultFactory.create(issue.getKey(), statusCode, createdAt, startedAt, finishedAt);
    }
}

