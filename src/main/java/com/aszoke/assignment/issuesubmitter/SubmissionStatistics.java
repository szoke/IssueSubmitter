package com.aszoke.assignment.issuesubmitter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SubmissionStatistics {

    private int issueCount;
    private int http200Count;
    private int http403Count;
    private long minTimeInWorkQueue = 0L;
    private long maxTimeInWorkQueue = 0L;
    private long averageTimeInWorkQueue = 0L;
    private long minSubmissionTime = 0L;
    private long maxSubmissionTime = 0L;
    private long averageSubmissionTime = 0L;
}
