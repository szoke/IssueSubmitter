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

    public static Issue fromCsv(String csv) {
        // DEMO-1000,11905,Backlog,07/Jun/18 3:13 PM,07/Jun/18 3:13 PM,,jiraadmin,Descript,MikeDemo2
        String[] fields = csv.split(",");

        // TODO  assert fields length equals to 9

        Issue issue = new Issue();

        // TODO aszoke
        if (fields.length < 9) {
            return issue;
        }

        issue.key = fields[0];
        issue.id = fields[1];
        issue.status = fields[2];
        issue.created = fields[3];
        issue.updated = fields[4];
        issue.assignee = fields[5];
        issue.creator = fields[6];
        issue.description = fields[7];
        issue.summary = fields[8];

        return issue;
    }
}
