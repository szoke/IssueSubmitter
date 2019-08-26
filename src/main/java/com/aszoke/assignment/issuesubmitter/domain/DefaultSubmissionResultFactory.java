package com.aszoke.assignment.issuesubmitter.domain;

public class DefaultSubmissionResultFactory implements SubmissionResultFactory {

    @Override
    public SubmissionResult create(final String issueKey, final int statusCode,
                                   final long createdAt, final long startedAt, final long finishedAt) {
        SubmissionResult result = new SubmissionResult();

        result.setIssueKey(issueKey);
        result.setStatusCode(statusCode);
        result.setCreatedAtMillis(createdAt);
        result.setStartedAtMillis(startedAt);
        result.setFinishedAtMillis(finishedAt);

        return result;
    }
}
