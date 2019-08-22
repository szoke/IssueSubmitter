package com.aszoke.assignment.issuesubmitter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static com.aszoke.assignment.issuesubmitter.Logger.logError;

public class DefaultJiraService implements JiraService {

    private final ExecutorService executorService;
    private final OneOffJiraSubmitterFactory oneOffJiraSubmitterFactory;

    public DefaultJiraService(final ExecutorService executorService,
                              final OneOffJiraSubmitterFactory oneOffJiraSubmitterFactory) {
        this.executorService = executorService;
        this.oneOffJiraSubmitterFactory = oneOffJiraSubmitterFactory;
    }

    @Override
    public List<SubmissionResult> submit(final Collection<Issue> issues) {
        List<Future<SubmissionResult>> futures = submitIssues(issues);
        List<SubmissionResult> results = collectResults(futures);
        shutDownExecutorService();
        return results;
    }

    private List<Future<SubmissionResult>> submitIssues(Collection<Issue> issues) {
        List<Future<SubmissionResult>> futures = new ArrayList<>(issues.size());
        for (Issue issue : issues) {
            OneOffJiraSubmitter oneOffJiraSubmitter = oneOffJiraSubmitterFactory.create(issue);
            Future<SubmissionResult> future = executorService.submit(oneOffJiraSubmitter);
            futures.add(future);
        }
        return futures;
    }

    private List<SubmissionResult> collectResults(Collection<Future<SubmissionResult>> futures) {
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
