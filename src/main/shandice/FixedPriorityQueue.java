package shandice;

import java.util.*;

/**
 * A {@code PriorityQueue} implementation that restricts the size of a set, keeping only the highest Objects.
 *
 * @param <E> Object that implements {@link Comparable}
 * @author Shandice Johnson 2016
 */
public class FixedPriorityQueue<E> extends PriorityQueue<E> {
    private static final long serialVersionUID = -6238173245275702863L;

    /**
     * The maximum size of the Map.
     */
    private final int maxSize;

    /**
     * Creates a map with a given maximum size
     *
     * @param maxSize the fixed size of the the map
     */
    public FixedPriorityQueue(int maxSize) {
        super();
        this.maxSize = maxSize;
    }

    /**
     * adds to the priority queue. Will remove the least value if maximum size has been reached
     * @param e {@inheritDoc}
     * @return {@inheritDoc}
     */
    public boolean add(E e) {
        remove(e);
        boolean added = super.add(e);

        if(size() > maxSize){
            this.remove(peek());
        }

        return added;
    }

    /**
     * adds to the priority queue. Will remove the least values if maximum size has been reached
     * @param c {@inheritDoc}
     * @return {@inheritDoc}
     */
    public  boolean addAll(Collection<? extends E> c) {
        boolean added = super.addAll(c);

        while(size() > maxSize){
            remove(peek());
        }

        return added;
    }
}
