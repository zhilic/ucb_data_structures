public class LinkedListDeque<T> {

    private int size;
    private LinkedNode sentinel;

    private class LinkedNode {

        private T item;
        private LinkedNode previous;
        private LinkedNode next;

        public LinkedNode(T t, LinkedNode p, LinkedNode n) {
            item = t;
            previous = p;
            next = n;
        }

    }

    public LinkedListDeque() {
        sentinel = new LinkedNode(null, null, null);
        sentinel.previous = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }


    /* add and remove must not involve any looping or recursion.
       A single such operation must take "constant time". */
    public void addFirst(T item) {
        LinkedNode oldFirst = sentinel.next;
        LinkedNode newFirst = new LinkedNode(item, sentinel, oldFirst);
        sentinel.next = newFirst;
        oldFirst.previous = newFirst;
        size += 1;
    }

    public void addLast(T item) {
        LinkedNode oldLast = sentinel.previous;
        LinkedNode newLast = new LinkedNode(item, oldLast, sentinel);
        sentinel.previous = newLast;
        oldLast.next = newLast;
        size += 1;
    }

    public boolean isEmpty() {
        if (sentinel.next == sentinel) {
            return true;
        }
        return false;
    }

    /* size must take constant time. */
    public int size() {
        return this.size;
    }

    public void printDeque() {
        for (int index = 0; index < size; index++) {
            System.out.print(get(index).toString() + " ");
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        LinkedNode first = sentinel.next;
        sentinel.next = first.next;
        first.next.previous = sentinel;
        size -= 1;
        return first.item;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        LinkedNode last = sentinel.previous;
        sentinel.previous = last.previous;
        last.previous.next = sentinel;
        size -= 1;
        return last.item;
    }

    /* get must use iteration, not recursion */
    public T get(int index) {
        if (index >= size || size == 0) {
            return null;
        }
        LinkedNode curr = sentinel;
        for (int i = 0; i <= index; i++) {
            curr = curr.next;
        }
        return curr.item;
    }

    public T getRecursive(int index) {
        if (index >= size || size == 0) {
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }

    /* helper method for getRecursive */
    private T getRecursiveHelper(LinkedNode n, int index) {
        if (index == 0) {
            return n.item;
        }
        return getRecursiveHelper(n.next, index - 1);
    }

}
