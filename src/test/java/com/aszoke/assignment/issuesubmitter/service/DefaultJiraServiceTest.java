package com.aszoke.assignment.issuesubmitter.service;

import com.aszoke.assignment.issuesubmitter.domain.Issue;
import com.aszoke.assignment.issuesubmitter.domain.SubmissionResult;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;

public class DefaultJiraServiceTest {

    @Mock
    private ExecutorService executorService;
    @Mock
    private JiraSubmitterFactory jiraSubmitterFactory;

    @InjectMocks
    private DefaultJiraService underTest;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldThrowWhenExecutorServiceIsNull() {
        new DefaultJiraService(null, jiraSubmitterFactory);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldThrowWhenOneOffJiraSubmitterFactoryIsNull() {
        new DefaultJiraService(executorService, null);
    }

    @Test(expected = NullPointerException.class)
    public void testSubmitShouldThrowWhenNullIsSubmitted() {
        underTest.submit(null);
    }

    @Test
    public void testSubmitShouldReturnEmptyResultsWhenEmptyCollectionIsSubmitted() {
        List<SubmissionResult> actual = underTest.submit(Collections.emptyList());

        verify(executorService).shutdown();
        verifyNoMoreInteractions(executorService);
        verifyZeroInteractions(jiraSubmitterFactory);
        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

    @Test
    public void testSubmitShouldCreateOneSubmitterPerIssueAndSubmitThemToExecutorServiceWhenCollectionIsNotEmpty()
            throws ExecutionException, InterruptedException {
        // TODO aszoke Split class under test?
        // This given part is although manageable, is a bit long and complex
        // In real world DefaultJiraService could be split into 3 classes where the body of the current private
        // methods would be in the new classes
        Issue issue1 = new Issue();
        OneOffJiraSubmitter oneOffJiraSubmitter1 = mock(OneOffJiraSubmitter.class);
        when(jiraSubmitterFactory.create(issue1)).thenReturn(oneOffJiraSubmitter1);
        Issue issue2 = new Issue();
        OneOffJiraSubmitter oneOffJiraSubmitter2 = mock(OneOffJiraSubmitter.class);
        when(jiraSubmitterFactory.create(issue2)).thenReturn(oneOffJiraSubmitter2);
        Future<SubmissionResult> future1 = mock(Future.class);
        SubmissionResult submissionResult1 = new SubmissionResult();
        when(future1.get()).thenReturn(submissionResult1);
        when(executorService.submit(oneOffJiraSubmitter1)).thenReturn(future1);
        Future<SubmissionResult> future2 = mock(Future.class);
        SubmissionResult submissionResult2 = new SubmissionResult();
        when(future2.get()).thenReturn(submissionResult2);
        when(executorService.submit(oneOffJiraSubmitter2)).thenReturn(future2);
        ArrayList<Issue> issues = new ArrayList<>();
        issues.add(issue1);
        issues.add(issue2);

        List<SubmissionResult> actual = underTest.submit(issues);

        verify(jiraSubmitterFactory).create(issue1);
        verify(jiraSubmitterFactory).create(issue2);
        verify(executorService).submit(oneOffJiraSubmitter1);
        verify(executorService).submit(oneOffJiraSubmitter2);
        verify(executorService).shutdown();
        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertSame(submissionResult1, actual.get(0));
        assertSame(submissionResult2, actual.get(1));
    }
}