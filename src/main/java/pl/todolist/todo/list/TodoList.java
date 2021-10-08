package pl.todolist.todo.list;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.todolist.todo.exceptions.DuplicateListTitleException;
import pl.todolist.todo.exceptions.NoItemWithThisTitleException;
import pl.todolist.todo.exceptions.NoSuchItemException;
import pl.todolist.todo.item.ItemStatus;
import pl.todolist.todo.item.TodoItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static pl.todolist.todo.core.TodoListTitleValidator.listTitleValidator;

public class TodoList {
    List<TodoItem> list;
    private String title;
    private static final Logger logger = LoggerFactory.getLogger(TodoList.class);

    private TodoList(String title) {
        listTitleValidator(title);
        this.title = title;
        this.list = new ArrayList<>();
    }

    public static TodoList of(String title) {
        listTitleValidator(title);
        return new TodoList(title);
    }

    private TodoList(String title, List<TodoItem> list) {
        listTitleValidator(title);
        this.list = list;
        this.title = title;
    }

    public void addList(TodoList one) {
        for (TodoItem x : one.getList()) {
            if (!this.list.contains(x)) {
                append(x);
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        listTitleValidator(title);
        this.title = title;
    }

    public List<TodoItem> getList() {
        return list;
    }

    public int getSize() {
        return this.list.size();
    }


    public void append(TodoItem item) {
        for (TodoItem x : this.list) {
            if (x.getTitle().equals(item.getTitle())) {
                logger.warn("Element with same title already exists");
                throw new DuplicateListTitleException("Element with same title already exists");
            }
        }
        this.list.add(item);
    }

    public void deleteItem(TodoItem item) {
        boolean isDone = false;
        if (this.list.contains(item)) {
            isDone = true;
            this.list.remove(item);
        }
        if (!isDone) {
            logger.warn("Item is not existing");
            throw new NoSuchItemException("Item is not existing");
        }
    }

    public void deleteItem(String title) {
        boolean isDone = false;
        for (TodoItem x : this.list.stream().toList()) {
            if (x.getTitle().equals(title)) {
                isDone = true;
                deleteItem(x);
            }
        }
        if (!isDone) {
            logger.warn("There is no item with this title");
            throw new NoItemWithThisTitleException("There is no item with this title");
        }
    }

    public void sortByStatus() {
        this.list = list.stream().sorted(TodoItem::compareTo).collect(Collectors.toList());
    }

    public TodoList filterByStatus(ItemStatus st) {
        TodoList filtered;
        List<TodoItem> listOfItems = this.list
                .stream()
                .filter(x -> x.getStatus()
                        .equals(st))
                .collect(Collectors.toList());
        return new TodoList(this.title, listOfItems);
    }

    public void sortByTitle() {
        this.list = getList().stream().sorted(Comparator.comparing(TodoItem::getTitle)).collect(Collectors.toList());
    }

    public void changeStatuses(TodoItem... items) {
        Arrays.stream(items).forEach(TodoItem::toggleStatus);
    }

    public void setStatusesToComplete(TodoItem... items) {
        Arrays.stream(items).forEach(TodoItem::complete);
    }
}
