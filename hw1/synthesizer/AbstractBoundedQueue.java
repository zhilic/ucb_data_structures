/**
 * @author zhilic created on 8/15/19
 */

package synthesizer;

public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {

    protected int fillCount;
    protected int capacity;

    public int capacity() {
        return capacity;
    }

    public int fillCount() {
        return fillCount;
    }

    /**
     * The following methods don't have to be written here,
     * since they are the same as those in the BoundedQueue Interface.
     */
//    @Override
//    public boolean isEmpty() {
//        return fillCount() == 0;
//    }
//
//    @Override
//    public boolean isFull() {
//        return fillCount() == capacity();
//    }
//
//    public abstract T peek();
//    public abstract T dequeue();
//    public abstract void enqueue(T x);

}
