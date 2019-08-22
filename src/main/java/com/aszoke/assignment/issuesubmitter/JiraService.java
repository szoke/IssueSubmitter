package com.aszoke.assignment.issuesubmitter;

import java.util.Collection;
import java.util.List;

public interface JiraService {

    List<SubmissionResult> submit(Collection<Issue> issues);
}
