package com.aszoke.assignment.issuesubmitter.domain;

import java.util.List;

public interface IssueFactory {

    List<Issue> create(List<String> csvLines);
}
