package com.aszoke.assignment.issuesubmitter.statistics;

import com.aszoke.assignment.issuesubmitter.domain.SubmissionResult;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class SubmissionStatisticsFactoryTest {

    private SubmissionStatisticsFactory underTest = new SubmissionStatisticsFactory();

    @Test(expected = NullPointerException.class)
    public void testCreateShouldThrowExceptionWhenArgumentIsNull() {
        underTest.create(null);
    }

    @Test
    public void testCreateShouldReturnZeroValuesWhenArgumentIsEmpty() {
        SubmissionStatistics actual = underTest.create(Collections.emptyList());

        assertNotNull(actual);
        assertEquals(0, actual.getIssueCount());
        assertEquals(0, actual.getHttp200Count());
        assertEquals(0, actual.getHttp403Count());
        assertEquals(0, actual.getMinTimeInWorkQueue());
        assertEquals(0, actual.getMaxTimeInWorkQueue());
        assertEquals(0, actual.getAverageTimeInWorkQueue());
        assertEquals(0, actual.getMinSubmissionTime());
        assertEquals(0, actual.getMaxSubmissionTime());
        assertEquals(0, actual.getAverageSubmissionTime());
    }

    @Test
    public void testCreateShouldPopulateValuesWhenArgumentHasElements() {
        SubmissionResult result1 = createSubmissionResult("TEST-1", 200, 1566650684100L, 1566650684200L, 1566650684400L);
        SubmissionResult result2 = createSubmissionResult("TEST-2", 403, 1566650685100L, 1566650685500L, 1566650686000L);
        ArrayList<SubmissionResult> results = new ArrayList<>();
        results.add(result1);
        results.add(result2);

        SubmissionStatistics actual = underTest.create(results);

        assertNotNull(actual);
        assertEquals(2, actual.getIssueCount());
        assertEquals(1, actual.getHttp200Count());
        assertEquals(1, actual.getHttp403Count());
        assertEquals(100, actual.getMinTimeInWorkQueue());
        assertEquals(400, actual.getMaxTimeInWorkQueue());
        assertEquals(250, actual.getAverageTimeInWorkQueue());
        assertEquals(200, actual.getMinSubmissionTime());
        assertEquals(500, actual.getMaxSubmissionTime());
        assertEquals(350, actual.getAverageSubmissionTime());
    }

    private SubmissionResult createSubmissionResult(String issueKey, int statusCode, long createdAt, long startedAt,
                                                    long finishedAt) {
        SubmissionResult r = new SubmissionResult();
        r.setIssueKey(issueKey);
        r.setStatusCode(statusCode);
        r.setCreatedAtMillis(createdAt);
        r.setStartedAtMillis(startedAt);
        r.setFinishedAtMillis(finishedAt);
        return r;
    }
}