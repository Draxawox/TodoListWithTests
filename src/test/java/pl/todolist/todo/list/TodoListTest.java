package pl.todolist.todo.list;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pl.todolist.todo.exceptions.DuplicateListTitleException;
import pl.todolist.todo.exceptions.NoItemWithThisTitleException;
import pl.todolist.todo.exceptions.NoSuchItemException;
import pl.todolist.todo.exceptions.TodoListTitleIsEmptyException;
import pl.todolist.todo.item.ItemStatus;
import pl.todolist.todo.item.TodoItem;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static pl.todolist.todo.core.TodoListTitleValidator.isValidTodoList;

class TodoListTest extends BaseListTest {

    //    CREATING LIST
    @Tag("creatingList")
    @Test
    public void shouldCreateElementWithValidFields() {
        assertThat(list, isValidTodoList(title));
    }

    @Tag("creatingList")
    @Test
    public void shouldThrowTodoListTitleIsEmptyExceptionIfTitleIsEmptyOrNull() {
        assertAll(
                () -> assertThrows(TodoListTitleIsEmptyException.class, () -> TodoList.of("")),
                () -> assertThrows(TodoListTitleIsEmptyException.class, () -> TodoList.of(null))
        );
    }

    @Tag("changingTitle")
    @Test
    public void ifTitleIsSetToNullOrBlankShouldThrowThrowTodoListTitleIsEmptyException() {
        assertAll(
                () -> assertThrows(TodoListTitleIsEmptyException.class, () -> list.setTitle("")),
                () -> assertThrows(TodoListTitleIsEmptyException.class, () -> list.setTitle(null))
        );
    }

    @Tag("changingTitle")
    @Test
    public void setTitleMethodShouldReturnNewTitle() {
        String newTitle = "newTitle";
        list.setTitle(newTitle);
        assertEquals(newTitle, list.getTitle());
    }

    @Tag("listManipulation")
    @Tag("addingItems")
    @Test
    public void appendMethodShouldAddNewItemToTheList() {
        list.append(item1);
        assertEquals(1, list.getSize());
    }

    @Tag("listManipulation")
    @Tag("addingItems")
    @Test
    public void appendMethodShouldThrowDuplicateListTitleExceptionWhenItemWithSameNameAdded() {
        list.append(item1);
        assertThrows(DuplicateListTitleException.class, () -> list.append(item1));
    }

    @Tag("listManipulation")
    @Test
    public void listShouldBeExtendedByUniqueRecordsFromSecondList() {
        TodoList list2 = TodoList.of("list2");
        list2.append(TodoItem.of("uniqueTitle", "uniqueDescription"));
        list2.append(item1);
        list.append(item1);
        list.append(item2);
        list.addList(list2);
        assertEquals(3, list.getSize());
    }


    @Tag("listManipulation")
    @Tag("removingItems")
    @Test
    public void deleteItemShouldRemoveGivenItemFromTheList() {
        list.append(item1);
        list.deleteItem(item1);
        assertEquals(0, list.getSize());
    }

    @Tag("listManipulation")
    @Tag("removingItems")
    @Test
    public void deleteItemShouldThrowNoSuchItemExceptionIfElementDoesNotExist() {
        assertThrows(NoSuchItemException.class, () -> list.deleteItem(item1));
    }

    @Tag("listManipulation")
    @Tag("removingItems")
    @Test
    public void deleteItemShouldRemoveItemWhenItsTitleIsGiven() {
        list.append(item1);
        list.deleteItem(item1.getTitle());
        assertEquals(0, list.getSize());
    }

    @Tag("listManipulation")
    @Tag("removingItems")
    @Test
    public void deleteItemShouldThrowNoItemWithThisTitleExceptionWhenItemTitleDoesNotExist() {
        assertThrows(NoItemWithThisTitleException.class, () -> list.deleteItem(item1.getTitle()));
    }

