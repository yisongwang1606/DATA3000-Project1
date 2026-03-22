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

        private Node(T data) {
            // We store the value inside the node so queue operations can access it later.
            this.data = data;
            // We initialize the next pointer to null because a new node is initially disconnected.
            this.next = null;
        }
    }

    private Node<T> frontNode;
    private Node<T> backNode;

    public QueueImplementation() {
        // We initialize the front pointer to null because the queue starts empty.
        this.frontNode = null;
        // We initialize the back pointer to null because the queue starts empty.
        this.backNode = null;
    }

    @Override
    public void enqueue(T newEntry) {
        // We create a new node so the incoming element can be linked into the queue.
        Node<T> newNode = new Node<>(newEntry);
        // We check whether the queue is empty so we can initialize both pointers correctly.
        if (isEmpty()) {
            // We point the front to the new node because it is the first element in the queue.
            frontNode = newNode;
        } else {
            // We link the current back node to the new node so the list remains connected.
            backNode.next = newNode;
        }
        // We move the back pointer to the new node because it is now the last element.
        backNode = newNode;
    }

    @Override
    public T dequeue() {
        // We prevent invalid removal by checking whether the queue has no elements.
        if (isEmpty()) {
            // We throw an exception so callers know dequeue cannot run on an empty queue.
            throw new IllegalStateException("Cannot dequeue from an empty queue.");
        }
        // We read the front value so we can return the removed element.
        T frontData = frontNode.data;
        // We move the front pointer forward to remove the first node logically.
        frontNode = frontNode.next;
        // We check whether the queue became empty after removing the front node.
        if (frontNode == null) {
            // We reset the back pointer to null so both pointers consistently represent an empty queue.
            backNode = null;
        }
        // We return the removed element to complete the dequeue contract.
        return frontData;
    }

    @Override
    public T getFront() {
        // We prevent invalid access by checking whether there is no front element.
        if (isEmpty()) {
            // We throw an exception so callers know front access is invalid on an empty queue.
            throw new IllegalStateException("Cannot get front from an empty queue.");
        }
        // We return the front data because this method should not remove elements.
        return frontNode.data;
    }

    @Override
    public boolean isEmpty() {
        // We report empty status by checking whether the front pointer is null.
        return frontNode == null;
    }

    @Override
    public void clear() {
        // We drop the front pointer so all nodes become unreachable and the queue is cleared.
        frontNode = null;
        // We drop the back pointer so queue state remains consistent after clearing.
        backNode = null;
    }
}
