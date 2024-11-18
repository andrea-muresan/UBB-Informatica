package org.example;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Utils {
    public static final String ID_AF = "src/id_af.txt";
    public static final String CONST_INT_AF = "src/const_int_af.txt";
    public static final String CONST_FLOAT_AF = "src/const_float_af.txt";

    /**
     * Reads a finite state machine from file
     * The file must respect a template
     */
    public static FiniteStateMachine readFromFile(String filename) {
        State startState = null;
        List<State> states = new ArrayList<>();
        List<State> finalStates = new ArrayList<>();
        List<String> alphabet = new ArrayList<>();
        Map<State, List<Pair<String, State>>> transitions = new HashMap<>();

        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] words = line.split(" ");

                switch (LineType.fromString(words[0])) {
                    case Q:
                        for (int i = 1; i < words.length; i++) {
                            states.add(new State(words[i]));
                        }
                        break;

                    case SIGMA:
                        for (int i = 1; i < words.length; i++) {
                            alphabet.add(words[i]);
                        }
                        break;

                    case S:
                        startState = new State(words[1]);
                        break;

                    case F:
                        for (int i = 1; i < words.length; i++) {
                            int finalI = i;
                            State finalState = states.stream()
                                    .filter(s -> s.getId().equals(words[finalI]))
                                    .findFirst()
                                    .orElseThrow(() -> new RuntimeException("Invalid final state!"));
                            finalStates.add(finalState);
                        }
                        break;

                    case TRANSITION:
                        State firstState = states.stream()
                                .filter(s -> s.getId().equals(words[0]))
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("Invalid start state!"));

                        List<String> transitionsAux = new ArrayList<>();
                        for (String input : words[1].split("")) {
                            if (!input.isEmpty()) transitionsAux.add(input);
                        }

                        State secondState = states.stream()
                                .filter(s -> s.getId().equals(words[2]))
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("Invalid end state!"));

                        for (String input : transitionsAux) {
                            if (!alphabet.contains(input)) {
                                throw new RuntimeException("Invalid input symbol!");
                            }
                            transitions
                                    .computeIfAbsent(firstState, k -> new ArrayList<>())
                                    .add(new Pair<>(input, secondState));
                        }
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + filename, e);
        }

        return new FiniteStateMachine(
                startState != null ? startState : throwInvalidStartState(),
                states,
                alphabet,
                finalStates,
                transitions
        );
    }

    private static State throwInvalidStartState() {
        throw new RuntimeException("Invalid start state!");
    }
}
