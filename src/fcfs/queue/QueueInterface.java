package fcfs.queue;

/**
 * Represents a generic queue interface.
 *
 * @param <T> The type of elements in the queue.
 */
public interface QueueInterface<T> {
    /**
     * Adds a new entry to the back of the queue.
     *
     * @param newEntry An object to be added.
     */
    void enqueue(T newEntry);

    /**
     * Removes and returns the entry at the front of the queue.
     *
     * @return The object at the front of the queue.
     * @throws IllegalStateException if the queue is empty before the operation.
     */
    T dequeue();

    /**
     * Retrieves the entry at the front of the queue.
     *
     * @return The object at the front of the queue.
     * @throws IllegalStateException if the queue is empty.
     */
    T getFront();

    /**
     * Checks whether the queue is empty.
     *
     * @return True if the queue is empty, false otherwise.
     */
    boolean isEmpty();

    /**
     * Removes all entries from the queue.
     */
    void clear();
}
