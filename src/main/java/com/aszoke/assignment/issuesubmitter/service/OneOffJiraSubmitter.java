package com.aszoke.assignment.issuesubmitter.service;

import com.aszoke.assignment.issuesubmitter.domain.Issue;
import com.aszoke.assignment.issuesubmitter.domain.SubmissionResult;
import com.aszoke.assignment.issuesubmitter.server.Jira;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.Callable;

import static com.aszoke.assignment.issuesubmitter.util.Logger.logError;
import static com.aszoke.assignment.issuesubmitter.util.Logger.logInfo;

public class OneOffJiraSubmitter implements Callable<SubmissionResult> {

    private final Jira jira;
    private final Issue issue;
    private final long createdAt;

    public OneOffJiraSubmitter(final Jira jira, final Issue issue) {
        this.jira = jira;
        this.issue = issue;
        this.createdAt = System.currentTimeMillis();
    }

    @Override
    public SubmissionResult call() {
        long startedAt = System.currentTimeMillis();

        logInfo("Submitting " + toJson(issue) + " to Jira...");
        int statusCode = jira.submit(issue);
        logInfo("Submitted " + issue.getKey() + ". Result: HTTP " + statusCode + ".");

        long finishedAt = System.currentTimeMillis();

        // TODO aszoke Extract into factory?
        SubmissionResult result = new SubmissionResult();
        result.setIssueKey(issue.getKey());
        result.setStatusCode(statusCode);
        result.setCreatedAtMillis(createdAt);
        result.setStartedAtMillis(startedAt);
        result.setFinishedAtMillis(finishedAt);

        return result;
    }

    private String toJson(final Issue issue) {
        try {
            return new ObjectMapper().writeValueAsString(issue);
        } catch (JsonProcessingException e) {
            logError("Error while serializing issue to JSON.");
            throw new RuntimeException(e);
        }
    }
}

