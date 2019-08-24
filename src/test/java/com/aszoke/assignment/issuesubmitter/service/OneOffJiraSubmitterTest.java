package com.aszoke.assignment.issuesubmitter.service;

import com.aszoke.assignment.issuesubmitter.domain.Issue;
import com.aszoke.assignment.issuesubmitter.domain.SubmissionResult;
import com.aszoke.assignment.issuesubmitter.server.Jira;
import org.junit.Test;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OneOffJiraSubmitterTest {

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldThrowExceptionWhenJiraIsNull() {
        new OneOffJiraSubmitter(null, new Issue());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldThrowExceptionWhenIssueIsNull() {
        new OneOffJiraSubmitter(mock(Jira.class), null);
    }

    @Test
    public void testCallShouldReturnStatusCodeFromJiraWithOriginalIssueKeyWhenHappyPath() {
        Jira jira = mock(Jira.class);
        String key = "ISSUE-987";
        Issue issue = createIssue(key);
        int statusCode = 200;
        when(jira.submit(issue)).thenReturn(statusCode);
        OneOffJiraSubmitter underTest = createUnderTest(jira, issue);

        SubmissionResult actual = underTest.call();

        assertNotNull(actual);
        assertEquals(key, actual.getIssueKey());
        assertEquals(statusCode, actual.getStatusCode());
        assertTrue(actual.getCreatedAtMillis() > 0L);
        assertTrue(actual.getStartedAtMillis() > 0L);
        assertTrue(actual.getFinishedAtMillis() > 0L);
    }

    private Issue createIssue(String key) {
        Issue issue = new Issue();
        issue.setKey(key);
        return issue;
    }

    private OneOffJiraSubmitter createUnderTest(Jira jira, Issue issue) {
        return new OneOffJiraSubmitter(jira, issue);
    }

}