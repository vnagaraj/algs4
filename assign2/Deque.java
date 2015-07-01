package assign2;
import java.util.Iterator;
import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * Created by VGN on 6/29/15.
 */
public class Deque<Item> implements Iterable<Item> {
    private int N; // no of items in the collection
    private Node first;
    private Node last;

    /*
    Helper linkedlist class
     */
    private class Node {
        private Item item;
        private Node next;
        private Node back;
    }

    /*
     Construct an empty deque
     */
    public Deque()
    {
      first = null;
      last = null;
       N = 0;
    }

    /*
     Is the deque empty
     */
    public boolean isEmpty()
    {
        return first == null;
    }

    /*
     Return the number of items on the deque
     */
    public int size(){
        return N;
    }

    /*
     Add the item to the front
     */
    public void addFirst(Item item)
    {
        if (item == null){
            throw new java.lang.NullPointerException("Cant add null item to dequeue");
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        if (oldfirst != null) {
            oldfirst.back = first;
        }
        first.back = null;
        N++;
        if (last == null) {
            last = first;
        }
    }

    /*
     Add the item to the end
     */
    public void addLast(Item item)  {
        if (item == null){
            throw new java.lang.NullPointerException("Cant add null item to dequeue");
        }
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.back = oldlast;
        if (oldlast != null){
            oldlast.next = last;
        }
        if (first == null){
            first = last;
        }
        N++;
    }

    /*
     Remove and return the item from the front
     */
    public Item removeFirst() {
        if (N ==0){
            throw new java.util.NoSuchElementException ("No item present in dequeue");
        }
        Item item = first.item;
        first = first.next;
        if (first != null) {
            first.back = null;
        }
        if (N == 1){
            last = first;
        }
        N--;
        return item;
    }

    /*
     Remove and return the item from the end
     */
    public Item removeLast() {
        if (N ==0){
            throw new java.util.NoSuchElementException ("No item present in dequeue");
        }
        Item item = last.item;
        last = last.back;
        if (last != null) {
            last.next = null;
        }
        if (N ==1){
            first = last;
        }
        N--;
        return item;
    }

    /*
     Return an iterator over items in order from front to end
     */
    public Iterator<Item> iterator()  {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new java.lang.UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    //unit testing
    public static void main(String[] args)  {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(4);
        deque.addFirst(7);
        deque.addLast(8);
        deque.removeFirst();
        deque.addFirst(15);
        deque.removeLast();
        Assert.assertTrue(deque.size()==2);
        Assert.assertTrue(deque.first.item==15);
        Assert.assertTrue(deque.last.item ==4);
        for (Integer a : deque){
            System.out.println(a);
        }
    }

    @Test
    public void test(){
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(0);
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addFirst(4);
        deque.addFirst(5);
        deque.removeLast();
        deque.addFirst(7);
        deque.addFirst(8);
        deque.addFirst(9);
        deque.addFirst(10);
        Assert.assertTrue(deque.removeLast() == 1);

    }

    /*
    @Test
    public void test1(){
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(5);
        deque.addLast(6);
        Assert.assertTrue(deque.size()==2);
        Assert.assertTrue(deque.first.item ==5);
        Assert.assertTrue(deque.last.item == 6);
    }

    @Test
    public void test2(){
        Deque<Integer> deque = new Deque<Integer>();
        deque.addLast(6);
        deque.addFirst(5);
        Assert.assertTrue(deque.size()==2);
        Assert.assertTrue(deque.first.item ==5);
        Assert.assertTrue(deque.last.item == 6);
    }

    @Test
    public void test3(){
        Deque<Integer> deque = new Deque<Integer>();
        deque.addLast(6);
        deque.removeFirst();
        Assert.assertTrue(deque.size()==0);
        Assert.assertEquals(deque.first,null);
        Assert.assertEquals(deque.last, null);
    }

    @Test
    public void test4(){
        Deque<Integer> deque = new Deque<Integer>();
        deque.addLast(6);
        deque.addLast(7);
        Assert.assertTrue(deque.size()==2);
        Assert.assertTrue(deque.first.item == 6);
        Assert.assertTrue(deque.last.item == 7);
        deque.removeFirst();
        Assert.assertTrue(deque.size()==1);
        Assert.assertTrue(deque.first.item == 7);
        Assert.assertTrue(deque.last.item == 7);
    }

    @Test
    public void test5(){
        Deque<Integer> deque = new Deque<Integer>();
        deque.addLast(6);
        deque.addFirst(8);
        deque.addLast(7);
        Assert.assertTrue(deque.size()==3);
        Assert.assertTrue(deque.first.item == 8);
        Assert.assertTrue(deque.last.item  == 7);
        deque.removeLast();
        Assert.assertTrue(deque.size()==2);
        Assert.assertTrue(deque.first.item == 8);
        Assert.assertTrue(deque.last.item == 6);
        deque.removeFirst();
        Assert.assertTrue(deque.size()==1);
        Assert.assertTrue(deque.first.item == 6);
        Assert.assertTrue(deque.last.item == 6);
        deque.removeLast();
        Assert.assertTrue(deque.size()==0);
        Assert.assertTrue(deque.first == null);
        Assert.assertTrue(deque.last == null);
    }

    @Test
    public void test6(){
      Deque<Integer> deque = new Deque<Integer>();
      deque.addFirst(4);
      deque.addFirst(7);
      deque.addLast(8);
      deque.removeFirst();
      deque.addFirst(15);
      deque.removeLast();
      Assert.assertTrue(deque.size()==2);
      Assert.assertTrue(deque.first.item==15);
      Assert.assertTrue(deque.last.item ==4);
      for (Integer a : deque){
          System.out.println(a);
      }
    }
    */

}
