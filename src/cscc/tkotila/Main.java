package cscc.tkotila;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in).useDelimiter("\n");
        List<String> tasks = new ArrayList<>();
        String userChoice = queryUserInput(userInput);

        while (!Objects.equals(userChoice, "0")) {
            switch (userChoice) {
                case "1" -> addTask(tasks, userInput);
                case "2" -> removeTask(tasks, userInput);
                case "3" -> updateTask(tasks, userInput);
                case "4" -> {
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.printf("%d ) %s%n", i, tasks.get(i));
                    }
                }
                default -> System.out.println("Please enter a valid command.");
            }

            userChoice = queryUserInput(userInput);
        }
    }

    private static void updateTask(List<String> tasks, Scanner userInput) {
        System.out.println("Enter the index of the task you would like to update: ");
        int taskIndex = userInput.nextInt();
        System.out.println("Enter a new description for the task: ");

        try {
            tasks.set(taskIndex, userInput.next());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Task does not exist.");
        }
    }

    private static void removeTask(List<String> tasks, Scanner userInput) {
        System.out.println("Enter the index of the task you would like to delete: ");

        try {
            tasks.remove(userInput.nextInt());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Task does not exist.");
        }
    }

    private static void addTask(List<String> tasks, Scanner userInput) {
        System.out.println("Enter a description for your task: ");
        tasks.add(userInput.next());
    }

    private static String queryUserInput(Scanner userInput) {
        System.out.println("Please choose an option:");
        System.out.println("(1) Add a task.");
        System.out.println("(2) Remove a task.");
        System.out.println("(3) Update a task.");
        System.out.println("(4) List all tasks.");
        System.out.println("(0) Exit.");
        return userInput.next();
    }
}
