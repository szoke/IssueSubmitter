package com.aszoke.assignment.issuesubmitter.service;

import java.util.concurrent.ExecutorService;

public interface ExecutorServiceFactory {

    ExecutorService create(int threadCount);
}
