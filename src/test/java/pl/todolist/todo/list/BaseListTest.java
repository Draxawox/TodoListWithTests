package pl.todolist.todo.list;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.todolist.todo.item.TodoItem;

public class BaseListTest {

    protected static final Logger logger = LoggerFactory.getLogger(BaseListTest.class);


    protected String title;
    protected TodoList list;
    protected TodoItem item1;
    protected TodoItem item2;

    @BeforeEach
    public void initialize() {
        title = "title";
        list = TodoList.of(title);
        item1 = TodoItem.of("title1", "description1");
        item2 = TodoItem.of("title2", "description2");
    }

    @AfterEach
    public void finish() {
        list = null;
        item1 = null;
        item2 = null;
    }
}
