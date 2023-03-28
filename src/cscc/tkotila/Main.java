package cscc.tkotila;

import java.util.*;
import java.util.function.Consumer;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in).useDelimiter("\n");

    public static void main(String[] args) {
        printChoices();
        TodoItemCollection tasks = new TodoItemCollection();
        String choice = SCANNER.next();

        while (!Objects.equals(choice, "0")) {
            switch (choice) {
                case "1" -> addTask(tasks);
                case "2" -> removeTask(tasks);
                case "3" -> editTask(tasks);
                case "4" -> listTasks(tasks);
                default -> System.out.println("Invalid option.");
            }
            tasks.sort();
            printChoices();
            choice = SCANNER.next();
        }
    }

    private static void addTask(TodoItemCollection tasks) {
        System.out.println("What should the title be?");
        String title = SCANNER.next();
        System.out.println("What should the description be?");
        String description = SCANNER.next();
        System.out.println("What should the priority be?");
        int priority = queryInt();
        tasks.add(new TodoItem(title, description, priority));
    }

    private static void removeTask(TodoItemCollection tasks) {
        System.out.println("What is the title of the task you wish to delete?");
        String title = SCANNER.next();

        if (tasks.getTask(title).isEmpty()) {
            System.out.println("A task with that title doesn't exist.");
            return;
        }

        tasks.deleteByTitle(title);
    }

    private static void editTask(TodoItemCollection tasks) {
        System.out.println("What is the title of the task you wish to edit?");
        String title = SCANNER.next();
        Optional<TodoItem> item = tasks.getTask(title);

        if (item.isEmpty()) {
            System.out.println("A task with that title doesn't exist.");
            return;
        }

        System.out.println("What should the new title be?");
        item.get().setTitle(SCANNER.next());
        System.out.println("What should the new description be?");
        item.get().setDescription(SCANNER.next());
        System.out.println("What should the new priority be?");
        item.get().setPriority(queryInt());
    }

    private static void listTasks(TodoItemCollection tasks) {
        if (tasks.size() == 0) {
            System.out.println("There are currently no tasks in the collection.");
            return;
        }

        for (TodoItem todoItem: tasks) {
            System.out.println(todoItem);
        }
    }

    private static int queryInt() {
        while (true) {
            try {
                return SCANNER.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
            }
        }
    }

    private static void printChoices() {
        System.out.println("(0) Exit.\n(1) Add a task.\n(2) Remove a task.\n(3) Edit a task.\n(4) List all tasks.");
    }
}

class TodoItem implements Comparable<TodoItem> {
    String title;
    String description;
    int priority;

    public TodoItem(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return String.format("Title: %s | Description: %s | Priority: %d", title, description, priority);
    }

    @Override
    public int compareTo(TodoItem rhs) {
        int res = this.getPriority() - rhs.getPriority();

        if (res == 0) {
            return this.getTitle().compareTo(rhs.getTitle());
        }

        return res;
    }
}

class TodoItemCollection implements Collection<TodoItem> {
    ArrayList<TodoItem> items;

    public TodoItemCollection() {
        this.items = new ArrayList<>();
    }

    public Optional<TodoItem> getTask(String title) {
        return items.stream().filter(rhs -> (Objects.equals(title, rhs.getTitle()))).findFirst();
    }

    public void deleteByTitle(String title) {
        items.removeIf(item -> Objects.equals(item.getTitle(), title));
    }

    public void sort() {
        items.sort(TodoItem::compareTo);
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return items.contains(o);
    }

    @Override
    public Iterator<TodoItem> iterator() {
        return items.iterator();
    }

    @Override
    public void forEach(Consumer<? super TodoItem> action) {
        this.forEach(action);
    }

    @Override
    public Object[] toArray() {
        return items.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return items.toArray(a);
    }

    @Override
    public boolean add(TodoItem todoItem) {
        return items.add(todoItem);
    }

    @Override
    public boolean remove(Object o) {
        return items.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return items.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends TodoItem> c) {
        return items.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return items.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return items.retainAll(c);
    }

    @Override
    public void clear() {
        items.clear();
    }
}