    @Tag("listManipulation")
    @Tag("sort")
    @Test
    public void listShouldBeSortedByStatus() {
        list.append(item1);
        list.append(item2);
        TodoItem item3 = TodoItem.of("title3", "desc");
        list.append(item3);
        list.getList().get(1).toggleStatus();
        list.getList().get(2).toggleStatus();
        list.getList().get(2).complete();
        list.sortByStatus();
        for (int i = 0; i < list.getSize(); i++) {
            System.out.println(list.getList().get(i).getStatus());
        }
        assertAll(
                () -> assertEquals(0, list.getList().indexOf(item3)),
                () -> assertEquals(1, list.getList().indexOf(item2)),
                () -> assertEquals(2, list.getList().indexOf(item1))
        );
    }

    @Tag("listManipulation")
    @Tag("filter")
    @Test
    public void sortByTitleMethodShouldReturnNewListOfItemsWithGivenStatuses() {
        list.append(item1);
        item2.toggleStatus();
        list.append(item2);
        TodoList filteredList = list.filterByStatus(ItemStatus.IN_PROGRESS);
        assertAll(
                () -> assertEquals(1, filteredList.getSize()),
                () -> assertEquals(ItemStatus.IN_PROGRESS, filteredList.getList().get(0).getStatus())
        );
    }

    @Tag("listManipulation")
    @Tag("sort")
    @Test
    public void sortByTitleMethodShouldSortTheList() {
        list.append(item2);
        list.append(item1);
        list.sortByTitle();
        assertAll(
                () -> assertEquals(0, list.getList().indexOf(item1)),
                () -> assertEquals(1, list.getList().indexOf(item2))
        );
    }

    @Tag("listManipulation")
    @Tag("changingStatuses")
    @Test
    public void statusesShouldBeChangedToInProgress() {
        list.append(item1);
        list.append(item2);
        list.changeStatuses(item1, item2);
        assertAll(
                () -> assertEquals(ItemStatus.IN_PROGRESS, list.getList().get(0).getStatus()),
                () -> assertEquals(ItemStatus.IN_PROGRESS, list.getList().get(1).getStatus())
        );
    }

    @Tag("listManipulation")
    @Tag("changingStatuses")
    @Test
    public void statusesShouldBeChangedToPending() {
        list.append(item1);
        list.append(item2);
        list.changeStatuses(item1, item2);
        list.changeStatuses(item1, item2);
        assertAll(
                () -> assertEquals(ItemStatus.PENDING, list.getList().get(0).getStatus()),
                () -> assertEquals(ItemStatus.PENDING, list.getList().get(1).getStatus())
        );
    }

    @Tag("listManipulation")
    @Tag("changingStatuses")
    @Test
    public void statusesShouldBeChangedToCompleted() {
        list.append(item1);
        list.append(item2);
        list.changeStatuses(item1, item2);
        list.setStatusesToComplete(item1, item2);
        assertAll(
                () -> assertEquals(ItemStatus.COMPLETED, list.getList().get(0).getStatus()),
                () -> assertEquals(ItemStatus.COMPLETED, list.getList().get(1).getStatus())
        );
    }

    @Tag("listManipulation")
    @Tag("changingStatuses")
    @Test
    public void statusesShouldBeChangedToInProgressFromCompleted() {
        list.append(item1);
        list.append(item2);
        list.changeStatuses(item1, item2);
        list.setStatusesToComplete(item1, item2);
        list.changeStatuses(item1, item2);
        assertAll(
                () -> assertEquals(ItemStatus.IN_PROGRESS, list.getList().get(0).getStatus()),
                () -> assertEquals(ItemStatus.IN_PROGRESS, list.getList().get(1).getStatus())
        );
    }

    @Tag("listManipulation")
    @Tag("changingStatuses")
    @Test
    public void statusesShouldNotBePendingAfterDoubleChangeFromCompleted() {
        list.append(item1);
        list.append(item2);
        list.changeStatuses(item1, item2);
        list.setStatusesToComplete(item1, item2);
        list.changeStatuses(item1, item2);
        list.changeStatuses(item1, item2);
        assertAll(
                () -> assertEquals(ItemStatus.IN_PROGRESS, list.getList().get(0).getStatus()),
                () -> assertEquals(ItemStatus.IN_PROGRESS, list.getList().get(1).getStatus())
        );
    }
}