package com.aszoke.assignment.issuesubmitter;

import com.aszoke.assignment.issuesubmitter.csv.*;
import com.aszoke.assignment.issuesubmitter.domain.DefaultIssueFactory;
import com.aszoke.assignment.issuesubmitter.domain.Issue;
import com.aszoke.assignment.issuesubmitter.domain.IssueFactory;
import com.aszoke.assignment.issuesubmitter.domain.SubmissionResult;
import com.aszoke.assignment.issuesubmitter.server.Jira;
import com.aszoke.assignment.issuesubmitter.server.MockJira;
import com.aszoke.assignment.issuesubmitter.service.*;
import com.aszoke.assignment.issuesubmitter.statistics.SubmissionStatistics;
import com.aszoke.assignment.issuesubmitter.statistics.SubmissionStatisticsFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.aszoke.assignment.issuesubmitter.util.Logger.logInfo;

public class Application {

    private static final String TEST_CSV_FILE = "miketest.csv";
    private static final Filter VALID_JIRA_ISSUE_REGEX_FILTER = new RegexFilter(".*,.*,.*,.*,.*,.*,.*,.*,.*");
    private static final int DEFAULT_THREAD_POOL_SIZE = 8;

    public static void main(String[] args) {
        int threadPoolSize = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_THREAD_POOL_SIZE;
        String userFilterRegex = args.length > 1 ? args[1] : null;

        // Poor man's DI
        List<Filter> filters = createFilters(VALID_JIRA_ISSUE_REGEX_FILTER);
        addUserRegexFilter(userFilterRegex, filters);
        CsvReader csvReader = createCsvReader(filters);
        IssueFactory issueFactory = createIssueFactory();
        JiraService jiraService = createJiraService(threadPoolSize);
        SubmissionStatisticsFactory submissionStatisticsFactory = createStatisticsFactory();

        List<String> lines = csvReader.readLines();
        List<Issue> issues = issueFactory.create(lines);
        List<SubmissionResult> results = jiraService.submit(issues);
        SubmissionStatistics statistics = submissionStatisticsFactory.create(results);
        printStatistics(statistics, threadPoolSize, userFilterRegex);
    }

    private static List<Filter> createFilters(Filter... filters) {
        ArrayList<Filter> list = new ArrayList<>();
        Collections.addAll(list, filters);
        return list;
    }

    private static List<Filter> addUserRegexFilter(String regex, List<Filter> filters) {
        if (regex == null) {
            return filters;
        }

        filters.add(new RegexFilter(regex));
        return filters;
    }

    private static CsvReader createCsvReader(Collection<Filter> filters) {
        CsvReader defaultCsvReader = new DefaultCsvReader(TEST_CSV_FILE);
        return new FilteringCsvReaderDecorator(defaultCsvReader, filters);
    }

    private static IssueFactory createIssueFactory() {
        return new DefaultIssueFactory();
    }

    private static JiraService createJiraService(int threadPoolSize) {
        ExecutorServiceFactory executorServiceFactory = new ThreadPoolExecutorFactory();
        Jira jira = new MockJira();
        JiraSubmitterFactory jiraSubmitterFactory = new OneOffJiraSubmitterFactory(jira);
        return new DefaultJiraService(executorServiceFactory.create(threadPoolSize), jiraSubmitterFactory);
    }

    private static SubmissionStatisticsFactory createStatisticsFactory() {
        return new SubmissionStatisticsFactory();
    }

    private static void printStatistics(SubmissionStatistics stats, int threadPoolSize, String userFilterRegex) {
        logInfo("-------- FINISHED --------");
        logInfo("Number of threads used for submission: " + threadPoolSize);
        logInfo("User regex used for filtering CSV content: " + userFilterRegex);
        logInfo("Number of issues submitted: " + stats.getIssueCount());
        logInfo("Number of successful submissions (HTTP 200): " + stats.getHttp200Count());
        logInfo("Number of failed submissions (HTTP 403): " + stats.getHttp403Count());
        logInfo("-");
        logInfo("Minimum time for a submission task to wait in the queue: " + stats.getMinTimeInWorkQueue() + " ms");
        logInfo("Maximum time for a submission task to wait in the queue: " + stats.getMaxTimeInWorkQueue() + " ms");
        logInfo("Average time for a submission task to wait in the queue: " + stats.getAverageTimeInWorkQueue() + " ms");
        logInfo("-");
        logInfo("Minimum time for a submission task to submit the issue: " + stats.getMinSubmissionTime() + " ms");
        logInfo("Maximum time for a submission task to submit the issue: " + stats.getMaxSubmissionTime() + " ms");
        logInfo("Average time for a submission task to submit the issue: " + stats.getAverageSubmissionTime() + " ms");
    }
}