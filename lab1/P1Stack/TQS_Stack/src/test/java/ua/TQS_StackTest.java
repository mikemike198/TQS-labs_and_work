package ua;

import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
// import org.junit.jupiter.params.ParameterizedTest;
// import org.junit.jupiter.params.provider.CsvSource;

/**
 * Unit test for simple App.
 */
public class TQS_StackTest 
{
    TQS_Stack<String> stack;
    TQS_Stack<String> stack2;

    @BeforeEach
    public void setUp() {
        stack = new TQS_Stack<String>();
    }

    @DisplayName("Test if the first stack is empty")
    @Test
    public void test_StackIsEmpty() {
        assertTrue(stack.isEmpty(),  "Should be empty!");
    }

    @DisplayName("Test if the size is zero")
    @Test
    public void test_SizeIsZero() {
        assertEquals(0, stack.size(), "");
    }

    @DisplayName("Test Size and Emptiness of a stack with n-pushes")
    @Test
    public void test_NPushes() {

        for (int i =0; i < 10; i++) {
            stack.push("" + i);
        }

        assertAll("size and emptyness", 
                    () -> assertEquals(10, stack.size()),
                    () -> assertEquals(false, stack.isEmpty()));
    }

    @DisplayName("Test push then pop the item pushed")
    @Test
    public void test_PopElement() {
        stack.push("One");

        assertEquals("One", stack.pop());
    }

    @DisplayName("Test push then peek the item pushed and test if size stays the same")
    @Test
    public void test_PeekX() {
        stack.push("One");

        assertAll("peek and size", 
                () -> assertEquals("One", stack.peek()), 
                () -> assertEquals(1, stack.size()));
    }

    @Test
    public void test_PopN() {
        for (int i =0; i < 10; i++) {
            stack.push("" + i);
        }
        for (int i =0; i < 10; i++) {
            stack.pop();
        }

        assertEquals(0, stack.size());
    }

    @DisplayName("Test for poping in a empty stack")
    @Test
    public void test_Exception_PopEmptyStack() {
        assertThrows(EmptyStackException.class, () ->
            stack.pop());
    }

    @DisplayName("Test for peeking in a empty stack")
    @Test
    public void test_Exception_PeekingEmptyStack() {
        assertThrows(EmptyStackException.class, () ->
            stack.peek());
    }
    @Test
    public void test_ExceptionBouded() {
        stack.turnBouded(3);
        stack.push("One");
        stack.push("Two");
        stack.push("Three");
        assertThrows(IllegalStateException.class, () ->
            stack.push("Four"));
    }
}

