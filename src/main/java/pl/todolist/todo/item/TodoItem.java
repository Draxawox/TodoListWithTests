package pl.todolist.todo.item;

import pl.todolist.todo.exceptions.TodoItemDescriptionIsEmpty;
import pl.todolist.todo.exceptions.TodoItemDescriptionIsTooLong;
import pl.todolist.todo.interfaces.StatusChangeable;

import java.text.SimpleDateFormat;
import java.util.Date;

import static pl.todolist.todo.core.TodoItemValidator.validateTitle;


//Comparable<TodoItem>
public class TodoItem implements StatusChangeable {
    private String title;
    private String description;
    private ItemStatus itemStatus;
    private boolean completed;
    private String creationDate;


    //    Test needs it
    @SuppressWarnings("unused")
    private TodoItem() {
    }

    private String setCreationDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        Date currentDate = new Date();
        return format.format(currentDate);
    }

    private TodoItem(String title, String description) {
        this.title = title;
        this.description = description;
        this.itemStatus = ItemStatus.PENDING;
        this.creationDate = setCreationDate();
        this.completed = false;
    }

    public static TodoItem of(String title, String description) {
        validateTitle(title);
        validateDescription(description);
        return new TodoItem(title, description);
    }


    private static void validateDescription(String description) {
        if (description.length() > 250) {
            throw new TodoItemDescriptionIsTooLong("Description has more than 250 characters");
        } else if (description.isBlank()) {
            throw new TodoItemDescriptionIsEmpty("Description is null or empty");
        }
    }

    public String getCreationDate() {
        return this.creationDate;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        validateTitle(title);
        this.title = title;
    }

    public ItemStatus getStatus() {
        return itemStatus;
    }

    public void setStatus(ItemStatus itemStatus) {
        boolean isPending = this.itemStatus.equals(ItemStatus.PENDING);
        boolean isCompleted = this.itemStatus.equals(ItemStatus.COMPLETED);

        if (!(isPending && itemStatus.equals(ItemStatus.COMPLETED)) &&
                !(isCompleted && itemStatus.equals(ItemStatus.PENDING))) {
            this.itemStatus = itemStatus;
        }
    }

    public void toggleStatus() {
        if (this.getStatus() == ItemStatus.PENDING) {
            this.setStatus(ItemStatus.IN_PROGRESS);
        } else if (this.getStatus() == ItemStatus.COMPLETED) {
            this.setStatus(ItemStatus.IN_PROGRESS);
        } else if (this.getStatus() == ItemStatus.IN_PROGRESS && !this.completed) {
            this.setStatus(ItemStatus.PENDING);
        }
    }

    @Override
    public void complete() {
        if (this.itemStatus == ItemStatus.IN_PROGRESS) {
            this.itemStatus = ItemStatus.COMPLETED;
            this.completed = true;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        validateDescription(description);
        this.description = description;
    }

    /* @Override
    public int compareTo(TodoItem item) {
        if (item.getStatus().ordinal() < this.getStatus().ordinal()) {
            return 1;
        } else if (item.getStatus().ordinal() > this.getStatus().ordinal()) {
            return -1;
        }
        return 1;
    }*/
}


