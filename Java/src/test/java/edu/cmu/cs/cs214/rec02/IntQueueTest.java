package edu.cmu.cs.cs214.rec02;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * TODO:
 * 1. The {@link LinkedIntQueue} has no bugs. We've provided you with some
 * example test cases.
 * Write your own unit tests to test against IntQueue interface with
 * specification testing method
 * using mQueue = new LinkedIntQueue();
 * 
 * 2.
 * Comment `mQueue = new LinkedIntQueue();` and uncomment `mQueue = new
 * ArrayIntQueue();`
 * Use your test cases from part 1 to test ArrayIntQueue and find bugs in the
 * {@link ArrayIntQueue} class
 * Write more unit tests to test the implementation of ArrayIntQueue, with
 * structural testing method
 * Aim to achieve 100% line coverage for ArrayIntQueue
 *
 * @author Alex Lockwood, George Guo, Terry Li
 */
public class IntQueueTest {

    private IntQueue mQueue;
    private List<Integer> testList;

    /**
     * Called before each test.
     */
    @Before
    public void setUp() {
        // comment/uncomment these lines to test each class
        // mQueue = new LinkedIntQueue();
        mQueue = new ArrayIntQueue();

        testList = new ArrayList<>(List.of(1, 2, 3));
    }

    /**
     * Structural testing ArrayIntQueue.
     */
    @Test
    public void testInitialState() {
        assertTrue(mQueue.isEmpty());
        assertEquals(0, mQueue.size());
        assertNull(mQueue.peek());
    }

    /**
     * Structural testing ArrayIntQueue.
     */
    @Test
    public void testEnqueueAndDequeue() {
        mQueue.enqueue(testList.get(0));
        mQueue.enqueue(testList.get(1));
        assertFalse(mQueue.isEmpty());
        assertEquals(2, mQueue.size());
        assertEquals(testList.get(0), mQueue.dequeue());
        assertEquals(testList.get(1), mQueue.peek());
        assertEquals(1, mQueue.size());
    }

    /**
     * Structural testing ArrayIntQueue.
     */
    @Test
    public void testEnqueueBeyondInitialCapacity() {
        for (int i = 0; i < 11; i++) { // Add one more than initial capacity
            mQueue.enqueue(i);
        }
        assertEquals(11, mQueue.size());
        for (int i = 0; i < 11; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testIsEmpty() {
        // This is an example unit test
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testNotEmpty() {
        mQueue.enqueue(1);
        assertFalse(mQueue.isEmpty());
    }

    @Test
    public void testPeekEmptyQueue() {
        assertNull(mQueue.peek());
    }

    @Test
    public void testPeekNoEmptyQueue() {
        mQueue.enqueue(testList.get(0));
        assertEquals(testList.get(0), mQueue.peek());
        mQueue.enqueue(testList.get(1));
        assertEquals(testList.get(0), mQueue.peek()); // First element remains
        assertEquals(2, mQueue.size());
    }

    @Test
    public void testEnqueue() {
        // This is an example unit test
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(testList.get(0), mQueue.peek());
            assertEquals(i + 1, mQueue.size());
        }
    }

    @Test
    public void testDequeue() {
        for (Integer item : testList) {
            mQueue.enqueue(item);
        }
        for (Integer expected : testList) {
            assertEquals(expected, mQueue.dequeue());
        }
        // Ensure dequeue on an empty queue returns null
        assertNull(mQueue.dequeue());
    }

    @Test
    public void testDequeueOnEmptyQueue() {
        assertNull(mQueue.dequeue());
    }

    @Test
    public void testClear() {
        assertEquals(0, mQueue.size());
        for (Integer item : testList) {
            mQueue.enqueue(item);
        }
        mQueue.clear();
        assertTrue(mQueue.isEmpty());
        assertEquals(0, mQueue.size());
    }

    @Test
    public void testSize() {
        assertEquals(0, mQueue.size());
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(i + 1, mQueue.size());
        }
        mQueue.dequeue();
        assertEquals(testList.size() - 1, mQueue.size());
        mQueue.clear();
        assertEquals(0, mQueue.size());
    }

    @Test
    public void testContent() throws IOException {
        // This is an example unit test
        InputStream in = new FileInputStream("src/test/resources/data.txt");
        try (Scanner scanner = new Scanner(in)) {
            scanner.useDelimiter("\\s*fish\\s*");

            List<Integer> correctResult = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                correctResult.add(input);
                System.out.println("enqueue: " + input);
                mQueue.enqueue(input);
            }

            for (Integer result : correctResult) {
                assertEquals(mQueue.dequeue(), result);
            }
        }
    }

    @Test
    public void testWrapAroundLogic() {
        // This test might need adaptation if mQueue is a LinkedIntQueue, as the
        // wrap-around concept applies specifically to ArrayIntQueue.
        for (int i = 0; i < 10; i++) {
            mQueue.enqueue(i);
        }
        for (int i = 0; i < 5; i++) {
            mQueue.dequeue();
        }
        for (int i = 10; i < 15; i++) {
            mQueue.enqueue(i);
        }
        assertEquals(10, mQueue.size());
        for (int i = 5; i < 15; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testSizeCorrectness() {
        assertTrue(mQueue.isEmpty());
        mQueue.enqueue(testList.get(0));
        mQueue.enqueue(testList.get(1));
        assertEquals(2, mQueue.size());
        mQueue.dequeue();
        assertEquals(1, mQueue.size());
        mQueue.clear();
        assertEquals(0, mQueue.size());
    }

    @Test
    public void testDequeueAndPeekWithSingleElement() {
        mQueue.enqueue(testList.get(0));
        assertEquals(testList.get(0), mQueue.peek());
        assertEquals(testList.get(0), mQueue.dequeue());
        assertTrue(mQueue.isEmpty());
        assertNull(mQueue.peek());
        assertNull(mQueue.dequeue());
    }

    @Test
    public void testResizeWithNonZeroHead() {
        // Step 1: Fill the queue to near its initial capacity to prepare for resizing
        int initialCapacity = 10; // Assuming INITIAL_SIZE is 10
        for (int i = 0; i < initialCapacity - 1; i++) {
            mQueue.enqueue(i);
        }

        // Step 2: Dequeue some elements to move the head forward
        int elementsToDequeue = 5;
        for (int i = 0; i < elementsToDequeue; i++) {
            mQueue.dequeue();
        }

        // Step 3: Continue enqueuing elements to trigger resizing
        for (int i = initialCapacity - 1; i < initialCapacity + 5; i++) { // Add more elements to ensure resizing occurs
            mQueue.enqueue(i);
        }

        // Step 4: Validate the queue size and content after resize
        assertEquals(initialCapacity + 5 - elementsToDequeue, mQueue.size());

        // Step 5: Validate the order and integrity of elements after resizing
        for (int i = elementsToDequeue; i < initialCapacity + 5; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }

        // Ensure the queue is empty after all operations
        assertTrue(mQueue.isEmpty());
    }

}
