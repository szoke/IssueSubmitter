package com.aszoke.assignment.issuesubmitter;

import java.util.List;

public interface IssueFactory {

    List<Issue> create(List<String> csvLines);
}
