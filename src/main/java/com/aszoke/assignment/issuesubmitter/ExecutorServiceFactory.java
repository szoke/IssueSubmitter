package com.aszoke.assignment.issuesubmitter;

import java.util.concurrent.ExecutorService;

public interface ExecutorServiceFactory {

    ExecutorService create(int threadCount);
}
