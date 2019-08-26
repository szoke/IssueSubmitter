package com.aszoke.assignment.issuesubmitter.service;

import com.aszoke.assignment.issuesubmitter.domain.Issue;
import com.aszoke.assignment.issuesubmitter.domain.SubmissionResult;
import com.aszoke.assignment.issuesubmitter.domain.SubmissionResultFactory;
import com.aszoke.assignment.issuesubmitter.server.Jira;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertSame;
import static org.mockito.Mockito.*;

public class OneOffJiraSubmitterTest {

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldThrowExceptionWhenJiraIsNull() {
        new OneOffJiraSubmitter(null, mock(SubmissionResultFactory.class), new Issue());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldThrowExceptionWhenSubmissionResultFactoryIsNull() {
        new OneOffJiraSubmitter(mock(Jira.class), null, new Issue());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldThrowExceptionWhenIssueIsNull() {
        new OneOffJiraSubmitter(mock(Jira.class), mock(SubmissionResultFactory.class), null);
    }

    @Test
    public void testCallShouldReturnSubmissionResultPopulatedFromExecutionResultAndTimestampsHappyPath() {
        Jira jira = mock(Jira.class);
        SubmissionResultFactory resultFactory = mock(SubmissionResultFactory.class);
        String key = "ISSUE-987";
        Issue issue = createIssue(key);
        int statusCode = 200;
        when(jira.submit(issue)).thenReturn(statusCode);
        SubmissionResult result = new SubmissionResult();
        // TODO aszoke The need for using anyXXX() matchers is a smell
        // Time dependent code should be further decoupled or else this test may produce false positives
        when(resultFactory.create(eq(key), eq(statusCode), anyLong(), anyLong(), anyLong())).thenReturn(result);
        OneOffJiraSubmitter underTest = createUnderTest(jira, resultFactory, issue);

        SubmissionResult actual = underTest.call();

        verify(jira).submit(issue);
        verify(resultFactory).create(eq(key), eq(statusCode), anyLong(), anyLong(), anyLong());
        assertNotNull(actual);
        assertSame(result, actual);
    }

    private Issue createIssue(String key) {
        Issue issue = new Issue();
        issue.setKey(key);
        return issue;
    }

    private OneOffJiraSubmitter createUnderTest(Jira jira, SubmissionResultFactory resultFactory, Issue issue) {
        return new OneOffJiraSubmitter(jira, resultFactory, issue);
    }

}