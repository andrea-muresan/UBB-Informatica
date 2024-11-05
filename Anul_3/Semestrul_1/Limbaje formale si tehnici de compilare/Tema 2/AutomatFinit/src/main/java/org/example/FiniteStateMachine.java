package org.example;

import java.util.List;
import java.util.Map;

public class FiniteStateMachine {
    private State startState;
    private List<State> states;
    private List<String> alphabet;
    private List<State> finalStates;
    private Map<State, List<Pair<String, State>>> transitions;

    public FiniteStateMachine(State startState, List<State> states, List<String> alphabet,
                              List<State> finalStates, Map<State, List<Pair<String, State>>> transitions) {
        this.startState = startState;
        this.states = states;
        this.alphabet = alphabet;
        this.finalStates = finalStates;
        this.transitions = transitions;
    }

    public State getStartState() {
        return startState;
    }

    public void setStartState(State startState) {
        this.startState = startState;
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(List<String> alphabet) {
        this.alphabet = alphabet;
    }

    public List<State> getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(List<State> finalStates) {
        this.finalStates = finalStates;
    }

    public Map<State, List<Pair<String, State>>> getTransitions() {
        return transitions;
    }

    public void setTransitions(Map<State, List<Pair<String, State>>> transitions) {
        this.transitions = transitions;
    }

    /**
     * Checks if a string is accepted or not by this state machine
     */
    public boolean isAccepted(String seq) {
        State currentState = startState;
        for (char ch : seq.toCharArray()) {
            List<Pair<String, State>> trans = transitions.get(currentState);
            if (trans == null) return false;

            Pair<String, State> pair = trans.stream()
                    .filter(p -> p.getFirst().equals(String.valueOf(ch)))
                    .findFirst()
                    .orElse(null);

            if (pair == null) return false;
            currentState = pair.getSecond();
        }
        return finalStates.contains(currentState);
    }

    /**
     * Calculates the longest prefix accepted by this state machine
     */
    public String longestPrefix(String seq) {
        String prefix = "";
        StringBuilder currentPrefix = new StringBuilder();
        State currentState = startState;

        for (char ch : seq.toCharArray()) {
            List<Pair<String, State>> trans = transitions.get(currentState);
            if (trans == null) return prefix;

            Pair<String, State> pair = trans.stream()
                    .filter(p -> p.getFirst().equals(String.valueOf(ch)))
                    .findFirst()
                    .orElse(null);

            if (pair == null) return prefix;
            currentState = pair.getSecond();
            currentPrefix.append(ch);

            if (finalStates.contains(currentState)) {
                prefix = currentPrefix.toString();
            }
        }
        return prefix;
    }

    /**
     * Find if the finite state machine is deterministic or no
     * @return  true - deterministic FSM
     *          false - nondeterministic FSM
     */
    public boolean isDeterministic() {
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
