package assign2.InterviewQuestions;

/**
 * Created by VGN on 11/12/15.
 * Implementing stack using 2 queues
 */
class MyStack {
    LinkedListQueue<Integer> queue1 = new LinkedListQueue<Integer>();
    LinkedListQueue<Integer> queue2 = new LinkedListQueue<Integer>();
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
           if (queue1.size != 0) {
               queue2.enqueue(val);
           }
            else{
               return;
           }
        }

        while (!queue2.isEmpty()){
            Integer val = queue2.dequeue();
            if (queue2.size != 0) {
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

    public static void main(String[] args){
        MyStack stack = new MyStack();
        stack.push(1);
        stack.push(2);
        stack.pop();
        System.out.println(stack.top());
    }
}
