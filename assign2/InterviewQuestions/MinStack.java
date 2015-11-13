package assign2.InterviewQuestions;

/**
 * Created by VGN on 11/12/15.
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
 */
public class MinStack {
    LinkedListStack<Integer> pushStack = new LinkedListStack<Integer>();
    LinkedListStack<Integer> minStack = new LinkedListStack<Integer>();
    private int size ;

    public void push(int x) {
        pushStack.push(x);
        if (minStack.isEmpty()){
            minStack.push(x);
        }
        else if (x <= minStack.peek()){
            minStack.push(x);
        }
        size += 1;
    }

    public void pop() {
        Integer val = pushStack.pop();
        if (val.equals(minStack.peek())){
            minStack.pop();
        }
    }

    public int top() {
        return pushStack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
