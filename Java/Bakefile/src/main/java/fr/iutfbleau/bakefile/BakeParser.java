package fr.iutfbleau.bakefile;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses the Bakefile into a list of Rule objects.
 */
public class BakeParser {

    static Logger logger = Logger.getLogger(BakeParser.class.getName());
    private File bakefile;
    private Map<String, String> variables;

    /**
     * Constructs a BakeParser with the specified filename.
     *
     * @param filename The name of the Bakefile to parse
     */
    public BakeParser(String filename) {
        try {
            this.bakefile = new File(filename);
            this.variables = new HashMap<>();
        } catch (NullPointerException e) {
            logger.warning("BakeParser: cannot find file " + filename);
            System.exit(-1);
        }
    }

    /**
     * Parses the Bakefile and returns a list of Rule objects.
     *
     * @return A list of Rule objects parsed from the Bakefile
     * @throws IOException If an I/O error occurs while reading the Bakefile
     */
    public List<Rule> parse() throws IOException {
        List<Rule> rules = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(bakefile))) {
            String line;
            StringBuilder continuedLine = new StringBuilder();
            while ((line = br.readLine()) != null) {
                line = line.trim();
                boolean shouldProcess = true;
                if (line.endsWith("\\")) {
                    continuedLine.append(line.substring(0, line.length() - 1));
                    shouldProcess = false;
                } else if (continuedLine.length() > 0) {
                    continuedLine.append(line);
                    line = continuedLine.toString();
                    continuedLine.setLength(0);
                }
                if (shouldProcess && (line.isEmpty() || line.startsWith("#"))) {
                    shouldProcess = false;
                }
                if (shouldProcess) {
                    if (line.startsWith("\t") || !line.contains("=")) {
                        processRuleLineAndCommands(line, br, rules);
                    } else {
                        processVariableAssignment(line);
                    }
                }
            }
        }
        return rules;
    }

    /**
     * Processes a variable assignment line and updates the variables map.
     *
     * @param line The line containing the variable assignment
     */
    private void processVariableAssignment(String line) {
        int eqIndex = line.indexOf("=");
        String varName = line.substring(0, eqIndex).trim();
        String varValue = line.substring(eqIndex + 1).trim();
        // Update the variable map
        variables.put(varName, varValue);
    }

    /**
     * Processes a rule line and its associated commands.
     *
     * @param line The rule line to process
     * @param br The BufferedReader to read commands from
     * @param rules The list of rules to add the parsed rule to
     * @throws IOException If an I/O error occurs while reading commands
     */
    private void processRuleLineAndCommands(String line, BufferedReader br, List<Rule> rules) throws IOException {
        // Perform variable substitution on the rule line.
        line = substituteVariables(line);
        List<Rule> newRules = parseRuleLine(line);
        if (!newRules.isEmpty()) {
            List<String> commands = readCommands(br);
            for (Rule r : newRules) {
                r.getCommands().addAll(commands);
                rules.add(r);
            }
        }
    }

    /**
     * Parses a rule line into a list of Rule objects.
     *
     * @param line The rule line to parse
     * @return A list of Rule objects parsed from the line
     */
    private List<Rule> parseRuleLine(String line) {
        int colonIndex = line.indexOf(":");
        if (colonIndex < 0) {
            return Collections.emptyList();
        }
        String targetsPart = line.substring(0, colonIndex).trim();
        String depsPart = line.substring(colonIndex + 1).trim();
        String[] targets = targetsPart.split("\\s+");
        List<String> dependencies = new ArrayList<>();
        if (!depsPart.isEmpty()) {
            String[] deps = depsPart.split("\\s+");
            dependencies.addAll(Arrays.asList(deps));
        }
        List<Rule> newRules = new ArrayList<>();
        for (String target : targets) {
            Rule rule = new Rule(target);
            rule.getDependencies().addAll(dependencies);
            newRules.add(rule);
        }
        return newRules;
    }

    /**
     * Reads commands from the BufferedReader.
     *
     * @param br The BufferedReader to read commands from
     * @return A list of commands read from the BufferedReader
     * @throws IOException If an I/O error occurs while reading commands
     */
    private List<String> readCommands(BufferedReader br) throws IOException {
        List<String> commands = new ArrayList<>();
        br.mark(1000);
        String line;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("\t")) {
                String commandLine = substituteVariables(line.trim());
                commands.add(commandLine);
                br.mark(1000);
            } else {
                br.reset();
                break;
            }
        }
        return commands;
    }

    /**
     * Substitutes occurrences of variables in the format $(VAR) with their values.
     *
     * @param line The line in which to substitute variables
     * @return The line with variables substituted
     */
    private String substituteVariables(String line) {
        Pattern pattern = Pattern.compile("\\$\\((\\w+)\\)");
        Matcher matcher = pattern.matcher(line);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String varName = matcher.group(1);
            String value = variables.getOrDefault(varName, "");
            matcher.appendReplacement(sb, Matcher.quoteReplacement(value));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}