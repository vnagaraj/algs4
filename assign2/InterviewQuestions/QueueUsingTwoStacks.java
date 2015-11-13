package assign2.InterviewQuestions;

/**
 * Created by VGN on 11/12/15.
 * Implement Queue using 2 stacks
 */
class QueueUsingTwoStacks {
    // Push element x to the back of queue.
    LinkedListStack<Integer> stack1 = new LinkedListStack<Integer>();
    LinkedListStack<Integer> stack2 = new LinkedListStack<Integer>();
    private int size ;

    public void push(int x) {
        stack1.push(x);
        size += 1;
    }

    // Removes the element from in front of queue.
    public void pop() {
        if (size == 0){
            throw new RuntimeException("Cannot pop from empty queue");
        }
        if (!stack2.isEmpty()){
           stack2.pop();
        }
        else{
            while (!stack1.isEmpty()){
                Integer val = stack1.pop();
                stack2.push(val);
            }
            stack2.pop();
        }
        size -= 1;

    }

    // Get the front element.
    public int peek() {
        if (!stack2.isEmpty()){
            return stack2.peek();
        }
        else{
            while (!stack1.isEmpty()){
                Integer val = stack1.pop();
                stack2.push(val);
            }
            return stack2.peek();
        }
    }

    // Return whether the queue is empty.
    public boolean empty() {
        return size == 0;
    }
}
