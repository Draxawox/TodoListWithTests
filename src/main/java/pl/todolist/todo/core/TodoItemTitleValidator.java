package pl.todolist.todo.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.todolist.todo.exceptions.TodoItemHaveEmptyTitleOrNullValue;
import pl.todolist.todo.item.TodoItem;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TodoItemTitleValidator {
    private static final Logger logger;
    private static final List<Predicate<String>> criteria;

    static {
        logger = LoggerFactory.getLogger(TodoItem.class);
        criteria = List.of(
                s -> s != null && !s.isBlank());
    }

    public static void validateTitle(String title) {
        validateTitle(title, criteria);
    }

    private static void validateTitle(String title, List<Predicate<String>> criteria) {
        List<Boolean> falsies;
        falsies = criteria.stream()
                .map(s -> s.test(title))
                .collect(Collectors.toList())
                .stream()
                .filter(x -> !x)
                .collect(Collectors.toList());
        if (falsies.size() > 0) {
            logger.warn("Item title is null or empty");
            throw new TodoItemHaveEmptyTitleOrNullValue("Item title is null or empty.");
        }
    }
}
