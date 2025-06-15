package org.hungry.compare;

import lombok.AllArgsConstructor;
import org.hungry.constant.ANSIBackgroundColour;
import org.hungry.constant.ANSIColour;
import org.hungry.constant.Text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

@AllArgsConstructor
public class DualCompare {

    private final String file1;
    private final String file2;
    private final SortedMap<String, Integer> linesWithOccurrence = new TreeMap<>();

    private static final String LINE_FORMAT = "%s [+%s]: [%s]";

    public List<String> compare() {
        parseFile(file1, 1);
        parseFile(file2, -1);

        printResult();
        return parseResultToList();
    }

    private void parseFile(String path, int modifier) {
        try {
            List<String> allLines = Files.readAllLines(Paths.get(path));

            for (String line : allLines) {
                int newLineOccurrence = linesWithOccurrence.getOrDefault(line, 0) + modifier;

                if (newLineOccurrence == 0)
                    linesWithOccurrence.remove(line);
                else
                    linesWithOccurrence.put(line, newLineOccurrence);
            }
        } catch(IOException ignored) {}
    }

    private void printResult() {
        StringBuilder screenOutputBuilder = new StringBuilder();

        linesWithOccurrence.forEach((line, occurrence) -> {
            if (occurrence > 0) {
                screenOutputBuilder.append(getColourFormattedLine(ANSIColour.BLACK, ANSIBackgroundColour.YELLOW,
                        Text.ORIGIN, occurrence, line));

            } else if (occurrence < 0) {
                screenOutputBuilder.append(getColourFormattedLine(ANSIColour.RED, ANSIBackgroundColour.CYAN,
                        Text.TARGET, occurrence, line));
            }
        });

        System.out.println(screenOutputBuilder);
    }

    static String getColourFormattedLine(String foregroundColour, String backGroundColour,
                                         String direction, int occurrences, String line) {

        return new StringBuilder(String.format("%s%s", foregroundColour, backGroundColour))
                .append(String.format(LINE_FORMAT, direction, occurrences, line))
                .append(ANSIColour.DEFAULT)
                .append("\n").toString();
    }

    private List<String> parseResultToList() {
        List<String> resultList = new LinkedList<>();

        linesWithOccurrence.forEach((line, occurrence) -> {
            if (occurrence > 0) {
                resultList.add(String.format(LINE_FORMAT, Text.ORIGIN, occurrence, line));

            } else if (occurrence < 0) {
                resultList.add(String.format(LINE_FORMAT, Text.TARGET, -occurrence, line));
            }
        });

        return resultList;
    }
}