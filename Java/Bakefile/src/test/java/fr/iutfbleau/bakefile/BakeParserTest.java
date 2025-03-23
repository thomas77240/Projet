package fr.iutfbleau.bakefile;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.*;

/**
 * Unit tests for the BakeParser class.
 */
class BakeParserTest {

    /**
     * Tests that the parse method returns an empty list if the bakefile is empty.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    void parse_returnsEmptyListIfBakefileIsEmpty() throws IOException {
        File emptyFile = File.createTempFile("empty", "bakefile");
        BakeParser parser = new BakeParser(emptyFile.getAbsolutePath());
        List<Rule> rules = parser.parse();
        assertTrue(rules.isEmpty());
        emptyFile.delete();
    }

    /**
     * Tests that the parse method correctly handles continued lines in the bakefile.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    void parse_handlesContinuedLines() throws IOException {
        File tempFile = File.createTempFile("continuedLines", "bakefile");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("target: dep1 \\\n dep2\n\tcommand");
        }
        BakeParser parser = new BakeParser(tempFile.getAbsolutePath());
        List<Rule> rules = parser.parse();
        assertEquals(1, rules.size());
        Rule rule = rules.get(0);
        assertEquals("target", rule.getTarget());
        assertEquals(2, rule.getDependencies().size());
        assertTrue(rule.getDependencies().contains("dep1"));
        assertTrue(rule.getDependencies().contains("dep2"));
        assertEquals(1, rule.getCommands().size());
        assertEquals("command", rule.getCommands().get(0));
        tempFile.delete();
    }
}