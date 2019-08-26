package com.aszoke.assignment.issuesubmitter.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    @Override
    public String toString() {
        return String.format("{" +
                        " \"key\": \"%s\"," +
                        " \"id\": \"%s\"," +
                        " \"status\": \"%s\"," +
                        " \"created\": \"%s\"," +
                        " \"updated\": \"%s\"," +
                        " \"assignee\": \"%s\"," +
                        " \"creator\": \"%s\"," +
                        " \"description\": \"%s\"," +
                        " \"summary\": \"%s\"" +
                        "}",
                key,
                id,
                status,
                created,
                updated,
                assignee,
                created,
                description,
                summary);
    }
}
