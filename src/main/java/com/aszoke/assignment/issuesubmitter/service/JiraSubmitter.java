package com.aszoke.assignment.issuesubmitter.service;

import com.aszoke.assignment.issuesubmitter.domain.SubmissionResult;

import java.util.concurrent.Callable;

public interface JiraSubmitter extends Callable<SubmissionResult> {
}
