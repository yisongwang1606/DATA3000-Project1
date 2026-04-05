package fcfs.queue;

/**
 * Implements a queue using a single linked list.
 *
 * @param <T> The type of elements stored in the queue.
 */
public class QueueImplementation<T> implements QueueInterface<T> {
    /**
     * Internal node for the single linked list.
     *
     * @param <T> node data type.
     */
    private static class Node<T> {
        // Stores the value held by this node.
        private T data;
        // Points to the next node in the list.
        private Node<T> next;

        // Creates a node with data and no next node.
        private Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    // Points to the first element of the queue.
    private Node<T> frontNode;
    // Points to the last element of the queue.
    private Node<T> backNode;

    // Initializes an empty queue.
    public QueueImplementation() {
        this.frontNode = null;
        this.backNode = null;
    }

    @Override
    // Adds a new element to the end of the queue.
    public void enqueue(T newEntry) {
        // Build a new node for the incoming element.
        Node<T> newNode = new Node<>(newEntry);

        // If queue is empty, new node becomes the front node.
        if (isEmpty()) {
            frontNode = newNode;
        } else {
            // Otherwise, link current back node to the new node.
            backNode.next = newNode;
        }

        // Move back pointer to the newly added node.
        backNode = newNode;
    }

    @Override
    // Removes and returns the element at the front of the queue.
    public T dequeue() {
        // Prevent dequeue on an empty queue.
        if (isEmpty()) {
            throw new IllegalStateException("Cannot dequeue from an empty queue.");
        }

        // Save front data before moving front pointer.
        T frontData = frontNode.data;

        // Advance front pointer to the next node.
        frontNode = frontNode.next;

        // If queue became empty, clear back pointer as well.
        if (frontNode == null) {
            backNode = null;
        }

        // Return removed element value.
        return frontData;
    }

    @Override
    // Returns the element at the front without removing it.
    public T getFront() {
        // Prevent front access on an empty queue.
        if (isEmpty()) {
            throw new IllegalStateException("Cannot get front from an empty queue.");
        }

        // Return current front value.
        return frontNode.data;
    }

    @Override
    // Checks whether the queue has no elements.
    public boolean isEmpty() {
        return frontNode == null;
    }

    @Override
    // Removes all elements by resetting pointers.
    public void clear() {
        frontNode = null;
        backNode = null;
    }
}
