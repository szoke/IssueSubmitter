package com.aszoke.assignment.issuesubmitter.statistics;

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
    private long minTimeInWorkQueue;
    private long maxTimeInWorkQueue;
    private long averageTimeInWorkQueue;
    private long minSubmissionTime;
    private long maxSubmissionTime;
    private long averageSubmissionTime;
}
