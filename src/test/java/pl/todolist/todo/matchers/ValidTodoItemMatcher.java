package pl.todolist.todo.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import pl.todolist.todo.item.TodoItem;

public class ValidTodoItemMatcher extends TypeSafeMatcher<TodoItem> {

    private String title;
    private String description;

    protected ValidTodoItemMatcher(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    protected boolean matchesSafely(TodoItem item) {
        return item.getTitle().equals(title) & item.getDescription().equals(description);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Valid todoItem with description");
    }

    public static Matcher<TodoItem> isValidTodoItem(String title, String description) {
        return new ValidTodoItemMatcher(title, description);
    }
}
