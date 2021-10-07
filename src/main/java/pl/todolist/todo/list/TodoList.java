package pl.todolist.todo.list;

import pl.todolist.todo.item.ItemStatus;
import pl.todolist.todo.item.TodoItem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TodoList {
    List<TodoItem> list;
    private String title;

    public TodoList(String title) {
        if (title.trim().equals("")) {
            System.out.println("wrong title");
            return;
        } else {
            this.title = title;
        }
        this.list = new ArrayList<>();
    }

    public static TodoList addTwoLists(TodoList one, TodoList two, String newTitle) {
        TodoList newer = new TodoList(newTitle);
        for (TodoItem s : one.getList()) {
            newer.append(s);
        }
        for (TodoItem s : two.getList()) {
            newer.append(s);
        }
        System.out.println("Todolist " + newTitle + " created");
        return newer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TodoItem> getList() {
        return list;
    }

    public int getSize() {
        return this.list.size();
    }


    public void append(TodoItem item) {
        boolean dup = false;
        for (TodoItem x : this.list) {
            if (x.getTitle().equals(item.getTitle())) {
                dup = true;
                break;
            }
        }
        if (!(this.list.contains(item))) {
            System.out.println("Item already exists");
        } else if (dup) {
            System.out.println("Item with same title already exists");
        } else {
            this.list.add(item);
            System.out.println("Item added successfully");
        }
    }

    public void deleteItem(String title) {
        boolean isDone = false;
        for (TodoItem x : this.list) {
            if (x.getTitle().equals(title)) {
                isDone = true;
                System.out.println("item removed successfully");
                this.list.remove(list.indexOf(title));
            }
        }
        if (!isDone) {
            System.out.println("There is no item with this title");
        }
    }

    /*public void sortByStatus() {
        this.list = list.stream().sorted(TodoItem::compareTo).collect(Collectors.toList());
    }*/

    public List filterByStatus(ItemStatus st) {
        return this.list.stream().filter(x -> x.getStatus().equals(st)).collect(Collectors.toList());
    }

    public void sortByTitle() {
        this.list = getList().stream().sorted(Comparator.comparing(TodoItem::getTitle)).collect(Collectors.toList());
    }


}

/*
Items status can be changed for several items
* */
