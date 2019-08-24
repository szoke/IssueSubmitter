package com.aszoke.assignment.issuesubmitter.statistics;

import com.aszoke.assignment.issuesubmitter.domain.SubmissionResult;

import java.util.Collection;

import static java.util.Objects.requireNonNull;

public class SubmissionStatisticsFactory {

    public SubmissionStatistics create(final Collection<SubmissionResult> results) {
        requireNonNull(results);

        if (results.size() == 0) {
            return new SubmissionStatistics();
        }

        // TODO aszoke Split up calculations eventually into separate classes
        //  if testing becomes tedious or more flexibility is needed

        int http200Count = 0;
        int http403Count = 0;

        long minTimeInWorkQueue = Long.MAX_VALUE;
        long maxTimeInWorkQueue = 0L;
        long averageTimeInWorkQueue = 0L;

        long minSubmissionTime = Long.MAX_VALUE;
        long maxSubmissionTime = 0L;
        long averageSubmissionTime = 0L;

        for (SubmissionResult result : results) {
            if (result.getStatusCode() == 200) {
                http200Count++;
            } else if (result.getStatusCode() == 403) {
                http403Count++;
            }

            long timeInWorkingQueue = result.getStartedAtMillis() - result.getCreatedAtMillis();
            long submissionTime = result.getFinishedAtMillis() - result.getStartedAtMillis();

            minTimeInWorkQueue = Math.min(minTimeInWorkQueue, timeInWorkingQueue);
            maxTimeInWorkQueue = Math.max(maxTimeInWorkQueue, timeInWorkingQueue);

            minSubmissionTime = Math.min(minSubmissionTime, submissionTime);
            maxSubmissionTime = Math.max(maxSubmissionTime, submissionTime);

            averageTimeInWorkQueue += timeInWorkingQueue;
            averageSubmissionTime += submissionTime;
        }

        averageTimeInWorkQueue /= results.size();
        averageSubmissionTime /= results.size();

        SubmissionStatistics stats = new SubmissionStatistics();

        stats.setIssueCount(results.size());
        stats.setHttp200Count(http200Count);
        stats.setHttp403Count(http403Count);
        stats.setMinTimeInWorkQueue(minTimeInWorkQueue);
        stats.setMaxTimeInWorkQueue(maxTimeInWorkQueue);
        stats.setAverageTimeInWorkQueue(averageTimeInWorkQueue);
        stats.setMinSubmissionTime(minSubmissionTime);
        stats.setMaxSubmissionTime(maxSubmissionTime);
        stats.setAverageSubmissionTime(averageSubmissionTime);

        return stats;
    }
}
