package fr.iutfbleau.bakefile;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * Parameterized tests for the BakeParser class.
 */
class BakeParserParameterizedTest {

    /**
     * Tests that the BakeParser correctly parses a rule from the provided bakefile content.
     *
     * @param bakefileContent the content of the bakefile to parse
     * @param expectedTarget the expected target of the parsed rule
     * @param expectedDependencies the expected dependencies of the parsed rule
     * @param expectedCommands the expected commands of the parsed rule
     * @throws IOException if an I/O error occurs
     */
    @ParameterizedTest
    @MethodSource("parseTestCases")
    void parse_parsesRuleCorrectly(String bakefileContent,
                                   String expectedTarget,
                                   List<String> expectedDependencies,
                                   List<String> expectedCommands) throws IOException {
        // Create a temporary file with the provided content.
        File tempFile = File.createTempFile("tempBakefile", ".bakefile");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write(bakefileContent);
        }
        // Parse the temporary file.
        BakeParser parser = new BakeParser(tempFile.getAbsolutePath());
        List<Rule> rules = parser.parse();
        // Verify that exactly one rule was parsed.
        assertEquals(1, rules.size(), "Expected exactly one rule to be parsed");
        Rule rule = rules.get(0);
        assertEquals(expectedTarget, rule.getTarget(), "Target did not match");
        assertEquals(expectedDependencies, rule.getDependencies(), "Dependencies did not match");
        assertEquals(expectedCommands, rule.getCommands(), "Commands did not match");
        tempFile.delete();
    }

    /**
     * Provides test cases for the parse\_parsesRuleCorrectly test.
     *
     * @return a stream of arguments for the parameterized test
     */
    static Stream<Arguments> parseTestCases() {
        return Stream.of(
                // Single rule without variable substitution.
                Arguments.of("target: dependency\n\tcommand",
                        "target",
                        Arrays.asList("dependency"),
                        Arrays.asList("command")),
                // Rule with variable substitution.
                Arguments.of("VAR=value\ntarget: $(VAR)\n\tcommand",
                        "target",
                        Arrays.asList("value"),
                        Arrays.asList("command")),
                // Rule ignoring a comment line.
                Arguments.of("# This is a comment\ntarget: dependency\n\tcommand",
                        "target",
                        Arrays.asList("dependency"),
                        Arrays.asList("command"))
        );
    }
}