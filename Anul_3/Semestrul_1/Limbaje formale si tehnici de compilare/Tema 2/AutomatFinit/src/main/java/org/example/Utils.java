package org.example;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Utility class for reading a finite state machine from file or console.
 */
public class Utils {
    private static final String FILE_NAME = "src/main/input.txt";

    /**
     * Reads a finite state machine from file
     * The file must respect a template
     */
    public static FiniteStateMachine readFromFile() throws IOException {
        State startState = null;
        List<State> states = new ArrayList<>();
        List<State> finalStates = new ArrayList<>();
        List<String> alphabet = new ArrayList<>();
        Map<State, List<Pair<String, State>>> transitions = new HashMap<>();

        Scanner scanner = new Scanner(new File(FILE_NAME));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] words = line.split(" ");

            switch (LineType.fromString(words[0])) {
                case Q:
                    // Lista de stari
                    for (int i = 1; i < words.length; i++) {
                        states.add(new State(words[i]));
                    }
                    break;
                case SIGMA:
                    // Lista de simboluri
                    for (int i = 1; i < words.length; i++) {
                        alphabet.add(words[i]);
                    }
                    break;
                case S:
                    // Stare initiala
                    startState = new State(words[1]);
                    break;
                case F:
                    // Lista stari finale
                    for (int i = 1; i < words.length; i++) {
                        int finalI = i;
                        State finalState = states.stream()
                                .filter(state -> state.getId().equals(words[finalI]))
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("Invalid final state!"));
                        finalStates.add(finalState);
                    }
                    break;
                case TRANSITION:
                    // Lista tranzactii
                    State firstState = states.stream()
                            .filter(state -> state.getId().equals(words[0]))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Invalid start state!"));

                    List<String> transitionsAux = Arrays.asList(words[1].split(""));
                    transitionsAux.removeIf(String::isEmpty);

                    State secondState = states.stream()
                            .filter(state -> state.getId().equals(words[2]))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Invalid end state!"));

                    for (String input : transitionsAux) {
                        if (alphabet.stream().noneMatch(a -> a.equals(input))) {
                            throw new RuntimeException("Invalid input state!");
                        }
                        transitions
                                .computeIfAbsent(firstState, k -> new ArrayList<>())
                                .add(new Pair<>(input, secondState));
                    }
                    break;
            }
        }

        scanner.close();

        return new FiniteStateMachine(
                Objects.requireNonNull(startState, "Invalid start state!"),
                states,
                alphabet,
                finalStates,
                transitions
        );
    }

    /**
     * Reads a finite state machine components from console
     * Inputs must be written in a given format
     * re
     */
    public static FiniteStateMachine readFromConsole(Scanner scanner) {
        State startState = null;
        List<State> states = new ArrayList<>();
        List<State> finalStates = new ArrayList<>();
        List<String> alphabet = new ArrayList<>();
        Map<State, List<Pair<String, State>>> transitions = new HashMap<>();

        System.out.println("Enter states (x to end): ");
        while (true) {
            String id = scanner.nextLine().trim();
            if (id.equals("x")) break;
            if (!id.isEmpty()) {
                states.add(new State(id));
            }
        }

        System.out.println("Enter inputs (x to end): ");
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equals("x")) break;
            if (!input.isEmpty()) {
                alphabet.add(input);
            }
        }

        System.out.println("Enter start state: ");
        while (startState == null) {
            String startId = scanner.nextLine().trim();
            startState = states.stream()
                    .filter(state -> state.getId().equals(startId))
                    .findFirst()
                    .orElse(null);
            if (startState == null) System.out.println("Invalid start state!");
        }

        System.out.println("Enter final states (x to end): ");
        while (true) {
            String id = scanner.nextLine().trim();
            if (id.equals("x")) break;
            State state = states.stream()
                    .filter(s -> s.getId().equals(id))
                    .findFirst()
                    .orElse(null);
            if (state == null) {
                System.out.println("Invalid final state!");
            } else {
                finalStates.add(state);
            }
        }

        System.out.println("Enter transitions separated by space (x to end): ");
        while (true) {
            String line = scanner.nextLine().trim();
            if (line.equals("x")) break;
            String[] words = line.split(" ");

            try {
                State firstState = states.stream()
                        .filter(state -> state.getId().equals(words[0]))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Invalid first state!"));

                String input = alphabet.stream()
                        .filter(a -> a.equals(words[1]))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Invalid input!"));

                State secondState = states.stream()
                        .filter(state -> state.getId().equals(words[2]))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Invalid second state!"));

                transitions
                        .computeIfAbsent(firstState, k -> new ArrayList<>())
                        .add(new Pair<>(input, secondState));
            } catch (RuntimeException ex) {
                ex.printStackTrace();
                continue;
            }
        }

        return new FiniteStateMachine(
                startState,
                states,
                alphabet,
                finalStates,
                transitions
        );
    }
}
