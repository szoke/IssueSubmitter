package com.aszoke.assignment.issuesubmitter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.aszoke.assignment.issuesubmitter.Logger.logError;

public class DefaultCsvReader implements CsvReader {

    private final String filename;

    public DefaultCsvReader(final String filename) {
        this.filename = filename;
    }

    @Override
    public List<String> readLines() {
        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename));) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            logError("File not found: " + filename);
            throw new RuntimeException(e);
        }
        return lines;
    }
}

