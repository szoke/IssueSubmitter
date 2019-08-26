package com.aszoke.assignment.issuesubmitter.domain;

public interface SubmissionResultFactory {

    SubmissionResult create(String issueKey, int statusCode, long createdAt, long startedAt, long finishedAt);
}
