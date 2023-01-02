import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Main {
    public static void main(String[] args) throws IOException {
        // Read input from file
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\fuatw\\IdeaProjects\\_410p3\\src\\Input_ALIFUAT_OZTURK_S015467.txt"));

        // Parse input alphabet
        int numInputSymbols = Integer.parseInt(reader.readLine());
        List<String> inputAlphabet = new ArrayList<>();
        for (int i = 0; i < numInputSymbols; i++) {
            inputAlphabet.add(reader.readLine());
        }

        // Parse tape alphabet
        int numTapeSymbols = Integer.parseInt(reader.readLine());
        List<String> tapeAlphabet = new ArrayList<>();
        String ss= reader.readLine();
        for (int i = 0; i < numTapeSymbols; i++) {
            tapeAlphabet.add(String.valueOf(ss.charAt(i)));
        }

        // Parse blank symbol
        String blankSymbol = reader.readLine();

        // Parse states
        int numStates = 15;
        List<State> states = new ArrayList<>();
        String[] stateNames = reader.readLine().split(" ");
        for (String stateName : stateNames) {
            states.add(new State(stateName));
        }

        // Parse start, accept, and reject states
        State startState = null;
        State acceptState = null;
        State rejectState = null;
        for (State state : states) {
            if (state.getName().equals(reader.readLine())) {
                startState = state;
            }
            if (state.getName().equals(reader.readLine())) {
                acceptState = state;
            }
            if (state.getName().equals(reader.readLine())) {
                rejectState = state;
            }
        }

        // Parse transitions
        List<Transition> transitions = new ArrayList<>();
        int n=0;
        ;
        while (n<15) {
            String line= reader.readLine();
            String[] parts = line.split(" ");
            String currentState = parts[0];
            String inputSymbol = parts[1];
            String tapeSymbol = parts[2];
            String direction = parts[3];
            String nextState = parts[4];
            transitions.add(new Transition(currentState, inputSymbol, tapeSymbol, direction, nextState));
            n++;
        }
        String inputString= reader.readLine();
        reader.close();

        // Create Turing machine
        TuringMachine tm = new TuringMachine(inputAlphabet, tapeAlphabet, blankSymbol, states, startState, acceptState, rejectState, transitions);

        // Run Turing machine on input string
        runTuringMachine(tm, inputString);
    }

    private static void runTuringMachine(TuringMachine tm, String inputString) {
        // Initialize tape with input string and pad with blank symbols
        StringBuilder tape = new StringBuilder();
        for (int i = 0; i < inputString.length(); i++) {
            tape.append(inputString.charAt(i));
        }
        tape.append(tm.getBlankSymbol());

        // Initialize tape head and current state
        int tapeHead = 0;
        State currentState = tm.getStartState();

        // Initialize list of visited states
        List<State> visitedStates = new ArrayList<>();
        visitedStates.add(currentState);

        // Keep track of whether the Turing machine has halted
        boolean halted = false;

        // Run the Turing machine until it halts
        while (!halted) {
            // Get the input symbol at the current position on the tape
            String inputSymbol = String.valueOf(tape.charAt(tapeHead));

            // Find a transition that matches the current state and input symbol
            Transition transition = null;
            for (Transition t : tm.getTransitions()) {
                if (t.getCurrentState().equals(currentState.getName()) && t.getInputSymbol().equals(inputSymbol)) {
                    transition = t;
                    break;
                }
            }

            // If no matching transition was found, halt the Turing machine
            if (transition == null) {
                halted = true;
                continue;
            }

            // Update the tape with the tape symbol from the transition
            tape.setCharAt(tapeHead, transition.getTapeSymbol().charAt(0));

            // Update the tape head position
            if (transition.getDirection().equals("L")) {
                tapeHead--;
            } else if (transition.getDirection().equals("R")) {
                tapeHead++;
            }

            // Update the current state
            for (State state : tm.getStates()) {
                if (state.getName().equals(transition.getNextState())) {
                    currentState = state;
                    break;
                }
            }
            visitedStates.add(currentState);

            // Check if the Turing machine has reached the accept or reject state
            if (currentState.equals(tm.getAcceptState())) {
                halted = true;
                printResult(visitedStates, "accepted");
            } else if (currentState.equals(tm.getRejectState())) {
                halted = true;
                printResult(visitedStates, "rejected");
            }
        }
    }
    private static void printResult(List<State> visitedStates, String result) {
        // Print the list of visited states
        System.out.print("ROUT: ");
        for (int i = 0; i < visitedStates.size() - 1; i++) {
            System.out.print(visitedStates.get(i).getName() + " ");
        }
        System.out.println(visitedStates.get(visitedStates.size() - 1).getName());

        // Print the result
        System.out.println("RESULT: " + result);
    }


}
class State {
    private String name;

    public State(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
class TuringMachine {
    private List<String> inputAlphabet;
    private List<String> tapeAlphabet;
    private String blankSymbol;
    private List<State> states;
    private State startState;
    private State acceptState;
    private State rejectState;
    private List<Transition> transitions;

    public TuringMachine(List<String> inputAlphabet, List<String> tapeAlphabet, String blankSymbol, List<State> states, State startState, State acceptState, State rejectState, List<Transition> transitions) {
        this.inputAlphabet = inputAlphabet;
        this.tapeAlphabet = tapeAlphabet;
        this.blankSymbol = blankSymbol;
        this.states = states;
        this.startState = startState;
        this.acceptState = acceptState;
        this.rejectState = rejectState;
        this.transitions = transitions;
    }

    public List<String> getInputAlphabet() {
        return inputAlphabet;
    }

    public List<String> getTapeAlphabet() {
        return tapeAlphabet;
    }

    public String getBlankSymbol() {
        return blankSymbol;
    }

    public List<State> getStates() {
        return states;
    }

    public State getStartState() {
        return startState;
    }

    public State getAcceptState() {
        return acceptState;
    }

    public State getRejectState() {
        return rejectState;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }
}
class Transition {
    private String currentState;
    private String inputSymbol;
    private String tapeSymbol;
    private String direction;
    private String nextState;

    public Transition(String currentState, String inputSymbol, String tapeSymbol, String direction, String nextState) {
        this.currentState = currentState;
        this.inputSymbol = inputSymbol;
        this.tapeSymbol = tapeSymbol;
        this.direction = direction;
        this.nextState = nextState;
    }

    public String getCurrentState() {
        return currentState;
    }

    public String getInputSymbol() {
        return inputSymbol;
    }

    public String getTapeSymbol() {
        return tapeSymbol;
    }

    public String getDirection() {
        return direction;
    }

    public String getNextState() {
        return nextState;
    }
}

