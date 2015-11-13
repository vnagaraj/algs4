package assign2.InterviewQuestions;

/**
 * Created by VGN on 11/12/15.
 * Implementing my version of stack using linklist
 */
public class LinkedListStack<Item> {

    Node first;
    int size;


    private class Node{
        Item item;
        Node next;

        Node(Item item){
            this.item = item;
        }
    }

    public void push(Item item){
        Node oldfirst = first;
        first = new Node(item);
        first.next = oldfirst;
        size += 1;
    }

    public Item pop(){
        if (size == 0){
            throw new RuntimeException("Cannot pop from empty stack");
        }
        Item item = first.item;
        first = first.next;
        size -= 1;
        return item;
    }

    public Item peek(){
        if (size == 0){
            throw new RuntimeException("Cannot peek from empty stack");
        }
        return first.item;
    }

    public boolean isEmpty(){
        return size ==0;
    }



}
