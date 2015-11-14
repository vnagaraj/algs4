package assign2.InterviewQuestions;

/**
 * Created by VGN on 11/12/15.
 * Implement Queue using 2 stacks
 */
class QueueUsingTwoStacks<Item> implements Queue<Item> {
    // Push element x to the back of queue.
    LinkedListStack<Item> stack1 = new LinkedListStack<Item>();
    LinkedListStack<Item> stack2 = new LinkedListStack<Item>();
    private int size ;

    public void enqueue(Item x) {
        stack1.push(x);
        size += 1;
    }

    // Removes the element from in front of queue.
    public Item dequeue() {
        Item item = null;
        if (size == 0){
            throw new RuntimeException("Cannot pop from empty queue");
        }
        if (!stack2.isEmpty()){
           item = stack2.pop();
        }
        else{
            while (!stack1.isEmpty()){
                Item val = stack1.pop();
                stack2.push(val);
            }
            stack2.pop();
        }
        size -= 1;
        return item;

    }

    // Get the front element.
    public Item peek() {
        if (!stack2.isEmpty()){
            return stack2.peek();
        }
        else{
            while (!stack1.isEmpty()){
                Item val = stack1.pop();
                stack2.push(val);
            }
            return stack2.peek();
        }
    }

    // Return whether the queue is empty.
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }
}
