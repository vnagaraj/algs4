package assign2.InterviewQuestions;

/**
 * Created by VGN on 11/12/15.
 * my version of queue using linkedlist
 */
public class LinkedListQueue<Item> implements Queue<Item> {
    Node first;
    Node last;
    int size;

    private class Node {
        Item item;
        Node next;
        Node(Item item){
            this.item = item;
        }
    }

    public void enqueue(Item item){
        //insert item at end of list
        if (size == 0){
            first = new Node(item);
            last = first;
        }
        else{
            Node oldlast = last;
            last = new Node(item);
            last.next = null;
            oldlast.next = last;
        }
        size += 1;
    }

    public Item dequeue(){
        //remove item from front of list
        if (size == 0){
            throw new RuntimeException("Cannot dequeue from empty queue");
        }
        Item item = first.item;
        first = first.next;
        size -= 1;
        return item;
    }

    public Item peek(){
        if (size == 0){
            throw new RuntimeException("Cannot dequeue from empty queue");
        }
        return first.item;
    }

    public boolean isEmpty(){
        return size ==0;
    }

    public int size(){
        return size;
    }
}
