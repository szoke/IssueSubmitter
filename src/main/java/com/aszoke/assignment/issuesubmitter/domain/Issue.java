package com.aszoke.assignment.issuesubmitter.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Issue {

    private String key;
    private String id;
    private String status;
    private String created;
    private String updated;
    private String assignee;
    private String creator;
    private String description;
    private String summary;
}
