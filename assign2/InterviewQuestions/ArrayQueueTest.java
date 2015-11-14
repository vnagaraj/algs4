package assign2.InterviewQuestions;

import assign1.InterviewQuestions.WeightedQuickUnionCompressionBySize;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by VGN on 11/13/15.
 * my version of queue using arrays
 */
class ArrayQueue<Item> implements Queue<Item> {

    private Item[] array;
    int size;
    int start_index; // to keep track for dequeue
    int end_endex; // to keep track for enqueue

    ArrayQueue(){
        array = (Item[])new Object[1];
    }

    public void enqueue(Item item){
        if (end_endex == array.length){
            resize(end_endex*2);
        }
        array[end_endex] = item;
        size ++;
        end_endex++;
    }

    private void resize(int length){
        Item[] temp =  (Item[])new Object[length];
        int index = 0;
        int counter = 0;
        for (int i=0; i< array.length ; i++){
           if( array[i] != null) {
               temp[counter++] = array[i];
           }
        }
        start_index =0;
        end_endex = counter;
        array = temp;
    }

    public Item dequeue(){
        //remove item from front of list
        if (size == 0){
            throw new RuntimeException("Cannot dequeue from empty queue");
        }
        Item item = array[start_index];
        array[start_index] = null; // to avoid loitering
        start_index+=1;
        size --;
        return item;
    }

    public Item peek(){
        if (size == 0){
            throw new RuntimeException("Cannot dequeue from empty queue");
        }
        return array[start_index];
    }

    public boolean isEmpty(){
        return size ==0;
    }

    public int size(){
        return size;
    }
}

public class ArrayQueueTest{
    @Test
    public void test1(){
        Queue<Integer> queue = new ArrayQueue<Integer>();
        queue.enqueue(4);
        queue.enqueue(5);
        assertTrue(queue.peek() == 4);
    }

    @Test
    public void test2(){
        Queue<Integer> queue = new ArrayQueue<Integer>();
        queue.enqueue(4);
        queue.enqueue(5);
        queue.dequeue();
        queue.enqueue(8);
        assertTrue(queue.peek() == 5);
    }
}




