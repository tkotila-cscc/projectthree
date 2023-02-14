package cscc.tkotila;

import java.util.*;
import java.util.function.Predicate;

public class Main {
    static Scanner INPUT_SCANNER = new Scanner(System.in).useDelimiter("\n");
    static List<TodoItem> TASKS = new ArrayList<>();
    static final String OPTION_QUERY = "(1) Add a task.\n(2) Remove a task.\n(3) Update a task.\n(4) List all tasks.\n(0) Exit.";
    static final String[] OPTION_LIST = {"0", "1", "2", "3", "4"};

    public static void main(String[] args) {
        String option = query(OPTION_QUERY, s -> Arrays.asList(OPTION_LIST).contains(s));

        while (!option.equals("0")) {
            switch (option) {
                case "1" -> addTask();
                case "2" -> removeTask();
                case "3" -> updateTask();
                case "4" -> listTasks();
            }

            TASKS.sort(Comparator.comparingInt(TodoItem::getPriority));
            option = query(OPTION_QUERY, s -> Arrays.asList(OPTION_LIST).contains(s));
        }
    }

    static void addTask() {
        TASKS.add(
                new TodoItem(
                        query("Enter a title for the task:"),
                        query("Enter a description for the task:"),
                        Integer.parseInt(
                                query("Enter a priority for the task:",
                                    s -> validateIntegerFormat(s, 0, 5)
                                )
                        )
                )
        );
    }

    static void removeTask() {
        TASKS.removeIf(
                item -> Objects.equals(
                    item.getTitle(),
                    query("Enter the title of the task you wish to delete:")
                )
        );
    }

    static void updateTask() {
        TodoItem modifiedTask;

        try {
            modifiedTask = TASKS.stream()
                    .filter(item -> Objects.equals(item.getTitle(), query("Enter the title of the task you wish to modify:")))
                    .findFirst()
                    .get();
        } catch (NoSuchElementException e) {
            System.out.println("There are no tasks with that title.");
            return;
        }

        TASKS.removeIf(
                item -> Objects.equals(
                        item.getTitle(),
                        modifiedTask.getTitle()
                )
        );

        TASKS.add(
                new TodoItem(
                        modifiedTask.getTitle(),
                        query("Enter a description for the task:"),
                        Integer.parseInt(
                                query("Enter a priority for the task:",
                                        s -> validateIntegerFormat(s, 0, 5)
                                )
                        )
                )
        );
    }

    static void listTasks() {
        for (TodoItem task: TASKS) {
            System.out.println(task);
        }
    }

    static String query(String queryMessage) {
        System.out.println(queryMessage);
        return INPUT_SCANNER.next();
    }

    static String query(String queryMessage, Predicate<String> validator) {
        System.out.println(queryMessage);
        String result = INPUT_SCANNER.next();

        while (!validator.test(result)) {
            System.out.println("Invalid input.");
            result = INPUT_SCANNER.next();
        }

        return result;
    }

    static boolean validateIntegerFormat(String s, int min, int max) {
        int parsed;

        try {
            parsed = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }

        return parsed >= min && parsed <= max;
    }
}
