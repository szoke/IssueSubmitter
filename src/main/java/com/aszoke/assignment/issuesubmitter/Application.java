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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.aszoke.assignment.issuesubmitter.util.Logger.logInfo;

public class Application {

    private static final String TEST_CSV_FILE = "miketest.csv";

    public static void main(String[] args) {
        assertArgs(args);

        int threadPoolSize = Integer.parseInt(args[0]);
        String filterRegex = args[1];

        // Poor man's DI
        List<Filter> filters = createFilters(new RegexFilter(filterRegex));
        CsvReader csvReader = createCsvReader(filters);
        IssueFactory issueFactory = createIssueFactory();
        JiraService jiraService = createJiraService(threadPoolSize);
        SubmissionStatisticsFactory submissionStatisticsFactory = createStatisticsFactory();

        List<String> lines = csvReader.readLines();
        List<Issue> issues = issueFactory.create(lines);
        List<SubmissionResult> results = jiraService.submit(issues);
        SubmissionStatistics statistics = submissionStatisticsFactory.create(results);
        printStatistics(statistics, threadPoolSize);
    }

    private static void assertArgs(String[] args) {
        if (args.length < 2) {
            printUsage();
            shutdown();
        }
    }

    private static void printUsage() {
        System.out.println("Missing arguments!");
        System.out.println("Usage: java -jar target/issuesubmitter.jar <thread-pool-size> <csv-filter-regex>");
        System.out.println("Example: java -jar target/issuesubmitter.jar 4 DEMO-9.*");
    }

    private static void shutdown() {
        System.exit(-1);
    }

    private static List<Filter> createFilters(Filter... filters) {
        return Arrays.asList(filters);
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
        OneOffJiraSubmitterFactory oneOffJiraSubmitterFactory = new OneOffJiraSubmitterFactory(jira);
        return new DefaultJiraService(executorServiceFactory.create(threadPoolSize), oneOffJiraSubmitterFactory);
    }

    private static SubmissionStatisticsFactory createStatisticsFactory() {
        return new SubmissionStatisticsFactory();
    }

    private static void printStatistics(SubmissionStatistics stats, int threadPoolSize) {
        logInfo("-------- FINISHED --------");
        logInfo("Number of threads used for submission: " + threadPoolSize);
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