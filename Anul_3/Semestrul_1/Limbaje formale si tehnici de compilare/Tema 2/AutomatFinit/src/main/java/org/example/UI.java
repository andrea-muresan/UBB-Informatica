package org.example;

import java.io.IOException;
import java.util.Scanner;

public class UI {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Print function for the first menu
     */
    private void printFirstMenu() {
        System.out.println("1. Read from file");
        System.out.println("2. Read from console");
        System.out.println("Press x to exit");
        System.out.println("--------------------");
        System.out.print(">> ");
    }

    /**
     * Print function for the second menu
     */
    private void printSecondMenu() {
        System.out.println("1. States");
        System.out.println("2. Alphabet");
        System.out.println("3. Transitions");
        System.out.println("4. Final states");
        System.out.println("5. Is sequence accepted?");
        System.out.println("6. Longest prefix for sequence");
        System.out.println("Press x to exit");
        System.out.println("--------------------");
        System.out.print(">> ");
    }

    /**
     * Entry point of the first menu
     */
    private void enterFirstMenu() throws IOException {
        String cmd;
        while (true) {
            printFirstMenu();
            cmd = scanner.nextLine();
            if (cmd.equalsIgnoreCase("x")) {
                System.out.println("Byee :(");
                break;
            }
            switch (cmd) {
                case "1":
                    enterSecondMenu(Utils.readFromFile());
                    break;
                case "2":
                    enterSecondMenu(Utils.readFromConsole(scanner));
                    break;
                default:
                    System.out.println("Invalid command!\n");
            }
        }
    }

    /**
     * Entry point for the second menu
     */
    private void enterSecondMenu(FiniteStateMachine fsm) {
        String cmd;
        while (true) {
            printSecondMenu();
            cmd = scanner.nextLine();
            if (cmd.equalsIgnoreCase("x")) {
                break;
            }
            switch (cmd) {
                case "1":
                    System.out.print("States: ");
                    System.out.println(String.join(", ", fsm.getStates().stream().map(State::getId).toArray(String[]::new)) + "\n");
                    break;

                case "2":
                    System.out.print("Alphabet: ");
                    System.out.println(String.join(", ", fsm.getAlphabet()) + "\n");
                    break;

                case "3":
                    System.out.println("Transitions:");
                    fsm.getTransitions().forEach((key, value) -> {
                        System.out.print(key.getId() + ":");
                        value.forEach(pair -> System.out.println("\t" + pair.getFirst() + " " + pair.getSecond().getId()));
                    });
                    break;

                case "4":
                    System.out.print("Final states: ");
                    System.out.println(String.join(", ", fsm.getFinalStates().stream().map(State::getId).toArray(String[]::new)) + "\n");
                    break;

                case "5":
                    if (!fsm.isDeterministic()) {
                        System.out.print("Nedeterminist\n");
                    } else {
                        System.out.print("Enter sequence: ");
                        String sequence = scanner.nextLine();
                        if (fsm.isAccepted(sequence)) {
                            System.out.println("Sequence is accepted!");
                        } else {
                            System.out.println("Sequence is declined!");
                        }
                    }
                    break;

                case "6":
                    if (!fsm.isDeterministic()) {
                        System.out.print("Nedeterminist\n");
                    } else {
                        System.out.print("Enter sequence: ");
                        String sequence = scanner.nextLine();
                        String prefix = fsm.longestPrefix(sequence);
                        System.out.println("The longest prefix is: " + prefix + ".");
                    }
                    break;

                default:
                    System.out.println("Invalid command!\n");
            }
        }
    }

    /**
     * Entry point of the program
     */
    public void run() throws IOException {
        enterFirstMenu();
    }

    public static void main(String[] args) throws IOException {
        UI ui = new UI();
        ui.run();
    }
}
