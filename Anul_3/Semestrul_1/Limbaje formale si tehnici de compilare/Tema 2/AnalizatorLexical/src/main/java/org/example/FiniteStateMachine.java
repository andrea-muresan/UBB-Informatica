package org.example;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FiniteStateMachine {
    private final State startState;
    private final List<State> states;
    private final List<String> alphabet;
    private final List<State> finalStates;
    private final Map<State, List<Pair<String, State>>> transitions;

    public FiniteStateMachine(State startState, List<State> states, List<String> alphabet,
                              List<State> finalStates, Map<State, List<Pair<String, State>>> transitions) {
        this.startState = startState;
        this.states = states;
        this.alphabet = alphabet;
        this.finalStates = finalStates;
        this.transitions = transitions;
    }
    /**
     * Checks if a string is accepted or not by this state machine
     */
    public boolean isAccepted(String seq) {
        State currentState = startState;
        for (char ch : seq.toCharArray()) {
            List<Pair<String, State>> trans = transitions.get(currentState);
            if (trans == null) {
                return false;
            }
            Optional<Pair<String, State>> pair = trans.stream()
                    .filter(p -> p.getFirst().equals(String.valueOf(ch)))
                    .findFirst();
            if (!pair.isPresent()) {
                return false;
            }
            currentState = pair.get().getSecond();
        }
        return finalStates.contains(currentState);
    }

    /**
     * Calculates the longest prefix accepted by this state machine
     */
    public String longestPrefix(String seq) {
        String prefix = "";
        String crtPrefix = "";
        State currentState = startState;
        for (char ch : seq.toCharArray()) {
            List<Pair<String, State>> trans = transitions.get(currentState);
            if (trans == null) {
                return prefix;
            }
            Optional<Pair<String, State>> pair = trans.stream()
                    .filter(p -> p.getFirst().equals(String.valueOf(ch)))
                    .findFirst();
            if (!pair.isPresent()) {
                return prefix;
            }
            currentState = pair.get().getSecond();
            crtPrefix += ch;
            if (finalStates.contains(currentState)) {
                prefix = crtPrefix;
            }
        }
        return prefix;
    }

    /**
     * Checks if the state machine is deterministic
     */
    public boolean isDeterminist() {
        for (List<Pair<String, State>> listOfPairs : transitions.values()) {
            for (Pair<String, State> pair : listOfPairs) {
                long count = listOfPairs.stream()
                        .filter(p -> p.getFirst().equals(pair.getFirst()))
                        .count();
                if (count > 1) {
                    return false;
                }
            }
        }
        return true;
    }


}
