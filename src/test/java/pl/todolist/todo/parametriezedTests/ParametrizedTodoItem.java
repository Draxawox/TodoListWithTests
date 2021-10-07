package pl.todolist.todo.parametriezedTests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import pl.todolist.todo.item.TodoItem;

import static org.hamcrest.MatcherAssert.assertThat;
import static pl.todolist.todo.matchers.ValidTodoItemMatcher.isValidTodoItem;

public class ParametrizedTodoItem {

    @ParameterizedTest
    @CsvFileSource(files = {"src/test/resources/todos.csv"}, numLinesToSkip = 1)
    public void shouldCreateValidTodoItemsCsvFileSource(String title, String description) {
        TodoItem item = TodoItem.of(title, description);
        assertThat(item, isValidTodoItem(title, description));
    }

    @ParameterizedTest
    @ArgumentsSource(TodoItemArgumentProvider.class)
    public void shouldCreateValidTodoItemsArgumentSource(String title, String description) {
        TodoItem item = TodoItem.of(title, description);
        assertThat(item, isValidTodoItem(title, description));
    }
}
