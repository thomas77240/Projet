package fr.iutfbleau.bakefile;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.*;

/**
 * Unit tests for the BakeMain class.
 */
class BakeMainTest {

    /**
     * Tests that the debug mode is enabled when the "-d" argument is passed.
     */
    @Test
    void processArguments_enablesDebugMode() {
        String[] args = {"-d"};
        List<String> targetArgs = BakeMain.processArguments(args);
        assertTrue(BakeMain.isDebugMode());
        assertTrue(targetArgs.isEmpty());
    }

    /**
     * Tests that the log file is set up when the "-l" argument is passed.
     */
    @Test
    void processArguments_setsUpLogFile() {
        String[] args = {"-l"};
        List<String> targetArgs = BakeMain.processArguments(args);
        assertTrue(targetArgs.isEmpty());
        File logFile = new File("./logfile.log");
        assertTrue(logFile.exists());
        logFile.delete();
    }

    /**
     * Tests that the target arguments are returned correctly.
     */
    @Test
    void processArguments_returnsTargetArgs() {
        String[] args = {"target1", "target2"};
        List<String> targetArgs = BakeMain.processArguments(args);
        assertEquals(2, targetArgs.size());
        assertTrue(targetArgs.contains("target1"));
        assertTrue(targetArgs.contains("target2"));
    }

    /**
     * Tests that the default target is returned if no arguments are provided.
     */
    @Test
    void determineTargets_returnsDefaultTargetIfNoArgs() {
        List<Rule> rules = Arrays.asList(new Rule("default"));
        List<String> targets = BakeMain.determineTargets(Collections.emptyList(), rules);
        assertEquals(1, targets.size());
        assertEquals("default", targets.get(0));
    }

    /**
     * Tests that the specified targets are returned correctly.
     */
    @Test
    void determineTargets_returnsSpecifiedTargets() {
        List<Rule> rules = Arrays.asList(new Rule("default"));
        List<String> targets = BakeMain.determineTargets(Arrays.asList("target1", "target2"), rules);
        assertEquals(2, targets.size());
        assertTrue(targets.contains("target1"));
        assertTrue(targets.contains("target2"));
    }

    /**
     * Tests that phony rules are removed and their targets are registered.
     */
    @Test
    void removePhonyRules_removesPhonyRulesAndRegistersTargets() {
        Rule phonyRule = new Rule(".PHONY");
        phonyRule.addDependency("phonyTarget");
        List<Rule> rules = new ArrayList<>(Arrays.asList(phonyRule, new Rule("realTarget")));
        BakeMain.removePhonyRules(rules);
        assertEquals(1, rules.size());
        assertEquals("realTarget", rules.get(0).getTarget());
        assertTrue(BakeMain.getPhonyTargets().contains("phonyTarget"));
    }

    /**
     * Tests that circular dependencies are detected.
     */
    @Test
    void buildTarget_detectsCircularDependency() {
        Rule rule1 = new Rule("target1");
        rule1.addDependency("target2");
        Rule rule2 = new Rule("target2");
        rule2.addDependency("target1");
        List<Rule> rules = Arrays.asList(rule1, rule2);
        Set<String> visited = new HashSet<>();
        BakeMain.buildTarget("target1", rules, visited);
        assertTrue(visited.isEmpty());
    }

    /**
     * Tests that a command is executed successfully.
     */
    @Test
    void executeCommand_executesSuccessfully() {
        String command = BakeMain.isWindows() ? "echo Hello" : "echo Hello";
        assertDoesNotThrow(() -> BakeMain.executeCommand(command));
    }

    /**
     * Tests that touched files are registered as generated files.
     */
    @Test
    void inferGeneratedFiles_registersTouchedFiles() {
        Rule rule = new Rule("target");
        BakeMain.inferGeneratedFiles("touch generatedFile", rule);
        assertTrue(rule.getGeneratedFiles().contains("generatedFile"));
    }

    /**
     * Tests that isUpToDate returns false if an output file is missing.
     */
    @Test
    void isUpToDate_returnsFalseIfOutputFileMissing() {
        Rule rule = new Rule("target");
        rule.addGeneratedFile("missingFile");
        List<Rule> rules = Collections.singletonList(rule);
        assertFalse(BakeMain.isUpToDate(rule, rules));
    }

    /**
     * Tests that isUpToDate returns true if all files are up to date.
     */
    @Test
    void isUpToDate_returnsTrueIfAllFilesUpToDate() throws IOException {
        File tempFile = File.createTempFile("output", "file");
        Rule rule = new Rule("target");
        rule.addGeneratedFile(tempFile.getAbsolutePath());
        List<Rule> rules = Collections.singletonList(rule);
        assertTrue(BakeMain.isUpToDate(rule, rules));
        tempFile.delete();
    }

    /**
     * Tests that resolveDependencyTime returns zero if a dependency file is missing.
     */
    @Test
    void resolveDependencyTime_returnsZeroIfDependencyFileMissing() {
        List<Rule> rules = Collections.emptyList();
        long depTime = BakeMain.resolveDependencyTime("missingFile", rules);
        assertEquals(0, depTime);
    }

    /**
     * Tests that resolveDependencyTime returns the last modified time if the file exists.
     */
    @Test
    void resolveDependencyTime_returnsLastModifiedTimeIfFileExists() throws IOException {
        File tempFile = File.createTempFile("dependency", "file");
        List<Rule> rules = Collections.emptyList();
        long depTime = BakeMain.resolveDependencyTime(tempFile.getAbsolutePath(), rules);
        assertEquals(tempFile.lastModified(), depTime);
        tempFile.delete();
    }
}