package com.aszoke.assignment.issuesubmitter.server;

import com.aszoke.assignment.issuesubmitter.domain.Issue;

import java.util.Random;

import static com.aszoke.assignment.issuesubmitter.util.Logger.logError;
import static com.aszoke.assignment.issuesubmitter.util.Logger.logInfo;

public class MockJira implements Jira {

    private static final Random RANDOM = new Random();
    private static final int FAILURE_PROBABILITY_IN_PERCENTS = 1;

    public int submit(final Issue issue) {
        try {
            Thread.sleep(getRandomLatency());
        } catch (InterruptedException e) {
            logError("MockJira sleep interrupted!");
        }
        return getRandomHttpStatusCode();
    }

    private int getRandomLatency() {
        return getRandomNumberInRange(10, 100);
    }

    private int getRandomHttpStatusCode() {
        return didSucceed(FAILURE_PROBABILITY_IN_PERCENTS) ? 200 : 403;
    }

    private boolean didSucceed(final int failureProbabilityInPercents) {
        return getRandomNumberInRange(1, 100) > failureProbabilityInPercents;
    }

    private int getRandomNumberInRange(final int min, final int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min.");
        }

        return RANDOM.nextInt((max - min) + 1) + min;
    }
}
