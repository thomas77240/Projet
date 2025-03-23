package fr.iutfbleau.bakefile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.*;
import java.util.stream.Collectors;

/**
 * Main class for the Bakefile build tool.
 */
public class BakeMain {

    private static final Logger logger = Logger.getLogger(BakeMain.class.getName());
    private static final boolean IS_WINDOWS = System.getProperty("os.name").toLowerCase().startsWith("windows");
    private static final String PHONY = ".PHONY";
    private static boolean debugMode = false;
    private static Set<String> phonyTargets = new HashSet<>();

    /**
     * Main method to start the Bakefile build process.
     *
     * @param args Command line arguments
     * @throws IOException If an I/O error occurs
     */
    public static void main(String[] args) throws IOException {
        logger.setLevel(Level.WARNING);
        List<String> targetArgs = processArguments(args);

        if (debugMode) {
            logger.setLevel(Level.FINE);
            logger.fine("Debug mode activated");
        }

        // Parse the Bakefile using the new parser
        BakeParser parser = new BakeParser("Bakefile");
        List<Rule> rules = parser.parse();
        if (rules.isEmpty()) {
            logger.severe("No rules found in Bakefile.");
            System.exit(1);
        }

        // Determination of targets to be built
        List<String> targetsToBuild = determineTargets(targetArgs, rules);
        removePhonyRules(rules);

        // Build each target with dependency resolution
        Set<String> visited = new HashSet<>();
        for (String target : targetsToBuild) {
            buildTarget(target, rules, visited);
        }
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    /**
     * Processes command line arguments.
     *
     * @param args Command line arguments
     * @return List of target arguments
     */
    public static List<String> processArguments(String[] args) {
        List<String> targetArgs = new ArrayList<>();
        for (String arg : args) {
            if ("-d".equals(arg)) {
                debugMode = true;
            } else if ("-l".equals(arg)) {
                setupLogFile();
            } else {
                targetArgs.add(arg);
            }
        }
        return targetArgs;
    }

    /**
     * Sets up the log file for logging.
     */
    private static void setupLogFile() {
        try {
            Path logFilePath = Paths.get("./logfile.log");
            if (Files.exists(logFilePath)) {
                Files.delete(logFilePath);
            }
            Handler fileHandler = new FileHandler("./logfile.log");
            SimpleFormatter simple = new SimpleFormatter();
            fileHandler.setFormatter(simple);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            logger.warning("Problem when creating the log file: " + e.getMessage());
        }
    }

    /**
     * Determines the targets to be built based on the provided arguments and rules.
     *
     * @param targetArgs List of target arguments
     * @param rules List of rules parsed from the Bakefile
     * @return List of targets to be built
     */
    public static List<String> determineTargets(List<String> targetArgs, List<Rule> rules) {
        List<String> targetsToBuild = new ArrayList<>();
        if (targetArgs.isEmpty()) {
            String defaultTarget = rules.stream()
                    .filter(rule -> !rule.getTarget().equals(PHONY))
                    .map(Rule::getTarget)
                    .findFirst()
                    .orElse(null);
            if (defaultTarget == null) {
                logger.log(Level.SEVERE, "No valid default target found.");
                System.exit(1);
            }
            targetsToBuild.add(defaultTarget);
            logger.log(Level.INFO, "No target specified, using default target: {0}", defaultTarget);
        } else {
            targetsToBuild.addAll(targetArgs);
        }
        targetsToBuild.remove(PHONY);
        return targetsToBuild;
    }

    /**
     * Removes phony rules from the list of rules and registers phony targets.
     *
     * @param rules List of rules parsed from the Bakefile
     */
    public static void removePhonyRules(List<Rule> rules) {
        Iterator<Rule> it = rules.iterator();
        while (it.hasNext()) {
            Rule r = it.next();
            if (r.getTarget().equals(PHONY)) {
                phonyTargets.addAll(r.getDependencies());
                logger.fine("Registered phony targets: " + r.getDependencies());
                it.remove();
            }
        }
    }

    /**
     * Returns the set of registered phony targets.
     *
     * @return Set of phony targets.
     */
    public static Set<String> getPhonyTargets() {
        return phonyTargets;
    }

    /**
     * Performs the commands of a rule and dynamically infers the generated files.
     *
     * @param rule The rule whose orders must be executed.
     */
    public static void executeCommands(Rule rule) {
        for (String cmd : rule.getCommands()) {
            executeCommand(cmd);
            inferGeneratedFiles(cmd, rule);
        }
    }

    /**
     * Recursively builds the target by first building its dependencies.
     * Detects circular dependencies.
     *
     * @param target  The target to build
     * @param rules   The list of rules
     * @param visited The set of visited targets to detect circular dependencies
     */
    public static void buildTarget(String target, List<Rule> rules, Set<String> visited) {
        if (visited.contains(target)) {
            logger.log(Level.INFO, "Circular dependency detected for target: {0}", target);
            return;
        }
        visited.add(target);
        Rule rule = findRuleForTarget(rules, target);
        if (rule == null) {
            logMissingRule(target);
        } else {
            buildDependencies(rule, rules, visited);
            executeTarget(target, rule, rules);
        }
        visited.remove(target);
    }

    /**
     * Logs a warning if a rule for the target is missing.
     *
     * @param target The target for which the rule is missing
     */
    private static void logMissingRule(String target) {
        File file = new File(target);
        if (file.exists()) {
            logger.log(Level.WARNING, "No rule for target {0} but file exists.", target);
        } else {
            logger.log(Level.WARNING, "Target file {0} does not exist and no rule found.", target);
        }
    }

    public static boolean isWindows() {
        return IS_WINDOWS;
    }

    /**
     * Builds the dependencies of a rule.
     *
     * @param rule The rule whose dependencies need to be built
     * @param rules The list of rules
     * @param visited The set of visited targets to detect circular dependencies
     */
    private static void buildDependencies(Rule rule, List<Rule> rules, Set<String> visited) {
        for (String dep : rule.getDependencies()) {
            if (findRuleForTarget(rules, dep) != null || phonyTargets.contains(dep)) {
                buildTarget(dep, rules, visited);
            }
        }
    }

    /**
     * Executes the target if it is not up-to-date.
     *
     * @param target The target to execute
     * @param rule The rule corresponding to the target
     * @param rules The list of rules
     */
    private static void executeTarget(String target, Rule rule, List<Rule> rules) {
        if (phonyTargets.contains(target)) {
            logger.log(Level.INFO, "Phony target, always execute: {0}", target);
            executeCommands(rule);
        } else {
            boolean upToDate = isUpToDate(rule, rules);
            logger.log(Level.INFO, upToDate ? "Target is up-to-date: {0}" : "Building target: {0}", target);
            if (!upToDate) {
                executeCommands(rule);
            }
        }
    }

    /**
     * Finds a rule corresponding to the given target.
     *
     * @param rules  The list of rules
     * @param target The target to find the rule for
     * @return The rule corresponding to the target, or null if not found
     */
    private static Rule findRuleForTarget(List<Rule> rules, String target) {
        for (Rule rule : rules) {
            if (rule.getTarget().equals(target)) {
                return rule;
            }
        }
        return null;
    }

    /**
     * Checks if the target is up-to-date compared to its generated files.
     *
     * @param rule The rule for the target
     * @param rules The list of rules
     * @return True if the target is up-to-date, false otherwise
     */
    public static boolean isUpToDate(Rule rule, List<Rule> rules) {
        List<File> outputs = resolveOutputs(rule);
        if (outputs.stream().anyMatch(file -> !file.exists())) {
            return false;
        }
        long oldestOutputTime = outputs.stream().mapToLong(File::lastModified).min().orElse(Long.MAX_VALUE);
        for (String dep : rule.getDependencies()) {
            long depTime = resolveDependencyTime(dep, rules);
            if (depTime > oldestOutputTime) {
                return false;
            }
        }
        return true;
    }

    /**
     * Resolves the output files for a rule.
     *
     * @param rule The rule to resolve outputs for
     * @return List of output files
     */
    private static List<File> resolveOutputs(Rule rule) {
        if (!rule.getGeneratedFiles().isEmpty()) {
            return rule.getGeneratedFiles().stream().map(File::new).collect(Collectors.toList());
        } else {
            File candidate = new File(rule.getTarget() + ".out");
            return candidate.exists() ? Collections.singletonList(candidate) : Collections.singletonList(new File(rule.getTarget()));
        }
    }

    /**
     * Resolves the last modified time of a dependency.
     *
     * @param dep The dependency to resolve
     * @param rules The list of rules
     * @return The last modified time of the dependency
     */
    public static long resolveDependencyTime(String dep, List<Rule> rules) {
        Rule depRule = findRuleForTarget(rules, dep);
        List<File> depOutputs;
        if (depRule != null) {
            depOutputs = !depRule.getGeneratedFiles().isEmpty()
                    ? depRule.getGeneratedFiles().stream().map(File::new).collect(Collectors.toList())
                    : resolveOutputs(depRule);
            // If any dependency output is missing, force a rebuild by returning a very high timestamp.
            if (depOutputs.stream().anyMatch(file -> !file.exists())) {
                return Long.MAX_VALUE;
            }
            return depOutputs.stream().mapToLong(File::lastModified).max().orElse(0);
        } else {
            File depFile = new File(dep);
            return depFile.exists() ? depFile.lastModified() : 0;
        }
    }

    /**
     * Executes a command line using ProcessBuilder.
     *
     * @param commandLine The command line to execute
     */
    public static void executeCommand(String commandLine) {
        if (commandLine.startsWith("@")) {
            commandLine = commandLine.substring(1).trim();
        }

        ProcessBuilder pb = IS_WINDOWS
                ? new ProcessBuilder("cmd.exe", "/c", commandLine)
                : new ProcessBuilder("sh", "-c", commandLine);
        pb.directory(new File(System.getProperty("user.dir")));
        try {
            logger.log(Level.INFO, "Executing command: {0}", commandLine);
            if (!debugMode) {
                logger.log(Level.INFO, "{0}", commandLine);
            }
            Process process = pb.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String output = reader.lines().collect(Collectors.joining("\n"));
                logger.log(Level.INFO, "{0}", output);
                if (!debugMode) {
                    logger.log(Level.INFO, "{0}", output);
                }
            }
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String errors = errorReader.lines().collect(Collectors.joining("\n"));
                if (!errors.isEmpty()) {
                    logger.log(Level.WARNING, "{0}", errors);
                }
            }
            process.waitFor();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error executing command: {0}", e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.log(Level.WARNING, "Execution interrupted: {0}", e.getMessage());
        }
    }

    /**
     * Infers generated files from a command (e.g., 'touch' command) and registers them as outputs.
     *
     * @param command The executed command.
     * @param rule    The rule from which this command belongs.
     */
    public static void inferGeneratedFiles(String command, Rule rule) {
        if (command.startsWith("touch")) {
            String[] parts = command.split("\\s+");
            if (parts.length > 1) {
                String generatedFile = parts[1].trim();
                rule.addGeneratedFile(generatedFile);
            }
        }
    }
}
