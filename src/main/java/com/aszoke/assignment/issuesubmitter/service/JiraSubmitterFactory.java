package com.aszoke.assignment.issuesubmitter.service;

import com.aszoke.assignment.issuesubmitter.domain.Issue;

public interface JiraSubmitterFactory {

    JiraSubmitter create(Issue issue);
}
