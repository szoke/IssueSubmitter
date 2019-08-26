package com.aszoke.assignment.issuesubmitter.service;

import com.aszoke.assignment.issuesubmitter.domain.Issue;
import com.aszoke.assignment.issuesubmitter.domain.SubmissionResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static com.aszoke.assignment.issuesubmitter.util.Logger.logError;
import static java.util.Objects.requireNonNull;

public class DefaultJiraService implements JiraService {

    private final ExecutorService executorService;
    private final JiraSubmitterFactory jiraSubmitterFactory;

    public DefaultJiraService(final ExecutorService executorService,
                              final JiraSubmitterFactory jiraSubmitterFactory) {
        requireNonNull(executorService);
        requireNonNull(jiraSubmitterFactory);

        this.executorService = executorService;
        this.jiraSubmitterFactory = jiraSubmitterFactory;
    }

    @Override
    public List<SubmissionResult> submit(final Collection<Issue> issues) {
        requireNonNull(issues);

        List<Future<SubmissionResult>> futures = submitIssues(issues);
        List<SubmissionResult> results = collectResults(futures);
        shutDownExecutorService();
        return results;
    }

    private List<Future<SubmissionResult>> submitIssues(final Collection<Issue> issues) {
        List<Future<SubmissionResult>> futures = new ArrayList<>(issues.size());
        for (Issue issue : issues) {
            JiraSubmitter jiraSubmitter = jiraSubmitterFactory.create(issue);
            Future<SubmissionResult> future = executorService.submit(jiraSubmitter);
            futures.add(future);
        }
        return futures;
    }

    private List<SubmissionResult> collectResults(final Collection<Future<SubmissionResult>> futures) {
        List<SubmissionResult> results = new ArrayList<>(futures.size());
        for (Future<SubmissionResult> future : futures) {
            try {
                results.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                logError("Error while collecting submission result! Future resolution failed.");
            }
        }
        return results;
    }

    private void shutDownExecutorService() {
        executorService.shutdown();
    }
}
