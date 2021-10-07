package pl.todolist.todo.parametriezedTests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.todolist.todo.item.TodoItem;

public class BaseTestPar {

    protected static final Logger logger = LoggerFactory.getLogger(pl.todolist.todo.item.BaseTest.class);

    protected String title = "title";
    protected String description = "desc";
    protected TodoItem item = TodoItem.of(title, description);

}
