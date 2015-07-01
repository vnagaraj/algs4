package assign2;
import java.util.Iterator;
import stdlib.StdRandom;


/**
 * Created by VGN on 6/30/15.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] q;            // queue elements
    private int N = 0;           // number of elements on queue
    private int first = 0;       // index of first element of queue
    private int last  = 0;       // index of next available slot

    /*
    Construct an empty randomized queue
     */
    public RandomizedQueue(){
        // cast needed since no generic array creation in Java
        q = (Item[]) new Object[2];
    }

    /*
    Is the queue empty
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /*
    Return the number of items on the queue
     */
    public int size(){
        return N;
    }

    /*
    Add the item
     */
    public void enqueue(Item item) {
        if (item == null){
            throw new NullPointerException("Adding null item to randomized queue");
        }
        if (N == q.length) resize(2*q.length);
        q[N] = item;
        N ++;
    }

    // resize the underlying array
    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = q[i];
        }
        q = temp;
    }
    /*
    Remove and return a random item
     */
    public Item dequeue() {
        if (N == 0){
            throw new java.util.NoSuchElementException("NO element in queue");
        }
        int random = StdRandom.uniform(N);
        Item item = q[random];
        q[random] = q[N-1];
        q[N-1] = null;
        N--;
        // shrink size of array if necessary
        if (N > 0 && N == q.length/4) resize(q.length/2);
        return item;
    }

    /*
    Return(but do not remove) a random item
     */
    public Item sample() {
        if (N == 0){
            throw new java.util.NoSuchElementException("NO element in queue");
        }
        int random = StdRandom.uniform(N);
        Item item = q[random];
        return item;
    }
    /*
    Return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
       return new RandomArrayIterator();
    }

    private class RandomArrayIterator implements Iterator<Item> {
        Item[] itr;
        int index = 0;
        RandomArrayIterator(){
            itr = (Item[])new Object[N];
            for(int i=0; i < N; i++){
                itr[i] = q[i];
            }
            StdRandom.shuffle(itr);
        }
        @Override
        public boolean hasNext() {
            if (N ==0)
                return false;
            if (index >=0 && index < N)
                return true;
            return false;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Item item = itr[index++];
            return item;
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    /*
        Unit testing
       */
    public static void main(String[] args)  {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<Integer>();
        randomizedQueue.enqueue(5);
        randomizedQueue.enqueue(15);
        randomizedQueue.enqueue(7);
        randomizedQueue.enqueue(58);
        System.out.println("Dequeu" + randomizedQueue.dequeue());
        for (Integer k: randomizedQueue){
            System.out.println(k);
        }
        for (Integer i : randomizedQueue){
            System.out.println(i);
        }
    }
}
