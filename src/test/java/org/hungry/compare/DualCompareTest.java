package org.hungry.compare;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DualCompareTest {

    private static final String ORIGIN_PATH = "src/test/resources/dual/origin.csv";
    private static final String TARGET_PATH = "src/test/resources/dual/target.csv";

    @Test
    void sanityCheck() {
        assertTrue(Boolean.TRUE);
    }

    @ParameterizedTest
    @CsvSource({
            "'\u001B[30m','\u001B[40m',TARGET,2,May the force be with you,'\u001B[30m\u001B[40mTARGET [+2]: [May the force be with you]\u001B[0m\n'",
            "'\u001B[36m','\u001B[42m',ORIGIN,43,Tha i fliuch an-drasta,'\u001B[36m\u001B[42mORIGIN [+43]: [Tha i fliuch an-drasta]\u001B[0m\n'"
    })
    void should_GetColourFormattedLine(String foregroundColour, String backgroundColour,
                                       String direction, int occurrences, String line, String expected) {
        // When
        String actual = DualCompare.getColourFormattedLine(foregroundColour, backgroundColour, direction, occurrences, line);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void should_Process() {
        // Given
        DualCompare dualComparator = new DualCompare(ORIGIN_PATH, TARGET_PATH);

        // When
        List<String> actual = dualComparator.compare();

        // Then
        assertTrue(actual.contains("TARGET [+1]: [And, oh! The latter block of poetry is missing!]"));
        assertTrue(actual.contains("ORIGIN [+1]: [did gyre and gimble in the wabe.]"));
    }
}