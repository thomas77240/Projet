package fr.iutfbleau.bakefile;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Unit tests for the Rule class.
 */
class RuleTest {

    /**
     * Tests that addGeneratedFile adds a file if it is not already present.
     */
    @Test
    void addGeneratedFile_addsFileIfNotPresent() {
        Rule rule = new Rule("target");
        rule.addGeneratedFile("file1");
        List<String> generatedFiles = rule.getGeneratedFiles();
        assertEquals(1, generatedFiles.size());
        assertTrue(generatedFiles.contains("file1"));
    }

    /**
     * Tests that addGeneratedFile does not add a duplicate file.
     */
    @Test
    void addGeneratedFile_doesNotAddDuplicateFile() {
        Rule rule = new Rule("target");
        rule.addGeneratedFile("file1");
        rule.addGeneratedFile("file1");
        List<String> generatedFiles = rule.getGeneratedFiles();
        assertEquals(1, generatedFiles.size());
    }

    /**
     * Tests that getGeneratedFiles returns an empty list if no files have been added.
     */
    @Test
    void getGeneratedFiles_returnsEmptyListIfNoFilesAdded() {
        Rule rule = new Rule("target");
        List<String> generatedFiles = rule.getGeneratedFiles();
        assertTrue(generatedFiles.isEmpty());
    }

    /**
     * Tests that getTarget returns the correct target.
     */
    @Test
    void getTarget_returnsCorrectTarget() {
        Rule rule = new Rule("target");
        assertEquals("target", rule.getTarget());
    }

    /**
     * Tests that getDependencies returns an empty list if no dependencies have been added.
     */
    @Test
    void getDependencies_returnsEmptyListIfNoDependenciesAdded() {
        Rule rule = new Rule("target");
        List<String> dependencies = rule.getDependencies();
        assertTrue(dependencies.isEmpty());
    }

    /**
     * Tests that addDependency adds a dependency to the list.
     */
    @Test
    void addDependency_addsDependencyToList() {
        Rule rule = new Rule("target");
        rule.addDependency("dependency1");
        List<String> dependencies = rule.getDependencies();
        assertEquals(1, dependencies.size());
        assertTrue(dependencies.contains("dependency1"));
    }

    /**
     * Tests that getCommands returns an empty list if no commands have been added.
     */
    @Test
    void getCommands_returnsEmptyListIfNoCommandsAdded() {
        Rule rule = new Rule("target");
        List<String> commands = rule.getCommands();
        assertTrue(commands.isEmpty());
    }

    /**
     * Tests that addCommand adds a command to the list.
     */
    @Test
    void addCommand_addsCommandToList() {
        Rule rule = new Rule("target");
        rule.addCommand("command1");
        List<String> commands = rule.getCommands();
        assertEquals(1, commands.size());
        assertTrue(commands.contains("command1"));
    }
}