package com.aszoke.assignment.issuesubmitter.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// TODO aszoke Extract interface
public class ThreadPoolExecutorFactory implements ExecutorServiceFactory {

    @Override
    public ExecutorService create(final int poolSize) {
        return new ThreadPoolExecutor(poolSize, poolSize, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }
}
