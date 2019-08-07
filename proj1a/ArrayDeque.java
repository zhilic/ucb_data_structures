public class ArrayDeque<T> {

    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        /* The starting size of your array should be 8. */
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 3;
        nextLast = 4;
    }

    /* AList Invariants:
     * 1. The position of the next item to be inserted is always size.
     * 2. Size is always the number of items in the AList.
     * 3. The last item in the list is always in position size - 1.
     */

    private void resize(int cap) {
        T[] tmp = (T[]) new Object[cap];
        /* start filling the new array from index 0 */
        /*
         * When resize is called, the original array must be full,
         * which means nextFirst = nextLast - 1 (/w circle)
         * or nextFirst = items.length - 1 && nextLast = 0 (/wo circle)
         */
        if (nextLast == 0 && nextFirst == items.length - 1) {
            System.arraycopy(items, 0, tmp, 0, size);
        } else {
            /* nextFirst = nextLast - 1 ==> circular array
            *  first copy from nextFirst+1 to items.length-1  ==> partial size: size-nextLast
            *  then copy from 0 to nextLast-1 ==> partial size: nextLast
            *  Example: [4,5,null,null,null,1,2,3]
            *  size = 5, nextFirst = 4, nextLast = 2
            *  size of first half = 3 (size - nextLast)
            *  size of second half = 2 (nextLast)
            */
            System.arraycopy(items, nextFirst + 1, tmp, 0, size - nextLast);
            System.arraycopy(items, 0, tmp, size - nextLast, nextLast);
        }
        nextFirst = tmp.length;
        nextLast = size;
        items = tmp;
    }

    /* add and remove must take constant time, except during resizing operations */
    public void addFirst(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[nextFirst] = item;
        if (nextFirst == 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst -= 1;
        }
        size += 1;
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[nextLast] = item;
        if (nextLast == items.length - 1) {
            nextLast = 0;
        } else {
            nextLast += 1;
        }
        size += 1;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /* get and size must take constant time */
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
        } else if (nextFirst == items.length - 1) {
            T first = items[0];
            nextFirst = 0;
            size -= 1;
            return first;
        } else {
            T first = items[nextFirst + 1];
            nextFirst += 1;
            size -= 1;
            return first;
        }
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        } else if (nextLast == 0) {
            T last = items[items.length - 1];
            nextLast = items.length - 1;
            size -= 1;
            return last;
        } else {
            T last = items[nextLast - 1];
            nextLast -= 1;
            size -= 1;
            return last;
        }
    }

    /* get and size must take constant time */
    public T get(int index) {
        if (index >= size || size == 0) {
            return null;
        } else if (nextFirst == items.length) {
            /* the beginning of the deque is at items index 0 */
            return items[index];
        } else if (index < items.length - nextFirst - 1) {
            /* index is located between [nextFirst + 1, items.length - 1]
            *  i.e. nextFirst = 1, items.length = 8 ==>
            *  the first 6 items of the deque are listed in the latter part of the array
            */
            return items[nextFirst + 1 + index];
        } else {
            /* size of first part (tmp) = items.length - nextFirst - 1
            * index of first part: [0, tmp - 1]  */
            return items[index - (items.length - nextFirst - 1)];
        }
    }

}
