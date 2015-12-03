package assign4.InterviewQuestions;
import org.testng.annotations.Test;
import stdlib.StdRandom;
import algs4.RedBlackBST;

import static org.testng.Assert.assertTrue;


/**
 * Created by VGN on 12/2/15.
 * Class to test the leafy stack operations
 */
public class LeakyStackTest {
    @Test
    public void test1(){
        LeakyStackUsingPriorityQueue stack = new LeakyStackUsingPriorityQueue();
        stack.push("A"); // A [ add A ]
        stack.push("B"); // A B [ add B ]
        stack.push("C"); // A B C [ add C ]
        stack.push("D"); // A B C D [ add D ]
        stack.push("E"); // A B C D E [ add E ]
        assertTrue(stack.pop().equals("E"));
        stack.push("F"); // A B C D F [ add F ]
        System.out.println(stack.leak()); // A B C F [ choose D at random; delete D ]
        System.out.println(stack.leak()); // A C F [ choose B at random; delete B ]
    }

    @Test
    public void test2(){
        LeakyStackUsingRBTree stack = new LeakyStackUsingRBTree();
        stack.push("A"); // A [ add A ]
        stack.push("B"); // A B [ add B ]
        stack.push("C"); // A B C [ add C ]
        stack.push("D"); // A B C D [ add D ]
        stack.push("E"); // A B C D E [ add E ]
        assertTrue(stack.pop().equals("E"));
        stack.push("F"); // A B C D F [ add F ]
        System.out.println(stack.leak()); // A B C F [ choose D at random; delete D ]
        System.out.println(stack.leak()); // A C F [ choose B at random; delete B ]
    }
}

/*
A leaky stack is a generalization of a stack that supports adding a string; removing the
most-recently added string; and deleting a random string, as in the following API:
*/
class LeakyStackUsingPriorityQueue {
    /*using max priority queue*/
    PQ<Integer> maxPQ ;
    String[] value;
    int key =0;

    /*create an empty randomized stack*/
    LeakyStackUsingPriorityQueue() {
       maxPQ = new PQ<Integer>(true);
       value = new String[1];
    }
    /*
    push the string on the randomized stack
     */
    void push(String item) {
        maxPQ.insert(++key);
        if (maxPQ.getSize() == value.length){
            //resize by doubling value array
            resize(2 * value.length);
        }
        value[key] = item;
    }

    void resize(int length){
        String[] temp = new String[length];
        for (int i=0; i < value.length; i++){
            temp[i] = value[i];
        }
        value = temp;
    }
    /*remove and return the string most recently added*/
    String pop() {
        int key = maxPQ.deleteMaxMin();
        String item = value[key];
        value[key] = null;
        return item;
    }

    /*remove a string from the stack, uniformly at random*/
    String leak() {
        int random = StdRandom.uniform(1, maxPQ.getSize());
        int key = maxPQ.deleteAtIndex(random);
        String item = value[key] ;
        value[key] = null;
        return item;

    }
}

class LeakyStackUsingRBTree {
    /*using left leaning red black tree*/
    RedBlackBST<Integer, String> rb;
    int key =0;

    /*create an empty randomized stack*/
    LeakyStackUsingRBTree() {
        rb = new RedBlackBST<Integer, String>();
    }
    /*
    push the string on the randomized stack
     */
    void push(String item) {
        rb.put(++key, item);
    }

    /*remove and return the string most recently added*/
    String pop() {
        String item = rb.get(key);
        rb.delete(key);
        return item;
    }

    /*remove a string from the stack, uniformly at random*/
    String leak() {
        int random = StdRandom.uniform(1,rb.size());
        int key  = rb.select(random);
        String item = rb.get(key);
        rb.delete(key);
        return item;
    }
}
