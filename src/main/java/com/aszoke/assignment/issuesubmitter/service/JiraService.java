package com.aszoke.assignment.issuesubmitter.service;

import com.aszoke.assignment.issuesubmitter.domain.Issue;
import com.aszoke.assignment.issuesubmitter.domain.SubmissionResult;

import java.util.Collection;
import java.util.List;

public interface JiraService {

    List<SubmissionResult> submit(Collection<Issue> issues);
}
