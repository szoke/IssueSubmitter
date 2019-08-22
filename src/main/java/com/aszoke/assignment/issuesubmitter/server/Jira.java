package com.aszoke.assignment.issuesubmitter.server;

import com.aszoke.assignment.issuesubmitter.domain.Issue;

public interface Jira {

    int submit(Issue issue);
}
