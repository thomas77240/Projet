package fr.iutfbleau.bakefile;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a rule in the Bakefile.
 */
public class Rule {
    private String target;
    private List<String> dependencies;
    private List<String> commands;

    // List to store dynamically generated files
    private List<String> generatedFiles = new ArrayList<>();

    /**
     * Adds a generated file to the list if it is not already present.
     *
     * @param file The name of the generated file to add
     */
    public void addGeneratedFile(String file) {
        if (!generatedFiles.contains(file)) {
            generatedFiles.add(file);
        }
    }

    /**
     * Gets the list of generated files.
     *
     * @return The list of generated files
     */
    public List<String> getGeneratedFiles() {
        return generatedFiles;
    }

    /**
     * Constructs a Rule with the specified target.
     *
     * @param target The target of the rule
     */
    public Rule(String target) {
        this.target = target;
        this.dependencies = new ArrayList<>();
        this.commands = new ArrayList<>();
    }

    /**
     * Gets the target of the rule.
     *
     * @return The target of the rule
     */
    public String getTarget() {
        return target;
    }

    /**
     * Gets the list of dependencies for the rule.
     *
     * @return The list of dependencies
     */
    public List<String> getDependencies() {
        return dependencies;
    }

    /**
     * Gets the list of commands for the rule.
     *
     * @return The list of commands
     */
    public List<String> getCommands() {
        return commands;
    }

    /**
     * Adds a dependency to the rule.
     *
     * @param dep The dependency to add
     */
    public void addDependency(String dep) {
        dependencies.add(dep);
    }

    /**
     * Adds a command to the rule.
     *
     * @param command The command to add
     */
    public void addCommand(String command) {
        commands.add(command);
    }
}