package pl.todolist.todo.item;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.todolist.todo.exceptions.TodoItemDescriptionIsEmpty;
import pl.todolist.todo.exceptions.TodoItemDescriptionIsTooLong;
import pl.todolist.todo.exceptions.TodoItemHaveEmptyTitleOrNullValue;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;
import static pl.todolist.todo.matchers.ValidTodoItemMatcher.isValidTodoItem;


@Tag("item")
class TodoItemTest extends BaseTest {

    Logger logger2 = LoggerFactory.getLogger(TodoItemTest.class);
//    DESCRIPTION

    @Test
    public void shouldCreateTodoItemWithValidTitleAndDescription() {
        assertThat(item, isValidTodoItem(title, description));
    }

    @Test
    public void shouldNotCreateTodoItemWithInvalidTitleAndDescription() {
        assertThat(item, not(isValidTodoItem(title + "g", description)));
    }

    @Tag("happy")
    @Test
    public void shouldNotCreateATodoItemWithDescriptionLongerThan250Chars() {
        assertThrows(TodoItemDescriptionIsTooLong.class, () -> TodoItem.of(title, "l".repeat(251)));
    }

    @Tag("exception")
    @Test
    public void shouldNotSetATodoItemDescriptionLongerThan250Chars() {
        assertThrows(TodoItemDescriptionIsTooLong.class, () -> item.setDescription("l".repeat(251)));
    }

    @Test
    public void shouldNotSetATodoItemDescriptionWhileNullOrEmpty() {
        assertThrows(TodoItemDescriptionIsEmpty.class, () -> item.setDescription(""));
    }

    @Test
    public void shouldNotCreateATodoItemWithEmptyDescription() {
        assertThrows(TodoItemDescriptionIsEmpty.class, () -> TodoItem.of(title, ""));
    }

    @Test
    public void setDescriptionShouldChangeDescription() {
        logger2.warn("elo");
        String newDescription = "newOne";
        item.setDescription(newDescription);
        assertEquals(newDescription, item.getDescription());
    }

//    STATUS CHANGING

    @Test
    public void shouldChangeStatusFromPendingToInProgress() {
        item.toggleStatus();
        assertEquals(ItemStatus.IN_PROGRESS, item.getStatus());
    }

    @Test
    public void shouldChangeStatusFromInProgressToPending() {
        item.toggleStatus();
        item.toggleStatus();
        assertEquals(ItemStatus.PENDING, item.getStatus());
    }

    @Test
    public void shouldNotChangeStatusToPendingWhenItWasCompletedOnce() {
        item.toggleStatus();
        item.complete();
        item.toggleStatus();
        item.toggleStatus();
        assertEquals(ItemStatus.IN_PROGRESS, item.getStatus());
    }

    @Test
    public void statusCanNotBeChangedToCompleteFormPending() {
        item.complete();
        assertEquals(ItemStatus.PENDING, item.getStatus());
    }

    @Test
    public void shouldChangeStatusFromInProgressToCompleted() {
        item.toggleStatus();
        item.complete();
        assertEquals(ItemStatus.COMPLETED, item.getStatus());
    }

//    TITLE

    @Test
    public void shouldThrowAnExceptionWhileSettingEmptyTitle() {
        assertThrows(TodoItemHaveEmptyTitleOrNullValue.class, () -> item.setTitle(""));
    }

    @Test
    public void shouldCreateTodoItemWithTitleAndDescription() {
        assertAll(
                () -> assertEquals(title, item.getTitle()),
                () -> assertEquals(description, item.getDescription())
        );
    }

    @Test
    public void setTitleShouldChangeTitle() {
        String newTitle = "new Title";
        item.setTitle(newTitle);
        assertEquals(newTitle, item.getTitle());
    }

    @Test
    public void validateTitleShouldThrowExceptionWhenCriteriaIsNotPassed() {
        assertAll(
                () -> assertThrows(TodoItemHaveEmptyTitleOrNullValue.class, () -> TodoItem.of("", description)),
                () -> assertThrows(TodoItemHaveEmptyTitleOrNullValue.class, () -> TodoItem.of(null, description))
        );
    }

    //    DATE
    @Test
    public void dateShouldBeFormattedAndMatchToRegex() {
        String regex = "^([1-9]|([012][0-9])|(3[01]))-([0]{0,1}[1-9]|1[012])-\\d\\d\\d\\d [012]{0,1}[0-9]:[0-6][0-9]$";
        assertTrue(item.getCreationDate().matches(regex));
    }

    @Test
    public void ifDateIsInWrongFormatMatchShouldGiveFalse() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date currentDate = new Date();
        String regex = "^([1-9]|([012][0-9])|(3[01]))-([0]{0,1}[1-9]|1[012])-\\d\\d\\d\\d [012]{0,1}[0-9]:[0-6][0-9]$";
        assertFalse(format.format(currentDate).matches(regex));
    }
}