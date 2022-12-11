import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// Class to represent a grammar and convert it to Chomsky normal form
public class CFGToCNFConverter {
    // Set of non-terminals
    private Map<Character, Set<String>> nonTerminals;
    // Set of terminals
    private Set<Character> terminals;
    // Set of rules
    private Map<Character, Set<String>> rules;
    // Start symbol
    private char startSymbol;

    // Function to read the input file
    public void readInput(String inputFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        nonTerminals = new HashMap<>();
        terminals = new HashSet<>();
        rules = new HashMap<>();

        String line;
        boolean readingNonTerminals = false;
        boolean readingTerminals = false;
        boolean readingRules = false;
        while ((line = reader.readLine()) != null) {
            if (line.equals("NON-TERMINAL")) {
                readingNonTerminals = true;
                readingTerminals = false;
                readingRules = false;
            } else if (line.equals("TERMINAL")) {
                readingNonTerminals = false;
                readingTerminals = true;
                readingRules = false;
            } else if (line.equals("RULES")) {
                readingNonTerminals = false;
                readingTerminals = false;
                readingRules = true;
            } else if (line.equals("START")) {
                readingNonTerminals = false;
                readingTerminals = false;
                readingRules = false;
                startSymbol = reader.readLine().charAt(0);
            } else if (readingNonTerminals) {
                char nonTerminal = line.charAt(0);
                nonTerminals.put(nonTerminal, new HashSet<>());
                rules.put(nonTerminal, new HashSet<>()); // add an empty set of rules for the non-terminal
            } else if (readingTerminals) {
                char terminal = line.charAt(0);
                terminals.add(terminal);
            } else if (readingRules) {
                String[] parts = line.split(":");
                char nonTerminal = parts[0].charAt(0);
                String rule = parts[1];
                rules.get(nonTerminal).add(rule);
            }
        }

        reader.close();
    }

    // Function to convert the grammar to Chomsky normal form

    public void convertToChomskyNormalForm() {
        // Step 1: eliminate rules with the empty string (epsilon) on the right
        for (char nonTerminal : nonTerminals.keySet()) {
            Set<String> ruleSet = rules.get(nonTerminal);
            ruleSet.remove("e"); // remove the epsilon rule

            // remove the epsilon rule
        }

        // Step 2: eliminate rules with a single non-terminal on the right
        Map<Character, Set<String>> newRules = new HashMap<>(); // new set of rules
        for (char nonTerminal : nonTerminals.keySet()) {
            Set<String> ruleSet = rules.get(nonTerminal);
            Set<String> newRuleSet = new HashSet<>(); // new set of rules for the non-terminal
            for (String rule : ruleSet) {
                if (rule.length() == 1 && nonTerminals.containsKey(rule.charAt(0))) {
                    // the rule has a single non-terminal on the right-hand side, so we need to replace it
                    // with the rules for that non-terminal
                    Set<String> expandedRuleSet = rules.get(rule.charAt(0));
                    newRuleSet.addAll(expandedRuleSet);
                } else {
                    // the rule is already in the desired form, so we can add it to the new set of rules
                    newRuleSet.add(rule);
                }
            }
            newRules.put(nonTerminal, newRuleSet);
        }
        rules = newRules; // update the set of rules

        // Step 3: eliminate rules with more than two symbols on the right
        newRules = new HashMap<>(); // new set of rules
        int numNewNonTerminals = 0; // counter for the number of new non-terminals we have created
        for (char nonTerminal : nonTerminals.keySet()) {
            Set<String> ruleSet = rules.get(nonTerminal);
            Set<String> newRuleSet = new HashSet<>(); // new set of rules for the non-terminal
            for (String rule : ruleSet) {
                if (rule.length() > 2) {
                    // the rule has more than two symbols on the right-hand side, so we need to split it
                    String[] symbols = splitRule(rule);
                    char newNonTerminal1 = (char) ('A' + numNewNonTerminals++); // create a new non-terminal
                    char newNonTerminal2 = (char) ('A' + numNewNonTerminals++); // create another new non-terminal

                    // add the first new rule to the set of new rules
                    newRuleSet.add(newNonTerminal1 + symbols[0]);
                    // add the second new rule to the set of new rules
                    newRuleSet.add(newNonTerminal2 + symbols[1]);
                    // add the third new rule to the set of new rules
                    Set<String> expandedRuleSet = new HashSet<>();
                    expandedRuleSet.add(newNonTerminal1 + newNonTerminal2 + "");
                    newRules.put(newNonTerminal2, expandedRuleSet);
                } else {
                    // the rule is already in the desired form, so we can add it to the new set of rules
                    newRuleSet.add(rule);
                }
            }
            newRules.put(nonTerminal, newRuleSet);
        }
        rules = newRules; // update the set of rules
    }

    // Function to split a rule with more than two symbols on the right-hand side into two separate rules
    private String[] splitRule(String rule) {
        String[] symbols = new String[2];
        symbols[0] = rule.substring(0, rule.length() / 2);
        symbols[1] = rule.substring(rule.length() / 2);
        return symbols;
    }

    public void printChomskyNormalForm() {
        // print the set of non-terminals
        System.out.println("NON-TERMINAL");
        for (char nonTerminal : nonTerminals.keySet()) {
            System.out.println(nonTerminal);
        }

        // print the set of terminals
        System.out.println("TERMINAL");
        for (char terminal : terminals) {
            System.out.println(terminal);
        }

        // print the set of rules
        System.out.println("RULES");
        for (char nonTerminal : nonTerminals.keySet()) {
            Set<String> ruleSet = rules.get(nonTerminal);
            for (String rule : ruleSet) {
                System.out.println(nonTerminal + ":" + rule);
            }
        }

        // print the start symbol
        System.out.println("START");
        System.out.println(startSymbol);
    }

    // Main function
    public static void main(String[] args) throws IOException {
        CFGToCNFConverter cnf = new CFGToCNFConverter();
        cnf.readInput("C:\\Users\\fuatw\\IdeaProjects\\_410proj2\\src\\G1.txt"); // read the input file
        cnf.convertToChomskyNormalForm(); // convert the grammar to Chomsky normal form
        cnf.printChomskyNormalForm(); // print the resulting grammar
    }
}


