package com.aszoke.assignment.issuesubmitter.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.aszoke.assignment.issuesubmitter.util.Logger.logError;
import static java.util.Objects.requireNonNull;

public class DefaultCsvReader implements CsvReader {

    private final String filename;

    public DefaultCsvReader(final String filename) {
        requireNonNull(filename);

        this.filename = filename;
    }

    @Override
    public List<String> readLines() {
        List<String> lines = new ArrayList<>();
        // TODO aszoke File access is test-unfriendly, should probably pass a Reader or Stream
        try (Scanner scanner = new Scanner(getClass().getResourceAsStream(filename))) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (Exception e) {
            logError("Error while reading file: " + filename);
            throw e;
        }
        return lines;
    }
}

