package com.aszoke.assignment.issuesubmitter.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SubmissionResult {

    private String issueKey;
    private int statusCode;
    private long createdAtMillis;
    private long startedAtMillis;
    private long finishedAtMillis;
}
