package util;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Yangzhe Xie
 * @date 20/10/19
 */
public class ConcurrentStack<T> {
    private final AtomicReference<StackNode<T>> head = new AtomicReference<>();

    // /////////////////////////////////
    public void put(final T item) {
        final StackNode<T> newHead = new StackNode<>(item);
        while (true) {
            newHead.next = head.get();
            if (head.compareAndSet(newHead.next, newHead))
                return;
        }
    }

    /**
     * gets the first element and removes it from the data structure . will return null if empty
     */
    public T get() {
        StackNode<T> temp;
        while (true) {
            temp = head.get();
            if (temp == null)
                return null;
            if (head.compareAndSet(temp, temp.next))
                return temp.item;
        }
    }

    /**
     * returns the last item that was inserted into the bin , without removing it
     */
    public T peek() {
        final StackNode<T> temp = head.get();
        if (temp == null)
            return null;
        return temp.item;
    }

    public boolean isEmpty() {
        return head.get() == null;
    }

    public void clear() {
        head.set(null);
    }

    /**
     * warning:might not give actual result when other threads adding/removing elements!
     */
    public int size() {
        StackNode<T> temp = head.get();
        if (temp == null || temp.item == null)
            return 0;
        int result = 1;
        while ((temp = temp.next) != null)
            if (temp.item != null)
                ++result;
        return result;
    }

    // /////////////////////////////////
    // ////////////
    // StackNode //
    // ////////////
    private static class StackNode<SS> {
        StackNode<SS> next = null;
        SS item;

        public StackNode(final SS newItem) {
            item = newItem;
        }
    }
}
