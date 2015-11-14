package assign2.InterviewQuestions;

/**
 * Created by VGN on 11/13/15.
 */
public interface Stack<Item> {
    // Push element x onto stack.
    public void push(int x);

    // Removes the element on top of the stack.
    public void pop() ;

    // Get the top element.
    public int top() ;

    // Return whether the stack is empty.
    public boolean empty() ;
}
