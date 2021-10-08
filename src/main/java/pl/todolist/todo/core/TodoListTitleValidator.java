package pl.todolist.todo.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.todolist.todo.exceptions.TodoListTitleIsEmptyException;
import pl.todolist.todo.list.TodoList;

public class TodoListTitleValidator extends TypeSafeMatcher<TodoList> {
    private static final Logger logger = LoggerFactory.getLogger(TodoList.class);
    private String title;

    protected TodoListTitleValidator(String title) {
        this.title = title;
    }

    public static void listTitleValidator(String title) {
        if (title == null || title.isBlank()) {
            logger.warn("List Title is empty or null");
            throw new TodoListTitleIsEmptyException("List Title is empty or null");
        }
    }

    public static Matcher<TodoList> isValidTodoList(String title) {
        return new TodoListTitleValidator(title);
    }

    @Override
    protected boolean matchesSafely(TodoList list) {
        return list.getTitle().equals(title);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Valid list");
    }
}
