package assign2.InterviewQuestions;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Created by VGN on 11/12/15.
 * Implementing stack using 2 queues
 */
class StackUsingQueues<Item> implements Stack<Item> {
    Queue<Integer> queue1 = new ArrayQueue<Integer>();
    Queue<Integer> queue2 = new ArrayQueue<Integer>();
    int size;

    // Push element x onto stack.
    public void push(int x) {
       if (queue2.isEmpty()){
           queue1.enqueue(x);
       }
       else{
           queue2.enqueue(x);
       }
       size += 1;
    }

    // Removes the element on top of the stack.
    public void pop() {
        if (size ==0){
            throw new RuntimeException("Cannot remove from empty stack");
        }
        size -=1;
        while (!queue1.isEmpty()){
           Integer val = queue1.dequeue();
           if (queue1.size() != 0) {
               queue2.enqueue(val);
           }
            else{
               return;
           }
        }

        while (!queue2.isEmpty()){
            Integer val = queue2.dequeue();
            if (queue2.size() != 0) {
                queue1.enqueue(val);
            }
            else {
                return;
            }
        }
    }

    // Get the top element.
    public int top() {
        int val = -1;
        if (size ==0){
            throw new RuntimeException("Cannot get top from empty stack");
        }
        while (!queue1.isEmpty()){
            val = queue1.dequeue();
            queue2.enqueue(val);
        }
        while (!queue2.isEmpty()){
            val = queue2.dequeue();
            queue1.enqueue(val);
        }
        return val;
    }

    // Return whether the stack is empty.
    public boolean empty() {
        return size ==0;
    }

}

public class MyStackTest{

    @Test
    public void test1(){
        Stack<Integer> myStack = new StackUsingQueues<Integer> ();
        myStack.push(1);
        myStack.push(2);
        assertTrue(myStack.top()== 2);
    }


    @Test
    public void test2(){
        Stack<Integer> stack = new StackUsingQueues<Integer> ();
        stack.push(1);
        stack.push(2);
        stack.pop();
        assertTrue(stack.top() == 1);
    }

}
