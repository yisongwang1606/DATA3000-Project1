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
        private T data;
        private Node<T> next;

        /** Creates a node with data and no next reference. */
        private Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<T> frontNode;
    private Node<T> backNode;

    /** Initializes an empty queue. */
    public QueueImplementation() {
        this.frontNode = null;
        this.backNode = null;
    }

    @Override
    /** Adds an entry to the back of the queue. */
    public void enqueue(T newEntry) {
        Node<T> newNode = new Node<>(newEntry);
        if (isEmpty()) {
            frontNode = newNode;
        } else {
            backNode.next = newNode;
        }
        backNode = newNode;
    }

    @Override
    /** Removes and returns the front entry. */
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot dequeue from an empty queue.");
        }
        T frontData = frontNode.data;
        frontNode = frontNode.next;
        // Keep both pointers consistent when removing the last element.
        if (frontNode == null) {
            backNode = null;
        }
        return frontData;
    }

    @Override
    /** Returns the front entry without removing it. */
    public T getFront() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot get front from an empty queue.");
        }
        return frontNode.data;
    }

    @Override
    /** Checks whether the queue contains no entries. */
    public boolean isEmpty() {
        return frontNode == null;
    }

    @Override
    /** Removes all entries from the queue. */
    public void clear() {
        frontNode = null;
        backNode = null;
    }
}
