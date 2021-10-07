package pl.todolist.todo.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {

    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    protected String title;
    protected String description;
    protected TodoItem item;

    @AfterEach
    public void afterTest() {
        item = null;
    }

    @BeforeEach

    public void initialize() {
        title = "title";
        description = "desc";
        item = TodoItem.of(title, description);
    }
}
