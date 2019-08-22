package com.aszoke.assignment.issuesubmitter;

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
